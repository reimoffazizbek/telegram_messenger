package uz.pdp.messenger.back.utils;

public interface ChannelOrGroupInformationUtils {
    static boolean checkName(String name){
        name = name.trim();
        if(name.isBlank()) return false;
        return true;
    }

    static boolean checkBio(String bio){
        bio = bio.trim();
        if(bio.isBlank() || bio.length()>30) return false;
        return true;
    }
}
