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

import com.lsinf1225.groupeo.uclove.custom_adapters.CustomAdapterFavourites;
import com.lsinf1225.groupeo.uclove.DrawerMainActivity;
import com.lsinf1225.groupeo.uclove.R;
import com.lsinf1225.groupeo.uclove.database.RelationManager;
import com.lsinf1225.groupeo.uclove.database.User;
import com.lsinf1225.groupeo.uclove.database.UserManager;

import java.util.ArrayList;
import java.util.List;

public class FavouritesFragment extends Fragment {

    private View myFragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_favourites, container, false);
        boolean hasFailed = true;

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
        List<String> userid = new ArrayList<>();

        RelationManager rm = new RelationManager(getActivity());
        UserManager um = new UserManager(getActivity());
        rm.open();
        um.open();
        for (int i = 1; i<=50; i++) {
            long friend_user_id = rm.getFavorite(user_id, i);
            if (friend_user_id != -1) {
                hasFailed = false;
                User friend = um.getUser(friend_user_id);
                name.add(friend.getUserFirstName() + " " + friend.getUserLastName());
                city.add(friend.getUserCity());
                image.add(friend.getUserProfilePic());
                userid.add(String.valueOf(friend.getUserID()));
            }
        }
        rm.close();

        if (!hasFailed) {
            // S'il y a un élément, on affiche la liste
            ListAdapter myListAdapter = new CustomAdapterFavourites(getActivity(), name, city, image, userid);
            ListView myListView = (ListView) myFragmentView.findViewById(R.id.favourites_list_view);
            myListView.setAdapter(myListAdapter);

            myListView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            TextView textView = (TextView) view.findViewById(R.id.custom_row_favourites_user_id);
                            Integer friendUserId = Integer.parseInt(textView.getText().toString());

                            Bundle args = new Bundle();
                            args.putInt("friend_user_id", friendUserId);
                            Fragment fragment = new FriendProfileFragment();
                            fragment.setArguments(args);

                            // Si on clique sur une rangée de la liste, on charge le profil de cet ami
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
            Fragment fragment = new FavouritesFailedFragment();
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }

        return myFragmentView;
    }
}