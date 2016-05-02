/**
 * Classe créée par Jean-François GAZET, trouvée sur http://fr.jeffprod.com/blog/2015/utilisation-d-une-base-sqlite-sous-android.html
 * Elle a cependant été adaptée à notre base de données.
 */

package com.lsinf1225.groupeo.uclove.database;

public class Preferences {

    private int pref_id;
    private int pref_user_id;
    private int pref_language_FR;
    private int pref_language_EN;
    private int pref_hair_color_blond;
    private int pref_hair_color_brown;
    private int pref_hair_color_black;
    private int pref_hair_color_ginger;
    private int pref_hair_color_greywhite;
    private int pref_hair_color_other;
    private int pref_hair_type_short;
    private int pref_hair_type_medium;
    private int pref_hair_type_long;
    private int pref_eyes_color_blue;
    private int pref_eyes_color_brown;
    private int pref_eyes_color_black;
    private int pref_eyes_color_green;
    private int pref_eyes_color_grey;
    private int pref_age_max;
    private int pref_age_min;
    private int pref_distance_max;

    // Constructeur
    public Preferences(int pref_id,
                       int pref_user_id,
                       int pref_language_FR,
                       int pref_language_EN,
                       int pref_hair_color_blond,
                       int pref_hair_color_brown,
                       int pref_hair_color_black,
                       int pref_hair_color_ginger,
                       int pref_hair_color_greywhite,
                       int pref_hair_color_other,
                       int pref_hair_type_short,
                       int pref_hair_type_medium,
                       int pref_hair_type_long,
                       int pref_eyes_color_blue,
                       int pref_eyes_color_brown,
                       int pref_eyes_color_black,
                       int pref_eyes_color_green,
                       int pref_eyes_color_grey,
                       int pref_age_max,
                       int pref_age_min,
                       int pref_distance_max)
    {
        this.pref_id=pref_id;
        this.pref_user_id=pref_user_id;
        this.pref_language_FR=pref_language_FR;
        this.pref_language_EN=pref_language_EN;
        this.pref_hair_color_black=pref_hair_color_black;
        this.pref_hair_color_blond=pref_hair_color_blond;
        this.pref_hair_color_brown=pref_hair_color_brown;
        this.pref_hair_color_ginger=pref_hair_color_ginger;
        this.pref_hair_color_greywhite=pref_hair_color_greywhite;
        this.pref_hair_color_other=pref_hair_color_other;
        this.pref_hair_type_long=pref_hair_type_long;
        this.pref_hair_type_medium=pref_hair_type_medium;
        this.pref_hair_type_short=pref_hair_type_short;
        this.pref_eyes_color_black=pref_eyes_color_black;
        this.pref_eyes_color_blue=pref_eyes_color_blue;
        this.pref_eyes_color_brown=pref_eyes_color_brown;
        this.pref_eyes_color_green=pref_eyes_color_green;
        this.pref_eyes_color_grey=pref_eyes_color_grey;
        this.pref_age_max=pref_age_max;
        this.pref_age_min=pref_age_min;

        this.pref_distance_max=pref_distance_max;

    }

    //ID
    public int getPrefID() {
        return pref_id;
    }

    public void setPrefID(int pref_id) {
        this.pref_id = pref_id;
    }

    //USERID
    public int getPrefUserID() {
        return pref_user_id;
    }

    public void setPrefUserID(int pref_user_id) {
        this.pref_user_id = pref_user_id;
    }

    //LANGUAGE_FR
    public int getPrefLanguageFR() {
        return pref_language_FR;
    }

    public void setPrefLanguageFR(int pref_language_FR) {
        this.pref_language_FR = pref_language_FR;
    }

    //LANGUAGE_EN
    public int getPrefLanguageEN() {
        return pref_language_EN;
    }

    public void setPrefLanguageEN(int pref_language_EN) {
        this.pref_language_EN = pref_language_EN;
    }

    //HAIR_BLOND
    public int getPrefHairColorBlond() {
        return pref_hair_color_blond;
    }

    public void setPrefHairColorBlond(int pref_hair_color_blond) {
        this.pref_hair_color_blond = pref_hair_color_blond;
    }

    //HAIR_BROWN
    public int getPrefHairColorBrown() {
        return pref_hair_color_brown;
    }

    public void setPrefHairColorBrown(int pref_hair_color_brown) {
        this.pref_hair_color_brown = pref_hair_color_brown;
    }

    //HAIR_BLACK
    public int getPrefHairColorBlack() {
        return pref_hair_color_black;
    }

    public void setPrefHairColorBlack(int pref_hair_color_black) {
        this.pref_hair_color_black = pref_hair_color_black;
    }

    //HAIR_GINGER
    public int getPrefHairColorGinger() {
        return pref_hair_color_ginger;
    }

    public void setPrefHairColorGinger(int pref_hair_color_ginger) {
        this.pref_hair_color_ginger = pref_hair_color_ginger;
    }

    //HAIR_GREYWHITE
    public int getPrefHairColorGreywhite() {
        return pref_hair_color_greywhite;
    }


    public void setPrefHairColorGreywhite(int pref_hair_color_greywhite) {
        this.pref_hair_color_greywhite = pref_hair_color_greywhite;
    }

    //HAIR_OTHER
    public int getPrefHairColorOther() {
        return pref_hair_color_other;
    }

    public void setPrefHairColorOther(int pref_hair_color_other) {
        this.pref_hair_color_other = pref_hair_color_other;
    }

    //HAIR_SHORT
    public int getPrefHairTypeShort() {
        return pref_hair_type_short;
    }

    public void setPrefHairTypeShort(int pref_hair_type_short) {
        this.pref_hair_type_short = pref_hair_type_short;
    }

    //HAIR_MEDIUM
    public int getPrefHairTypeMedium() {
        return pref_hair_type_medium;
    }

    public void setPrefHairTypeMedium(int pref_hair_type_medium) {
        this.pref_hair_type_medium = pref_hair_type_medium;
    }

    //HAIR_LONG
    public int getPrefHairTypeLong() {
        return pref_hair_type_long;
    }

    public void setPrefHairTypeLong(int pref_hair_type_long) {
        this.pref_hair_type_long = pref_hair_type_long;
    }

    //EYES_BLUE
    public int getPrefEyesColorBlue() {
        return pref_eyes_color_blue;
    }

    public void setPrefEyesColorBlue(int pref_eyes_color_blue) {
        this.pref_eyes_color_blue = pref_eyes_color_blue;
    }

    //EYES_BROWN
    public int getPrefEyesColorBrown() {
        return pref_eyes_color_brown;
    }

    public void setPrefEyesColorBrown(int pref_eyes_color_brown) {
        this.pref_eyes_color_brown = pref_eyes_color_brown;
    }

    //EYES_BLACK
    public int getPrefEyesColorBlack() {
        return pref_eyes_color_black;
    }

    public void setPrefEyesColorBlack(int pref_eyes_color_black) {
        this.pref_eyes_color_black = pref_eyes_color_black;
    }

    //EYES_GREEN
    public int getPrefEyesColorGreen() {
        return pref_eyes_color_green;
    }

    public void setPrefEyesColorGreen(int pref_eyes_color_green) {
        this.pref_eyes_color_green = pref_eyes_color_green;
    }

    //EYES_GREY
    public int getPrefEyesColorGrey() {
        return pref_eyes_color_grey;
    }

    public void setPrefEyesColorGrey(int pref_eyes_color_grey) {
        this.pref_eyes_color_grey = pref_eyes_color_grey;
    }

    //AGE_MAX
    public int getPrefAgeMax() {
        return pref_age_max;
    }

    public void setPrefAgeMax(int pref_age_max) {
        this.pref_age_max = pref_age_max;
    }

    //AGE_MIN
    public int getPrefAgeMin() {
        return pref_age_min;
    }

    public void setPrefAgeMin(int pref_age_min) {
        this.pref_age_min = pref_age_min;
    }

    //DISTANCE_MAX
    public int getPrefDistanceMax() {
        return pref_distance_max;
    }

    public void setPrefDistanceMax(int pref_distance_max) {
        this.pref_distance_max = pref_distance_max;
    }

} // class Preferences
