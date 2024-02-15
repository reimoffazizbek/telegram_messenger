package uz.pdp.messenger.back.service.impl;

import uz.pdp.messenger.back.modul.User;
import uz.pdp.messenger.back.modul.handler.Handler;
import uz.pdp.messenger.back.modul.handler.Message;
import uz.pdp.messenger.back.modul.handler.MessageHandler;
import uz.pdp.messenger.back.modul.handler.chat.Chat;
import uz.pdp.messenger.back.modul.handler.chat.ChatUserInformation;
import uz.pdp.messenger.back.modul.handler.group.Group;
import uz.pdp.messenger.back.modul.handler.group.GroupUserInformation;
import uz.pdp.messenger.back.payload.*;
import uz.pdp.messenger.back.repository.AuthRepository;
import uz.pdp.messenger.back.repository.HandlerRepository;
import uz.pdp.messenger.back.repository.MessageRepository;
import uz.pdp.messenger.back.repository.impl.AuthRepositoryImpl;
import uz.pdp.messenger.back.repository.impl.HandlerRepositoryImpl;
import uz.pdp.messenger.back.repository.impl.MessageRepositoryImpl;
import uz.pdp.messenger.back.service.HandlerService;
import uz.pdp.messenger.back.service.MessageService;

import static uz.pdp.messenger.back.utils.TelegramMessages.*;

import java.util.*;

public class HandlerServiceImpl implements HandlerService {
    private static final HandlerService instance = new HandlerServiceImpl();
    private static final HandlerRepository handlerRepository = HandlerRepositoryImpl.getInstance();
    private static final AuthRepository authRepository = AuthRepositoryImpl.getInstance();
    private static final MessageRepository messageRepository = MessageRepositoryImpl.getInstance();
    private HandlerServiceImpl() {}
    public static HandlerService getInstance() {
        return instance;
    }
    @Override
    public List<HandlerDTO> findAllMyHandlerByUserId(UUID id) {
        List<Handler> findAllMyHandlers = handlerRepository.findAllMyHandlerByUserId(id);
        Collections.sort(findAllMyHandlers);
        List<HandlerDTO> resultHandlerDTOS = new ArrayList<>();
        List<ChatUserInformationDTO> chatUserInformationDTOS = new ArrayList<>();
        List<GroupUserInformationDTO> groupUserInformationDTOS = new ArrayList<>();
        for (Handler findAllMyHandler : findAllMyHandlers) {
            if(findAllMyHandler instanceof Chat chat){
                for (ChatUserInformation userInformation : chat.getUserInformations()) {
                    chatUserInformationDTOS.add(new ChatUserInformationDTO(userInformation, id));
                }
                resultHandlerDTOS.add(new ChatDTO(chat.getID(), chatUserInformationDTOS));
                chatUserInformationDTOS = new ArrayList<>();
            } else if(findAllMyHandler instanceof Group group){
                for (GroupUserInformation user : group.getUsers()) {
                    groupUserInformationDTOS.add(new GroupUserInformationDTO(user, id));
                }
                resultHandlerDTOS.add(new GroupDTO(group.getID(), groupUserInformationDTOS, group.getGroupName(), group.getLogo(), group.getBio()));
                groupUserInformationDTOS = new ArrayList<>();
            }
        }
        return resultHandlerDTOS;
    }

    @Override
    public ChatDTO findChatByUserId(UUID firstUserId, UUID secondUserId) {
        Chat resultChat = handlerRepository.findChatByUserId(firstUserId, secondUserId);
        if(resultChat==null)
            return null;
        List<ChatUserInformationDTO> chatUserInformationDTOS = new ArrayList<>();
        for (ChatUserInformation userInformation : resultChat.getUserInformations()) {
            chatUserInformationDTOS.add(new ChatUserInformationDTO(userInformation, firstUserId));
        }
        return new ChatDTO(resultChat.getID(), chatUserInformationDTOS);
    }

    @Override
    public ChatDTO createChat(UUID firstUserId, UUID secondUserId) {
        User firstUser = authRepository.findUserByUserId(firstUserId);
        User secondUser = authRepository.findUserByUserId(secondUserId);
        Chat newChat = new Chat(firstUser, secondUser);
        handlerRepository.save(newChat);
        List<ChatUserInformationDTO> chatUserInformationDTOS = new ArrayList<>(2);
//        System.out.println(newChat.getUserInformations());
        for (ChatUserInformation userInformation : newChat.getUserInformations()) {
            chatUserInformationDTOS.add(new ChatUserInformationDTO(userInformation, firstUserId));
        }
        return new ChatDTO(newChat.getID(), chatUserInformationDTOS);
    }

    @Override
    public boolean deleteChat(UUID chatId) {
        return handlerRepository.delete(chatId);
    }

    @Override
    public GroupDTO createGroup(CreateGroupDTO dto) {
        User adminUser = authRepository.findUserByUserId(dto.admin().id());
        List<User> users = new ArrayList<>();
        for (UserDTO userDTO : dto.userDTOS()) {
            if(!users.contains(authRepository.findUserByUserId(userDTO.id())))
                users.add(authRepository.findUserByUserId(userDTO.id()));
        }
        Group newGroup = new Group(dto.groupFullName(), adminUser, users);
        handlerRepository.save(newGroup);
        ArrayList<GroupUserInformationDTO> groupUserInformationDTOS = new ArrayList<>();
        for (GroupUserInformation user : newGroup.getUsers()) {
            groupUserInformationDTOS.add(new GroupUserInformationDTO(user, dto.admin().id()));
        }
        return new GroupDTO(newGroup.getID(), groupUserInformationDTOS, newGroup.getGroupName(), newGroup.getLogo(), newGroup.getBio());
    }

    @Override
    public GroupDTO addUserToGroup(GroupDTO currentGroup, List<UserDTO> newUsers, UUID currentUserId) {
        Group group = handlerRepository.findGroupByGroupId(currentGroup.id());
        for (UserDTO newUser : newUsers) {
            GroupUserInformation groupUserInformation = new GroupUserInformation(authRepository.findUserByUserId(newUser.id()));
            if(!group.getUsers().contains(groupUserInformation))
                group.getUsers().add(groupUserInformation);
        }
        List<GroupUserInformationDTO> users = new ArrayList<>();
        for (GroupUserInformation user : group.getUsers()) {
            users.add(new GroupUserInformationDTO(user, currentUserId));
        }
        return new GroupDTO(group.getID(), users, group.getGroupName(), group.getLogo(), group.getBio());
    }

    @Override
    public GroupDTO editGroupName(GroupDTO currentGroup, String newName, UUID currentUserId) {
        Group group = handlerRepository.findGroupByGroupId(currentGroup.id());
        group.setGroupName(newName);
        List<GroupUserInformationDTO> groupUserInformations = new ArrayList<>();
        for (GroupUserInformation user : group.getUsers()) {
            groupUserInformations.add(new GroupUserInformationDTO(user, currentUserId));
        }
        return new GroupDTO(group.getID(), groupUserInformations, group.getGroupName(), group.getLogo(), group.getBio());
    }

    @Override
    public GroupDTO deleteUserFromGroup(List<GroupUserInformationDTO> deleteUser, GroupDTO currentGroup, UserDTO currentUser) {
        Group group = handlerRepository.findGroupByGroupId(currentGroup.id());
        for (GroupUserInformationDTO groupUserInformationDTO : deleteUser) {
            for (GroupUserInformation user : group.getUsers()) {
                if(user.getUser().getId().equals(groupUserInformationDTO.user().id())){
                    group.getUsers().remove(user);
                    break;
                }
            }
        }
        List<GroupUserInformationDTO> groupUserInformations = new ArrayList<>();
        for (GroupUserInformation user : group.getUsers()) {
            groupUserInformations.add(new GroupUserInformationDTO(user, currentUser.id()));
        }
        return new GroupDTO(group.getID(), groupUserInformations, group.getGroupName(), group.getLogo(), group.getBio());
    }

    @Override
    public GroupDTO editOrCreateGroupBio(GroupDTO currentGroup, String newBio, UserDTO currentUser) {
        Group group = handlerRepository.findGroupByGroupId(currentGroup.id());
        group.setBio(newBio);
        List<GroupUserInformationDTO> groupUserInformations = new ArrayList<>();
        for (GroupUserInformation user : group.getUsers()) {
            groupUserInformations.add(new GroupUserInformationDTO(user, currentUser.id()));
        }
        return new GroupDTO(group.getID(), groupUserInformations, group.getGroupName(), group.getLogo(), group.getBio());
    }

}
