package uz.pdp.messenger.back.modul.handler;

import uz.pdp.messenger.back.modul.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public abstract class Message implements Comparable<Message> {
    private final Date time = new Date();
    @Override
    public int compareTo(Message o){
        return time.compareTo(o.time);
    }
    public Date getTime() {
        return time;
    }
}
