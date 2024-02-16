package uz.pdp.messenger.front;

import java.util.Scanner;

import static uz.pdp.messenger.back.utils.Design.*;

public interface CoutAndCin {
    Scanner inputStr = new Scanner(System.in);
    Scanner inputInt = new Scanner(System.in);
    String buttonException = "\nNo such button exists❗";
    String informationException = "\nYou have entered incorrect information❗";
    String commandNotFound = "\nno such command found❗";
    String exitKey = "00";
    String backKey = "-";
    String nextKey = "+";
    String programName = "Messenger";
    int consoleX = 100;

    static void coutMainText(String text){
        String repeat = "-".repeat((consoleX/2) - (text.length()/2));

        System.out.println(COLOR_BLUE + repeat + text + repeat);
    }
    static void coutAdditionText(String text){
        System.out.println(COLOR_YELLOW + text);
    }
    static void coutErrorText(String text){
        System.out.println(COLOR_RED + text);
    }
    static void coutButtonText(String text){
        System.out.println(COLOR_PURPLE + text);
    }
    static void coutExitButton(){
        System.out.println(COLOR_PURPLE + exitKey + ". Exit🔚");
    }
    static void coutBackButton(){
        System.out.println(COLOR_PURPLE + backKey + ". Back🔙`");
    }
    static void coutNextButton(){
        System.out.println(COLOR_PURPLE + nextKey + ". Next⏭️");
    }
    static void coutMyMessage(String message){
        System.out.println(COLOR_WHITE + message);
    }
    static void coutHisMessage(String message){
        System.out.println(COLOR_CYAN + message);
    }
    static String getInputAsString(String message){
        System.out.print(COLOR_CYAN + message);
        return inputStr.nextLine();
    }

    static int getInputAsInt(String message){
        System.out.print(COLOR_CYAN + message);
        return inputInt.nextInt();
    }

}
