package uz.pdp.messenger.back.modul.handler;

import uz.pdp.messenger.back.modul.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public abstract class Message {
    private static int sequence=1;
    private final int id = sequence++;
    private final String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
    private Handler handler;
    private User senderUser;
    private String message;
    private List<MessageLike> likes = new LinkedList<>();
    private boolean seen = false;
    public Message(Handler handler, User senderUser, String message) {
        this.handler = handler;
        this.senderUser = senderUser;
        this.message = message;
    }
    public abstract String messageFormat(String message);
    public String getMessage(UUID hisOwnId){
        if(!senderUser.getId().equals(hisOwnId))
            seen = true;
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public Handler getHandler() {
        return handler;
    }

    public User getSenderUser() {
        return senderUser;
    }

    public List<MessageLike> getLikes() {
        return likes;
    }

    public boolean isSeen() {
        return seen;
    }
}
