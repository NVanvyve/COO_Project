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

import com.lsinf1225.groupeo.uclove.CustomAdapterFriends;
import com.lsinf1225.groupeo.uclove.CustomAdapterMessagesList;
import com.lsinf1225.groupeo.uclove.DrawerMainActivity;
import com.lsinf1225.groupeo.uclove.R;
import com.lsinf1225.groupeo.uclove.database.Message;
import com.lsinf1225.groupeo.uclove.database.MessageManager;
import com.lsinf1225.groupeo.uclove.database.Relation;
import com.lsinf1225.groupeo.uclove.database.RelationManager;
import com.lsinf1225.groupeo.uclove.database.User;
import com.lsinf1225.groupeo.uclove.database.UserManager;

import java.util.ArrayList;
import java.util.List;

public class MessagesListFragment extends Fragment {

    private View myFragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_messages_list, container, false);
        boolean hasFailed = true;

        // On récupère l'userID
        long user_id = ((DrawerMainActivity) getActivity()).returnUserID();

        // Initalisation des array pour l'affichage :
        List<String> name = new ArrayList<>();
        List<String> message = new ArrayList<>();
        List<String> image = new ArrayList<>();
        List<String> userid = new ArrayList<>();

        RelationManager rm = new RelationManager(getActivity());
        UserManager um = new UserManager(getActivity());
        MessageManager mm = new MessageManager(getActivity());
        rm.open();
        um.open();
        mm.open();
        for (int i = 1; i<=50; i++) {
            long friend_user_id = rm.getFriendsWhoSentMessage(user_id, i);
            if (friend_user_id != -1) {
                Relation relation = rm.getRelationFromUserIds(user_id, friend_user_id);
                long rel_id = relation.getRelID();

                hasFailed = false;
                User friend = um.getUser(friend_user_id);

                name.add(friend.getUserFirstName() + " " + friend.getUserLastName());

                Message recentMessage = mm.getMostRecentMessage(rel_id);
                message.add(recentMessage.getMessageText());

                image.add(friend.getUserProfilePic());
                userid.add(String.valueOf(friend.getUserID()));
            }
        }
        rm.close();
        um.close();
        mm.close();

        if (!hasFailed) {
            ListAdapter myListAdapter = new CustomAdapterMessagesList(getActivity(), name, message, image, userid);
            ListView myListView = (ListView) myFragmentView.findViewById(R.id.messages_list_list_view);
            myListView.setAdapter(myListAdapter);

            myListView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            TextView textView = (TextView) view.findViewById(R.id.custom_row_messages_list_user_id);
                            Integer friendUserId = Integer.parseInt(textView.getText().toString());

                            Bundle args = new Bundle();
                            args.putInt("friend_user_id", friendUserId);
                            Fragment fragment = new MessageFragment();
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
            Fragment fragment = new MessagesListFailedFragment();
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }

        return myFragmentView;
    }
}