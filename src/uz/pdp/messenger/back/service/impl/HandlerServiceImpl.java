package uz.pdp.messenger.back.service.impl;

import uz.pdp.messenger.back.modul.User;
import uz.pdp.messenger.back.modul.handler.Handler;
import uz.pdp.messenger.back.modul.handler.chat.Chat;
import uz.pdp.messenger.back.modul.handler.chat.ChatUserInformation;
import uz.pdp.messenger.back.payload.ChatDTO;
import uz.pdp.messenger.back.payload.ChatUserInformationDTO;
import uz.pdp.messenger.back.payload.HandlerDTO;
import uz.pdp.messenger.back.repository.AuthRepository;
import uz.pdp.messenger.back.repository.HandlerRepository;
import uz.pdp.messenger.back.repository.impl.AuthRepositoryImpl;
import uz.pdp.messenger.back.repository.impl.HandlerRepositoryImpl;
import uz.pdp.messenger.back.service.HandlerService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HandlerServiceImpl implements HandlerService {
    private static final HandlerService instance = new HandlerServiceImpl();
    private static final HandlerRepository handlerRepository = HandlerRepositoryImpl.getInstance();
    private static final AuthRepository authRepository = AuthRepositoryImpl.getInstance();
    private HandlerServiceImpl() {}
    public static HandlerService getInstance() {
        return instance;
    }
    @Override
    public List<HandlerDTO> findAllMyHandlerByUserId(UUID id) {
        List<Handler> findAllMyHandlers = handlerRepository.findAllMyHandlerByUserId(id);
        List<HandlerDTO> resultHandlerDTOS = new ArrayList<>(findAllMyHandlers.size());
        List<ChatUserInformationDTO> chatUserInformationDTOS = new ArrayList<>();
        for (Handler findAllMyHandler : findAllMyHandlers) {
            if(findAllMyHandler instanceof Chat chat){
                for (ChatUserInformation userInformation : chat.getUserInformations()) {
                    chatUserInformationDTOS.add(new ChatUserInformationDTO(userInformation, id));
                }
                resultHandlerDTOS.add(new ChatDTO(chat.getID(), chatUserInformationDTOS));
                chatUserInformationDTOS = new ArrayList<>();
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
        System.out.println(newChat.getUserInformations());
        for (ChatUserInformation userInformation : newChat.getUserInformations()) {
            chatUserInformationDTOS.add(new ChatUserInformationDTO(userInformation, firstUserId));
        }

        return new ChatDTO(newChat.getID(), chatUserInformationDTOS);
    }

}
