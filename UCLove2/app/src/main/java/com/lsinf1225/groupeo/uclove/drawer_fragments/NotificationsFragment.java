package com.lsinf1225.groupeo.uclove.drawer_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lsinf1225.groupeo.uclove.DrawerMainActivity;
import com.lsinf1225.groupeo.uclove.R;
import com.lsinf1225.groupeo.uclove.database.User;
import com.lsinf1225.groupeo.uclove.database.UserManager;
import com.squareup.picasso.Picasso;

public class NotificationsFragment extends Fragment {

    private View myFragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_notifications, container, false);

        // On récupère l'userID
        long user_id = ((DrawerMainActivity) getActivity()).returnUserID();
        UserManager m = new UserManager(getActivity()); // gestionnaire de la table "user"
        m.open();
        User currentUser = m.getUser(user_id);
        m.close();

        return myFragmentView;
    }
}