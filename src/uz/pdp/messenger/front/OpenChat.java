package uz.pdp.messenger.front;


import uz.pdp.messenger.back.payload.*;
import uz.pdp.messenger.back.service.AuthService;
import uz.pdp.messenger.back.service.HandlerService;
import uz.pdp.messenger.back.service.MessageService;
import uz.pdp.messenger.back.service.impl.AuthServiceImpl;
import uz.pdp.messenger.back.service.impl.HandlerServiceImpl;
import uz.pdp.messenger.back.service.impl.MessageServiceImpl;

import static uz.pdp.messenger.back.utils.MessageFormatted.*;
import static uz.pdp.messenger.front.CoutAndCin.*;
import static uz.pdp.messenger.front.ChatCommands.*;
import static uz.pdp.messenger.front.create.CreateContact.*;

import java.util.List;

public class OpenChat {
    private static UserDTO currentUser;
    private static UserDTO secondUser;
    private static ChatDTO currentChat;
    private static final MessageService messageService = MessageServiceImpl.getInstance();
    private static final HandlerService handlerService = HandlerServiceImpl.getInstance();
    private static final AuthService authService = AuthServiceImpl.getInstance();
    private static List<MessageDTO> messages;
    private static boolean ishora;

    public static void openChat(ChatDTO chat, UserDTO user) {
        ishora = true;
        currentUser = user;
        currentChat = chat;
        if(!currentChat.ChatUserInformation().get(0).user().id().equals(currentUser.id()))
            secondUser = currentChat.ChatUserInformation().get(0).user();
        else
            secondUser = currentChat.ChatUserInformation().get(1).user();
        while (ishora) {
            messages = messageService.findAllMessagesInTheChat(currentChat.id(), currentUser.id());
            chatDisplay();
            sendMessage();
        }
    }
    private static void chatDisplay() {
        coutMainText(secondUser.fullName());
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i) instanceof ChatMessageDTO chatMessage) {
                boolean myMessage = chatMessage.senderUser().id().equals(currentUser.id());
//                String header = headerOfTheMessage(chatMessage.senderUser().fullName(), i+1, myMessage);
//                String body = bodyOfTheMessage(chatMessage.message(), false, myMessage);
//                String like = likeOfTheMessage(chatMessage.likes(), chatMessage.time(), chatMessage.seen(), myMessage);
                String messageFormat = messageFormat(chatMessage.senderUser().fullName(), i + 1, chatMessage.message(), chatMessage.likes(), currentUser.id(), chatMessage.time(), chatMessage.seen(), myMessage);
                System.out.println(messageFormat);
            }
        }
    }

    private static void sendMessage() {
        coutAdditionText(commandsText);
        String commandOrMessage;
        while (true) {
            commandOrMessage = getInputAsString("Input command or send message: ").trim();
            if(commandOrMessage.isBlank()){
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
                messageService.sendChatMessage(commandOrMessage, currentUser, currentChat);
                break;
            }
        }
    }

    private static void like(String command) {
        if(command.length() < deleteMessage.length()+2) return;
        else if(command.charAt(2)!=' ') return;
        StringBuilder str = new StringBuilder(command);
        for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == ' ') {
                str.deleteCharAt(i);
                i--;
            }
        }
        try {
            int messageIndex = Integer.parseInt(str.substring(2));
            if(messageIndex>0 && messageIndex<=messages.size()){
                messageIndex--;
                if(messages.get(messageIndex) instanceof ChatMessageDTO chatMessage){
                    coutAdditionText("Likes menu: ");
                    for (int i = 0; i < likes.length; i++) {
                        coutButtonText(i+1 + ". " + likes[i] + ".");
                    }
                    coutExitButton();
                    while (true) {
                        String clickButton = getInputAsString("Choose => ").trim();
                        if(clickButton.equals(exitKey)) return;
                        try {
                            int index = Integer.parseInt(clickButton);
                            if(index>0 && index<=likes.length){
                                index--;
                                messageService.clickLikeTheMessage(chatMessage.messageId(), likes[index], currentUser);
                                return;
                            } else{
                                coutErrorText(buttonException);
                            }
                        } catch (Exception e){
                            coutErrorText(buttonException);
                        }
                    }
                }
            }
        }catch (Exception e){
            return;
        }
    }

    private static void editMessage(String command) {
        if(command.length() < deleteMessage.length()+2) return;
        else if(command.charAt(2)!=' ') return;
        StringBuilder str = new StringBuilder(command);
        for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == ' ') {
                str.deleteCharAt(i);
                i--;
            }
        }
        try {
            int messageIndex = Integer.parseInt(str.substring(2));
            if(messageIndex>0 && messageIndex<=messages.size()){
                messageIndex--;
                if(messages.get(messageIndex) instanceof ChatMessageDTO chatMessage){
                    if(chatMessage.senderUser().id().equals(currentUser.id())) {
                        coutAdditionText("Old message : " + chatMessage.message());
                        coutAdditionText("Command : " + quit);
                        while (true) {
                            String newMessage = getInputAsString("Enter new message : ").trim();
                            if (newMessage.isBlank()) continue;
                            switch (newMessage) {
                                case quit -> {
                                    return;
                                }
                                default -> {
                                    messageService.editMessage(newMessage, chatMessage.messageId(), currentUser.id());
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            return;
        }
    }

    private static void deleteMessage(String command) {
        if(command.length() < deleteMessage.length()+2) return;
        else if(command.charAt(2)!=' ') return;
        StringBuilder str = new StringBuilder(command);
        for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == ' ') {
                str.deleteCharAt(i);
                i--;
            }
        }
        try {
            int messageIndex = Integer.parseInt(str.substring(2));
            if(messageIndex>0 && messageIndex<=messages.size()){
                messageIndex--;
                if(messages.get(messageIndex) instanceof ChatMessageDTO chatMessage){
                    messageService.deleteChatMessage(chatMessage.messageId(), currentUser);
                }
            }
        }catch (Exception e){
            return;
        }
    }

    private static void chatSettings(){
        while (true){
            coutMainText("Chat Settings");
            coutAdditionText("Logo = " + secondUser.logo());
            coutAdditionText("Full name = " + secondUser.fullName());
            coutAdditionText("Phone number = " + secondUser.phoneNumber());
            if(secondUser.bio()!=null)
                coutAdditionText("Bio = " + secondUser.bio());
            if(!authService.isContact(currentUser, secondUser))
                coutButtonText("1. Add to contact");
            else
                coutButtonText("1. Change the name");
            coutButtonText("2. delete user");
            coutExitButton();
            loopTwo:
            while (true){
                switch (getInputAsString("Choose => ")){
                    case "1" -> {
                        secondUser = changeContact(currentUser, secondUser);
                        break loopTwo;
                    }
                    case "2" -> {
                        coutAdditionText("Are you sure you want to delete the chat?");
                        coutErrorText("1. YES");
                        coutButtonText("2. NO");
                        while (true) {
                            switch (getInputAsString("Choose => ")){
                                case "1" -> {handlerService.deleteChat(currentChat.id()); ishora = false; return;}
                                case "2" -> {break loopTwo;}
                                default -> coutErrorText(buttonException);
                            }
                        }
                    }
                    case exitKey -> {
                        return;
                    }
                    default -> coutErrorText(buttonException);
                }
            }

        }
    }
}
