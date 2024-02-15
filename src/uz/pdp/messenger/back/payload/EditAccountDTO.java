package uz.pdp.messenger.back.payload;

import java.util.UUID;

public record EditAccountDTO(UUID currentUserId, String firstname, String lastname, String username, String password, String bio) {
}
