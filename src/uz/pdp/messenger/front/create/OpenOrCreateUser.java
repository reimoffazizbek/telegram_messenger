package uz.pdp.messenger.front.create;

import uz.pdp.messenger.back.exception.InformationException;
import uz.pdp.messenger.back.payload.SignInDTO;
import uz.pdp.messenger.back.payload.SignUpDTO;
import uz.pdp.messenger.back.payload.UserDTO;
import uz.pdp.messenger.back.service.AuthService;
import uz.pdp.messenger.back.service.impl.AuthServiceImpl;

import static uz.pdp.messenger.front.create.CreateUserInformation.*;
import static uz.pdp.messenger.front.CoutAndCin.*;
import static uz.pdp.messenger.back.exception.ExceptionMessage.*;

public class OpenOrCreateUser {
    private static byte level;
    private static String firstname;
    private static String lastname;
    private static String username;
    private static String phoneNumber;
    private static String password;
    private static String usernameOrPhoneNumber;
    private static final AuthService authService = AuthServiceImpl.getInstance();

    public static UserDTO createNewUser() {
        level = 1;
        boolean next = true;
        while (true) {
            if (level == 1) {
                firstname = createFirstname("Enter your firstname.", next, false);
                if (firstname.equals(exitKey)) return null;
                else if (firstname.equals(nextKey)) {
                    next = false;
                    firstname = null;
                }
                level++;
            }
            if (level == 2) {
                lastname = createLastname("Enter your lastname.", next, true);
                if (lastname.equals(exitKey)) return null;
                else if (lastname.equals(backKey)) {
                    level--;
                    next = true;
                    continue;
                } else if (lastname.equals(nextKey)) {
                    next = false;
                    lastname = null;
                }
                level++;
            }
            if (level == 3) {
                username = createUsername("Enter your username.", false, true);
                if (username.equals(exitKey)) return null;
                else if (username.equals(backKey)) {
                    level--;
                    if(lastname==null){
                        next = true;
                    }
                    continue;
                }
                level++;
            }
            if (level == 4) {
                phoneNumber = createPhoneNumber("Enter your phone number.", false, true);
                if (phoneNumber.equals(exitKey)) return null;
                else if (phoneNumber.equals(backKey)) {
                    level--;
                    continue;
                }
                level++;
            }
            if (level == 5) {
                password = createPassword("Enter your password.", false, true);
                if (password.equals(exitKey)) return null;
                else if (password.equals(backKey)) {
                    level--;
                    continue;
                }
                level++;
            } else {
                try {
                    SignUpDTO newSignUpDTO = new SignUpDTO(firstname, lastname, username, phoneNumber, password);
                    return authService.signUp(newSignUpDTO);
                } catch (InformationException e) {
                    coutErrorText(informationException + " : " + e.getMessage());
                    level = 1;
                    next = true;
                }
            }
        }
    }

    public static UserDTO openUserDTO() {
        level = 1;
        while (true) {
            if (level == 1) {
                usernameOrPhoneNumber = createPhoneNumberOrUsername("Enter your phone number or username.", false, false);
                if (usernameOrPhoneNumber.equals(exitKey)) return null;
                level++;
            }
            if (level == 2) {
                password = createPassword("Enter your password", false, true);
                if (password.equals(exitKey)) return null;
                else if (password.equals(backKey)) {
                    level--;
                    continue;
                }
                level++;
            } else {
                String exceptionMessage;
                SignInDTO signIn;
                if(usernameOrPhoneNumber.charAt(0)=='@') {
                    signIn = new SignInDTO(usernameOrPhoneNumber, null, password);
                    exceptionMessage = ErrorPassAndUsername;
                } else {
                    signIn = new SignInDTO(null, usernameOrPhoneNumber, password);
                    exceptionMessage = ErrorPassAndPhonenumber;
                }
                try {
                    UserDTO resUser;
                    if(usernameOrPhoneNumber.charAt(0)=='@') {
                        resUser = authService.signIn(signIn);
                        exceptionMessage = "Username ";
                    } else {
                        resUser = authService.signIn(signIn);
                        exceptionMessage = "Phone number ";
                    }
                    return resUser;
                }catch (InformationException e){
                    if(!e.getMessage().equals(notFoundUser))
                        coutErrorText(exceptionMessage);
                    else
                        coutErrorText(e.getMessage());
                    level=1;
                }
            }
        }
    }
}
