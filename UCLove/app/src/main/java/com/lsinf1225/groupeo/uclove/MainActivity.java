package com.lsinf1225.groupeo.uclove;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lsinf1225.groupeo.uclove.database.UserManager;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void switchToCreateAccount(View v) {
        // On lance l'activité de création de compte
        Intent AccountCreation = new Intent(MainActivity.this, AccountCreation.class);
        startActivity(AccountCreation);
    }

    public void connection(View v) {
        final EditText usernameField = (EditText) findViewById(R.id.main_username);
        String username = usernameField.getText().toString();

        final EditText passwordField = (EditText) findViewById(R.id.main_password);
        String password = passwordField.getText().toString();

        UserManager m = new UserManager(this); // gestionnaire de la table "user"
        m.open();

        long user_id = m.isAlreadyInDatabase(username, password);

        m.close();

        if (user_id > 0) {
            Intent DrawerActivity = new Intent(MainActivity.this, DrawerMainActivity.class);
            DrawerActivity.putExtra("userID", user_id);

            // Puis on lance l'intent !
            startActivity(DrawerActivity);
        } else {
            Context context = getApplicationContext();
            CharSequence text = getResources().getString(R.string.connection_invalid);
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    @Override
    public void onBackPressed() {
    }
}