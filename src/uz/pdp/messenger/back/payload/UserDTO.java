package uz.pdp.messenger.back.payload;

import uz.pdp.messenger.back.enums.Language;
import uz.pdp.messenger.back.modul.User;

import java.util.UUID;

public record UserDTO(UUID id, String firstname, String lastname, String fullName, String logo, String username, String phoneNumber, String bio, Language lang) {
    public UserDTO(User user, UUID id) {
        this(user.getId(), user.getFirstname(id), user.getLastname(id), user.getFullName(id), user.getLogo(id), user.getUsername(), user.getPhoneNumber(), user.getBio(), user.getLanguage());
    }

    @Override
    public String toString() {
        return "[" + logo + ", " + fullName + "]";
    }
}
