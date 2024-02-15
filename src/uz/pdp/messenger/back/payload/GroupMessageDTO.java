package uz.pdp.messenger.back.payload;


import uz.pdp.messenger.back.modul.handler.chat.ChatMessage;
import uz.pdp.messenger.back.modul.handler.group.GroupMessage;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record GroupMessageDTO(int messageId, UUID groupId, String message, Date time, List<MessageLikeDTO> likes, UserDTO senderUser, boolean seen) implements MessageDTO {
    public GroupMessageDTO(GroupMessage message, List<MessageLikeDTO> likes, UUID userId) {
        this(message.getId(), message.getHandler().getID(), message.getMessage(userId), message.getTime(), likes, new UserDTO(message.getSenderUser().getId(),
                message.getSenderUser().getFirstname(userId),
                message.getSenderUser().getLastname(userId),
                message.getSenderUser().getFullName(userId),
                message.getSenderUser().getLogo(userId),
                message.getSenderUser().getUsername(),
                message.getSenderUser().getPhoneNumber(),
                message.getSenderUser().getBio(),
                message.getSenderUser().getLanguage()), message.isSeen());
    }
}
