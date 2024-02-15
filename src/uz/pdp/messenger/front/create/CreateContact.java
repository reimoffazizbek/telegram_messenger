package uz.pdp.messenger.front.create;

import uz.pdp.messenger.back.payload.ChatDTO;
import uz.pdp.messenger.back.payload.UserDTO;
import uz.pdp.messenger.back.service.AuthService;
import uz.pdp.messenger.back.service.HandlerService;
import uz.pdp.messenger.back.service.impl.AuthServiceImpl;
import uz.pdp.messenger.back.service.impl.HandlerServiceImpl;

import static uz.pdp.messenger.front.create.CreateUserInformation.*;
import static uz.pdp.messenger.front.CoutAndCin.*;
import static uz.pdp.messenger.back.exception.ExceptionMessage.*;

public class CreateContact {
    private static byte level;
    private static String phoneNumber;
    private static String firstname;
    private static String lastname;
    private static final HandlerService handlerService = HandlerServiceImpl.getInstance();
    private static final AuthService authService = AuthServiceImpl.getInstance();
    public static ChatDTO createContact(UserDTO currentUser){
        level = 1;
        while (true){
            if(level==1){
                phoneNumber = createPhoneNumber("Enter your phone number.", false, false);
                if(phoneNumber.equals(exitKey)) return null;
                if(phoneNumber.equals(currentUser.phoneNumber())) {
                    coutErrorText("the phone number you entered is the same as your phone number❗");
                    continue;
                }
                level++;
            } if(level==2){
                firstname = createFirstname("Enter firstname.", true, true);
                if(firstname.equals(exitKey)) return null;
                else if(firstname.equals(nextKey)){
                    firstname = null;
                } else if(firstname.equals(backKey)){
                    level--;
                    continue;
                }
                level++;
            } if(level==3){
                lastname = createLastname("Enter lastname.", true, true);
                if(lastname.equals(exitKey)) return null;
                else if(lastname.equals(nextKey)){
                    lastname = null;
                } else if (lastname.equals(backKey)) {
                    level--;
                    continue;
                }
                level++;
            } else{
                UserDTO newContact = authService.findUserByPhoneNumber(phoneNumber, currentUser.id());
                if(newContact==null){
                    coutErrorText(notFoundUser);
                    level = 1;
                    continue;
                }
                if(firstname==null && lastname==null)
                    authService.editeOrCreateContact(currentUser.id(), newContact.id(), newContact.firstname(), newContact.lastname());
                else
                    authService.editeOrCreateContact(currentUser.id(), newContact.id(), firstname, lastname);
                ChatDTO oldChat = handlerService.findChatByUserId(currentUser.id(), newContact.id());
                if(oldChat!=null){
                    return oldChat;
                }
                ChatDTO newChat = handlerService.createChat(currentUser.id(), newContact.id());
                return newChat;
            }
        }
    }

    public static UserDTO changeContact(UserDTO currentUser, UserDTO secondUser){
        level = 1;
        while (true){
            if(level==1){
                firstname = createFirstname("Enter firstname.", true, false);
                if(firstname.equals(exitKey)) return null;
                else if(firstname.equals(nextKey)){
                    firstname = null;
                }
                level++;
            } if(level==2){
                lastname = createLastname("Enter lastname.", true, true);
                if(lastname.equals(exitKey)) return null;
                else if(lastname.equals(nextKey)){
                    lastname = null;
                } else if (lastname.equals(backKey)) {
                    level--;
                    continue;
                }
                level++;
            } else{
                if(firstname==null && lastname==null)
                    return authService.editeOrCreateContact(currentUser.id(), secondUser.id(), secondUser.firstname(), secondUser.lastname());
                else
                    return authService.editeOrCreateContact(currentUser.id(), secondUser.id(), firstname, lastname);
            }
        }
    }
}
