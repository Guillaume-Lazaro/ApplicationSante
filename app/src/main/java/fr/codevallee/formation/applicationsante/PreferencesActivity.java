package fr.codevallee.formation.applicationsante;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class PreferencesActivity extends AppCompatActivity{

    private ListView listViewMetiers;
    private String[] listMetiers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        //Récupération des éléments de l'interface:
        listViewMetiers = (ListView) findViewById(R.id.lv_metiers);

        //Remplissage de la liste:
        listMetiers = getResources().getStringArray(R.array.metiers);
        ArrayAdapter<String> metiersAdapter;
        metiersAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listMetiers);
        listViewMetiers.setAdapter(metiersAdapter);

    }
}
