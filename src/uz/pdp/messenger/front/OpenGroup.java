package uz.pdp.messenger.front;

import uz.pdp.messenger.back.payload.*;
import uz.pdp.messenger.back.service.HandlerService;
import uz.pdp.messenger.back.service.MessageService;
import uz.pdp.messenger.back.service.impl.HandlerServiceImpl;
import uz.pdp.messenger.back.service.impl.MessageServiceImpl;

import static uz.pdp.messenger.front.ChatCommands.deleteMessage;
import static uz.pdp.messenger.front.ChatCommands.quit;
import static uz.pdp.messenger.front.CoutAndCin.*;
import static uz.pdp.messenger.back.utils.MessageFormatted.*;
import static uz.pdp.messenger.front.GroupCommands.*;
import static uz.pdp.messenger.front.create.ChannelOrGroupInformation.*;
import static uz.pdp.messenger.front.create.CreateContact.changeContact;

import java.util.List;


public class OpenGroup {
    private static UserDTO currentUser;
    private static GroupDTO currentGroup;
    private static GroupUserInformationDTO myInformation;
    private static List<MessageDTO> messages;
    private static final MessageService messageService = MessageServiceImpl.getInstance();
    private static final HandlerService handlerService = HandlerServiceImpl.getInstance();
    private static boolean ishora;

    public static void openGroup(UserDTO user, GroupDTO groupDTO) {
        ishora = true;
        currentUser = user;
        for (GroupUserInformationDTO groupUserInformationDTO : groupDTO.GroupUserInformationDTO()) {
            if (groupUserInformationDTO.user().id().equals(user.id())) {
                myInformation = groupUserInformationDTO;
                break;
            }
        }
        currentGroup = groupDTO;
        while (ishora) {
            messages = messageService.findAllMessagesInTheGroup(currentGroup.id(), currentUser.id());
            GroupDisplay();
            sentMessage();
        }
    }

    private static void sentMessage() {
        coutAdditionText(commandsText);
        String commandOrMessage;
        while (true) {
            commandOrMessage = getInputAsString("Input command or send message: ").trim();
            if (commandOrMessage.isBlank()) {
                continue;
            } else if (commandOrMessage.equalsIgnoreCase(settings)) {
                chatSettings();
                break;
            } else if (commandOrMessage.toLowerCase().startsWith(deleteMessage)) {
                deleteMessage(commandOrMessage);
                break;
            } else if (commandOrMessage.toLowerCase().startsWith(edit)) {
                editMessage(commandOrMessage);
                break;
            } else if (commandOrMessage.toLowerCase().startsWith(like)) {
                like(commandOrMessage);
                break;
            } else if (commandOrMessage.equalsIgnoreCase(quit)) {
                ishora = false;
                break;
            } else {
                messageService.sendGroupMessage(commandOrMessage, currentUser, currentGroup);
                break;
            }
        }
    }

    private static void chatSettings() {
        while (true) {
            coutMainText("Group Settings");
            coutAdditionText("Logo = " + currentGroup.logo());
            coutAdditionText("Group name = " + currentGroup.groupName());
            if (currentGroup.bio() != null)
                coutAdditionText("Bio = " + currentGroup.bio());
            coutAdditionText("\nMembers:");
            for (int i = 0; i < currentGroup.GroupUserInformationDTO().size(); i++) {
                coutAdditionText(i+1 + ". " + currentGroup.GroupUserInformationDTO().get(i).user().logo() + " " + currentGroup.GroupUserInformationDTO().get(i).user().fullName() + (currentGroup.GroupUserInformationDTO().get(i).isAdmin() ? " admin." : "."));
            }
            coutAdditionText("\n");
            if (myInformation.isAdmin()){
                coutButtonText("1. Add user to group");
                coutButtonText("2. Remove a user from a group");
                coutButtonText("3. Change the name of the group");
                coutButtonText("4. Change group bio");
                coutButtonText("5. Clear history");
                coutButtonText("6. Delete Group");
                coutExitButton();
                loopTwo:
                while (true){
                    switch (getInputAsString("Choose => ")){
                        case "1" -> {
                            List<UserDTO> userDTOS = addUserToGroup("Select the users you want to add to the group.", currentGroup, myInformation.user());
                            if(userDTOS==null) break loopTwo;
                            currentGroup = handlerService.addUserToGroup(currentGroup, userDTOS, myInformation.user().id());
                            break loopTwo;
                        }
                        case "2" -> {
                            List<GroupUserInformationDTO> deleteUser = deleteUserFromGroup("Select the people you want to delete.", currentGroup);
                            if(deleteUser==null) break loopTwo;
                            currentGroup = handlerService.deleteUserFromGroup(deleteUser, currentGroup, currentUser);
                            break loopTwo;
                        }
                        case "3" -> {
                            String newName = createName("Enter new group name.", false, false);
                            if(newName.equals(exitKey)) break loopTwo;
                            currentGroup = handlerService.editGroupName(currentGroup, newName, myInformation.user().id());
                            break loopTwo;
                        }
                        case "4" -> {
                            String newBio = createBio("Enter a new bio.", false, false);
                            if(newBio.equals(exitKey)) break loopTwo;
                            currentGroup = handlerService.editOrCreateGroupBio(currentGroup, newBio, currentUser);
                            break loopTwo;
                        }
                        case "5" -> {}
                        case "6" -> {}
                        case exitKey -> {return;}
                        default -> coutErrorText(buttonException);
                    }
                }
            } else {
                break;
            }
//            loopTwo:
//            while (true) {
//                switch (getInputAsString("Choose => ")) {
//                    case "1" -> {
//                        secondUser = changeContact(currentUser, secondUser);
//                        break loopTwo;
//                    }
//                    case "2" -> {
//                        coutAdditionText("Are you sure you want to delete the chat?");
//                        coutErrorText("1. YES");
//                        coutButtonText("2. NO");
//                        while (true) {
//                            switch (getInputAsString("Choose => ")) {
//                                case "1" -> {
//                                    handlerService.deleteChat(currentChat.id());
//                                    ishora = false;
//                                    return;
//                                }
//                                case "2" -> {
//                                    break loopTwo;
//                                }
//                                default -> coutErrorText(buttonException);
//                            }
//                        }
//                    }
//                    case exitKey -> {
//                        return;
//                    }
//                    default -> coutErrorText(buttonException);
//                }
//            }

        }
    }

    private static void editMessage(String command) {
        if (command.length() < deleteMessage.length() + 2) return;
        else if (command.charAt(2) != ' ') return;
        StringBuilder str = new StringBuilder(command);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ') {
                str.deleteCharAt(i);
                i--;
            }
        }
        try {
            int messageIndex = Integer.parseInt(str.substring(2));
            if (messageIndex > 0 && messageIndex <= messages.size()) {
                messageIndex--;
                if (messages.get(messageIndex) instanceof GroupMessageDTO groupMessage) {
                    if (groupMessage.senderUser().id().equals(currentUser.id())) {
                        coutAdditionText("Old message : " + groupMessage.message());
                        coutAdditionText("Command : " + quit);
                        while (true) {
                            String newMessage = getInputAsString("Enter new message : ").trim();
                            if (newMessage.isBlank()) continue;
                            switch (newMessage) {
                                case quit -> {
                                    return;
                                }
                                default -> {
                                    messageService.editMessage(newMessage, groupMessage.messageId(), currentUser.id());
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            return;
        }
    }

    private static void deleteMessage(String command) {
        if (command.length() < deleteMessage.length() + 2) return;
        else if (command.charAt(2) != ' ') return;
        StringBuilder str = new StringBuilder(command);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ') {
                str.deleteCharAt(i);
                i--;
            }
        }
        try {
            int messageIndex = Integer.parseInt(str.substring(2));
            if (messageIndex > 0 && messageIndex <= messages.size()) {
                messageIndex--;
                if (messages.get(messageIndex) instanceof GroupMessageDTO groupMessage) {
                    messageService.deleteGroupMessage(myInformation, groupMessage.messageId());
                }
            }
        } catch (Exception e) {
            return;
        }
    }

    private static void like(String command) {
        if (command.length() < deleteMessage.length() + 2) return;
        else if (command.charAt(2) != ' ') return;
        StringBuilder str = new StringBuilder(command);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ') {
                str.deleteCharAt(i);
                i--;
            }
        }
        try {
            int messageIndex = Integer.parseInt(str.substring(2));
            if (messageIndex > 0 && messageIndex <= messages.size()) {
                messageIndex--;
                if (messages.get(messageIndex) instanceof GroupMessageDTO groupMessage) {
                    coutAdditionText("Likes menu: ");
                    for (int i = 0; i < likes.length; i++) {
                        coutButtonText(i + 1 + ". " + likes[i] + ".");
                    }
                    coutExitButton();
                    while (true) {
                        String clickButton = getInputAsString("Choose => ").trim();
                        if (clickButton.equals(exitKey)) return;
                        try {
                            int index = Integer.parseInt(clickButton);
                            if (index > 0 && index <= likes.length) {
                                index--;
                                messageService.clickLikeTheMessage(groupMessage.messageId(), likes[index], currentUser);
                                return;
                            } else {
                                coutErrorText(buttonException);
                            }
                        } catch (Exception e) {
                            coutErrorText(buttonException);
                        }
                    }
                }
            }
        } catch (Exception e) {
            return;
        }
    }

    private static void GroupDisplay() {
        coutMainText(currentGroup.groupName() + "---" + currentGroup.GroupUserInformationDTO().size() + " members");
        boolean myMessage;
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i) instanceof GroupMessageDTO groupMessage) {
                myMessage = groupMessage.senderUser().id().equals(currentUser.id());
                String messageFormat = messageFormat(groupMessage.senderUser().fullName(), i + 1, groupMessage.message(), groupMessage.likes(), currentUser.id(), groupMessage.time(), groupMessage.seen(), myMessage);
                System.out.println(messageFormat);
            }
        }
    }
}
