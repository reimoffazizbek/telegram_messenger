package uz.pdp.messenger.back.modul.handler.chat;

import uz.pdp.messenger.back.modul.User;
import uz.pdp.messenger.back.modul.handler.Handler;
import uz.pdp.messenger.back.modul.handler.Message;

public class ChatMessage extends Message {
    public ChatMessage(Chat chat, User senderUser, String message) {
        super(chat, senderUser, message);
    }

    @Override
    public String messageFormat(String message) {
        return null;
    }
}
