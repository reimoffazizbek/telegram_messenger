package uz.pdp.messenger.back.service;

import uz.pdp.messenger.back.exception.InformationException;
import uz.pdp.messenger.back.payload.EditAccountDTO;
import uz.pdp.messenger.back.payload.SignInDTO;
import uz.pdp.messenger.back.payload.SignUpDTO;
import uz.pdp.messenger.back.payload.UserDTO;

import java.util.List;
import java.util.UUID;

public interface AuthService {
    UserDTO signUp(SignUpDTO dto) throws InformationException;
    UserDTO signIn(SignInDTO dto) throws InformationException;

    UserDTO findUserByPhoneNumber(String phoneNumber, UUID id);
    UserDTO findUserByUsername(String username, UUID id);

    UserDTO editeOrCreateContact(UUID firstUserId, UUID secondUserId, String firstname, String lastname);

    boolean isContact(UserDTO firstUserDto, UserDTO secondUserDto);

    List<UserDTO> findAllMyContactByUserId(UUID userId);

    UserDTO editFirstNameAndLastName(UUID currentUserId, String newFirstname, String newLastname);

    UserDTO editUsername(UUID currentUserId, String newUsername);

    UserDTO editBio(UUID currentUserId, String newBio);
}
