package uz.pdp.messenger.back.repository;

import uz.pdp.messenger.back.modul.handler.Handler;
import uz.pdp.messenger.back.modul.handler.chat.Chat;
import uz.pdp.messenger.back.modul.handler.group.Group;

import java.util.List;
import java.util.UUID;

public interface HandlerRepository {
    List<Handler> findAllMyHandlerByUserId(UUID id);

    Chat findChatByUserId(UUID firstUserId, UUID secondUserId);

    boolean save(Handler newChat);

    Chat findChatByChatId(UUID chatId);

    boolean delete(UUID chatId);

    Group findGroupByGroupId(UUID groupId);
}
