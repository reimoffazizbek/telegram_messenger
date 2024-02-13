package uz.pdp.messenger.back.modul.handler;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class MessageLike {
    private String like;
    private List<UUID> users = new LinkedList<>();

    public MessageLike(String like, UUID userID) {
        this.like = like;
        this.users.add(userID);
    }

    public String getLike() {
        return like;
    }

    public List<UUID> getUsers() {
        return users;
    }

    public void setUsers(UUID usersId) {
        this.users.add(usersId);
    }
}
