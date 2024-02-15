package uz.pdp.messenger.back.payload;

import uz.pdp.messenger.back.modul.handler.chat.ChatUserInformation;
import uz.pdp.messenger.back.modul.handler.group.GroupUserInformation;

import java.util.UUID;

public record GroupUserInformationDTO(UserDTO user, int newSms, boolean replyOrMark, boolean isAdmin) {
    public GroupUserInformationDTO(GroupUserInformation groupUserInformation, UUID id) {
        this(new UserDTO(groupUserInformation.getUser(), id), groupUserInformation.getNewSms(), groupUserInformation.getReplyOrMark(), groupUserInformation.isAdmin());
    }
}
