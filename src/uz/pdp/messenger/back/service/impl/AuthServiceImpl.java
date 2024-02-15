package uz.pdp.messenger.back.service.impl;

import uz.pdp.messenger.back.exception.InformationException;
import uz.pdp.messenger.back.modul.User;
import uz.pdp.messenger.back.payload.EditAccountDTO;
import uz.pdp.messenger.back.payload.SignInDTO;
import uz.pdp.messenger.back.payload.SignUpDTO;
import uz.pdp.messenger.back.payload.UserDTO;
import uz.pdp.messenger.back.repository.AuthRepository;
import uz.pdp.messenger.back.repository.impl.AuthRepositoryImpl;
import uz.pdp.messenger.back.service.AuthService;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

import static uz.pdp.messenger.back.exception.ExceptionMessage.*;

public class AuthServiceImpl implements AuthService {
    private static final AuthService instance = new AuthServiceImpl();
    private static final AuthRepository authRepository = AuthRepositoryImpl.getInstance();
    private AuthServiceImpl() {}
    public static AuthService getInstance() {
        return instance;
    }

    @Override
    public UserDTO signUp(SignUpDTO dto) throws InformationException {
        User user = authRepository.findUserByUsername(dto.username());
        boolean exception = false;
        StringJoiner exceptionText = new StringJoiner(" and ");
        if(user!=null){
            exceptionText.add(foundUsername);
            exception = true;
        }
        user = authRepository.findUserByPhoneNumber(dto.phone());
        if(user!=null){
            exceptionText.add(foundPhoneNumber);
            exception = true;
        }
        if(exception) throw new InformationException(exceptionText.toString());

        User newUser = new User(dto.firstname(), dto.lastName(), dto.username(), dto.phone(), dto.password());
        authRepository.save(newUser);
        return new UserDTO(newUser.getId(), newUser.getFirstname(), newUser.getLastname(), newUser.getFullName(), newUser.getLogo(), newUser.getUsername(), newUser.getPhoneNumber(), newUser.getBio(), newUser.getLanguage());
    }

    @Override
    public UserDTO signIn(SignInDTO dto) throws InformationException {
        User user;
        if(dto.username()==null)
            user = authRepository.findUserByPhoneNumber(dto.phone());
        else
            user = authRepository.findUserByUsername(dto.username());

        if(user==null)
            throw new InformationException(notFoundUser);
        else if(!user.getPassword().equals(dto.password()))
            throw new InformationException(notEqualsPassword);

        return new UserDTO(user.getId(), user.getFirstname(), user.getLastname(), user.getFullName(), user.getLogo(), user.getUsername(), user.getPhoneNumber(), user.getBio(), user.getLanguage());
    }

    @Override
    public UserDTO findUserByPhoneNumber(String phoneNumber, UUID id) {
        User userByPhoneNumber = authRepository.findUserByPhoneNumber(phoneNumber);
        if(userByPhoneNumber==null)
            return null;
        return new UserDTO(userByPhoneNumber, id);
    }

    @Override
    public UserDTO findUserByUsername(String username, UUID id) {
        User userByUsername = authRepository.findUserByUsername(username);
        if(userByUsername==null)
            return null;
        return new UserDTO(userByUsername, id);
    }

    @Override
    public UserDTO editeOrCreateContact(UUID firstUserId, UUID secondUserId, String firstname, String lastname) {
        User user = authRepository.findUserByUserId(secondUserId);
        user.setLogoAndFullName(firstUserId, firstname, lastname);
        return new UserDTO(user, firstUserId);
    }

    @Override
    public boolean isContact(UserDTO firstUserDto, UserDTO secondUserDto) {
        User secondUser = authRepository.findUserByUserId(secondUserDto.id());
        return secondUser.isContact(firstUserDto.id());
    }

    @Override
    public List<UserDTO> findAllMyContactByUserId(UUID userId) {
        List<User> users = authRepository.findAllMyContactByUserId(userId);
        List<UserDTO> result = new ArrayList<>();
        for (User user : users) {
            result.add(new UserDTO(user, userId));
        }
        return result;
    }

    @Override
    public UserDTO editFirstNameAndLastName(UUID currentUserId, String newFirstname, String newLastname) {
        User user = authRepository.findUserByUserId(currentUserId);
        user.setLogoAndFullName(currentUserId, newFirstname, newLastname);
        return new UserDTO(user, currentUserId);
    }

    @Override
    public UserDTO editUsername(UUID currentUserId, String newUsername) {
        User user = authRepository.findUserByUserId(currentUserId);
        if(authRepository.findUserByUsername(newUsername) != null) throw new RuntimeException("Such a username exists");
        user.setUsername(newUsername);
        return new UserDTO(user, currentUserId);
    }

    @Override
    public UserDTO editBio(UUID currentUserId, String newBio) {
        User user = authRepository.findUserByUserId(currentUserId);
        user.setBio(newBio);
        return new UserDTO(user, currentUserId);
    }


}
