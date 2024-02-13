package uz.pdp.messenger.back.modul.handler;

import java.util.UUID;

public abstract class Handler {
    private final UUID ID = UUID.randomUUID();

    public UUID getID() {
        return ID;
    }
}
