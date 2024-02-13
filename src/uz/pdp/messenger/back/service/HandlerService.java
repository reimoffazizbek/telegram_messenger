package uz.pdp.messenger.back.service;

import uz.pdp.messenger.back.payload.ChatDTO;
import uz.pdp.messenger.back.payload.HandlerDTO;

import java.util.List;
import java.util.UUID;

public interface HandlerService {
    List<HandlerDTO> findAllMyHandlerByUserId(UUID id);

    ChatDTO findChatByUserId(UUID firstUser, UUID secondUser);

    ChatDTO createChat(UUID firstUserId, UUID secondUserId);
}
