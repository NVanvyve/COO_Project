package com.lsinf1225.groupeo.uclove.drawer_fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lsinf1225.groupeo.uclove.NotificationSender;
import com.lsinf1225.groupeo.uclove.custom_adapters.CustomAdapterNotifications;
import com.lsinf1225.groupeo.uclove.DrawerMainActivity;
import com.lsinf1225.groupeo.uclove.R;
import com.lsinf1225.groupeo.uclove.database.Notification;
import com.lsinf1225.groupeo.uclove.database.NotificationManager;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private View myFragmentView;
    long user_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_notifications, container, false);
        boolean hasFailed = true;

        // On récupère l'userID
        user_id = ((DrawerMainActivity) getActivity()).returnUserID();

        // Initalisation des array pour l'affichage :
        List<String> text = new ArrayList<>();
        List<String> date = new ArrayList<>();
        List<String> code = new ArrayList<>();
        List<String> status = new ArrayList<>();
        List<String> id = new ArrayList<>();

        NotificationManager notificationManager = new NotificationManager(getActivity());
        notificationManager.open();
        for (int i = 1; i <= 50; i++) {
            Notification notif = notificationManager.getRecentNotifications(user_id, i);
            if (notif.getNotifID() != -1) {
                hasFailed = false;
                text.add(notif.getNotifText());
                date.add(notif.getNotifDate());
                code.add(String.valueOf(notif.getNotifCode()));
                status.add(notif.getNotifStatus());
                id.add(String.valueOf(notif.getNotifID()));
            }
        }
        notificationManager.close();

        if (!hasFailed) {
            ListAdapter myListAdapter = new CustomAdapterNotifications(getActivity(), text, date, status, code, id);
            ListView myListView = (ListView) myFragmentView.findViewById(R.id.notifications_list_view);
            myListView.setAdapter(myListAdapter);

            myListView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            TextView textView1 = (TextView) view.findViewById(R.id.notifications_code);
                            Integer code = Integer.parseInt(textView1.getText().toString());

                            TextView textView2 = (TextView) view.findViewById(R.id.notifications_id);
                            Integer notifId = Integer.parseInt(textView2.getText().toString());

                            NotificationManager nm = new NotificationManager(getActivity());
                            nm.open();
                            Notification notif = nm.getNotification((long)notifId);
                            notif.setNotifStatus("Read");
                            nm.modNotification(notif);
                            nm.close();

                            NotificationSender ns = new NotificationSender("", notifId, getActivity());
                            ns.cancelNotification();

                            if (code == 1) {
                                Fragment fragment = new FriendsSearchFragment();
                                FragmentManager fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.content_frame, fragment)
                                        .commit();
                                ((DrawerMainActivity) getActivity()).setTitle(getActivity().getString(R.string.nav_friends_search));
                            } else if (code == 2) {
                                Fragment fragment = new FriendsFragment();
                                FragmentManager fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.content_frame, fragment)
                                        .commit();
                                ((DrawerMainActivity) getActivity()).setTitle(getActivity().getString(R.string.nav_friends));
                            } else if (code == 3) {
                                Fragment fragment = new MessagesListFragment();
                                FragmentManager fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.content_frame, fragment)
                                        .commit();
                                ((DrawerMainActivity) getActivity()).setTitle(getActivity().getString(R.string.nav_messages));
                            } else if (code == 4) {
                                Fragment fragment = new MeetRequestsFragment();
                                FragmentManager fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.content_frame, fragment)
                                        .commit();
                                ((DrawerMainActivity) getActivity()).setTitle(getActivity().getString(R.string.nav_meet_requests));
                            } else if (code == 5) {
                                Fragment fragment = new PlannedMeetingsFragment();
                                FragmentManager fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.content_frame, fragment)
                                        .commit();
                                ((DrawerMainActivity) getActivity()).setTitle(getActivity().getString(R.string.nav_meet_planned));
                            }
                        }
                    }
            );

            myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    NotificationManager nm = new NotificationManager(getActivity());
                    nm.open();
                    nm.deleteNotificationsFromUser(user_id);

                    NotificationSender ns = new NotificationSender("", 0, getActivity());
                    ns.cancelAllNotifications();

                    Context context = getActivity();
                    CharSequence text = getResources().getString(R.string.notifications_toast);;
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    Fragment fragment = new NotificationsFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .commit();
                    return true;
                }
            });

        } else {
            Fragment fragment = new NotificationsFailedFragment();
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }

        return myFragmentView;
    }
}