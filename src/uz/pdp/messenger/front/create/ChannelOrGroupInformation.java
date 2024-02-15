package uz.pdp.messenger.front.create;


import uz.pdp.messenger.back.payload.GroupDTO;
import uz.pdp.messenger.back.payload.GroupUserInformationDTO;
import uz.pdp.messenger.back.payload.UserDTO;
import uz.pdp.messenger.back.service.AuthService;
import uz.pdp.messenger.back.service.impl.AuthServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static uz.pdp.messenger.front.CoutAndCin.*;
import static uz.pdp.messenger.back.utils.Design.*;
import static uz.pdp.messenger.back.utils.ChannelOrGroupInformationUtils.*;


public class ChannelOrGroupInformation {

    private static final AuthService authService = AuthServiceImpl.getInstance();

    public static String createName(String additionMessage, boolean next, boolean back){
        coutAdditionText(additionMessage);
        if(next) coutNextButton();
        if(back) coutBackButton();
        coutExitButton();
        String name;
        while (true){
            name = getInputAsString("Name : ");
            if(name.equals(exitKey)) return name;
            else if(next && name.equals(nextKey)) return name;
            else if(back && name.equals(backKey)) return name;
            if(checkName(name)) return name;
            else coutErrorText(informationException);
        }
    }
    public static String createBio(String additionMessage, boolean next, boolean back){
        coutAdditionText(additionMessage);
        if(next) coutNextButton();
        if(back) coutBackButton();
        coutExitButton();
        String bio;
        while (true){
            bio = getInputAsString("Bio : ");
            if(bio.equals(exitKey)) return bio;
            else if(next && bio.equals(nextKey)) return bio;
            else if(back && bio.equals(backKey)) return bio;
            if(checkBio(bio)) return bio;
            else coutErrorText(informationException);
        }
    }

    public static List<UserDTO> chooseUser(UUID currentUserId, String additionMessage){
        coutAdditionText(additionMessage);
        List<UserDTO> users = authService.findAllMyContactByUserId(currentUserId);
        for (int i = 0; i < users.size(); i++) {
            coutButtonText(i+1 + ". " + users.get(i).logo() + " " + users.get(i).fullName());
        }
        coutNextButton();
        coutExitButton();
        String deleteCommand = "/d";
        coutAdditionText("Command : " + deleteCommand + " index");
        boolean delete = false;
        String inputButton;
        List<UserDTO> result = new ArrayList<>();
        if(users.isEmpty()) return result;
        while (true){
            inputButton = getInputAsString("Choose => ").trim();
            if(inputButton.equals(exitKey)) return null;
            else if(inputButton.equals(nextKey)) return result;

            try {
                if(inputButton.startsWith(deleteCommand) && inputButton.length()>3 && inputButton.charAt(2)==' ') {
                    StringBuilder command = new StringBuilder(inputButton);
                    for (int i = 0; i < command.length(); i++) {
                        if(command.charAt(i)==' ') {
                            command.deleteCharAt(i);
                            i--;
                        }
                    }
                    inputButton = command.substring(2);
                    delete = true;
                }
                int index = Integer.parseInt(inputButton);
                if(index>0 && index<=users.size()){
                    if(delete){
                        result.remove(index-1);
                        delete = false;
                    } else {
                        if(!result.contains(users.get(index-1)))
                            result.add(users.get(index-1));
                    }
                    coutAdditionText(result.toString());
                    if (result.size() == users.size()) return result;
                } else{
                    coutErrorText(buttonException);
                }
            } catch (Exception e){
                coutErrorText(buttonException);
                delete = false;
            }
        }
    }

    public static List<UserDTO> addUserToGroup(String additionMessage, GroupDTO groupDTO, UserDTO admin){
        coutAdditionText(additionMessage);
        List<UserDTO> allMyContactByUserId = authService.findAllMyContactByUserId(admin.id());
        List<GroupUserInformationDTO> groupUserInformationDTOS = groupDTO.GroupUserInformationDTO();
        for (GroupUserInformationDTO groupUserInformationDTO : groupUserInformationDTOS) {
            allMyContactByUserId.remove(groupUserInformationDTO.user());
        }
        for (int i = 0; i < allMyContactByUserId.size(); i++) {
            coutButtonText(i+1 + ". " + allMyContactByUserId.get(i).logo() + " " + allMyContactByUserId.get(i).fullName());
        }
        coutNextButton();
        coutExitButton();
        String deleteCommand = "/d";
        coutAdditionText("Command : " + deleteCommand + " index");
        boolean delete = false;
        String inputButton;
        List<UserDTO> result = new ArrayList<>();
        if(allMyContactByUserId.isEmpty()) return result;
        while (true){
            inputButton = getInputAsString("Choose => ").trim();
            if(inputButton.equals(exitKey)) return null;
            else if(!result.isEmpty() && inputButton.equals(nextKey)) return result;

            try {
                if(inputButton.startsWith(deleteCommand) && inputButton.length()>3 && inputButton.charAt(2)==' ') {
                    StringBuilder command = new StringBuilder(inputButton);
                    for (int i = 0; i < command.length(); i++) {
                        if(command.charAt(i)==' ') {
                            command.deleteCharAt(i);
                            i--;
                        }
                    }
                    inputButton = command.substring(2);
                    delete = true;
                }
                int index = Integer.parseInt(inputButton);
                if(index>0 && index<=allMyContactByUserId.size()){
                    if(delete){
                        result.remove(index-1);
                        delete = false;
                    } else {
                        if(!result.contains(allMyContactByUserId.get(index-1)))
                            result.add(allMyContactByUserId.get(index-1));
                    }
                    coutAdditionText(result.toString());
                    if (result.size() == allMyContactByUserId.size()) return result;
                } else{
                    coutErrorText(buttonException);
                }
            } catch (Exception e){
                coutErrorText(buttonException);
                delete = false;
            }
        }
    }

    public static List<GroupUserInformationDTO> deleteUserFromGroup(String additionMessage, GroupDTO groupDTO){
        List<GroupUserInformationDTO> users = groupDTO.GroupUserInformationDTO();
        for (GroupUserInformationDTO user : users) {
            if(user.isAdmin()){
                users.remove(user);
                break;
            }
        }
        for (int i = 0; i < users.size(); i++) {
            coutButtonText(i+1 + ". " + users.get(i).user().logo() + " " + users.get(i).user().fullName() + ".");
        }
        coutNextButton();
        coutExitButton();
        String deleteCommand = "/d";
        coutAdditionText("Command : " + deleteCommand + " index");
        boolean delete = false;
        String inputButton;
        List<GroupUserInformationDTO> result = new ArrayList<>();
        if(users.isEmpty()) return result;
        while (true){
            inputButton = getInputAsString("Choose => ").trim();
            if(inputButton.equals(exitKey)) return null;
            else if(!result.isEmpty() && inputButton.equals(nextKey)) return result;

            try {
                if(inputButton.startsWith(deleteCommand) && inputButton.length()>3 && inputButton.charAt(2)==' ') {
                    StringBuilder command = new StringBuilder(inputButton);
                    for (int i = 0; i < command.length(); i++) {
                        if(command.charAt(i)==' ') {
                            command.deleteCharAt(i);
                            i--;
                        }
                    }
                    inputButton = command.substring(2);
                    delete = true;
                }
                int index = Integer.parseInt(inputButton);
                if(index>0 && index<=users.size()){
                    if(delete){
                        result.remove(index-1);
                        delete = false;
                    } else {
                        if(!result.contains(users.get(index-1)))
                            result.add(users.get(index-1));
                    }
                    coutAdditionText(result.toString());
                } else{
                    coutErrorText(buttonException);
                }
            } catch (Exception e){
                coutErrorText(buttonException);
                delete = false;
            }
        }
    }
}
