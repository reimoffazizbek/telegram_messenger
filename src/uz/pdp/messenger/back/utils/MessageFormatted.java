package uz.pdp.messenger.back.utils;

import uz.pdp.messenger.back.modul.User;
import uz.pdp.messenger.back.modul.handler.MessageLike;
import uz.pdp.messenger.back.payload.MessageLikeDTO;
import uz.pdp.messenger.back.payload.UserDTO;
import uz.pdp.messenger.back.service.AuthService;
import uz.pdp.messenger.back.service.impl.AuthServiceImpl;

import java.text.MessageFormat;
import java.util.*;

import static uz.pdp.messenger.back.utils.Design.*;

import static uz.pdp.messenger.back.utils.UserInformationUtils.*;

public interface MessageFormatted {
    int MESSAGE_WIDTH = 40;
    int MY_MESSAGE = 100 - MESSAGE_WIDTH;
    int HIS_MESSAGE = 5;

    String yesSeen = "👍";
    String notSeen = "👎";


    public static String messageFormat(String fullName, int Id, String message, List<MessageLikeDTO> like, UUID currentId, Date date, boolean seen, boolean myMessage) {
        String space;
        if(myMessage) space = " ".repeat(MY_MESSAGE);
        else space = " ".repeat(HIS_MESSAGE);
        StringBuilder res = new StringBuilder(space + COLOR_RESET + " ________________________________________ \n");
        res.append(space).append("|\u001B[34m %-19s   \u001B[0m   |\u001B[34m MID = %-6d\u001B[0m|\n");
        res.append(space).append("|----------------------------------------|\n");
        if (fullName.length() > 16) {
            fullName = fullName.substring(0, 16) + "...";
        }
        res = new StringBuilder(res.toString().formatted(fullName, Id));
        res.append(MyMessageFormat(message, 34, space));
        res.append(space).append("|----------------------------------------|\n");
        res.append(EmojiFormat(like, space, currentId));
        int likeSoni = (like.size() % 5);
        if(likeSoni==0)
            res.append(space).append("|                              ");
        else {
            res.append("       ".repeat(4 - likeSoni));
        }
        if(likeSoni!=4)
            res.append("|");
        String time = date.getHours() + ":" + date.getMinutes();
        res.append("\u001B[34m %-8s\u001B[0m|".formatted(time));
        if(myMessage) res.append("\n").append(space).append(" -------------------------------------").append(seen ? yesSeen : notSeen).append("- ");
        else res.append("\n").append(space).append(" ----------------------------------------");
        return res.toString();
    }

    private static String EmojiFormat(List<MessageLikeDTO> like, String space, UUID userId) {
        StringBuilder res = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        if(!like.isEmpty()) res.append(space).append("|");
        for (int i = 0; i < like.size(); i++) {
            if(i % 5 == 0) {
                temp.append(like.get(i));
                for (int j = temp.length() - like.get(i).like().length() + 1; j <= 6; j++) {
                    temp.append(" ");
                }
                if(like.get(i).users().contains(userId))
                    res.append(COLOR_BLUE + temp + COLOR_RESET).append("|");
                else
                    res.append(temp).append("|");
            }
            else if((i+1) % 5 == 0){
                temp.append(like.get(i));
                for (int j = temp.length() - like.get(i).like().length() + 1; j <= 7; j++) {
                    temp.append(" ");
                }
                if(like.get(i).users().contains(userId))
                    res.append(COLOR_BLUE + temp + COLOR_RESET).append("|\n");
                else
                    res.append(temp).append("|\n");
                if(i+1<like.size()) res.append(space).append("|");
            }
            else{
                temp.append(like.get(i));
                for (int j = temp.length() - like.get(i).like().length() + 1; j <= 4; j++) {
                    temp.append(" ");
                }
                if(like.get(i).users().contains(userId))
                    res.append(COLOR_BLUE + temp + COLOR_RESET).append("|");
                else
                    res.append(temp).append("|");
            }
            temp.delete(0, temp.length());
        }
        return res.toString();
    }

    private static String MyMessageFormat(String message, int tempLength, String space) {
        StringBuilder res = new StringBuilder();
        ArrayList<String> split = messageSplit(message, tempLength);

        for (String s : split) {
            res.append(space).append("|  \u001B[33m ").append(s).append(" \u001B[0m |\n");
        }
        return res.toString();
    }

    private static ArrayList<String> messageSplit(String message, int lineLength) {
        StringBuilder str = new StringBuilder();
        ArrayList<String> res = new ArrayList<>();
        for (String s : message.split(" +")) {
            if(str.length() + s.length() > lineLength){
                str.append(" ".repeat(lineLength - str.length() + 1));
                res.add(str.toString());
                str.delete(0, str.length());
            }
            if (s.length() <= lineLength) {
                str.append(s).append(" ");
            } else {
                res.remove(0);
                for (int i = 0; i < s.length(); i += lineLength) {
                    if (i + lineLength <= s.length()) {
                        str.append(s, i, i + lineLength).append(" ");
                        res.add(str.toString());
                        str.delete(0, str.length());
                    } else {
                        str.append(s.substring(i)).append(" ");
                    }
                }
            }
        }
        str.append(" ".repeat(lineLength - str.length() + 1));
        res.add(str.toString());
        return res;
    }

//    static String headerOfTheMessage(String fullName, int messageId, boolean myMessage) {
//        if (MESSAGE_WIDTH < 30)
//            throw new IllegalArgumentException("Hajim juda kichik!!!");
//
//        int numOfSpace = myMessage ? MY_MESSAGE : HIS_MESSAGE;
//        String space = " ".repeat(numOfSpace);
//        String resultHeader = space + " " + "_".repeat(MESSAGE_WIDTH) + " \n";
//        fullName = (fullName.length() >= MESSAGE_WIDTH - 13) ? fullName.substring(0, MESSAGE_WIDTH - 16) + "..." : fullName;
//
//
//        resultHeader += space + MessageFormat.format("| %-{0}s |  %-7d |%n", MESSAGE_WIDTH - 13);
//        resultHeader = resultHeader.formatted(fullName, messageId);
//        resultHeader += space + "|" + "-".repeat(MESSAGE_WIDTH) + "|";
//        return resultHeader;
//    }
//
//    static String bodyOfTheMessage(String msg, boolean top, boolean myMessage) {
//        StringBuilder resultBody = new StringBuilder("\n");
//        int numOfSpace = myMessage ? MY_MESSAGE : HIS_MESSAGE;
//        int messageWidth = MESSAGE_WIDTH - 4;
//        StringBuilder message = deleteSpace(msg, messageWidth);
//        String space = " ".repeat(numOfSpace);
//        if(top) resultBody.append(space + " " + "_".repeat(MESSAGE_WIDTH) + " \n");
//        String temp = space + MessageFormat.format("|  %-{0}s  |\n", messageWidth);
//        String tmp;
//        while (true) {
//            if (message.length() <= messageWidth) {
//                resultBody.append(temp.formatted(message.toString().trim()));
//                break;
//            } else {
//                if (message.lastIndexOf(" ", messageWidth) == -1) {
//                    tmp = message.substring(0, messageWidth);
//                    message.delete(0, tmp.length());
//                } else {
//                    tmp = message.substring(0, message.lastIndexOf(" ", messageWidth));
//                    message.delete(0, tmp.length() + 1);
//                }
//                resultBody.append(temp.formatted(tmp.trim()));
//            }
//        }
//        return resultBody.toString();
//    }
//    static String likeOfTheMessage(List<MessageLikeDTO> likes, Date date, boolean seen, boolean myMessage){
//        int numOfSpace = myMessage ? MY_MESSAGE : HIS_MESSAGE;
//        boolean messageningEgasi = numOfSpace==MY_MESSAGE;
//        String space = " ".repeat(numOfSpace);
//        StringBuilder resultLike = new StringBuilder();
//        resultLike.append(space).append("|").append("-".repeat(MESSAGE_WIDTH)).append("|\n");
//        String emojiTemp = MessageFormat.format("%-{0}s|", (MESSAGE_WIDTH-14)/5);
//        String temp = "|{0}{1}{2}{3}{4}\n";
//        String[] strings = new String[5];
//
//        String time = date.getHours() + ":" + date.getMinutes();
//        if(messageningEgasi)
//            resultLike.append(space).append("|").append("-".repeat(MESSAGE_WIDTH-(time.length()+1+yesSeen.length()))).append(seen ? yesSeen : notSeen).append("-").append(time).append("|");
//        else
//            resultLike.append(space).append("|").append("-".repeat(MESSAGE_WIDTH-(time.length()))).append(time).append("|");
//        return resultLike.toString();
//    }
//
//    private static StringBuilder deleteSpace(String message, int messageWidth) {
//        StringBuilder res = new StringBuilder(message.trim());
//        // logica yozishim mumkun
//        return res;
//    }
}
