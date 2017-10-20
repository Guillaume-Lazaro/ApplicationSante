package fr.codevallee.formation.applicationsante;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.ArraySet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class PreferencesActivity extends AppCompatActivity{

    private ListView listViewMetiers;
    private ArrayList<String> listMetiers;
    private SharedPreferences spMetiers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        //Récupération des éléments de l'interface:
        listViewMetiers = (ListView) findViewById(R.id.lv_metiers);
        Button buttonAjouterMetier = (Button) findViewById(R.id.button_ajouter_metier);

        //Récupération des sharedPreferences:
        spMetiers = getSharedPreferences("metiers",MODE_PRIVATE);
        listMetiers = new ArrayList<>(spMetiers.getStringSet("metiersSet", new HashSet<String>()));

        /*
        //Remplissage de la liste par défaut:
        listMetiers = new ArrayList<>();
        listMetiers.add("Infirmier");
        listMetiers.add("Brancardier");
        listMetiers.add("Médecin");
        */

        /*
        SharedPreferences.Editor metiersEditor = spMetiers.edit();
        metiersEditor.putStringSet("metiersSet",new HashSet<String>(listMetiers));

        metiersEditor.commit();
        */

        //Récupération et mise en forme:
        listMetiers = new ArrayList<>(spMetiers.getStringSet("metiersSet", new HashSet<String>()));
        refreshListMetiers();

        //Boutons et clickListener :
        buttonAjouterMetier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor metiersEditor = spMetiers.edit();
                listMetiers.add("Chrirurgien");
                metiersEditor.putStringSet("metiersSet",new HashSet<String>(listMetiers));
                metiersEditor.commit();

                refreshListMetiers();
            }
        });
    }

    public void refreshListMetiers() {
        ArrayAdapter<String> metiersAdapter;
        metiersAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listMetiers);
        listViewMetiers.setAdapter(metiersAdapter);
    }
}
