package uz.pdp.messenger.back.repository.impl;

import uz.pdp.messenger.back.modul.User;
import uz.pdp.messenger.back.modul.handler.Handler;
import uz.pdp.messenger.back.modul.handler.chat.Chat;
import uz.pdp.messenger.back.modul.handler.group.Group;
import uz.pdp.messenger.back.modul.handler.group.GroupUserInformation;
import uz.pdp.messenger.back.repository.HandlerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HandlerRepositoryImpl implements HandlerRepository {
    private static final HandlerRepository instance = new HandlerRepositoryImpl();
    private static final List<Handler> handlers = new ArrayList<>();

    private HandlerRepositoryImpl() {
    }

    public static HandlerRepository getInstance() {
        return instance;
    }

    @Override
    public List<Handler> findAllMyHandlerByUserId(UUID id) {
        List<Handler> result = new ArrayList<>();
        for (Handler handler : handlers) {
            if (handler instanceof Chat chat) {
                if (chat.getUserInformations().get(0).getUser().getId().equals(id) || chat.getUserInformations().get(1).getUser().getId().equals(id)) {
                    result.add(chat);
                }
            } else if(handler instanceof Group group){
                for (GroupUserInformation user : group.getUsers()) {
                    if(user.getUser().getId().equals(id)){
                        result.add(group);
                        break;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Chat findChatByUserId(UUID firstUserId, UUID secondUserId) {
        User tempFirstUser;
        User tempSecondUser;
        for (Handler handler : handlers) {
            if (handler instanceof Chat chat) {
                tempFirstUser = chat.getUserInformations().get(0).getUser();
                tempSecondUser = chat.getUserInformations().get(1).getUser();
                if (
                        tempFirstUser.getId().equals(firstUserId) && tempSecondUser.getId().equals(secondUserId)
                                || tempFirstUser.getId().equals(secondUserId) && tempSecondUser.getId().equals(firstUserId)
                ) {
                    return chat;
                }
            }
        }
        return null;
    }

    @Override
    public boolean save(Handler newChat) {
        handlers.add(newChat);
        return true;
    }

    @Override
    public Chat findChatByChatId(UUID chatId) {
        for (Handler handler : handlers) {
            if(handler instanceof Chat chat){
                if(chat.getID().equals(chatId)){
                    return chat;
                }
            }
        }
        return null;
    }

    @Override
    public boolean delete(UUID chatId) {
        for (Handler handler : handlers) {
            if(handler.getID().equals(chatId)) {
                handlers.remove(handler);
                return true;
            }
        }
        return false;
    }

    @Override
    public Group findGroupByGroupId(UUID groupId) {
        for (Handler handler : handlers) {
            if(handler.getID().equals(groupId)){
                return (Group) handler;
            }
        }
        return null;
    }
}
