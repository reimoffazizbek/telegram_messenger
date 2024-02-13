package uz.pdp.messenger.back.payload;

import uz.pdp.messenger.back.modul.handler.chat.ChatUserInformation;

import java.util.UUID;

public record ChatUserInformationDTO(UserDTO user, int newSms, boolean replyOrMark) {
    public ChatUserInformationDTO(ChatUserInformation chatUserInformation) {
        this(new UserDTO(chatUserInformation.getUser()), chatUserInformation.getNewSms(), chatUserInformation.getReplyOrMark());
    }
    public ChatUserInformationDTO(ChatUserInformation chatUserInformation, UUID id) {
        this(new UserDTO(chatUserInformation.getUser(), id), chatUserInformation.getNewSms(), chatUserInformation.getReplyOrMark());
    }
}
