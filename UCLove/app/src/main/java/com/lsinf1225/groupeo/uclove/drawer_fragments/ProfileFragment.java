package com.lsinf1225.groupeo.uclove.drawer_fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lsinf1225.groupeo.uclove.DrawerMainActivity;
import com.lsinf1225.groupeo.uclove.NotificationSender;
import com.lsinf1225.groupeo.uclove.R;
import com.lsinf1225.groupeo.uclove.database.User;
import com.lsinf1225.groupeo.uclove.database.UserManager;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ProfileFragment extends Fragment {

    String language_code;

    private View myFragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_profile, container, false);

        // On récupère l'userID
        long user_id = ((DrawerMainActivity) getActivity()).returnUserID();
        UserManager m = new UserManager(getActivity()); // gestionnaire de la table "user"
        m.open();
        User currentUser = m.getUser(user_id);
        m.close();

        language_code = Locale.getDefault().getLanguage();

        // On affiche la photo de profil
        ImageView myImage = (ImageView) myFragmentView.findViewById(R.id.profile_profile_picture);
        Picasso.with(myImage.getContext()).load(currentUser.getUserProfilePic()).fit().centerCrop().into(myImage);

        //On récupère les données de l'utilisateur
        String username = currentUser.getUserUsername();
        String firstName = currentUser.getUserFirstName();
        String lastName = currentUser.getUserLastName();
        String birthDate = currentUser.getUserBirthDate();
        String city = currentUser.getUserCity();
        String language = mapLanguage(currentUser.getUserLanguage());
        String hairColor = mapHairColor(currentUser.getUserHairColor());
        String hairType = mapHairStyle(currentUser.getUserHairType());
        String eyesColor = mapEyesColor(currentUser.getUserEyesColor());

        // On formate la date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(birthDate);
            Calendar cal = DateToCalendar(date);
            birthDate = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.YEAR);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // On affiche les données de l'utilisateur
        TextView myText;
        myText = (TextView) myFragmentView.findViewById(R.id.profile_username_data);
        myText.setText(username);

        String name = firstName + " " + lastName;
        myText = (TextView) myFragmentView.findViewById(R.id.profile_name);
        myText.setText(name);

        myText = (TextView) myFragmentView.findViewById(R.id.profile_birth_date_data);
        myText.setText(birthDate);

        myText = (TextView) myFragmentView.findViewById(R.id.profile_city_data);
        myText.setText(city);

        myText = (TextView) myFragmentView.findViewById(R.id.profile_language_data);
        myText.setText(language);

        myText = (TextView) myFragmentView.findViewById(R.id.profile_hair_color_data);
        myText.setText(hairColor);

        myText = (TextView) myFragmentView.findViewById(R.id.profile_hair_type_data);
        myText.setText(hairType);

        myText = (TextView) myFragmentView.findViewById(R.id.profile_eyes_color_data);
        myText.setText(eyesColor);

        return myFragmentView;
    }

    public static Calendar DateToCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    private String mapLanguage(String language) {
        if (language_code.equals("fr")) {
            switch (language) {
                case "French":
                    language = "Français";
                    break;
                case "English":
                    language = "Anglais";
                    break;
                default:
                    language = "Non spécifié";
                    break;
            }
        }
        return language;
    }

    private String mapHairColor(String haircolor) {
        if (language_code.equals("fr")) {
            switch (haircolor) {
                case "Blond":
                    haircolor = "Blond";
                    break;
                case "Brown":
                    haircolor = "Brun";
                    break;
                case "Black":
                    haircolor = "Noir";
                    break;
                case "Ginger":
                    haircolor = "Roux";
                    break;
                case "Grey/white":
                    haircolor = "Gris/blanc";
                    break;
                case "Other":
                    haircolor = "Autre";
                    break;
                default:
                    haircolor = "Non spécifié";
                    break;
            }
        }
        return haircolor;
    }

    private String mapHairStyle(String hairstyle) {
        if (language_code.equals("fr")) {
            switch (hairstyle) {
                case "Short":
                    hairstyle = "Court";
                    break;
                case "Medium":
                    hairstyle = "Moyen";
                    break;
                case "Long":
                    hairstyle = "Long";
                    break;
                default:
                    hairstyle = "Non spécifié";
                    break;
            }
        }
        return hairstyle;
    }

    private String mapEyesColor(String eyescolor) {
        if (language_code.equals("fr")) {
            switch (eyescolor) {
                case "Blue":
                    eyescolor = "Bleu";
                    break;
                case "Brown":
                    eyescolor = "Brun";
                    break;
                case "Black":
                    eyescolor = "Noir";
                    break;
                case "Green":
                    eyescolor = "Vert";
                    break;
                case "Grey":
                    eyescolor = "Gris";
                    break;
                default:
                    eyescolor = "Non spécifié";
                    break;
            }
        }
        return eyescolor;
    }
}