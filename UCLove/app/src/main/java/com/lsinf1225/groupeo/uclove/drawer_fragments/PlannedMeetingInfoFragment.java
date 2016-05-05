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
import com.lsinf1225.groupeo.uclove.database.CalendarManager;
import com.lsinf1225.groupeo.uclove.database.RDV;
import com.lsinf1225.groupeo.uclove.database.RDVManager;
import com.lsinf1225.groupeo.uclove.database.User;
import com.lsinf1225.groupeo.uclove.database.UserManager;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PlannedMeetingInfoFragment extends Fragment {

    String language_code;
    long user_id_a;
    long rdv_id;
    RDV rdv;
    private View myFragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_planned_meeting_info, container, false);

        // On récupère les User IDs
        long user_id_a = ((DrawerMainActivity) getActivity()).returnUserID();
        long user_id_b;
        Bundle b = getArguments();
        rdv_id = (long) b.getInt("rdv_id");

        // On récupère le rendez-vous
        RDVManager m = new RDVManager(getActivity());
        m.open();
        rdv = m.getRDV(rdv_id);

        if (user_id_a == rdv.getRDVUserIDA()) {
            user_id_b = rdv.getRDVUserIDB();
        } else {
            user_id_b = rdv.getRDVUserIDA();
        }

        UserManager userManager = new UserManager(getActivity());
        userManager.open();
        User user_b = userManager.getUser(user_id_b);
        User user_a = userManager.getUser(user_id_a);

        String name = user_b.getUserFirstName() + " " + user_b.getUserLastName();
        String picture = user_b.getUserProfilePic();
        String rdvDate = "";

        String coordA = user_a.getUserPosition();
        String coordB = user_b.getUserPosition();

        GPSTracker gps = new GPSTracker(getActivity());
        double distance = gps.Distance(coordA, coordB);

        // On affiche sa photo de profil
        ImageView myImage = (ImageView) myFragmentView.findViewById(R.id.planned_meeting_info_image);
        Picasso.with(myImage.getContext()).load(picture).fit().centerCrop().into(myImage);

        // On formate la date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(rdv.getRDVDate());
            Calendar cal = DateToCalendar(date);
            rdvDate = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        TextView myText;

        myText = (TextView) myFragmentView.findViewById(R.id.planned_meeting_info_name);
        myText.setText(name);

        myText = (TextView) myFragmentView.findViewById(R.id.planned_meeting_info_on);
        myText.append(" " + rdvDate);

        myText = (TextView) myFragmentView.findViewById(R.id.planned_meeting_info_distance);
        myText.append(" " + (int)distance + " km.");

        return myFragmentView;
    }

    public static Calendar DateToCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}