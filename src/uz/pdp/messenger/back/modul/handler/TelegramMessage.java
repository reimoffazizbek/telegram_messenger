package uz.pdp.messenger.back.modul.handler;

import java.util.Date;
import java.util.UUID;

public class TelegramMessage extends Message {
    private final String message;
    private final Handler handler;
    public TelegramMessage(String message, Handler handler) {
        this.message = message;
        this.handler = handler;
    }

    public String getMessage() {
        return message;
    }

    public Handler getHandler() {
        return handler;
    }
}
