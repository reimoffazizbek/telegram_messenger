package uz.pdp.messenger.back.modul.handler.group;

import uz.pdp.messenger.back.modul.User;
import uz.pdp.messenger.back.modul.handler.MessageHandler;
import uz.pdp.messenger.back.modul.handler.chat.Chat;
import uz.pdp.messenger.back.modul.handler.chat.ChatUserInformation;

import java.util.UUID;

public class GroupMessage extends MessageHandler {
    private User senderUser;
    private boolean seen = false;

    public GroupMessage(Group group, User senderUser, String message) {
        super(group, message);
        this.senderUser = senderUser;
    }
    @Override
    public String getMessage(UUID hisOwnId) {
        if(!senderUser.getId().equals(hisOwnId)) {
            this.seen = true;
        }
        if(super.getHandler() instanceof Group group){
            for (GroupUserInformation userInformation : group.getUsers()) {
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
