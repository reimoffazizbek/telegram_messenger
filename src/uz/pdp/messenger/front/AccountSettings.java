package uz.pdp.messenger.front;

import uz.pdp.messenger.back.payload.EditAccountDTO;
import uz.pdp.messenger.back.payload.UserDTO;
import uz.pdp.messenger.back.service.AuthService;
import uz.pdp.messenger.back.service.impl.AuthServiceImpl;

import static uz.pdp.messenger.front.CoutAndCin.*;
import static uz.pdp.messenger.front.create.CreateUserInformation.*;

public class AccountSettings {
    private static UserDTO currentUser;

    private static final AuthService authService = AuthServiceImpl.getInstance();

    public static UserDTO startSettings(UserDTO user){
        currentUser = user;
        while (true){
            coutMainText("Settings Menu");
            coutAdditionText("Logo = " + currentUser.logo());
            coutAdditionText("Full name = " + currentUser.fullName());
            coutAdditionText("Username = " + currentUser.username());
            coutAdditionText("Phone number = " + currentUser.phoneNumber());
            if(currentUser.bio()!=null)
                coutAdditionText("Bio = " + currentUser.bio());
            coutAdditionText("\n");
            coutButtonText("1. Change of name");
            coutButtonText("2. Change of username");
            coutButtonText("3. Change of Bio");
            coutButtonText("4. Change of Password");
            coutExitButton();
            loopTwo:
            while (true){
                switch (getInputAsString("Choose => ")){
                    case "1" -> {
                        byte level = 1;
                        boolean next = true;
                        String newFirstName = null;
                        String newLastName = null;
                        while (true){
                            if(level==1){
                                newFirstName = createFirstname("Enter new first name.", next, false);
                                if(newFirstName.equals(exitKey)) break loopTwo;
                                else if(newFirstName.equals(nextKey)){
                                    next = false;
                                    newFirstName = null;
                                }
                                level++;
                            } if(level==2) {
                                newLastName = createLastname("Enter new last name.", next, true);
                                if(newLastName.equals(exitKey)) break loopTwo;
                                else if(newLastName.equals(nextKey)){
                                    next = false;
                                    newLastName = null;
                                } else if(newLastName.equals(backKey)){
                                    next = true;
                                    level--;
                                    continue;
                                }
                                level++;
                            } else{
                                currentUser = authService.editFirstNameAndLastName(currentUser.id(), newFirstName, newLastName);
                                break loopTwo;
                            }
                        }
                    }
                    case "2" -> {
                        while (true) {
                            String newUsername = createUsername("Enter new username.", false, false);
                            if (newUsername.equals(exitKey)) break loopTwo;
                            else if(newUsername.equals(currentUser.username())) {coutErrorText("You have entered your previous username❗");}
                            try {
                                UserDTO userDTO = authService.editUsername(currentUser.id(), newUsername);
                                if(userDTO==null){
                                    coutErrorText("Such a username exists");
                                } else{
                                    currentUser = userDTO;
                                    break loopTwo;
                                }
                            } catch (RuntimeException e){
                                coutErrorText("Such a username exists❗");
                            }
                        }
                    }
                    case "3" -> {
                        String newBio = createBio("Enter new Bio.", false, false);
                        if(newBio.equals(exitKey)) break loopTwo;
                        currentUser = authService.editBio(currentUser.id(), newBio);
                        break loopTwo;
                    }
                    case "4" -> {}
                    case exitKey -> {return currentUser;}
                    default -> coutErrorText(buttonException);
                }
            }
        }
    }
}
