package fr.codevallee.formation.applicationsante.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import fr.codevallee.formation.applicationsante.AddUserActivity;
import fr.codevallee.formation.applicationsante.ModifyUserActivity;
import fr.codevallee.formation.applicationsante.R;
import fr.codevallee.formation.applicationsante.User;
import fr.codevallee.formation.applicationsante.UserDAO;
import fr.codevallee.formation.applicationsante.UserDataSource;

public class UtilisateurFragment extends Fragment  {

    private TextView tvNomPrenom;
    private TextView tvSexe;
    private TextView tvMetier;
    private TextView tvService;
    private TextView tvEmail;
    private TextView tvTel;
    private TextView tvCv;
    private TextView tvNoUser;

    private Button buttonModifier;
    private Button buttonSupprimer;

    private int currentPosition=0;
    private User userSelected;

    //Communication avec la liste des utilisateurs:
    UserDeleted mCallback;

    public interface UserDeleted {
        public void refresh();
    }

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
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Pour la communication avec la liste
        try {
            mCallback = (UserDeleted) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()+"n'implémente pas l'interface UserDeleted");
        }
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

        //TextView pour l'affichage en cas d'absence d'utilisateur
        tvNoUser = view.findViewById(R.id.tv_no_user);

        //Récupération des boutons:
        buttonModifier = view.findViewById(R.id.button_modifier);
        buttonSupprimer = view.findViewById(R.id.button_supprimer);

        buttonModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ModifyUserActivity.class);
                intent.putExtra("user_id",userSelected.getId());
                startActivity(intent);
            }
        });

        buttonSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage(R.string.verification_suppression);
                alertDialogBuilder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        UserDataSource userDataSource = new UserDataSource(getContext());
                        UserDAO userDAO = new UserDAO(userDataSource);
                        userDAO.delete(userSelected.getId());

                        //Communication avec la liste:
                        mCallback.refresh();
                    }
                });

                alertDialogBuilder.setNegativeButton(R.string.non,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Pour le moment, on ne fait rien ici
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        return view;
    }

    public void updateUserView(int userId) {
        //Récupération de la base de données et mise à jour de l'user:
        UserDataSource userDataSource = new UserDataSource(getContext());
        UserDAO userDAO = new UserDAO(userDataSource);
        userSelected = userDAO.read(userId);

        setViewVisible(true);
        tvNomPrenom.setText(userSelected.getNom() + " " + userSelected.getPrenom());
        tvSexe.setText(userSelected.getSexe());
        tvMetier.setText(userSelected.getMetier());
        tvService.setText(userSelected.getService());
        tvEmail.setText(userSelected.getMail());
        tvTel.setText(userSelected.getTel());
        tvCv.setText(userSelected.getCV());
    }

    public void showNoUser() {
        setViewVisible(false);
    }

    public void setViewVisible (boolean visible) {
        if (visible) {
            tvNomPrenom.setVisibility(View.VISIBLE);
            tvSexe.setVisibility(View.VISIBLE);
            tvMetier.setVisibility(View.VISIBLE);
            tvService.setVisibility(View.VISIBLE);
            tvEmail.setVisibility(View.VISIBLE);
            tvTel.setVisibility(View.VISIBLE);
            tvCv.setVisibility(View.VISIBLE);

            buttonModifier.setVisibility(View.VISIBLE);
            buttonSupprimer.setVisibility(View.VISIBLE);

            tvNoUser.setVisibility(View.INVISIBLE);
        } else {
            tvNomPrenom.setVisibility(View.INVISIBLE);
            tvSexe.setVisibility(View.INVISIBLE);
            tvMetier.setVisibility(View.INVISIBLE);
            tvService.setVisibility(View.INVISIBLE);
            tvEmail.setVisibility(View.INVISIBLE);
            tvTel.setVisibility(View.INVISIBLE);
            tvCv.setVisibility(View.INVISIBLE);

            buttonModifier.setVisibility(View.INVISIBLE);
            buttonSupprimer.setVisibility(View.INVISIBLE);

            tvNoUser.setVisibility(View.VISIBLE);
        }
    }
}
