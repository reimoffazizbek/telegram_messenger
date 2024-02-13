package uz.pdp.messenger.front;

import uz.pdp.messenger.back.modul.User;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
//        User user1 = new User("Azizbek", null, "@azizbek", "+998904217300", "1234567a");
//        User user2 = new User("Eldor", "Xaytbaev", "@eldor", "+998900959919", "1234567e");
//        User user3 = new User(null, "Kadirov", "@kadirovvv", "+998912588363", "1234567j");
//
//        System.out.println(user1.getLogo(user1.getId()) + " " + user1.getFullName(user1.getId()));
//        System.out.println(user2.getLogo(user2.getId()) + " " + user2.getFullName(user2.getId()));
//        System.out.println(user3.getLogo(user3.getId()) + " " + user3.getFullName(user3.getId()));
//
//        user1.setLogoAndFullName(user2.getId(), "Azizbek", "Jora");
//
//        System.out.println(user1.getLogo(user1.getId()) + " " + user1.getFullName(user1.getId()));
//        System.out.println(user1.getLogo(user2.getId()) + " " + user1.getFullName(user2.getId()));
//
//        user1.setLogoAndFullName(user1.getId(), "Azizbek", "Reimov");
//
//        System.out.println(user1.getLogo(user1.getId()) + " " + user1.getFullName(user1.getId()));
//        System.out.println(user1.getLogo(user2.getId()) + " " + user1.getFullName(user2.getId()));
//        System.out.println(user1.getLogo(user3.getId()) + " " + user1.getFullName(user3.getId()));
        Registration.StartRegistration();
    }

}
