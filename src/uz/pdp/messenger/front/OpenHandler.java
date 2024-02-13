package uz.pdp.messenger.front;

import uz.pdp.messenger.back.payload.HandlerDTO;
import uz.pdp.messenger.back.payload.UserDTO;

public class OpenHandler {
    private static UserDTO currentUser;
    private static HandlerDTO currentHandler;
    public static void openHandler(UserDTO user, HandlerDTO handler){
        currentUser = user;
        currentHandler = handler;
    }
}
