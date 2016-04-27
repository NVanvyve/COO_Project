package com.lsinf1225.groupeo.uclove;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.net.Uri;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class Menu extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        Intent intent = getIntent();

        long user_id = intent.getLongExtra("userID", -1L);
        System.out.println(user_id);

        UserManager m = new UserManager(this); // gestionnaire de la table "user"
        m.open();

        User currentUser = m.getUser(user_id);

        m.close();

        System.out.println("LENGTH 2 = " + currentUser.getUserProfilePic().length);
        Bitmap bitmap = BitmapFactory.decodeByteArray(currentUser.getUserProfilePic() , 0, currentUser.getUserProfilePic().length);

        ImageView profile_picture = (ImageView) findViewById(R.id.menu_profile_picture);
        //profile_picture.setImageBitmap(bitmap);
        //profile_picture.setImageResource(R.drawable.testpic);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        profile_picture.setMinimumHeight(dm.heightPixels);
        profile_picture.setMinimumWidth(dm.widthPixels);
        profile_picture.setImageBitmap(bitmap);
    }
}