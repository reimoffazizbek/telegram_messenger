package uz.pdp.messenger.front;



import uz.pdp.messenger.back.exception.InformationException;
import uz.pdp.messenger.back.payload.CreateGroupDTO;
import uz.pdp.messenger.back.payload.SignUpDTO;
import uz.pdp.messenger.back.payload.UserDTO;
import uz.pdp.messenger.back.service.AuthService;
import uz.pdp.messenger.back.service.HandlerService;
import uz.pdp.messenger.back.service.impl.AuthServiceImpl;
import uz.pdp.messenger.back.service.impl.HandlerServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static uz.pdp.messenger.front.CoutAndCin.*;
import static uz.pdp.messenger.front.create.OpenOrCreateUser.*;

public class Registration {
    private static UserDTO currentUser;
    static {
        AuthService instance = AuthServiceImpl.getInstance();
        HandlerService handlerService = HandlerServiceImpl.getInstance();
        try {
            UserDTO userDTO = instance.signUp(new SignUpDTO("Azizbek1", "Reimov1", "@reimoffazizbek1", "+998904217300", "1234567a"));
            UserDTO userDTO1 = instance.signUp(new SignUpDTO("Azizbek2", "Reimov2", "@reimoffazizbek2", "+998904217301", "1234567a"));
            UserDTO userDTO2 = instance.signUp(new SignUpDTO("Azizbek3", "Reimov3", "@reimoffazizbek3", "+998904217302", "1234567a"));
            UserDTO userDTO3 = instance.signUp(new SignUpDTO("Azizbek4", "Reimov4", "@reimoffazizbek4", "+998904217303", "1234567a"));
            UserDTO userDTO4 = instance.signUp(new SignUpDTO("Azizbek5", "Reimov5", "@reimoffazizbek5", "+998904217304", "1234567a"));
//            UserDTO userDTO1 = instance.signUp(new SignUpDTO("Eldor", "Xaytbaev", "@eldor13", "+998900959919", "1234567e"));
            handlerService.createChat(userDTO.id(), userDTO1.id());
            handlerService.createChat(userDTO.id(), userDTO2.id());
            handlerService.createChat(userDTO.id(), userDTO3.id());
            handlerService.createChat(userDTO.id(), userDTO4.id());
            instance.editeOrCreateContact(userDTO.id(), userDTO1.id(), userDTO1.firstname(), userDTO1.lastname());
            instance.editeOrCreateContact(userDTO.id(), userDTO2.id(), userDTO2.firstname(), userDTO2.lastname());
            instance.editeOrCreateContact(userDTO.id(), userDTO3.id(), userDTO3.firstname(), userDTO3.lastname());
            instance.editeOrCreateContact(userDTO.id(), userDTO4.id(), userDTO4.firstname(), userDTO4.lastname());
            handlerService.createGroup(new CreateGroupDTO(userDTO, "Test", new ArrayList<>(List.of(userDTO1, userDTO4))));
        } catch (InformationException e) {
            throw new RuntimeException(e);
        }
    }
    public static void StartRegistration(){
        whileOne:
        while (true){
            coutMainText("Registration");
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
