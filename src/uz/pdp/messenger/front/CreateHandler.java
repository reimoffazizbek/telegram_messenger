package uz.pdp.messenger.front;

import uz.pdp.messenger.back.modul.User;
import uz.pdp.messenger.back.payload.HandlerDTO;
import uz.pdp.messenger.back.payload.UserDTO;

import static uz.pdp.messenger.front.CoutAndCin.*;
import static uz.pdp.messenger.front.create.CreateContact.*;
import static uz.pdp.messenger.front.create.CreateGroup.*;
import static uz.pdp.messenger.front.OpenHandler.*;


public class CreateHandler {
    private static UserDTO currentUser;
    private static HandlerDTO currentHandler;
    public static void createHandler(UserDTO user){
        currentUser = user;
        while (true){
            coutButtonText("1. Create contact");
            coutButtonText("2. Create group");
            coutButtonText("3. Create channel");
            coutExitButton();
            whileTwo:
            while (true){
                switch (getInputAsString("Choose => ")){
                    case "1" -> {currentHandler = createContact(currentUser); break whileTwo;}
                    case "2" -> {currentHandler = createGroup(currentUser); break whileTwo;}
                    case "3" -> {}
                    case exitKey -> {return;}
                    default -> coutErrorText(buttonException);
                }
            }
            if(currentHandler != null){
                System.out.println(currentHandler);
                openHandler(currentUser, currentHandler);
                return;
            }
        }
    }
}
