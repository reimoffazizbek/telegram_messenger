package uz.pdp.messenger.front;

import uz.pdp.messenger.back.payload.ChatDTO;
import uz.pdp.messenger.back.payload.ChatUserInformationDTO;
import uz.pdp.messenger.back.payload.HandlerDTO;
import uz.pdp.messenger.back.payload.UserDTO;
import uz.pdp.messenger.back.service.AuthService;
import uz.pdp.messenger.back.service.HandlerService;
import uz.pdp.messenger.back.service.impl.AuthServiceImpl;
import uz.pdp.messenger.back.service.impl.HandlerServiceImpl;


import java.util.List;
import static uz.pdp.messenger.front.MenuCommands.*;
import static uz.pdp.messenger.back.utils.Design.*;
import static uz.pdp.messenger.front.CoutAndCin.*;
import static uz.pdp.messenger.front.OpenHandler.*;
import static uz.pdp.messenger.front.CreateHandler.*;

public class MainMenu {
    public static UserDTO currentUser;
    public static final HandlerService handlerService = HandlerServiceImpl.getInstance();
    public static final AuthService authService = AuthServiceImpl.getInstance();
    public static List<HandlerDTO> myHandlers;

    public static void StartMainMenu(UserDTO user) {
        currentUser = user;
        String commandOrButton = null;
        int buttonIndex;
        while (true) {
            HandlersDisplay();
            coutExitButton();
            coutAdditionText("commands: " + create + ", " + search + ", " + settings);
            try {
                commandOrButton = getInputAsString("Command or choose => ");
                if(commandOrButton.equals(exitKey)) return;
                buttonIndex = Integer.parseInt(commandOrButton);
                if(buttonIndex>0 && buttonIndex<=myHandlers.size()){
                    buttonIndex--;
                    openHandler(currentUser, myHandlers.get(buttonIndex));
                }else {
                    coutErrorText(buttonException);
                }
            }catch (NumberFormatException e){
                switch (commandOrButton){
                    case search -> {}
                    case settings -> {}
                    case create -> {createHandler(currentUser);}
                    default -> coutErrorText(commandNotFound);
                }
            }
        }
    }

    private static void HandlersDisplay() {
        coutMainText("------" + programName + "-------------------------------");
        int numberOfHandlers = 1;
        myHandlers = handlerService.findAllMyHandlerByUserId(currentUser.id());
        String chatName = null;
        String chatLogo = null;
        int chatNewSms = 0;
        boolean replyOrMark = false;
        for (HandlerDTO myHandler : myHandlers) {
            if (myHandler instanceof ChatDTO chatDTO) {
                String chatColor = null;
                for (ChatUserInformationDTO chatUserInformationDTO : chatDTO.ChatUserInformation()) {
                    if (!chatUserInformationDTO.user().id().equals(currentUser.id())) {
                        chatColor = (authService.isContact(currentUser, chatUserInformationDTO.user())) ? CONTACT_COLOR : NO_CONTACT_COLOR;
                        chatName = chatUserInformationDTO.user().fullName();
                        chatLogo = chatUserInformationDTO.user().logo();
                    } else {
                        chatNewSms = chatUserInformationDTO.newSms();
                        replyOrMark = chatUserInformationDTO.replyOrMark();
                    }
                }
                coutButtonText(chatColor + numberOfHandlers++ + ". " + chatLogo + " " + chatName + " |" + chatNewSms + ((replyOrMark) ? "@|" : "|"));
            }
        }
    }
}
