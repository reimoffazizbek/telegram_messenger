package uz.pdp.messenger.back.repository;

import uz.pdp.messenger.back.modul.User;

import java.util.List;
import java.util.UUID;

public interface AuthRepository {
    User findUserByUsername(String username);

    User findUserByPhoneNumber(String phone);

    boolean save(User newUser);

    User findUserByUserId(UUID userId);

    List<User> findAllMyContactByUserId(UUID userId);
}
