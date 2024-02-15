package uz.pdp.messenger.back.payload;

import uz.pdp.messenger.back.modul.handler.TelegramMessage;

import java.util.Date;
import java.util.UUID;

public record TelegramMessageDTO(UUID chatId, String message, Date time) implements MessageDTO{
    public TelegramMessageDTO(TelegramMessage telegramMessage) {
        this(telegramMessage.getHandler().getID(), telegramMessage.getMessage(), telegramMessage.getTime());
    }
}
