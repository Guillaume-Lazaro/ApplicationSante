package fr.codevallee.formation.applicationsante;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class PreferencesActivity extends AppCompatActivity{

    private ListView listViewMetiers;
    private ArrayList<String> listMetiers;
    private SharedPreferences spMetiers;
    private String metierEntre;
    private Button buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        //Récupération des éléments de l'interface:
        listViewMetiers = (ListView) findViewById(R.id.lv_metiers);
        Button buttonAjouterMetier = (Button) findViewById(R.id.button_ajouter_metier);
        buttonCancel = (Button) findViewById(R.id.button_annuler);

        //Récupération des sharedPreferences:
        spMetiers = getSharedPreferences("metiers",MODE_PRIVATE);
        listMetiers = new ArrayList<>(spMetiers.getStringSet("metiersSet", new HashSet<String>()));

        //Récupération et mise en forme:
        listMetiers = new ArrayList<>(spMetiers.getStringSet("metiersSet", new HashSet<String>()));
        refreshListMetiers();

        //Boutons et clickListener :
        buttonAjouterMetier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogMetier();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreferencesActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //Affichage d'une alertDialog pour demander le métier à entrer
    public void alertDialogMetier() {
        //On crée l'alertDialog avec un EditText
        this.metierEntre = "";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.metier);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        //On assigne 2 boutons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Si oui, on ajoute le métier
                metierEntre = input.getText().toString();
                addMetier();
            }
        });
        builder.setNegativeButton(R.string.annuler, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Sinon, on ferme la dialog
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void addMetier() {
        if(!metierEntre.equals(""))
            listMetiers.add(metierEntre);

        SharedPreferences.Editor metiersEditor = spMetiers.edit();
        metiersEditor.putStringSet("metiersSet",new HashSet<String>(listMetiers));
        metiersEditor.commit();
        refreshListMetiers();
    }

    public void refreshListMetiers() {
        ArrayAdapter<String> metiersAdapter;
        metiersAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listMetiers);
        listViewMetiers.setAdapter(metiersAdapter);
    }
}
