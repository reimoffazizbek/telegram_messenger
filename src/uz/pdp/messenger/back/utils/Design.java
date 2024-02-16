package uz.pdp.messenger.back.utils;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Character.*;

public interface Design {

    String COLOR_RESET = "\u001B[0m";
    String COLOR_BLACK = "\u001B[30m";
    String COLOR_RED = "\u001B[31m";
    String COLOR_GREEN = "\u001B[32m";
    String COLOR_YELLOW = "\u001B[33m";
    String COLOR_BLUE = "\u001B[34m";
    String COLOR_PURPLE = "\u001B[35m";
    String COLOR_CYAN = "\u001B[36m";
    String COLOR_WHITE = "\u001B[37m";
    String CONTACT_COLOR = COLOR_GREEN;
    String NO_CONTACT_COLOR = COLOR_RESET;
    String NEW_SMS = "\u001B[1m" + "\u001B[4m";
    String GROUP_COLOR = COLOR_YELLOW;
    String[] logoChar = new String[]{
            "\uD835\uDCD0",
            "\uD835\uDCD1",
            "\uD835\uDCD2",
            "\uD835\uDCD3",
            "\uD835\uDCD4",
            "\uD835\uDCD5",
            "\uD835\uDCD6",
            "\uD835\uDCD7",
            "\uD835\uDCD8",
            "\uD835\uDCD9",
            "\uD835\uDCDA",
            "\uD835\uDCDB",
            "\uD835\uDCDC",
            "\uD835\uDCDD",
            "\uD835\uDCDE",
            "\uD835\uDCDF",
            "\uD835\uDCE0",
            "\uD835\uDCE1",
            "\uD835\uDCE2",
            "\uD835\uDCE3",
            "\uD835\uDCE4",
            "\uD835\uDCE5",
            "\uD835\uDCE6",
            "\uD835\uDCE7",
            "\uD835\uDCE8",
            "\uD835\uDCE9"
    };


    static String logoChars(char ch){
        ch = toUpperCase(ch);
        String res = logoChar[ch-65];
        return res!=null ? res : String.valueOf(ch);
    }
}
