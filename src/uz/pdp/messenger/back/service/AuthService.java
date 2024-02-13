package uz.pdp.messenger.back.service;

import uz.pdp.messenger.back.exception.InformationException;
import uz.pdp.messenger.back.payload.SignInDTO;
import uz.pdp.messenger.back.payload.SignUpDTO;
import uz.pdp.messenger.back.payload.UserDTO;

import java.util.UUID;

public interface AuthService {
    UserDTO signUp(SignUpDTO dto) throws InformationException;
    UserDTO signIn(SignInDTO dto) throws InformationException;

    UserDTO findUserByPhoneNumber(String phoneNumber);

    boolean editeOrCreateContact(UUID firstUserId, UUID secondUserId, String firstname, String lastname);

    boolean isContact(UserDTO firstUserDto, UserDTO secondUserDto);
}
