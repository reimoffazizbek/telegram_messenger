package uz.pdp.messenger.back.modul.handler.chat;

import uz.pdp.messenger.back.modul.User;

public class ChatUserInformation {
    private User user;
    private int newSms = 0;
    private boolean replyOrMark = false;

    public ChatUserInformation(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNewSms() {
        return newSms;
    }

    public void setNewSms(int newSms) {
        this.newSms = newSms;
    }

    public boolean getReplyOrMark() {
        return replyOrMark;
    }

    public void setReplyOrMark(boolean replyOrMark) {
        this.replyOrMark = replyOrMark;
    }

    public boolean isReplyOrMark() {
        return replyOrMark;
    }
}
