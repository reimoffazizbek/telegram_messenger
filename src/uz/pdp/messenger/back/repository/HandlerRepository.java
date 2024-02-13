package uz.pdp.messenger.back.repository;

import uz.pdp.messenger.back.modul.handler.Handler;
import uz.pdp.messenger.back.modul.handler.chat.Chat;

import java.util.List;
import java.util.UUID;

public interface HandlerRepository {
    List<Handler> findAllMyHandlerByUserId(UUID id);

    Chat findChatByUserId(UUID firstUserId, UUID secondUserId);

    boolean save(Chat newChat);
}
