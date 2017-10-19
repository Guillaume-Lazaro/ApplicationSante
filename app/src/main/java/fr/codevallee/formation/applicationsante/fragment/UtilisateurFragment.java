package fr.codevallee.formation.applicationsante.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import fr.codevallee.formation.applicationsante.R;
import fr.codevallee.formation.applicationsante.User;
import fr.codevallee.formation.applicationsante.UserDAO;
import fr.codevallee.formation.applicationsante.UserDataSource;

public class UtilisateurFragment extends Fragment {

    private TextView tvNomPrenom;
    private TextView tvSexe;
    private TextView tvMetier;
    private TextView tvService;
    private TextView tvEmail;
    private TextView tvTel;
    private TextView tvCv;

    private Button buttonModifier;
    private Button buttonSupprimer;

    public static UtilisateurFragment newInstance() {
        UtilisateurFragment fragment = new UtilisateurFragment();
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();

        //On vérifie qu'il y a des args, si oui on met à jour la view avec l'user concerné
        if (args != null) {
            updateUserView(args.getInt("position"));
        } /*else if (mCurrentPosition != -1) { //TODO s'occuper de mCurrentPosition
            // Set article based on saved instance state defined during onCreateView
            updateArticleView(mCurrentPosition);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_utilisateur, container, false);

        //Récupération des TextViews:
        tvNomPrenom = view.findViewById(R.id.tv_nom_prenom_display);
        tvSexe = view.findViewById(R.id.tv_sexe);
        tvMetier = view.findViewById(R.id.tv_metier);
        tvService = view.findViewById(R.id.tv_service);
        tvEmail = view.findViewById(R.id.tv_email);
        tvTel = view.findViewById(R.id.tv_tel);
        tvCv = view.findViewById(R.id.tv_cv);

        //Récupération des boutons:
        buttonModifier = view.findViewById(R.id.button_modifier);
        buttonSupprimer = view.findViewById(R.id.button_supprimer);

        buttonModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO accés à la modification
            }
        });

        buttonSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO pop-up pour la confirmation de suppression
            }
        });


        return view;
    }

    public void updateUserView(int userId) {
        //Récupération de la base de données et mise à jour de l'user:
        UserDataSource userDataSource = new UserDataSource(getContext());
        UserDAO userDAO = new UserDAO(userDataSource);
        User user = userDAO.read(userId);

        tvNomPrenom.setText(user.getNom() + " " + user.getPrenom());
        tvSexe.setText(user.getSexe());
        tvMetier.setText(user.getMetier());
        tvService.setText(user.getService());
        tvEmail.setText(user.getMail());
        tvTel.setText(user.getTel());
        tvCv.setText(user.getCV());
    }
}
