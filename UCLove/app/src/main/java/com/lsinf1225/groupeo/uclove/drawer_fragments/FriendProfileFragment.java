package com.lsinf1225.groupeo.uclove.drawer_fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.lsinf1225.groupeo.uclove.DrawerMainActivity;
import com.lsinf1225.groupeo.uclove.GPSTracker;
import com.lsinf1225.groupeo.uclove.R;
import com.lsinf1225.groupeo.uclove.database.NotifAuthManager;
import com.lsinf1225.groupeo.uclove.database.Relation;
import com.lsinf1225.groupeo.uclove.database.RelationManager;
import com.lsinf1225.groupeo.uclove.database.User;
import com.lsinf1225.groupeo.uclove.database.UserManager;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FriendProfileFragment extends Fragment {

    String language_code;
    boolean isUserA = false;
    Relation relation;
    long user_id_b;

    private View myFragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_friend_profile, container, false);

        // On récupère les User IDs
        long user_id_a = ((DrawerMainActivity) getActivity()).returnUserID();
        Bundle b = getArguments();
        user_id_b = (long)b.getInt("friend_user_id");

        // On récupère la relation entre les deux amis
        RelationManager rm = new RelationManager(getActivity());
        rm.open();
        relation = rm.getRelationFromUserIds(user_id_a, user_id_b);
        rm.close();
        if (relation.getRelUserIDA() == (int)user_id_a) {
            isUserA = true;
        }

        UserManager m = new UserManager(getActivity());
        m.open();
        User currentUser = m.getUser(user_id_b);
        User friend = m.getUser(user_id_a);
        m.close();

        language_code = Locale.getDefault().getLanguage();

        // On affiche la photo de profil
        ImageView myImage = (ImageView) myFragmentView.findViewById(R.id.friend_profile_profile_picture);
        Picasso.with(myImage.getContext()).load(currentUser.getUserProfilePic()).fit().centerCrop().into(myImage);

        //On récupère les données de l'utilisateur
        String firstName = currentUser.getUserFirstName();
        String lastName = currentUser.getUserLastName();
        String birthDate = currentUser.getUserBirthDate();
        String city = currentUser.getUserCity();
        String language = mapLanguage(currentUser.getUserLanguage());
        String hairColor = mapHairColor(currentUser.getUserHairColor());
        String hairType = mapHairStyle(currentUser.getUserHairType());
        String eyesColor = mapEyesColor(currentUser.getUserEyesColor());
        String position = currentUser.getUserPosition();
        GPSTracker gps = new GPSTracker(getActivity());

        double distance = gps.Distance(position, friend.getUserPosition());

        ((DrawerMainActivity) getActivity()).setTitle(this.getString(R.string.nav_profile_of) + " " + firstName);

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

        TextView myText;

        String name = firstName + " " + lastName;
        myText = (TextView) myFragmentView.findViewById(R.id.friend_profile_name);
        myText.setText(name);

        myText = (TextView) myFragmentView.findViewById(R.id.friend_profile_birth_date_data);
        myText.setText(birthDate);

        myText = (TextView) myFragmentView.findViewById(R.id.friend_profile_city_data);
        myText.setText(city);

        myText = (TextView) myFragmentView.findViewById(R.id.friend_profile_language_data);
        myText.setText(language);

        myText = (TextView) myFragmentView.findViewById(R.id.friend_profile_hair_color_data);
        myText.setText(hairColor);

        myText = (TextView) myFragmentView.findViewById(R.id.friend_profile_hair_type_data);
        myText.setText(hairType);

        myText = (TextView) myFragmentView.findViewById(R.id.friend_profile_eyes_color_data);
        myText.setText(eyesColor);

        String distanceData = String.valueOf((int)distance) + " km";
        myText = (TextView) myFragmentView.findViewById(R.id.friend_profile_distance_data);
        myText.setText(distanceData);

        // On met le switch dans la bonne position
        if (isUserA) {
            if (relation.getRelFavA() == 0) {
                Switch sw = (Switch) myFragmentView.findViewById(R.id.friend_profile_favourite);
                sw.setChecked(false);
            }
        } else {
            if (relation.getRelFavB() == 0) {
                Switch sw = (Switch) myFragmentView.findViewById(R.id.friend_profile_favourite);
                sw.setChecked(false);
            }
        }

        // On écoute le bouton send_message
        Button buttonSend= (Button) myFragmentView.findViewById(R.id.friend_profile_send_message);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putInt("friend_user_id", (int)user_id_b);
                Fragment fragment = new MessageFragment();
                fragment.setArguments(args);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();
            }
        });

        // On écoute le bouton meet
        Button buttonMeet= (Button) myFragmentView.findViewById(R.id.friend_profile_meet);
        buttonMeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putInt("friend_user_id", (int)user_id_b);
                Fragment fragment = new MeetOnProfileFragment();
                fragment.setArguments(args);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();
            }
        });


        // On écoute les changements du switch
        Switch sw = (Switch) myFragmentView.findViewById(R.id.friend_profile_favourite);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (isUserA) {
                        relation.setRelFavA(1);
                        RelationManager rm = new RelationManager(getActivity());
                        rm.open();
                        rm.modRelation(relation);
                        rm.close();
                    } else {
                        relation.setRelFavB(1);
                        RelationManager rm = new RelationManager(getActivity());
                        rm.open();
                        rm.modRelation(relation);
                        rm.close();
                    }
                } else {
                    if (isUserA) {
                        relation.setRelFavA(0);
                        RelationManager rm = new RelationManager(getActivity());
                        rm.open();
                        rm.modRelation(relation);
                        rm.close();
                    } else {
                        relation.setRelFavB(0);
                        RelationManager rm = new RelationManager(getActivity());
                        rm.open();
                        rm.modRelation(relation);
                        rm.close();
                    }
                }
            }
        });

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