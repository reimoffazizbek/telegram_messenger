package uz.pdp.messenger.back.modul.handler;

import java.util.Date;
import java.util.UUID;

public abstract class Handler implements Comparable<Handler>{
    private final UUID ID = UUID.randomUUID();
    private Date lastMessageTime = new Date();
    public UUID getID() {
        return ID;
    }

    public Date getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(Date lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    @Override
    public int compareTo(Handler o) {
        return o.lastMessageTime.compareTo(this.lastMessageTime);
    }
}
