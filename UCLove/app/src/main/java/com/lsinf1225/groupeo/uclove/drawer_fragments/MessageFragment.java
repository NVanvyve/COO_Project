package com.lsinf1225.groupeo.uclove.drawer_fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.lsinf1225.groupeo.uclove.custom_adapters.CustomAdapterMessages;
import com.lsinf1225.groupeo.uclove.DrawerMainActivity;
import com.lsinf1225.groupeo.uclove.R;
import com.lsinf1225.groupeo.uclove.database.Message;
import com.lsinf1225.groupeo.uclove.database.MessageManager;
import com.lsinf1225.groupeo.uclove.database.Notification;
import com.lsinf1225.groupeo.uclove.database.NotificationManager;
import com.lsinf1225.groupeo.uclove.database.Relation;
import com.lsinf1225.groupeo.uclove.database.RelationManager;
import com.lsinf1225.groupeo.uclove.database.User;
import com.lsinf1225.groupeo.uclove.database.UserManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MessageFragment extends Fragment {

    User user_a;
    User user_b;
    long user_id_b;
    long user_id_a;
    long rel_id;

    private View myFragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_message, container, false);

        // On récupère les userids
        user_id_a = ((DrawerMainActivity) getActivity()).returnUserID();
        Bundle b = getArguments();
        user_id_b = (long)b.getInt("friend_user_id");

        UserManager um = new UserManager(getActivity());
        um.open();
        user_a = um.getUser(user_id_a);
        user_b = um.getUser(user_id_b);

        ((DrawerMainActivity) getActivity()).setTitle(this.getString(R.string.nav_message_with) + " " + user_b.getUserFirstName());

        RelationManager rm = new RelationManager(getActivity());
        rm.open();
        Relation relation = rm.getRelationFromUserIds(user_id_a, user_id_b);

        rel_id = relation.getRelID();

        // Initalisation des array pour l'affichage :
        List<String> message = new ArrayList<>();
        List<String> image = new ArrayList<>();
        List<Boolean> messageright = new ArrayList<>();

        MessageManager m = new MessageManager(getActivity());
        m.open();
        for (int i = 1; i<=50; i++) {
            Message objectMessage = m.getRecentMessage(rel_id, i);
            if (objectMessage.getMessageID() != -1) {
                message.add(objectMessage.getMessageText());
                if (objectMessage.getMessageUserID() == user_id_a){
                    image.add(user_a.getUserProfilePic());
                    messageright.add(true);
                } else {
                    image.add(user_b.getUserProfilePic());
                    messageright.add(false);
                }
            }
        }
        m.close();

        // On affiche la liste des messages
        ListAdapter myListAdapter = new CustomAdapterMessages(getActivity(), message, image, messageright);
        ListView myListView = (ListView) myFragmentView.findViewById(R.id.message_list_view);
        myListView.setAdapter(myListAdapter);


        Button button= (Button) myFragmentView.findViewById(R.id.message_send);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = (EditText) myFragmentView.findViewById(R.id.message_text_field);
                String textMessage = text.getText().toString();

                // Enregistre la date et l'heure
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                int hours = c.get(Calendar.HOUR_OF_DAY);
                int minutes = c.get(Calendar.MINUTE);
                int seconds = c.get(Calendar.SECOND);
                String date = year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;

                MessageManager mm = new MessageManager(getActivity());
                Message newMessage = new Message(0, (int)rel_id, (int)user_id_a, date, textMessage);
                mm.open();
                mm.addMessage(newMessage); // Ajoute le message

                // Envoie une notif
                NotificationManager nm = new NotificationManager(getActivity());
                nm.open();
                Notification notif = new Notification(0, (int)user_id_b, date, "Vous avez un nouveau message de "+user_a.getUserFirstName()+" "+user_a.getUserLastName()+".", "Unread", 3);
                nm.addNotification(notif);
                nm.close();

                Bundle args = new Bundle();
                args.putInt("friend_user_id", (int)user_id_b);
                Fragment fragment = new MessageFragment();
                fragment.setArguments(args);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();
            }
        });


        return myFragmentView;
    }
}