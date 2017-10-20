package fr.codevallee.formation.applicationsante;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;


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
    private Button buttonAdd;
    private Button buttonCancel;

    private ArrayList<String> arrayMetier;
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
        buttonAdd = (Button) findViewById(R.id.button_ajouter);
        buttonCancel = (Button) findViewById(R.id.button_annuler);

        //ça c'est pour le radio button:
        int selectedId = radioSexeGroup.getCheckedRadioButtonId();
        radioSexeButton = (RadioButton) findViewById(selectedId);

        //Récupération de la liste des métiers dans les sharedPreferences:
        SharedPreferences spMetiers = getSharedPreferences("metiers",MODE_PRIVATE);
        arrayMetier = new ArrayList<>(spMetiers.getStringSet("metiersSet", new HashSet<String>()));

        //ça c'est pour l'auto complete text view:
        ArrayAdapter<String> metierAdapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, arrayMetier);
        actvMetier.setThreshold(1);
        actvMetier.setAdapter(metierAdapter);

        //ça c'est pour le spinner:
        arrayService = getResources().getStringArray(R.array.services);
        spinnerService.setOnItemSelectedListener(this);
        ArrayAdapter<String> serviceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayService);
        serviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerService.setAdapter(serviceAdapter);

        //Assignation d'une action au bouton d'ajout:
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (champsCorrects()) {
                    createUser();
                    Intent intent = new Intent(AddUserActivity.this, MainActivity.class);
                    startActivity(intent);
                    AddUserActivity.this.finish();
                } else {
                    Toast.makeText(AddUserActivity.this, getResources().getString(R.string.champ_incorrect), Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddUserActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void createUser() {
        String nom,prenom,sexe,metier,service,mail,tel,cv;
        nom = etNom.getText().toString();
        prenom = etPrenom.getText().toString();

        int rbSelectedId = radioSexeGroup.getCheckedRadioButtonId();    //Ici, on récupére l'id du radiobutton séléctionné
        radioSexeButton = (RadioButton) findViewById(rbSelectedId);     //afin de pouvoir récupérer le bon radiobutton
        sexe = radioSexeButton.getText().toString();                    //et enfin de récupérer la chaine correspondante

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
        if (etNom.getText().toString().equals(""))
            return false;
        else if (etPrenom.getText().toString().equals(""))
            return false;
        else if (actvMetier.getText().toString().equals(""))
            return false;
        else if (etMail.getText().toString().equals(""))
            return false;
        else if (!telCorrect())
            return false;
        else if (etMail.getText().toString().equals(""))
            return false;
        else if (etCv.getText().toString().equals(""))
            return false;
        else
            return true;
    }

    public boolean telCorrect() {
        String regexStr = "^[0-9]{10}$";

        if (etTel.getText().toString().matches(regexStr)) {
            return true;
        } else {
            Toast.makeText(this, getResources().getString(R.string.numero_incorrect), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
