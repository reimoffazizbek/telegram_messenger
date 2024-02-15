package uz.pdp.messenger.back.repository.impl;

import uz.pdp.messenger.back.modul.User;
import uz.pdp.messenger.back.repository.AuthRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AuthRepositoryImpl implements AuthRepository {
    private static final AuthRepository instance = new AuthRepositoryImpl();
    private static final List<User> users = new ArrayList<>();
    private AuthRepositoryImpl(){}
    public static AuthRepository getInstance() {
        return instance;
    }

    @Override
    public User findUserByUsername(String username) {
        for (User user : users) {
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    @Override
    public User findUserByPhoneNumber(String phone) {
        for (User user : users) {
            if(user.getPhoneNumber().equals(phone)){
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean save(User newUser) {
        users.add(newUser);
        return true;
    }

    @Override
    public User findUserByUserId(UUID userId) {
        for (User user : users) {
            if(user.getId().equals(userId)){
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> findAllMyContactByUserId(UUID userId) {
        List<User> result = new ArrayList<>();
        for (User user : users) {
            if(!user.getId().equals(userId) && user.isContact(userId)){
                result.add(user);
            }
        }
        return result;
    }
}
