package uz.pdp.messenger.back.modul.handler;

import uz.pdp.messenger.back.modul.User;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public abstract class MessageHandler extends Message {
    private static int sequence=1;
    private final int id = sequence++;
    private Handler handler;
    private String message;
    private List<MessageLike> likes = new LinkedList<>();
    public MessageHandler(Handler handler, String message) {
        this.handler = handler;
        this.message = message;
    }
    public abstract String getMessage(UUID hisOwnId);

    public int getId() {
        return id;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    protected String getMessage() {
        return message;
    }
    public List<MessageLike> getLikes() {
        return likes;
    }
}
