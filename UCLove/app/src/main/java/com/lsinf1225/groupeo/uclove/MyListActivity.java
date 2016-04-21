package com.lsinf1225.groupeo.uclove;

        import android.app.ListActivity;
        import java.util.List;
        import java.util.Arrays;
        import android.os.Bundle;
        import android.widget.ArrayAdapter;
        import android.widget.AdapterView;
        import android.widget.Toast;
        import android.view.View;
        import android.content.Intent;

public class MyListActivity extends ListActivity {
    public static final String CHOSEN_TEXT = "texteChoisi";
    List<String> someStrings = Arrays.asList("Adrien Ballet", "Vincent Vandervilt", "Maxime Wattiaux", "Maxime de Streel", "Nicolas Vanvyve");
    String chosenString;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, //contexte courant
                android.R.layout.simple_list_item_1, //id de l'element de layout utilise
                                                    //par defaut pour representer les
                                                    //lignes de la liste (cet id est pre
                                                    // -defini dans Android)
                someStrings); // la collection d'elements à adapter à la liste
        setListAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chosenString = someStrings.get(position);
                Toast.makeText(MyListActivity.this, "Vous avez choisi : " + chosenString, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void finish() {
        if(chosenString != null) {
            Intent data = new Intent();
            data.putExtra(CHOSEN_TEXT, chosenString);
            setResult(RESULT_OK, data);
        }
        super.finish();
    }
}
