package uz.pdp.messenger.front;

import uz.pdp.messenger.back.modul.User;
import uz.pdp.messenger.back.modul.handler.MessageLike;
import uz.pdp.messenger.back.payload.MessageLikeDTO;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import static uz.pdp.messenger.back.utils.MessageFormatted.*;
import static uz.pdp.messenger.back.utils.Design.*;
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

//        String header = headerOfTheMessage("Azizbek Re'imov123456789123456789", 100, true);
//        System.out.println(header);
//        String body = bodyOfTheMessage("salom", false, true);
//        System.out.println(body);
//        List<MessageLikeDTO> messageLikeDTOS = new ArrayList<>();
//        messageLikeDTOS.add(new MessageLikeDTO("☺️", 54));
//        messageLikeDTOS.add(new MessageLikeDTO("☺️", 54));
//        messageLikeDTOS.add(new MessageLikeDTO("👍", 124));
//        messageLikeDTOS.add(new MessageLikeDTO("☺️", 54));
//        messageLikeDTOS.add(new MessageLikeDTO("☺️", 54));
//        messageLikeDTOS.add(new MessageLikeDTO("👍", 124));
//        messageLikeDTOS.add(new MessageLikeDTO("👍", 124));
//        messageLikeDTOS.add(new MessageLikeDTO("👍", 124));
//        messageLikeDTOS.add(new MessageLikeDTO("👩🏼‍❤️‍💋‍👨🏻", 124));
//        messageLikeDTOS.add(new MessageLikeDTO("👩🏼‍❤️‍💋‍👨🏻", 124));
//        messageLikeDTOS.add(new MessageLikeDTO("👩🏼‍❤️‍💋‍👨🏻", 124));
//        messageLikeDTOS.add(new MessageLikeDTO("👩🏼‍❤️‍💋‍👨🏻", 124));
//        String s = likeOfTheMessage(new ArrayList<>(messageLikeDTOS), new Date(), false, true);
//        System.out.println(s);
////        System.out.println("|----------------------------------------|");
////        System.out.println("|       |       |       |       |        |");
////        System.out.println(" ---------------------15:35-👎- ");
//
////        for (int i = 1; i <= 100; i++) {
////            System.out.println(i + "-" + MessageFormat.format("\u001B[{0}m", i) + "asd");
////        }
////        System.out.println( + "asdasdasdfasdfasdf");
//        System.out.println();

//        System.out.println(messageFormat("Azizbek reimov azatovich", 1, "assalomu aleykum yaxshimisz nima gapla olamda hsadkfhaf asdhf jhask hask hkahsd jhaksj.", messageLikeDTOS, new Date(), true, true));
//
//        messageLikeDTOS.add(new MessageLikeDTO("👩🏼‍❤️‍💋‍👨🏻", 124));
//        messageLikeDTOS.add(new MessageLikeDTO("👩🏼‍", 124));
////        messageLikeDTOS.add(new MessageLikeDTO("⏭️", 124));
////        messageLikeDTOS.add(new MessageLikeDTO("🦄", 124));
//
//        System.out.println(messageFormat("Azizbek reimov azatovich", 1, "assalomu aleykum yaxshimisz nima gapla olamda hsadkfhaf asdhf jhask hask hasd a sdas s ajkshd fkjahs khfashk jdfhkajs hdkfjhaks dhfkhask  hasjdfk askahsd jhaksj.", messageLikeDTOS, new Date(), true, true));

        String x = COLOR_BLUE + "asd" + COLOR_RESET;
        for (int i = 0; i < x.length(); i++) {
            System.out.println(x.charAt(i));
        }
        System.out.println(x.length());
    }

}
