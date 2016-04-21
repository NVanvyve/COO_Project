package com.lsinf1225.groupeo.uclove;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private TextView currentSelection;
    private Button selectItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentSelection = (TextView) findViewById(R.id.current_selection);
        selectItemButton = (Button) findViewById(R.id.button_choose_item);
        selectItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(MainActivity.this, MyListActivity.class);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //"Gonfle" le menu (ajout des elements de la barre d'action)
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if(data.hasExtra(MyListActivity.CHOSEN_TEXT))
                currentSelection.setText(data.getExtras().getString(MyListActivity.CHOSEN_TEXT));
        }
    }
}
