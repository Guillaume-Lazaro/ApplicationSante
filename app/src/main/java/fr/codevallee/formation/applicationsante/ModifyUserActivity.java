package fr.codevallee.formation.applicationsante;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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


public class ModifyUserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText etNom;
    private EditText etPrenom;
    private RadioGroup radioSexeGroup;
    private RadioButton radioSexeButton;
    private AutoCompleteTextView actvMetier;
    private Spinner spinnerService;
    private EditText etMail;
    private EditText etTel;
    private EditText etCv;
    private Button buttonModify;
    private Button buttonCancel;

    private ArrayList<String> arrayMetier;
    private String[] arrayService;

    User userSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user);

        //Récupération des élements de l'interface:
        etNom = (EditText) findViewById(R.id.et_nom);
        etPrenom = (EditText) findViewById(R.id.et_prenom);
        radioSexeGroup = (RadioGroup) findViewById(R.id.radio_sexe);
        actvMetier = (AutoCompleteTextView) findViewById(R.id.actv_metier);
        spinnerService = (Spinner) findViewById(R.id.spinner_service);
        etMail = (EditText) findViewById(R.id.et_mail);
        etTel = (EditText) findViewById(R.id.et_tel);
        etCv = (EditText) findViewById(R.id.et_cv);
        buttonModify = (Button) findViewById(R.id.button_modifier_valider);
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

        //Assignation d'une action au bouton de modification:
        buttonModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (champsCorrects()) {
                    modifyUser();
                    Intent intent = new Intent(ModifyUserActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ModifyUserActivity.this, getResources().getString(R.string.champ_incorrect), Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModifyUserActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //On récupére l'user passé en argument
        int userId = getIntent().getIntExtra("user_id",0);
        UserDataSource userDataSource = new UserDataSource(this);
        UserDAO userDAO = new UserDAO(userDataSource);
        userSelected = userDAO.read(userId);
        updateUserField();
    }

    public void updateUserField() {
        //On remplit les champs avec les informations de l'user récupéré:
        etNom.setText(userSelected.getNom());
        etPrenom.setText(userSelected.getPrenom());
        actvMetier.setText(userSelected.getMetier());
        etMail.setText(userSelected.getMail());
        etTel.setText(userSelected.getTel());
        etCv.setText(userSelected.getCV());

        //Boucle pour comparer l'info en base de données au
        int sexe=1;
        for(int i=0 ; i<radioSexeGroup.getChildCount() ; i++) {
            RadioButton rbCurrent = (RadioButton)radioSexeGroup.getChildAt(i);
            Log.d("Test","Comparaison de "+rbCurrent.getText()+" et "+userSelected.getSexe());
            if(rbCurrent.getText().equals(userSelected.getSexe())) {
                sexe=i;
            }
        }
        radioSexeGroup.check(radioSexeGroup.getChildAt(sexe).getId());
        //spinnerService.set //TODO améliorer ça
    }

    public void modifyUser() {
        String nom,prenom,sexe,metier,service,mail,tel,cv;
        int id = userSelected.getId();
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

        User updateUser = new User(id,nom,prenom,sexe,metier,service,mail,tel,cv);

        //Mise à jour de l'objet updateUser dans la base de données;
        UserDataSource userDataSource = new UserDataSource(this);
        UserDAO userDAO = new UserDAO(userDataSource);
        userDAO.update(updateUser);
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
        //Service sélectionné:
        String item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //On verra si on met un truc là...
    }
}
