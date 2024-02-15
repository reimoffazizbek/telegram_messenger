package uz.pdp.messenger.back.service.impl;

import uz.pdp.messenger.back.exception.NotFoundException;
import uz.pdp.messenger.back.modul.User;
import uz.pdp.messenger.back.modul.handler.*;
import uz.pdp.messenger.back.modul.handler.chat.Chat;
import uz.pdp.messenger.back.modul.handler.chat.ChatMessage;
import uz.pdp.messenger.back.modul.handler.chat.ChatUserInformation;
import uz.pdp.messenger.back.modul.handler.group.Group;
import uz.pdp.messenger.back.modul.handler.group.GroupMessage;
import uz.pdp.messenger.back.modul.handler.group.GroupUserInformation;
import uz.pdp.messenger.back.payload.*;
import uz.pdp.messenger.back.repository.AuthRepository;
import uz.pdp.messenger.back.repository.HandlerRepository;
import uz.pdp.messenger.back.repository.MessageRepository;
import uz.pdp.messenger.back.repository.impl.AuthRepositoryImpl;
import uz.pdp.messenger.back.repository.impl.HandlerRepositoryImpl;
import uz.pdp.messenger.back.repository.impl.MessageRepositoryImpl;
import uz.pdp.messenger.back.service.MessageService;

import java.util.*;

public class MessageServiceImpl implements MessageService {
    private static final MessageService instance = new MessageServiceImpl();
    private static final MessageRepository messageRepository = MessageRepositoryImpl.getInstance();
    private static final HandlerRepository handlerRepository = HandlerRepositoryImpl.getInstance();
    private static final AuthRepository authRepository = AuthRepositoryImpl.getInstance();
    private MessageServiceImpl() {}
    public static MessageService getInstance() {
        return instance;
    }

    @Override
    public List<MessageDTO> findAllMessagesInTheChat(UUID chatId, UUID userId) {
        Set<Message> messages = messageRepository.findAllMessagesInTheChat(chatId);
        List<MessageDTO> result = new ArrayList<>();
        List<MessageLikeDTO> likes;
        for (Message message : messages) {
            if(message instanceof ChatMessage chatMessage) {
                likes = new ArrayList<>();
                for (MessageLike like : chatMessage.getLikes()) {
                    likes.add(new MessageLikeDTO(like.getLike(), like.getUsers()));
                }
                result.add(new ChatMessageDTO(chatMessage, likes, userId));
            } else {
                result.add(new TelegramMessageDTO((TelegramMessage) message));
            }
        }
        return result;
    }

    @Override
    public ChatMessageDTO sendChatMessage(String message, UserDTO currentUser, ChatDTO currentChat) {
        User user = authRepository.findUserByUserId(currentUser.id());
        if(user==null) throw new NotFoundException("Bunday user mavjud emas❗");
        Chat chat = handlerRepository.findChatByChatId(currentChat.id());
        ChatMessage newMessage = new ChatMessage(chat, user, message);
        chat.setLastMessageTime(newMessage.getTime());
        messageRepository.save(newMessage);
        for (ChatUserInformation userInformation : chat.getUserInformations()) {
            if(!userInformation.getUser().getId().equals(currentUser.id())){
                userInformation.setNewSms(userInformation.getNewSms()+1);
            }
        }
        List<MessageLikeDTO> likes = new ArrayList<>();
        return new ChatMessageDTO(
                newMessage.getId(),
                newMessage.getHandler().getID(),
                newMessage.getMessage(currentUser.id()),
                newMessage.getTime(),
                likes,
                currentUser,
                false);
    }

    @Override
    public boolean deleteChatMessage(int messageId, UserDTO currentUser) {
        Message deleteMessage = messageRepository.deleteChatMessage(messageId, currentUser.id());
        if(deleteMessage == null) return false;
        if(deleteMessage instanceof MessageHandler message){
            if(message.getHandler() instanceof Chat chat){
                for (ChatUserInformation userInformation : chat.getUserInformations()) {
                    if(!userInformation.getUser().getId().equals(currentUser.id())){
                        userInformation.setNewSms(userInformation.getNewSms()!=0 ? userInformation.getNewSms()-1 : userInformation.getNewSms());
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean editMessage(String newMessage, int messageId, UUID currentUserId) {
        Message message = messageRepository.findMessageByMessageId(messageId);
        if(message==null) return false;
        if(message instanceof ChatMessage chatMessage){
            if(chatMessage.getSenderUser().getId().equals(currentUserId)) {
                chatMessage.setMessage(newMessage);
                return true;
            }
        } else if(message instanceof GroupMessage groupMessage){
            if(groupMessage.getSenderUser().getId().equals(currentUserId)) {
                groupMessage.setMessage(newMessage);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<MessageDTO> findAllMessagesInTheGroup(UUID groupId, UUID userId) {
        Set<Message> messages = messageRepository.findAllMessagesInTheGroup(groupId);
        ArrayList<MessageDTO> result = new ArrayList<>();
        ArrayList<MessageLikeDTO> likes = new ArrayList<>();
        for (Message message : messages) {
            if(message instanceof GroupMessage groupMessage){
                for (MessageLike like : groupMessage.getLikes()) {
                    likes.add(new MessageLikeDTO(like.getLike(), like.getUsers()));
                }
                result.add(new GroupMessageDTO(groupMessage, likes, userId));
                likes = new ArrayList<>();
            }
        }
        return result;
    }

    @Override
    public GroupMessageDTO sendGroupMessage(String message, UserDTO currentUser, GroupDTO currentGroup) {
        User user = authRepository.findUserByUserId(currentUser.id());
        if(user==null) throw new NotFoundException("Bunday user mavjud emas❗");
        Group group = handlerRepository.findGroupByGroupId(currentGroup.id());
        GroupMessage newMessage = new GroupMessage(group, user, message);
        group.setLastMessageTime(newMessage.getTime());
        messageRepository.save(newMessage);
        for (GroupUserInformation userInformation : group.getUsers()) {
            if(!userInformation.getUser().getId().equals(currentUser.id())){
                userInformation.setNewSms(userInformation.getNewSms()+1);
            }
        }
        List<MessageLikeDTO> likes = new ArrayList<>();
        return new GroupMessageDTO(
                newMessage.getId(),
                newMessage.getHandler().getID(),
                newMessage.getMessage(currentUser.id()),
                newMessage.getTime(),
                likes,
                currentUser,
                false);
    }

    @Override
    public boolean clickLikeTheMessage(int messageId, String like, UserDTO currentUser) {
        Message message = messageRepository.findMessageByMessageId(messageId);
        if(message==null) throw new NotFoundException("Not fount message");

        if(message instanceof MessageHandler messageHandler){
            loopOne:
            for (MessageLike chatMessageLike : messageHandler.getLikes()) {
                for (UUID userId : chatMessageLike.getUsers()) {
                    if(userId.equals(currentUser.id())){
                        if(chatMessageLike.getLike().equals(like)){
                            chatMessageLike.getUsers().remove(currentUser.id());
                            if(chatMessageLike.getUsers().isEmpty())
                                messageHandler.getLikes().remove(chatMessageLike);
                            return true;
                        } else {
                            chatMessageLike.getUsers().remove(currentUser.id());
                            if(chatMessageLike.getUsers().isEmpty())
                                messageHandler.getLikes().remove(chatMessageLike);
                            break loopOne;
                        }
                    }
                }
            }
            for (MessageLike chatMessageLike : messageHandler.getLikes()) {
                if(chatMessageLike.getLike().equals(like)){
                    chatMessageLike.getUsers().add(currentUser.id());
                    return true;
                }
            }
            MessageLike newLike = new MessageLike(like, currentUser.id());
            messageHandler.getLikes().add(newLike);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteGroupMessage(GroupUserInformationDTO myInformation, int messageId) {
        Message deleteMessage = messageRepository.deleteGroupMessage(myInformation.isAdmin(), messageId, myInformation.user().id());
        if(deleteMessage == null) return false;
        if (deleteMessage instanceof MessageHandler messageHandler){
            if(messageHandler.getHandler() instanceof Group group){
                for (GroupUserInformation userInformation : group.getUsers()) {
                    if(!userInformation.getUser().getId().equals(myInformation.user().id())){
                        userInformation.setNewSms(userInformation.getNewSms()!=0 ? userInformation.getNewSms()-1 : userInformation.getNewSms());
                    }
                }
            }
        }
        return true;
    }

}
