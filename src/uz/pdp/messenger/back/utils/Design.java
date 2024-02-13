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

    static String logoChars(char ch){
        ch = toUpperCase(ch);
        Map<Character, String> logoChar = new HashMap<>(26);
        logoChar.put('A', "\uD835\uDCD0");
        logoChar.put('B', "\uD835\uDCD1");
        logoChar.put('C', "\uD835\uDCD2");
        logoChar.put('D', "\uD835\uDCD3");
        logoChar.put('E', "\uD835\uDCD4");
        logoChar.put('F', "\uD835\uDCD5");
        logoChar.put('G', "\uD835\uDCD6");
        logoChar.put('H', "\uD835\uDCD7");
        logoChar.put('I', "\uD835\uDCD8");
        logoChar.put('J', "\uD835\uDCD9");
        logoChar.put('K', "\uD835\uDCDA");
        logoChar.put('L', "\uD835\uDCDB");
        logoChar.put('M', "\uD835\uDCDC");
        logoChar.put('N', "\uD835\uDCDD");
        logoChar.put('O', "\uD835\uDCDE");
        logoChar.put('P', "\uD835\uDCDF");
        logoChar.put('Q', "\uD835\uDCE0");
        logoChar.put('R', "\uD835\uDCE1");
        logoChar.put('S', "\uD835\uDCE2");
        logoChar.put('T', "\uD835\uDCE3");
        logoChar.put('U', "\uD835\uDCE4");
        logoChar.put('V', "\uD835\uDCE5");
        logoChar.put('W', "\uD835\uDCE6");
        logoChar.put('X', "\uD835\uDCE7");
        logoChar.put('Y', "\uD835\uDCE8");
        logoChar.put('Z', "\uD835\uDCE9");
        String res = logoChar.get(ch);
        return res!=null ? res : String.valueOf(ch);
    }
}
