package uz.pdp.messenger.front.create;

import static uz.pdp.messenger.front.CoutAndCin.*;
import static uz.pdp.messenger.back.utils.UserInformationUtils.*;

public interface CreateUserInformation {
    static String createFirstname(String additionMessage, boolean next, boolean back){
        coutAdditionText(additionMessage);
        if(next) coutNextButton();
        if(back) coutBackButton();
        coutExitButton();
        String firstname;
        while (true){
            firstname = getInputAsString("Firstname = ");
            if(firstname.equals(exitKey)) return firstname;
            if(next && firstname.equals(nextKey)) return firstname;
            if(back && firstname.equals(backKey)) return firstname;
            if(checkFirstname(firstname)) return firstname;
            else coutErrorText(informationException);
        }
    }
    static String createLastname(String additionMessage, boolean next, boolean back){
        coutAdditionText(additionMessage);
        if(next) coutNextButton();
        if(back) coutBackButton();
        coutExitButton();
        String lastname;
        while (true){
            lastname = getInputAsString("Lastname = ");
            if(lastname.equals(exitKey)) return lastname;
            if(next && lastname.equals(nextKey)) return lastname;
            if(back && lastname.equals(backKey)) return lastname;
            if(checkLastname(lastname)) return lastname;
            else coutErrorText(informationException);
        }
    }
    static String createUsername(String additionMessage, boolean next, boolean back){
        coutAdditionText(additionMessage);
        if(next) coutNextButton();
        if(back) coutBackButton();
        coutExitButton();
        String username;
        while (true){
            username = getInputAsString("Username = ");
            if(username.equals(exitKey)) return username;
            if(next && username.equals(nextKey)) return username;
            if(back && username.equals(backKey)) return username;
            username = correctUsername(username);
            if(checkUsername(username)) return username;
            else coutErrorText(informationException);
        }
    }

    static String createPhoneNumber(String additionMessage, boolean next, boolean back){
        coutAdditionText(additionMessage);
        if(next) coutNextButton();
        if(back) coutBackButton();
        coutExitButton();
        String phoneNumber;
        while (true){
            phoneNumber = getInputAsString("Phone number = ");
            if(phoneNumber.equals(exitKey)) return phoneNumber;
            if(next && phoneNumber.equals(nextKey)) return phoneNumber;
            if(back && phoneNumber.equals(backKey)) return phoneNumber;
            phoneNumber = correctPhoneNumber(phoneNumber);
            if(checkPhoneNumber(phoneNumber)) return phoneNumber;
            else coutErrorText(informationException);
        }
    }

    static String createPassword(String additionMessage, boolean next, boolean back){
        coutAdditionText(additionMessage);
        if(next) coutNextButton();
        if(back) coutBackButton();
        coutExitButton();
        String password;
        while (true){
            password = getInputAsString("Password = ");
            if(password.equals(exitKey)) return password;
            if(next && password.equals(nextKey)) return password;
            if(back && password.equals(backKey)) return password;
            if(checkPassword(password)) return password;
            else coutErrorText(informationException);
        }
    }

    static String createPhoneNumberOrUsername(String additionMessage, boolean next, boolean back){
        coutAdditionText(additionMessage);
        if(next) coutNextButton();
        if(back) coutBackButton();
        coutExitButton();
        String phoneOrUsername;
        String phone;
        String username;
        while (true){
            phoneOrUsername = getInputAsString("Phone number or username = ");
            if(phoneOrUsername.equals(exitKey)) return phoneOrUsername;
            if(next && phoneOrUsername.equals(nextKey)) return phoneOrUsername;
            if(back && phoneOrUsername.equals(backKey)) return phoneOrUsername;
            phone = correctPhoneNumber(phoneOrUsername);
            if(checkPhoneNumber(phone)) return phone;
            username = correctUsername(phoneOrUsername);
            if(checkUsername(username)) return username;
            else coutErrorText(informationException);
        }
    }
}
