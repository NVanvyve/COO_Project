package com.lsinf1225.groupeo.uclove.drawer_fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lsinf1225.groupeo.uclove.custom_adapters.CustomAdapterPlannedMeetings;
import com.lsinf1225.groupeo.uclove.DrawerMainActivity;
import com.lsinf1225.groupeo.uclove.R;
import com.lsinf1225.groupeo.uclove.database.RDV;
import com.lsinf1225.groupeo.uclove.database.RDVManager;
import com.lsinf1225.groupeo.uclove.database.User;
import com.lsinf1225.groupeo.uclove.database.UserManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlannedMeetingsFragment extends Fragment {

    private View myFragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_planned_meetings, container, false);
        boolean hasFailed = true;

        // On récupère l'userID
        long user_id = ((DrawerMainActivity) getActivity()).returnUserID();

        // Initalisation des array pour l'affichage :
        List<String> name = new ArrayList<>();
        List<String> date = new ArrayList<>();
        List<String> userid = new ArrayList<>();

        RDVManager rdvManager = new RDVManager(getActivity());
        UserManager userManager = new UserManager(getActivity());
        rdvManager.open();
        userManager.open();
        for (int i = 1; i<=50; i++) {
            RDV rdv = rdvManager.getRDVAccepted(user_id, i);
            if (rdv.getRDVID() != -1) {
                hasFailed = false;

                // On formate la date
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date rdvdate = format.parse(rdv.getRDVDate());
                    Calendar cal = DateToCalendar(rdvdate);
                    String formattedDate = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1);
                    date.add(formattedDate);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                long user_id_a = rdv.getRDVUserIDA();
                long user_id_b = rdv.getRDVUserIDB();
                User user_a = userManager.getUser(user_id_a);
                User user_b = userManager.getUser(user_id_b);
                if (user_id == user_id_a) {
                    name.add(user_b.getUserFirstName()+" "+user_b.getUserLastName());
                    userid.add(String.valueOf(user_b.getUserID()));
                } else {
                    name.add(user_a.getUserFirstName()+" "+user_a.getUserLastName());
                    userid.add(String.valueOf(user_a.getUserID()));
                }
            }
        }
        rdvManager.close();
        userManager.close();

        if (!hasFailed) {
            ListAdapter myListAdapter = new CustomAdapterPlannedMeetings(getActivity(), name, date, userid);
            ListView myListView = (ListView) myFragmentView.findViewById(R.id.planned_meetings_list_view);
            myListView.setAdapter(myListAdapter);

            myListView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            TextView textView = (TextView) view.findViewById(R.id.custom_row_planned_meetings_rdv_id);
                            Integer rdvId = Integer.parseInt(textView.getText().toString());

                            Bundle args = new Bundle();
                            args.putInt("rdv_id", rdvId);
                            Fragment fragment = new PlannedMeetingInfoFragment();
                            fragment.setArguments(args);

                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction()
                                    .setCustomAnimations(R.animator.slide_in_left,
                                            R.animator.slide_out_right, 0, 0)
                                    .replace(R.id.content_frame, fragment)
                                    .commit();
                        }
                    }
            );
        } else {
            Fragment fragment = new PlannedMeetingsFailedFragment();
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }

        return myFragmentView;
    }

    public static Calendar DateToCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}