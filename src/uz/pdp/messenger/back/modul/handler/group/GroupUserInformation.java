package uz.pdp.messenger.back.modul.handler.group;

import uz.pdp.messenger.back.modul.User;

public class GroupUserInformation {
    private User user;
    private int newSms = 0;
    private boolean admin = false;
    private boolean replyOrMark = false;

    public GroupUserInformation(User user) {
        this.user = user;
    }

    public GroupUserInformation(User user, boolean admin) {
        this.user = user;
        this.admin = admin;
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

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
