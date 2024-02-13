package uz.pdp.messenger.back.repository.impl;

import uz.pdp.messenger.back.modul.handler.Message;
import uz.pdp.messenger.back.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;

public class MessageRepositoryImpl implements MessageRepository {
    private static final MessageRepository instance = new MessageRepositoryImpl();
    private static final List<Message> users = new ArrayList<>();
    private MessageRepositoryImpl(){}
    public static MessageRepository getInstance() {
        return instance;
    }

}
