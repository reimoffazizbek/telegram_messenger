package uz.pdp.messenger.back.modul.handler.chat;

import uz.pdp.messenger.back.modul.User;
import uz.pdp.messenger.back.modul.handler.Handler;
import uz.pdp.messenger.back.modul.handler.Message;
import uz.pdp.messenger.back.modul.handler.MessageHandler;

import java.util.UUID;

public class ChatMessage extends MessageHandler {
    private User senderUser;
    private boolean seen = false;

    public ChatMessage(Chat chat, User senderUser, String message) {
        super(chat, message);
        this.senderUser = senderUser;
    }
    @Override
    public String getMessage(UUID hisOwnId) {
        if(!senderUser.getId().equals(hisOwnId)) {
            this.seen = true;
        }
        if(super.getHandler() instanceof Chat chat){
            for (ChatUserInformation userInformation : chat.getUserInformations()) {
                if(userInformation.getUser().getId().equals(hisOwnId))
                    userInformation.setNewSms(0);
            }
        }
        return super.getMessage();
    }

    public User getSenderUser() {
        return senderUser;
    }

    public boolean isSeen() {
        return seen;
    }
}
