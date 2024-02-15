
package uz.pdp.messenger.back.service;

import uz.pdp.messenger.back.modul.handler.Handler;
import uz.pdp.messenger.back.modul.handler.Message;
import uz.pdp.messenger.back.payload.*;


import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface MessageService {

    List<MessageDTO> findAllMessagesInTheChat(UUID chatId, UUID userId);

    ChatMessageDTO sendChatMessage(String message, UserDTO currentUser, ChatDTO currentChat);

    boolean deleteChatMessage(int messageId, UserDTO currentUser);

    boolean editMessage(String newMessage, int messageId, UUID currentUserId);

    List<MessageDTO> findAllMessagesInTheGroup(UUID groupId, UUID userId);

    GroupMessageDTO sendGroupMessage(String message, UserDTO currentUser, GroupDTO currentGroup);

    boolean clickLikeTheMessage(int messageId, String like, UserDTO currentUser);

    boolean deleteGroupMessage(GroupUserInformationDTO myInformation, int messageId);
}
