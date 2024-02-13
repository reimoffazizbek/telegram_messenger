package uz.pdp.messenger.back.utils;


import static java.lang.Character.*;
import static uz.pdp.messenger.front.CoutAndCin.*;


public interface UserInformationUtils {

    static boolean checkFirstname(String firstname){
        if(firstname.isBlank() || firstname.equals(nextKey) || firstname.equals(backKey)) return false;
        return true;
    }
    static boolean checkLastname(String lastname){
        if(lastname.isBlank() || lastname.equals(nextKey) || lastname.equals(backKey)) return false;
        return true;
    }
    static boolean checkUsername(String username) {
        username = username.trim();
        if (username.length() < 5) return false;
        else if (!username.startsWith("@")) return false;
        return true;
    }

    static boolean checkPhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.trim();
        if (phoneNumber.length() != 13) return false;
        else if (!phoneNumber.startsWith("+998")) return false;
        for (int i = 4; i < phoneNumber.length(); i++) {
            if (!isDigit(phoneNumber.charAt(i))) return false;
        }
        return true;
    }

    static boolean checkPassword(String password) {
        password = password.trim();
        if (password.length() < 8 || password.length() > 16) return false;
        return true;
    }

    static String correctPhoneNumber(String phoneNumber) {
        return !phoneNumber.startsWith("+998") ? "+998" + phoneNumber : phoneNumber;
    }

    static String correctUsername(String username) {
        return !username.startsWith("@") ? "@" + username : username;
    }
}
