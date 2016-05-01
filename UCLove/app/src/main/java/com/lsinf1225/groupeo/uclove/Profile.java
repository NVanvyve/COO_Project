package com.lsinf1225.groupeo.uclove;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

public class Profile extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        Intent intent = getIntent();

        long user_id = intent.getLongExtra("userID", -1L);

        UserManager m = new UserManager(this);
        m.open();

        User currentUser = m.getUser(user_id);

        m.close();

        ImageView myImage = (ImageView) findViewById(R.id.profile_picture);

        Picasso.with(myImage.getContext()).load(currentUser.getUserProfilePic()).fit().centerCrop().into(myImage);

        String lang = Locale.getDefault().getDisplayLanguage();

        String firstname = currentUser.getUserFirstName();
        String lastname = currentUser.getUserLastName();
        String login = currentUser.getUserUsername();
        String city = currentUser.getUserCity();
        String birthdate = currentUser.getUserBirthDate();


        //A traduire
        String sex = currentUser.getUserSex();
        String orientation = currentUser.getUserSexuality();
        String language = currentUser.getUserLanguage();
        String hairtype = currentUser.getUserHairType();
        String haircolor = currentUser.getUserHairColor();
        String eyescolor = currentUser.getUserEyesColor();

        if((lang.compareTo("français"))==0)
        {
            sex = mapSex(sex);
            orientation = mapSexuality(orientation);
            language = mapLanguage(language);
            hairtype = mapHairStyle(hairtype);
            haircolor = mapHairColor(haircolor);
            eyescolor = mapEyesColor(eyescolor);
        }

        TextView item_login = (TextView) findViewById(R.id.profile_login);
        TextView item_city = (TextView) findViewById(R.id.profile_city);
        TextView item_sex = (TextView) findViewById(R.id.profil_sex);
        TextView item_birthdate = (TextView) findViewById(R.id.profile_birth);
        TextView item_language = (TextView) findViewById(R.id.profile_language);
        TextView item_eyescolor = (TextView) findViewById(R.id.profile_eyes_color);

        TextView text_hair = (TextView) findViewById(R.id.hair);
        text_hair.append(" "+haircolor+" & "+hairtype);

        TextView item_name = (TextView) findViewById(R.id.profile_name);
        item_name.setText(firstname+" "+lastname);


        item_login.append(" "+login);

        item_city.append(" "+city);
        item_sex.setText(sex+" & "+orientation);
        item_birthdate.setText(birthdate);
        item_language.setText(language);
        item_eyescolor.append(" "+eyescolor);


    }

    public String mapLanguage(String language) {
        String returnedLanguage;
        switch (language) {
            case "French":
                returnedLanguage = "Français";
                break;
            case "English":
                returnedLanguage = "Anglais";
                break;
            default:
                returnedLanguage = "Invalid";
        }
        return returnedLanguage;
    }

    public String mapHairColor(String hairColor) {
        String returnedHairColor;
        switch (hairColor) {
            case "Blond":
                returnedHairColor = "Blond";
                break;
            case "Brown":
                returnedHairColor = "Brun";
                break;
            case "Black":
                returnedHairColor = "Noir";
                break;
            case "Ginger":
                returnedHairColor = " Roux";
                break;
            case "Grey/white":
                returnedHairColor = "Gris/blanc";
                break;
            case "Other":
                returnedHairColor = "Autre";
                break;
            default:
                returnedHairColor = "Invalid";
        }
        return returnedHairColor;
    }

    public String mapHairStyle(String hairStyle) {
        String returnedHairStyle;
        switch (hairStyle) {
            case "Short":
                returnedHairStyle = "Court";
                break;
            case "Moyen":
                returnedHairStyle = "Medium";
                break;
            case "Long":
                returnedHairStyle = "Long";
                break;
            default:
                returnedHairStyle = "Invalid";
        }
        return returnedHairStyle;
    }

    public String mapEyesColor(String eyesColor) {
        String returnedEyesColor;
        switch (eyesColor) {
            case "Blue":
                returnedEyesColor = "Bleu";
                break;
            case "Brown":
                returnedEyesColor = "Brun";
                break;
            case "Black":
                returnedEyesColor = "Noir";
                break;
            case "Green":
                returnedEyesColor = "Vert";
                break;
            case "Grey":
                returnedEyesColor = "Gris";
                break;
            default:
                returnedEyesColor = "Invalid";
        }
        return returnedEyesColor;
    }

    public String mapSex(String sex) {
        String returnedSex;
        switch (sex) {
            case "Male":
                returnedSex = "Homme";
                break;
            case "Female":
                returnedSex = "Femme";
                break;
            default:
                returnedSex = "Invalid";
        }
        return returnedSex;
    }

    public String mapSexuality(String sexuality) {
        String returnedSexuality;
        switch (sexuality) {
            case "Heterosexual":
                returnedSexuality = "Hétérosexuel";
                break;
            case "Homosexual":
                returnedSexuality = "Homosexuel";
                break;
            case "Bisexual":
                returnedSexuality = "Bisexuel";
                break;
            default:
                returnedSexuality = "Invalid";
        }
        return returnedSexuality;
    }
}


