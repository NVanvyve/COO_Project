package com.lsinf1225.groupeo.uclove.drawer_fragments;

import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.app.DialogFragment;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lsinf1225.groupeo.uclove.CustomAdapterDatePreferences;
import com.lsinf1225.groupeo.uclove.DatePickerFragmentAccountCreation;
import com.lsinf1225.groupeo.uclove.DatePickerFragmentDatePreferences;
import com.lsinf1225.groupeo.uclove.DrawerMainActivity;
import com.lsinf1225.groupeo.uclove.R;
import com.lsinf1225.groupeo.uclove.database.Calendar;
import com.lsinf1225.groupeo.uclove.database.CalendarManager;
import com.lsinf1225.groupeo.uclove.database.User;
import com.lsinf1225.groupeo
        .uclove.database.UserManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DatePreferencesFragment extends Fragment {

    private View myFragmentView;
    User currentUser;
    long user_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_date_preferences, container, false);

        // On récupère l'userID
        user_id = ((DrawerMainActivity) getActivity()).returnUserID();
        UserManager m = new UserManager(getActivity()); // gestionnaire de la table "user"
        m.open();
        currentUser = m.getUser(user_id);
        m.close();


        // Initalisation des array pour l'affichage :
        List<String> date = new ArrayList<>();
        List<Integer> dateid = new ArrayList<>();

        CalendarManager cm = new CalendarManager(getActivity());
        cm.open();
        for (int i = 1; i<=50; i++) {
            Calendar objectCalendar = cm.getDatePreferences(user_id, i);
            if (objectCalendar.getCalID() != -1) {
                date.add(objectCalendar.getCalDate());
                dateid.add(objectCalendar.getCalID());
            }
        }
        cm.close();

        ListAdapter myListAdapter = new CustomAdapterDatePreferences(getActivity(), date, dateid);
        ListView myListView = (ListView) myFragmentView.findViewById(R.id.date_preferences_list_view);
        myListView.setAdapter(myListAdapter);

        Button button= (Button) myFragmentView.findViewById(R.id.date_preferences_pick_date);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragmentDatePreferences();
                newFragment.show(getActivity().getFragmentManager(), "datePicker");
            }
        });

        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.custom_row_date_preferences_cal_id);
                Integer dateid = Integer.parseInt(textView.getText().toString());
                Calendar a = new Calendar(dateid, 0, "", "");
                CalendarManager cm = new CalendarManager(getActivity());
                cm.open();
                cm.supCalendar(a);
                cm.close();
                Fragment fragment = new DatePreferencesFragment();
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