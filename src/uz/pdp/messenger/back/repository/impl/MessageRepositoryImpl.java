package uz.pdp.messenger.back.repository.impl;

import uz.pdp.messenger.back.modul.handler.Message;
import uz.pdp.messenger.back.modul.handler.MessageHandler;
import uz.pdp.messenger.back.modul.handler.MessageLike;
import uz.pdp.messenger.back.modul.handler.TelegramMessage;
import uz.pdp.messenger.back.modul.handler.chat.Chat;
import uz.pdp.messenger.back.modul.handler.chat.ChatMessage;
import uz.pdp.messenger.back.modul.handler.group.GroupMessage;
import uz.pdp.messenger.back.repository.MessageRepository;
import uz.pdp.messenger.back.service.impl.MessageServiceImpl;

import java.util.*;

public class MessageRepositoryImpl implements MessageRepository {
    private static final MessageRepository instance = new MessageRepositoryImpl();
    private static final List<Message> messages = new ArrayList<>();
    private MessageRepositoryImpl(){}
    public static MessageRepository getInstance() {
        return instance;
    }

    @Override
    public Set<Message> findAllMessagesInTheChat(UUID chatId) {
        TreeSet<Message> result = new TreeSet<>();
        for (Message message : messages) {
            if(message instanceof ChatMessage chatMessage){
                if(chatMessage.getHandler().getID().equals(chatId)){
                    result.add(chatMessage);
                }
            } else if(message instanceof TelegramMessage telegramMessage){
                if(telegramMessage.getHandler().getID().equals(chatId)){
                    result.add(telegramMessage);
                }
            }
        }
        return result;
    }

    @Override
    public boolean save(Message newMessage) {
        messages.add(newMessage);
        return true;
    }

    @Override
    public Message deleteChatMessage(int messageId, UUID currentUserId) {
        for (Message message : messages) {
            if(message instanceof MessageHandler messageHandler){
                if(messageHandler.getId() == messageId){
                    if(messageHandler instanceof ChatMessage chatMessage){
                        if(chatMessage.getSenderUser().getId().equals(currentUserId)){
                            messages.remove(message);
                            return message;
                        }
                        else
                            return null;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Message findMessageByMessageId(int messageId) {
        for (Message message : messages) {
            if(message instanceof MessageHandler messageHandler){
                if(messageHandler.getId() == messageId){
                    return message;
                }
            }
        }
        return null;
    }

    @Override
    public Set<Message> findAllMessagesInTheGroup(UUID groupId) {
        Set<Message> result = new TreeSet<>();
        for (Message message : messages) {
            if(message instanceof GroupMessage groupMessage){
                if(groupMessage.getHandler().getID().equals(groupId)){
                    result.add(groupMessage);
                }
            } else if(message instanceof TelegramMessage telegramMessage){
                if(telegramMessage.getHandler().getID().equals(groupId)){
                    result.add(telegramMessage);
                }
            }
        }
        return result;
    }

    @Override
    public Message deleteGroupMessage(boolean admin, int messageId, UUID userId) {
        for (Message message : messages) {
            if(message instanceof GroupMessage groupMessage){
                if(groupMessage.getId() == messageId){
                    if(admin){
                        messages.remove(groupMessage);
                        return groupMessage;
                    } else{
                        if(groupMessage.getSenderUser().getId().equals(userId)){
                            messages.remove(groupMessage);
                            return groupMessage;
                        } else{
                            return null;
                        }
                    }
                }
            }
        }
        return null;
    }

}
