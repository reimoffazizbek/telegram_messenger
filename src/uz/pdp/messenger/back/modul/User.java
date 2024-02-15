package uz.pdp.messenger.back.modul;

import uz.pdp.messenger.back.enums.Language;
import uz.pdp.messenger.back.utils.Design;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User{
    private final UUID id = UUID.randomUUID(); // *Unique
    private Map<UUID, NameAndLogo> nameAndLogo = new HashMap<>();
    private String username; // *Unique
    private String bio;
    private Language language = Language.UZ;
    private String phoneNumber; // *Unique
    private String password;

    public User(String firstname, String lastname, String username, String phoneNumber, String password) {
        this.nameAndLogo.put(this.id, new NameAndLogo(firstname, lastname));
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public boolean isContact(UUID hisOwnId){
        if(this.id.equals(hisOwnId)) return true;
        return nameAndLogo.containsKey(hisOwnId);
    }

    public UUID getId() {
        return id;
    }

    public void setLogoAndFullName(UUID hisOwnId, String firstname, String lastname){
        NameAndLogo nameAndLogoObj = nameAndLogo.get(hisOwnId);
        if(nameAndLogoObj==null){
            nameAndLogo.put(hisOwnId, new NameAndLogo(firstname, lastname));
        } else {
            nameAndLogoObj.setLogoAndFullName(firstname, lastname);
        }
    }

    public String getFirstname(UUID hisOwnId) {
        NameAndLogo nameAndLogoObj = nameAndLogo.get(hisOwnId);
        if(nameAndLogoObj==null){
            return nameAndLogo.get(this.id).firstname;
        }
        return nameAndLogoObj.firstname;
    }
    public String getFirstname(){
        return nameAndLogo.get(this.id).firstname;
    }


    public String getLastname(UUID hisOwnId) {
        NameAndLogo nameAndLogoObj = nameAndLogo.get(hisOwnId);
        if(nameAndLogoObj==null){
            return nameAndLogo.get(this.id).lastname;
        }
        return nameAndLogoObj.lastname;
    }

    public String getLastname(){
        return nameAndLogo.get(this.id).lastname;
    }

    public String getFullName(UUID hisOwnId) {
        NameAndLogo nameAndLogoObj = nameAndLogo.get(hisOwnId);
        if(nameAndLogoObj==null){
            return nameAndLogo.get(this.id).fullName;
        }
        return nameAndLogoObj.fullName;
    }
    public String getFullName(){
        return nameAndLogo.get(this.id).fullName;
    }
    public String getLogo(UUID hisOwnId) {
        NameAndLogo nameAndLogoObj = nameAndLogo.get(hisOwnId);
        if(nameAndLogoObj==null){
            return nameAndLogo.get(this.id).logo;
        }
        return nameAndLogoObj.logo;
    }

    public String getLogo(){
        return nameAndLogo.get(this.id).logo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public class NameAndLogo{
        private String firstname;
        private String lastname;
        private String fullName;
        private String logo;

        public NameAndLogo(String firstname, String lastname) {
            this.setLogoAndFullName(firstname, lastname);
        }

        /**
         * @discription agar nomini ozgartirmoqchi bo'lsa xardoim shunga murojat qilishimiz kerak!
         *              sababi fullname bilan logotipxam ozgarishi kerak!!
         * @param firstname
         * @param lastname
         */
        private void setLogoAndFullName(String firstname, String lastname) {
            if (firstname != null) {
                this.firstname = firstname;
                this.fullName = firstname;
                if(lastname!=null){
                    this.lastname = lastname;
                    this.fullName += " " + lastname;
                    this.logo = "[" + Design.logoChars(firstname.charAt(0)) + Design.logoChars(lastname.charAt(0)) + "]";
                } else{
                    this.logo = "[" + Design.logoChars(firstname.charAt(0)) + "]";
                }
            } else {
                this.lastname = lastname;
                this.fullName = lastname;
                this.logo = "[" + Design.logoChars(lastname.charAt(0)) + "]";
            }
        }

    }

}
