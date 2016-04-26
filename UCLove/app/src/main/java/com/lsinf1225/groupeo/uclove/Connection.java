package com.lsinf1225.groupeo.uclove;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by nicolasvanvyve on 26/04/16.
 */
public class Connection extends MainActivity {

    String Login= "";
    String Passwd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection);

        Button ConnectButton = (Button) findViewById(R.id.ConnectButton);
        ConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(/*...*/);
                EditText log = (EditText) findViewById(R.id.editLogin);
                EditText pass = (EditText) findViewById(R.id.editPasswd);
                Login  = String.valueOf(log.getText().toString());
                Passwd = String.valueOf(pass.getText().toString());
                if ((Login.compareTo("")==0)||(Passwd.compareTo("")==0)) {
                    // Un champ est rester vide
                    // Afficher un message et ne pas aller plus loin
                }

                //Verifier que le couple Login/Passwd existe dans la base de données
            });
}
