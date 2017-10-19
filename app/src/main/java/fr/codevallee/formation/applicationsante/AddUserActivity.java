package fr.codevallee.formation.applicationsante;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;


public class AddUserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText etNom;
    private EditText etPrenom;
    private RadioGroup radioSexeGroup;
    private RadioButton radioSexeButton;
    private AutoCompleteTextView actvMetier;
    private Spinner spinnerService;
    private EditText etMail;
    private EditText etTel;
    private EditText etCv;

    private String[] arrayMetier;
    private String[] arrayService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        //Récupération des élements de l'interface:
        etNom = (EditText) findViewById(R.id.et_nom);
        etPrenom = (EditText) findViewById(R.id.et_prenom);
        radioSexeGroup = (RadioGroup) findViewById(R.id.radio_sexe);
        actvMetier = (AutoCompleteTextView) findViewById(R.id.actv_metier);
        spinnerService = (Spinner) findViewById(R.id.spinner_service);
        etMail = (EditText) findViewById(R.id.et_mail);
        etTel = (EditText) findViewById(R.id.et_tel);
        etCv = (EditText) findViewById(R.id.et_cv);

        //ça c'est pour le radio button:
        int selectedId = radioSexeGroup.getCheckedRadioButtonId();
        radioSexeButton = (RadioButton) findViewById(selectedId);

        //ça c'est pour l'auto complete text view:
        arrayMetier = getResources().getStringArray(R.array.metiers);
        ArrayAdapter<String> metierAdapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, arrayMetier);
        actvMetier.setThreshold(1);
        actvMetier.setAdapter(metierAdapter);

        //ça c'est pour le spinner:
        arrayService = getResources().getStringArray(R.array.services);
        spinnerService.setOnItemSelectedListener(this);
        ArrayAdapter<String> serviceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayService);
        serviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerService.setAdapter(serviceAdapter);

    }

    //Pour plus tard:
    public void createUser() {
        String nom,prenom,sexe,metier,service,mail,tel,cv;
        nom = etNom.getText().toString();
        prenom = etPrenom.getText().toString();
        sexe = radioSexeButton.getText().toString();
        metier = actvMetier.getText().toString();
        service = spinnerService.getSelectedItem().toString();
        mail = etMail.getText().toString();
        tel = etTel.getText().toString();
        cv = etCv.getText().toString();

        User newUser = new User(nom,prenom,sexe,metier,service,mail,tel,cv);

        //Insertion de l'objet newUser dans la base de données;
        UserDataSource userDataSource = new UserDataSource(this);
        UserDAO userDAO = new UserDAO(userDataSource);
        userDAO.create(newUser);
    }

    public boolean champsCorrects() {
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Service sélectionné:
        String item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //On verra si on met un truc là...
    }
}
