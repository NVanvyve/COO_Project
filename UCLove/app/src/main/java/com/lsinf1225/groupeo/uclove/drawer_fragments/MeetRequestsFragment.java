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
import com.lsinf1225.groupeo.uclove.R;
import com.lsinf1225.groupeo.uclove.database.CalendarManager;
import com.lsinf1225.groupeo.uclove.database.Notification;
import com.lsinf1225.groupeo.uclove.database.NotificationManager;
import com.lsinf1225.groupeo.uclove.database.RDV;
import com.lsinf1225.groupeo.uclove.database.RDVManager;
import com.lsinf1225.groupeo.uclove.database.User;
import com.lsinf1225.groupeo.uclove.database.UserManager;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MeetRequestsFragment extends Fragment {

    long user_id;
    User userb;
    User usera;
    RDV rdv;
    com.lsinf1225.groupeo.uclove.database.Calendar cal;
    private View myFragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_meet_requests, container, false);

        // On récupère l'userID
        user_id = ((DrawerMainActivity) getActivity()).returnUserID();
        UserManager um = new UserManager(getActivity());
        um.open();
        usera = um.getUser(user_id);

        // On récupère une demande de rendez-vous
        RDVManager m = new RDVManager(getActivity());
        m.open();
        rdv = m.getRDVRequest(user_id);

        long rdvId = rdv.getRDVID();
        String rdvDate = rdv.getRDVDate();
        long rdvUserIdB = rdv.getRDVUserIDA();

        if (rdvId != -1) { // Si une demande de rendez-vous a été trouvée

            cal = new com.lsinf1225.groupeo.uclove.database.Calendar(0, 0, "", "");
            CalendarManager cm = new CalendarManager(getActivity());
            cm.open();
            cal = cm.getCalendarByUserIdAndDate(user_id, rdvDate);

            // On récupère le profil de l'utilisateur qui a fait la demande
            um.open();
            userb = um.getUser(rdvUserIdB);

            // On affiche sa photo de profil
            ImageView myImage = (ImageView) myFragmentView.findViewById(R.id.meet_request_image);
            Picasso.with(myImage.getContext()).load(userb.getUserProfilePic()).fit().centerCrop().into(myImage);

            //On récupère le nom de l'utilisateur qui a fait la demande
            String firstName = userb.getUserFirstName();
            String lastName = userb.getUserLastName();

            // On formate la date
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = format.parse(rdvDate);
                Calendar cal = DateToCalendar(date);
                rdvDate = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            TextView myText;

            String name = firstName + " " + lastName;
            myText = (TextView) myFragmentView.findViewById(R.id.meet_request_name);
            myText.setText(name);

            myText = (TextView) myFragmentView.findViewById(R.id.meet_request_on);
            myText.append(" "+rdvDate);
        } else {
            Fragment fragment = new MeetRequestsFailedFragment();
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }

        // Si l'utilisateur appuye sur le bouton accepter
        Button button1= (Button) myFragmentView.findViewById(R.id.meet_request_button_accept);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdv.setRDVStatus("Accepted"); // Rendez-vous accepté
                RDVManager rm = new RDVManager(getActivity());
                rm.open();
                rm.modRDV(rdv);

                // Envoi d'une notification
                NotificationManager nm = new NotificationManager(getActivity());
                nm.open();
                Notification notif = new Notification(0, userb.getUserID(), "",  usera.getUserFirstName()+" "+usera.getUserLastName()+" a accepté votre demande de rendez-vous.", "Unread", 5);
                nm.addNotification(notif);

                CalendarManager calendarManager = new CalendarManager(getActivity());
                calendarManager.open();
                cal.setCalStatus("Busy"); // Il n'est plus libre
                calendarManager.modCalendar(cal);

                Context context = getActivity();
                CharSequence text = getResources().getString(R.string.meet_request_accepted);;
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                Fragment fragment = new MeetRequestsFragment();
                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();
            }
        });

        // Si l'utilisateur appuye sur le bouton refuser
        Button button2= (Button) myFragmentView.findViewById(R.id.meet_request_button_decline);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdv.setRDVStatus("Declined"); // RDV refusé
                RDVManager rm = new RDVManager(getActivity());
                rm.open();
                rm.modRDV(rdv);

                Context context = getActivity();
                CharSequence text = getResources().getString(R.string.meet_request_declined);;
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                Fragment fragment = new MeetRequestsFragment();
                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();
            }
        });

        return myFragmentView;
    }

    public static Calendar DateToCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}