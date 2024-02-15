package uz.pdp.messenger.back.repository;

import uz.pdp.messenger.back.modul.handler.Message;
import uz.pdp.messenger.back.modul.handler.MessageLike;
import uz.pdp.messenger.back.modul.handler.chat.ChatMessage;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface MessageRepository {
    Set<Message> findAllMessagesInTheChat(UUID handlerId);

    boolean save(Message newMessage);

    Message deleteChatMessage(int messageId, UUID currentUserId);

    Message findMessageByMessageId(int messageId);

    Set<Message> findAllMessagesInTheGroup(UUID groupId);

    Message deleteGroupMessage(boolean admin, int messageId, UUID userId);
}
