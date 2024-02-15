package uz.pdp.messenger.back.modul.handler.group;

import uz.pdp.messenger.back.modul.User;
import uz.pdp.messenger.back.modul.handler.Handler;
import uz.pdp.messenger.back.utils.Design;

import java.util.ArrayList;
import java.util.List;

import static uz.pdp.messenger.back.utils.Design.*;

public class Group extends Handler {
    private String groupName;
    private String logo;
    private String bio;
    private List<GroupUserInformation> users = new ArrayList<>();

    public Group(String groupName, User admin, List<User> users) {
        this.groupName = groupName;
        this.logo = "[" + logoChars(groupName.charAt(0)) + "]";
        this.users.add(new GroupUserInformation(admin, true));
        for (User user : users) {
            this.users.add(new GroupUserInformation(user));
        }
    }

    public List<GroupUserInformation> getUsers() {
        return users;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getLogo() {
        return logo;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
        this.logo = "[" + logoChars(groupName.charAt(0)) + "]";
    }
}
