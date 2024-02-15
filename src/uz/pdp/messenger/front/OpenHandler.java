package uz.pdp.messenger.front;

import uz.pdp.messenger.back.payload.*;

import java.util.List;
import static uz.pdp.messenger.front.OpenChat.*;
import static uz.pdp.messenger.front.OpenGroup.*;

public class OpenHandler {
    private static UserDTO currentUser;
    private static HandlerDTO currentHandler;
    public static void openHandler(UserDTO user, HandlerDTO handler){
        currentUser = user;
        currentHandler = handler;
        if(handler instanceof ChatDTO chatDTO){
            openChat(chatDTO, currentUser);
        } else if(handler instanceof GroupDTO groupDTO){
            openGroup(currentUser, groupDTO);
        }
    }
}
