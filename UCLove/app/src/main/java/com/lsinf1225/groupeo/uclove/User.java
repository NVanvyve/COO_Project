/**
 * Classe créée par Jean-François GAZET, trouvée sur http://fr.jeffprod.com/blog/2015/utilisation-d-une-base-sqlite-sous-android.html
 * Elle a cependant été adaptée à notre base de données.
 */

package com.lsinf1225.groupeo.uclove;

public class User {

    private int user_id;
    private String user_username;
    private String user_password;
    private String user_first_name;
    private String user_last_name;
    private String user_birth_date;
    private String user_city;
    private String user_language;
    private String user_hair_color;
    private String user_hair_type;
    private String user_eyes_color;
    private String user_sex;
    private String user_sexuality;
    private String user_position;
    private byte[] user_profile_picture;

    // Constructeur
    public User(int user_id,
                String user_username,
                String user_password,
                String user_first_name,
                String user_last_name,
                String user_birth_date,
                String user_city,
                String user_language,
                String user_hair_color,
                String user_hair_type,
                String user_eyes_color,
                String user_sex,
                String user_sexuality,
                String user_position,
                byte[] user_profile_picture) {
        this.user_id=user_id;
        this.user_username=user_username;
        this.user_password=user_password;
        this.user_first_name=user_first_name;
        this.user_last_name=user_last_name;
        this.user_birth_date=user_birth_date;
        this.user_city=user_city;
        this.user_language=user_language;
        this.user_hair_color=user_hair_color;
        this.user_hair_type=user_hair_type;
        this.user_eyes_color=user_eyes_color;
        this.user_sex=user_sex;
        this.user_sexuality=user_sexuality;
        this.user_position=user_position;
        this.user_profile_picture=user_profile_picture;

    }

    // USERID
    public int getUserID() {
        return user_id;
    }

    public void setUserID(int user_id) {
        this.user_id = user_id;
    }

    //USERNAME
    public String getUserUsername() {
        return user_username;
    }

    public void setUserUsername(String user_username) {
        this.user_username = user_username;
    }

    //PASSWORD
    public String getUserPassword() {
        return user_password;
    }

    public void setUserPassword(String user_password) {
        this.user_password = user_password;
    }

    //FIRSTNAME
    public String getUserFirstName() {
        return user_first_name;
    }

    public void setUserFirstName(String user_first_name) {
        this.user_first_name = user_first_name;
    }

    //LASTNAME
    public String getUserLastName() {
        return user_last_name;
    }

    public void setUserLastName(String user_last_name) {
        this.user_last_name = user_last_name;
    }

    //BIRTHDATE
    public String getUserBirthDate() {
        return user_birth_date;
    }

    public void setUserBirthDate(String user_birth_date) {
        this.user_birth_date = user_birth_date;
    }

    //CITY
    public String getUserCity() {
        return user_city;
    }

    public void setUserCity(String user_city) {
        this.user_city = user_city;
    }

    //LANGUAGE
    public String getUserLanguage() {
        return user_language;
    }

    public void setUserLanguage(String user_language) {
        this.user_language = user_language;
    }

    //HAIRCOLOR
    public String getUserHairColor() {
        return user_hair_color;
    }

    public void setUserHairColor(String user_hair_color) {
        this.user_hair_color = user_hair_color;
    }

    //HAIRTYPE
    public String getUserHairType() {
        return user_hair_type;
    }

    public void setUserHairType(String user_hair_type) {
        this.user_hair_type = user_hair_type;
    }

    //EYESCOLOR
    public String getUserEyesColor() {
        return user_eyes_color;
    }

    public void setUserEyesColor(String user_eyes_color) {
        this.user_eyes_color = user_eyes_color;
    }

    //SEX
    public String getUserSex() {
        return user_sex;
    }

    public void setUserSex(String user_sex) {
        this.user_sex = user_sex;
    }

    //SEX
    public String getUserSexuality() {
        return user_sexuality;
    }

    public void setUserSexuality(String user_sexuality) {
        this.user_sexuality = user_sexuality;
    }

    //POSITION
    public String getUserPosition() {
        return user_position;
    }

    public void setUserPosition(String user_position) {
        this.user_position = user_position;
    }

    //PROFILEPIC
    public byte[] getUserProfilePic() {
        return user_profile_picture;
    }

    public void setUserProfilePic(byte[] user_profile_picture) {
        this.user_profile_picture = user_profile_picture;
    }

} // class User