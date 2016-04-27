package com.lsinf1225.groupeo.uclove;

import android.app.Activity;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button_create_account = (Button) findViewById(R.id.main_create_account);

        button_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent AccountCreation = new Intent(MainActivity.this, AccountCreation.class);

                // Puis on lance l'intent !
                startActivity(AccountCreation);
            }
        });



    }
}