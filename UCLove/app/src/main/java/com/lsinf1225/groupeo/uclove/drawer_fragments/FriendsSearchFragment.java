package com.lsinf1225.groupeo.uclove.drawer_fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lsinf1225.groupeo.uclove.DrawerMainActivity;
import com.lsinf1225.groupeo.uclove.GPSTracker;
import com.lsinf1225.groupeo.uclove.R;
import com.lsinf1225.groupeo.uclove.database.Notification;
import com.lsinf1225.groupeo.uclove.database.NotificationManager;
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

public class FriendsSearchFragment extends Fragment {

    User currentUser;
    String language_code;
    long user_id;
    long newFriendUserId;
    private View myFragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_friends_search, container, false);

        // On récupère l'userID
        user_id = ((DrawerMainActivity) getActivity()).returnUserID();
        UserManager m = new UserManager(getActivity()); // gestionnaire de la table "user"
        m.open();
        currentUser = m.getUser(user_id);
        newFriendUserId = m.userSearcher(currentUser);
        if (newFriendUserId != -1) { // Si un utilisateur a été trouvé.
            User newFriend;
            newFriend = m.getUser(newFriendUserId);
            m.close();

            language_code = Locale.getDefault().getLanguage();

            // On affiche la photo de profil
            ImageView myImage = (ImageView) myFragmentView.findViewById(R.id.friends_search_profile_picture);
            Picasso.with(myImage.getContext()).load(newFriend.getUserProfilePic()).fit().centerCrop().into(myImage);

            //On récupère les données de l'utilisateur
            String firstName = newFriend.getUserFirstName();
            String lastName = newFriend.getUserLastName();
            String birthDate = newFriend.getUserBirthDate();
            String city = newFriend.getUserCity();
            String language = mapLanguage(newFriend.getUserLanguage());
            String hairColor = mapHairColor(newFriend.getUserHairColor());
            String hairType = mapHairStyle(newFriend.getUserHairType());
            String eyesColor = mapEyesColor(newFriend.getUserEyesColor());
            String position = newFriend.getUserPosition();

            GPSTracker gps = new GPSTracker(getActivity());
            double distance = gps.Distance(position, currentUser.getUserPosition());

            // On formate la date
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = format.parse(birthDate);
                Calendar cal = DateToCalendar(date);
                birthDate = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            TextView myText;

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

            String distanceData = String.valueOf((int)distance) + " km";
            myText = (TextView) myFragmentView.findViewById(R.id.profile_distance_data);
            myText.setText(distanceData);
        } else {
            Fragment fragment = new FriendsSearchFailedFragment();
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }

        // Si on accepte l'amitié
        Button button1= (Button) myFragmentView.findViewById(R.id.friends_search_button_like);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelationManager m = new RelationManager(getActivity());
                m.open();
                Relation r = m.getRelationFromUserIdsOrdered(newFriendUserId, user_id);
                if ((r.getRelStatus()).equals("Request")) { // Si la demande était déjà en request
                    r.setRelStatus("Accepted"); // On est direct ami
                    m.modRelation(r);

                    NotificationManager nm = new NotificationManager(getActivity());
                    nm.open();
                    Notification notif = new Notification(0, (int)newFriendUserId, "", "Vous êtes désormais ami avec "+currentUser.getUserFirstName()+" "+currentUser.getUserLastName()+".", "Unread", 2);
                    nm.addNotification(notif);
                    nm.close();

                    Context context = getActivity();
                    CharSequence text = getResources().getString(R.string.friends_search_accepted);;
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    Fragment fragment = new FriendsSearchFragment();
                    // Insert the fragment by replacing any existing fragment
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .commit();
                } else {
                    r.setRelStatus("Request"); // Sinon on crée la relation et on la passe en request
                    r.setRelUserIDA((int)user_id);
                    r.setRelUserIDB((int)newFriendUserId);
                    m.addRelation(r);

                    NotificationManager nm = new NotificationManager(getActivity());
                    nm.open();
                    Notification notif = new Notification(0, (int)newFriendUserId, "", "Vous avez une nouvelle demande d'amitié de "+currentUser.getUserFirstName()+" "+currentUser.getUserLastName()+".", "Unread", 1);
                    nm.addNotification(notif);
                    nm.close();

                    Context context = getActivity();
                    CharSequence text = getResources().getString(R.string.friends_search_request);;
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    Fragment fragment = new FriendsSearchFragment();
                    // Insert the fragment by replacing any existing fragment
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .commit();
                }
            }
        });

        // Si on refuse l'amitié
        Button button2= (Button) myFragmentView.findViewById(R.id.friends_search_button_dislike);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelationManager m = new RelationManager(getActivity());
                m.open();
                Relation r = m.getRelationFromUserIdsOrdered(newFriendUserId, user_id);
                if ((r.getRelStatus()).equals("Request")) {
                    r.setRelStatus("Declined");
                    m.modRelation(r);
                    m.close();
                    Fragment fragment = new FriendsSearchFragment();
                    // Insert the fragment by replacing any existing fragment
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .commit();
                } else {
                    r.setRelStatus("Declined");
                    r.setRelUserIDA((int)user_id);
                    r.setRelUserIDB((int)newFriendUserId);
                    m.addRelation(r);
                    m.close();
                    Fragment fragment = new FriendsSearchFragment();
                    // Insert the fragment by replacing any existing fragment
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .commit();
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