package uz.pdp.messenger.back.modul.handler.chat;

import uz.pdp.messenger.back.modul.User;
import uz.pdp.messenger.back.modul.handler.Handler;

import java.util.*;

public class Chat extends Handler{
    private List<ChatUserInformation> userInformations = new ArrayList<>(2);
    public Chat(User firstUser, User secondUser) {
        this.userInformations.add(new ChatUserInformation(firstUser));
        this.userInformations.add(new ChatUserInformation(secondUser));
    }
    public List<ChatUserInformation> getUserInformations() {
        return userInformations;
    }
}
