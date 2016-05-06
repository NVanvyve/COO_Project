package com.lsinf1225.groupeo.uclove;

import android.app.FragmentManager;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.lsinf1225.groupeo.uclove.database.Calendar;
import com.lsinf1225.groupeo.uclove.database.CalendarManager;
import com.lsinf1225.groupeo.uclove.database.Notification;
import com.lsinf1225.groupeo.uclove.database.NotificationManager;
import com.lsinf1225.groupeo.uclove.database.User;
import com.lsinf1225.groupeo.uclove.database.UserManager;
import com.lsinf1225.groupeo.uclove.drawer_fragments.DatePreferencesFragment;
import com.lsinf1225.groupeo.uclove.drawer_fragments.EditProfileFragment;
import com.lsinf1225.groupeo.uclove.drawer_fragments.FavouritesFragment;
import com.lsinf1225.groupeo.uclove.drawer_fragments.FriendsFragment;
import com.lsinf1225.groupeo.uclove.drawer_fragments.FriendsSearchFragment;
import com.lsinf1225.groupeo.uclove.drawer_fragments.MeetRequestsFragment;
import com.lsinf1225.groupeo.uclove.drawer_fragments.MessagesListFragment;
import com.lsinf1225.groupeo.uclove.drawer_fragments.NotificationsFragment;
import com.lsinf1225.groupeo.uclove.drawer_fragments.NotificationsPreferencesFragment;
import com.lsinf1225.groupeo.uclove.drawer_fragments.PlannedMeetingsFragment;
import com.lsinf1225.groupeo.uclove.drawer_fragments.ProfileFragment;
import com.lsinf1225.groupeo.uclove.drawer_fragments.SearchPreferencesFragment;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class DrawerMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener{

    long user_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // On récupère l'user_id et on update sa position
        Intent intent = getIntent();
        user_id = intent.getLongExtra("userID", -1L);
        UserManager m = new UserManager(this); // gestionnaire de la table "user"
        m.open();
        User currentUser = m.getUser(user_id);
        GPSTracker gps = new GPSTracker(this);
        String position = gps.getCoord(); // On récupère les coordonnées
        currentUser.setUserPosition(position);
        m.modUser(currentUser);
        m.close();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // custom drawer header
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_drawer_main);
        ImageView myImage = (ImageView) headerView.findViewById(R.id.nav_header_profile_picture);
        TextView myText = (TextView) headerView.findViewById(R.id.nav_header_text);
        Picasso.with(myImage.getContext()).load(currentUser.getUserProfilePic()).fit().centerCrop().into(myImage);
        String completeName = currentUser.getUserFirstName() + " " + currentUser.getUserLastName();
        myText.setText(completeName);
        // Create a new fragment and specify the planet to show based on position
        Fragment fragment = new ProfileFragment();

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        navigationView.setCheckedItem(R.id.nav_profile);
        setTitle(this.getString(R.string.nav_profile));

        // Envoie des notifications système
        NotificationManager nm = new NotificationManager(this);
        nm.open();
        for (int i = 0; i < 10; i++) {
            Notification notif = nm.getRecentUnreadNotifications(user_id, i);
            if (notif.getNotifID() != -1) {
                NotificationSender notifToSend = new NotificationSender(notif.getNotifText(), notif.getNotifID(), this);
                notifToSend.send();
            }
        }
        nm.close();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // Do nothing
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Fragment fragment = new ProfileFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
            setTitle(this.getString(R.string.nav_profile));

        } else if (id == R.id.nav_notifications) {
            Fragment fragment = new NotificationsFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
            setTitle(this.getString(R.string.nav_notifications));

        } else if (id == R.id.nav_friends_search) {
            Fragment fragment = new FriendsSearchFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
            setTitle(this.getString(R.string.nav_friends_search));

        } else if (id == R.id.nav_search_preferences) {
            Fragment fragment = new SearchPreferencesFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
            setTitle(this.getString(R.string.nav_search_preferences));

        } else if (id == R.id.nav_messages) {
            Fragment fragment = new MessagesListFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
            setTitle(this.getString(R.string.nav_messages));

        } else if (id == R.id.nav_notifications_preferences) {
            Fragment fragment = new NotificationsPreferencesFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
            setTitle(this.getString(R.string.nav_notifications_preferences));

        } else if (id == R.id.nav_date_preferences) {
            Fragment fragment = new DatePreferencesFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
            setTitle(this.getString(R.string.nav_date_preferences));

        } else if (id == R.id.nav_meet_requests) {
            Fragment fragment = new MeetRequestsFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
            setTitle(this.getString(R.string.nav_meet_requests));

        } else if (id == R.id.nav_meet_planned) {
            Fragment fragment = new PlannedMeetingsFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
            setTitle(this.getString(R.string.nav_meet_planned));

        } else if (id == R.id.nav_friends) {
            Fragment fragment = new FriendsFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
            setTitle(this.getString(R.string.nav_friends));

        } else if (id == R.id.nav_favourites) {
            Fragment fragment = new FavouritesFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
            setTitle(this.getString(R.string.nav_favourites));

        } else if (id == R.id.nav_edit_profile) {
            Fragment fragment = new EditProfileFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
            setTitle(this.getString(R.string.nav_edit_profile));

        } else if (id == R.id.nav_log_off) {
            // On passe l'userID dans l'intent
            Intent intent = new Intent(DrawerMainActivity.this, MainActivity.class);
            // Puis on lance l'intent !
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public long returnUserID() {
        return user_id;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        //do some stuff for example write on log and update TextField on activity
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String freeDate = sdf.format(c.getTime());

        Calendar newCal = new Calendar(0, (int)user_id, freeDate, "Free");
        CalendarManager cm = new CalendarManager(this);
        cm.open();
        cm.addCalendar(newCal);
        cm.close();

        Fragment fragment = new DatePreferencesFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
        setTitle(this.getString(R.string.nav_date_preferences));
    }
}
