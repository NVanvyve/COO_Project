package com.lsinf1225.groupeo.uclove;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Menu extends AppCompatActivity {

    long user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        Intent intent = getIntent();

        user_id = intent.getLongExtra("userID", -1L);

        UserManager m = new UserManager(this); // gestionnaire de la table "user"
        m.open();

        User currentUser = m.getUser(user_id);

        m.close();


        ImageView myImage = (ImageView) findViewById(R.id.menu_profile_picture);
        Picasso.with(myImage.getContext()).load(currentUser.getUserProfilePic()).fit().centerCrop().into(myImage);

    }

    public void switchToProfile(View v) {
        // Acces à Profil
        Intent Profile = new Intent(Menu.this, Profile.class);
        Profile.putExtra("userID", user_id);
        startActivity(Profile);
    }

}