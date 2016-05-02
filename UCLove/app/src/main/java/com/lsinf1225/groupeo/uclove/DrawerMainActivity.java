package com.lsinf1225.groupeo.uclove;

import android.app.FragmentManager;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.lsinf1225.groupeo.uclove.database.User;
import com.lsinf1225.groupeo.uclove.database.UserManager;
import com.lsinf1225.groupeo.uclove.drawer_fragments.NotificationsPreferencesFragment;
import com.lsinf1225.groupeo.uclove.drawer_fragments.ProfileFragment;
import com.lsinf1225.groupeo.uclove.drawer_fragments.SearchPreferencesFragment;
import com.squareup.picasso.Picasso;

public class DrawerMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

        // On récupère l'user_id.
        Intent intent = getIntent();
        user_id = intent.getLongExtra("userID", -1L);
        UserManager m = new UserManager(this); // gestionnaire de la table "user"
        m.open();
        User currentUser = m.getUser(user_id);
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
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

        } else if (id == R.id.nav_search_preferences) {
            Fragment fragment = new SearchPreferencesFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
            setTitle(this.getString(R.string.nav_search_preferences));

        } else if (id == R.id.nav_notifications_preferences) {
            Fragment fragment = new NotificationsPreferencesFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
            setTitle(this.getString(R.string.nav_notifications_preferences));

        } else if (id == R.id.nav_friends) {

        } else if (id == R.id.nav_favourites) {

        } else if (id == R.id.nav_messages) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public long returnUserID() {
        return user_id;
    }
}
