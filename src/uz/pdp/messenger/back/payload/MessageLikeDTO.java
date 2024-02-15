package uz.pdp.messenger.back.payload;

import uz.pdp.messenger.back.modul.handler.MessageLike;

import static uz.pdp.messenger.back.utils.Design.*;

import java.util.List;
import java.util.UUID;

public record MessageLikeDTO(String like, List<UUID> users) {
    @Override
    public String toString() {
        return like + users.size();
    }

    public int length(){
        return String.valueOf(users.size()).length() + 1;
    }
}
