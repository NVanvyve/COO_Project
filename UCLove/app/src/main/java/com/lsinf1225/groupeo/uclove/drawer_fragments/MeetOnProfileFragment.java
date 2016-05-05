package com.lsinf1225.groupeo.uclove.drawer_fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lsinf1225.groupeo.uclove.CustomAdapterDatePreferences;
import com.lsinf1225.groupeo.uclove.CustomAdapterMeetOnProfile;
import com.lsinf1225.groupeo.uclove.DatePickerFragmentDatePreferences;
import com.lsinf1225.groupeo.uclove.DrawerMainActivity;
import com.lsinf1225.groupeo.uclove.R;
import com.lsinf1225.groupeo.uclove.database.Calendar;
import com.lsinf1225.groupeo.uclove.database.CalendarManager;
import com.lsinf1225.groupeo.uclove.database.RDV;
import com.lsinf1225.groupeo.uclove.database.RDVManager;
import com.lsinf1225.groupeo.uclove.database.Relation;
import com.lsinf1225.groupeo.uclove.database.RelationManager;
import com.lsinf1225.groupeo.uclove.database.User;
import com.lsinf1225.groupeo.uclove.database.UserManager;

import java.util.ArrayList;
import java.util.List;

public class MeetOnProfileFragment extends Fragment {

    private View myFragmentView;
    User currentUser;
    long user_id_a;
    long user_id_b;
    Relation relation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_meet_on_profile, container, false);

        // On récupère les userids
        user_id_a = ((DrawerMainActivity) getActivity()).returnUserID();
        Bundle b = getArguments();
        user_id_b = (long)b.getInt("friend_user_id");

        UserManager um = new UserManager(getActivity());
        um.open();
        User user_a = um.getUser(user_id_a);
        User user_b = um.getUser(user_id_b);

        // On récupère la relation entre les deux users
        RelationManager rm = new RelationManager(getActivity());
        rm.open();
        relation =  rm.getRelationFromUserIds(user_id_a, user_id_b);
        rm.close();

        ((DrawerMainActivity) getActivity()).setTitle(this.getString(R.string.nav_free_dates_of) + " " + user_b.getUserFirstName());

        // Initalisation des array pour l'affichage :
        List<String> date = new ArrayList<>();
        List<Integer> dateid = new ArrayList<>();

        CalendarManager cm = new CalendarManager(getActivity());
        cm.open();
        for (int i = 1; i<=50; i++) {
            Calendar objectCalendar = cm.getFreeDatesOfFriend(user_id_a, user_id_b, i);
            if (objectCalendar.getCalID() != -1) {
                date.add(objectCalendar.getCalDate());
                dateid.add(objectCalendar.getCalID());
            }
        }
        cm.close();

        ListAdapter myListAdapter = new CustomAdapterMeetOnProfile(getActivity(), date, dateid);
        ListView myListView = (ListView) myFragmentView.findViewById(R.id.meet_on_profile_list_view);
        myListView.setAdapter(myListAdapter);

        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.custom_row_meet_on_profile_cal_id);
                Integer dateid = Integer.parseInt(textView.getText().toString());

                CalendarManager cm = new CalendarManager(getActivity());
                cm.open();
                Calendar cal = cm.getCalendar((long)dateid);

                RDV rdv = new RDV(0, relation.getRelID(), (int)user_id_a, (int)user_id_b, cal.getCalDate(), "Request");

                RDVManager rdvm = new RDVManager(getActivity());
                rdvm.open();
                rdvm.addRDV(rdv);
                rdvm.close();

                Context context = getActivity();
                CharSequence text = getResources().getString(R.string.meet_on_profile_toast);;
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                Bundle args = new Bundle();
                args.putInt("friend_user_id", (int)user_id_b);
                Fragment fragment = new MeetOnProfileFragment();
                fragment.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();
                return true;
            }
        });

        // Inflate the layout for this fragment
        return myFragmentView;
    }
}