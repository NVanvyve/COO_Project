package com.lsinf1225.groupeo.uclove.drawer_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lsinf1225.groupeo.uclove.CustomAdapter;
import com.lsinf1225.groupeo.uclove.DrawerMainActivity;
import com.lsinf1225.groupeo.uclove.R;
import com.lsinf1225.groupeo.uclove.database.RelationManager;
import com.lsinf1225.groupeo.uclove.database.User;
import com.lsinf1225.groupeo.uclove.database.UserManager;
import com.squareup.picasso.Picasso;

import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment {

    private View myFragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_friends, container, false);

        // On récupère l'userID
        long user_id = ((DrawerMainActivity) getActivity()).returnUserID();
        UserManager m = new UserManager(getActivity()); // gestionnaire de la table "user"
        m.open();
        User currentUser = m.getUser(user_id);
        m.close();

        // Initalisation des array pour l'affichage :
        List<String> name = new ArrayList<>();
        List<String> city = new ArrayList<>();
        List<String> image = new ArrayList<>();

        RelationManager rm = new RelationManager(getActivity());
        UserManager um = new UserManager(getActivity());
        rm.open();
        um.open();
        for (int i = 1; i<=50; i++) {
            long friend_user_id = rm.getFriend(user_id, i);
            if (friend_user_id != -1) {
                User friend = um.getUser(friend_user_id);
                name.add(friend.getUserFirstName() + " " + friend.getUserLastName());
                city.add(friend.getUserCity());
                image.add(friend.getUserProfilePic());
            }
        }
        rm.close();

        ListAdapter myListAdapter = new CustomAdapter(getActivity(), name, city, image);
        ListView myListView = (ListView) myFragmentView.findViewById(R.id.friends_list_view);
        myListView.setAdapter(myListAdapter);

        myListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        System.out.println("lawl");
                        //String food = String.valueOf(parent.getItemAtPosition(position));
                        //Toast.makeText(getActivity(), food, Toast.LENGTH_SHORT).show();
                    }
                }
        );





        return myFragmentView;
    }
}