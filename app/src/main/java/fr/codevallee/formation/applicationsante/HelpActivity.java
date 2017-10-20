package fr.codevallee.formation.applicationsante;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class HelpActivity extends AppCompatActivity{

    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        //Récupération de l'interface
        webView = (WebView) findViewById(R.id.wv_help);
        Button buttonRetour = (Button) findViewById(R.id.button_retour);

        //webView.loadUrl("https://fr.wikipedia.org/wiki/H%C3%B4pital");    //Par internet
        webView.loadUrl("file:///android_asset/Hopital.htm");               //En local

        //Assignation de l'action au bouton de retour:
        buttonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
