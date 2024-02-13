package uz.pdp.messenger.front;



import uz.pdp.messenger.back.exception.InformationException;
import uz.pdp.messenger.back.payload.SignUpDTO;
import uz.pdp.messenger.back.payload.UserDTO;
import uz.pdp.messenger.back.service.AuthService;
import uz.pdp.messenger.back.service.impl.AuthServiceImpl;

import static uz.pdp.messenger.front.CoutAndCin.*;
import static uz.pdp.messenger.front.create.OpenOrCreateUser.*;

public class Registration {
    private static UserDTO currentUser;
    static {
        AuthService instance = AuthServiceImpl.getInstance();
        try {
            instance.signUp(new SignUpDTO("Azizbek", "Reimov", "@reimoffazizbek", "+998904217300", "1234567a"));
            instance.signUp(new SignUpDTO("Eldor", "Xaytbaev", "@eldor13", "+998900959919", "1234567e"));
        } catch (InformationException e) {
            throw new RuntimeException(e);
        }
    }
    public static void StartRegistration(){
        whileOne:
        while (true){
            coutMainText("-------------------------Registration--------------------------");
            coutButtonText("1. SignIn");
            coutButtonText("2. SignUp");
            whileTwo:
            while (true){
                switch (getInputAsString("Choose => ")){
                    case "1" -> {currentUser = openUserDTO(); break whileTwo;}
                    case "2" -> {currentUser = createNewUser(); break whileTwo;}
                    default -> coutErrorText(buttonException);
                }
            }
            if(currentUser!=null) {
                System.out.println(currentUser + "\n\n\n\n\n\n\n\n");
                MainMenu.StartMainMenu(currentUser);
            }
            currentUser = null;
        }
    }
}
