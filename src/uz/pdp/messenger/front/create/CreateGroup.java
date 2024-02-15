package uz.pdp.messenger.front.create;

import uz.pdp.messenger.back.payload.CreateGroupDTO;
import uz.pdp.messenger.back.payload.GroupDTO;
import uz.pdp.messenger.back.payload.UserDTO;
import uz.pdp.messenger.back.service.HandlerService;
import uz.pdp.messenger.back.service.impl.HandlerServiceImpl;

import static uz.pdp.messenger.front.create.ChannelOrGroupInformation.*;
import static uz.pdp.messenger.front.CoutAndCin.*;

import java.util.List;

public class CreateGroup {
    private static byte level;
    private static String groupName;
    private static UserDTO currentUser;
    private static List<UserDTO> users;
    private static final HandlerService handlerService = HandlerServiceImpl.getInstance();
    public static GroupDTO createGroup(UserDTO user){
        level = 1;
        currentUser = user;
        while (true){
            if(level==1){
                groupName = createName("Enter the name of your group", false, false);
                if(groupName.equals(exitKey)) return null;
                level++;
            } else if(level==2){
                users = chooseUser(currentUser.id(), "Select if you want to add users to the group.");
                if(users == null) return null;
                level++;
            } else {
                GroupDTO newGroup = handlerService.createGroup(new CreateGroupDTO(currentUser, groupName, users));
                return newGroup;
            }
        }
    }
}
