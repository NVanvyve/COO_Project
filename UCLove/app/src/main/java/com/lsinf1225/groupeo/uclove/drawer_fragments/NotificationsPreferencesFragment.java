package com.lsinf1225.groupeo.uclove.drawer_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.lsinf1225.groupeo.uclove.DrawerMainActivity;
import com.lsinf1225.groupeo.uclove.R;
import com.lsinf1225.groupeo.uclove.database.NotifAuth;
import com.lsinf1225.groupeo.uclove.database.NotifAuthManager;
import com.lsinf1225.groupeo.uclove.database.Preferences;
import com.lsinf1225.groupeo.uclove.database.PreferencesManager;
import com.lsinf1225.groupeo.uclove.database.User;
import com.lsinf1225.groupeo.uclove.database.UserManager;

public class NotificationsPreferencesFragment extends Fragment {

    private View myFragmentView;
    User currentUser;
    NotifAuth notifPreference1;
    NotifAuth notifPreference2;
    NotifAuth notifPreference3;
    NotifAuth notifPreference4;
    NotifAuth notifPreference5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_notifications_preferences, container, false);

        // On récupère l'userID
        long user_id = ((DrawerMainActivity) getActivity()).returnUserID();
        UserManager m = new UserManager(getActivity()); // gestionnaire de la table "user"
        m.open();
        currentUser = m.getUser(user_id);
        m.close();

        NotifAuthManager nam = new NotifAuthManager(getActivity());
        nam.open();
        notifPreference1 = nam.getNotifAuth(currentUser.getUserID(), 1);
        notifPreference2 = nam.getNotifAuth(currentUser.getUserID(), 2);
        notifPreference3 = nam.getNotifAuth(currentUser.getUserID(), 3);
        notifPreference4 = nam.getNotifAuth(currentUser.getUserID(), 4);
        notifPreference5 = nam.getNotifAuth(currentUser.getUserID(), 5);
        nam.close();

        // Lit les préférences de notifications et mets les switches dans la bonne position
        setFields();


        Switch sw;

        sw = (Switch) myFragmentView.findViewById(R.id.pref_notif_friend_request);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    notifPreference1.setNotifAuthBool(1);
                    NotifAuthManager nam = new NotifAuthManager(getActivity());
                    nam.open();
                    nam.modNotifAuth(notifPreference1);
                    nam.close();
                } else {
                    notifPreference1.setNotifAuthBool(0);
                    NotifAuthManager nam = new NotifAuthManager(getActivity());
                    nam.open();
                    nam.modNotifAuth(notifPreference1);
                    nam.close();
                }
            }
        });

        sw = (Switch) myFragmentView.findViewById(R.id.pref_notif_friend_accepted);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    notifPreference2.setNotifAuthBool(1);
                    NotifAuthManager nam = new NotifAuthManager(getActivity());
                    nam.open();
                    nam.modNotifAuth(notifPreference2);
                    nam.close();
                } else {
                    notifPreference2.setNotifAuthBool(0);
                    NotifAuthManager nam = new NotifAuthManager(getActivity());
                    nam.open();
                    nam.modNotifAuth(notifPreference2);
                    nam.close();
                }
            }
        });

        sw = (Switch) myFragmentView.findViewById(R.id.pref_notif_new_message);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    notifPreference3.setNotifAuthBool(1);
                    NotifAuthManager nam = new NotifAuthManager(getActivity());
                    nam.open();
                    nam.modNotifAuth(notifPreference3);
                    nam.close();
                } else {
                    notifPreference3.setNotifAuthBool(0);
                    NotifAuthManager nam = new NotifAuthManager(getActivity());
                    nam.open();
                    nam.modNotifAuth(notifPreference3);
                    nam.close();
                }
            }
        });

        sw = (Switch) myFragmentView.findViewById(R.id.pref_notif_meeting_request);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    notifPreference4.setNotifAuthBool(1);
                    NotifAuthManager nam = new NotifAuthManager(getActivity());
                    nam.open();
                    nam.modNotifAuth(notifPreference4);
                    nam.close();
                } else {
                    notifPreference4.setNotifAuthBool(0);
                    NotifAuthManager nam = new NotifAuthManager(getActivity());
                    nam.open();
                    nam.modNotifAuth(notifPreference4);
                    nam.close();
                }
            }
        });

        sw = (Switch) myFragmentView.findViewById(R.id.pref_notif_meeting_accepted);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    notifPreference5.setNotifAuthBool(1);
                    NotifAuthManager nam = new NotifAuthManager(getActivity());
                    nam.open();
                    nam.modNotifAuth(notifPreference5);
                    nam.close();
                } else {
                    notifPreference5.setNotifAuthBool(0);
                    NotifAuthManager nam = new NotifAuthManager(getActivity());
                    nam.open();
                    nam.modNotifAuth(notifPreference5);
                    nam.close();
                }
            }
        });

        // Inflate the layout for this fragment
        return myFragmentView;
    }

    public void setFields() {
        if (notifPreference1.getNotifAuthBool() == 0) {
            Switch sw = (Switch) myFragmentView.findViewById(R.id.pref_notif_friend_request);
            sw.setChecked(false);
        }

        if (notifPreference2.getNotifAuthBool() == 0) {
            Switch sw = (Switch) myFragmentView.findViewById(R.id.pref_notif_friend_accepted);
            sw.setChecked(false);
        }

        if (notifPreference3.getNotifAuthBool() == 0) {
            Switch sw = (Switch) myFragmentView.findViewById(R.id.pref_notif_new_message);
            sw.setChecked(false);
        }

        if (notifPreference4.getNotifAuthBool() == 0) {
            Switch sw = (Switch) myFragmentView.findViewById(R.id.pref_notif_meeting_request);
            sw.setChecked(false);
        }

        if (notifPreference5.getNotifAuthBool() == 0) {
            Switch sw = (Switch) myFragmentView.findViewById(R.id.pref_notif_meeting_accepted);
            sw.setChecked(false);
        }
    }
}