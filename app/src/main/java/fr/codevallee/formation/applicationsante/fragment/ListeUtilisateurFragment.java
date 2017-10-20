package fr.codevallee.formation.applicationsante.fragment;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import fr.codevallee.formation.applicationsante.AddUserActivity;
import fr.codevallee.formation.applicationsante.R;
import fr.codevallee.formation.applicationsante.User;
import fr.codevallee.formation.applicationsante.UserAdapter;
import fr.codevallee.formation.applicationsante.UserDAO;
import fr.codevallee.formation.applicationsante.UserDataSource;

public class ListeUtilisateurFragment extends ListFragment implements NavigationView.OnNavigationItemSelectedListener,UtilisateurFragment.UserDeleted {

    OnHeadlineSelectedListener mCallback;
    private ArrayList<User> listUser;

    //Test:
    private Toolbar toolbar;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    public interface OnHeadlineSelectedListener {
        public void onUserSelected(int userId);
        public void noUserSelected();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        refresh();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        //Si on est en mode tablette, on mette la liste en mode choix unique
        if(getFragmentManager().findFragmentById(R.id.fragment_user) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_user, container, false);

        //Assignation de l'action du bouton d'ajout
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddUserActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void refresh() {
        //On rafraichit la liste
        UserDataSource userDataSource = new UserDataSource(getContext());
        UserDAO userDAO = new UserDAO(userDataSource);

        listUser = (ArrayList<User>) userDAO.readAll();
        UserAdapter userAdapter = new UserAdapter(getContext(), listUser);
        setListAdapter(userAdapter);

        //et on séléctionne le premier de la liste pour l'affichage:
        UtilisateurFragment userFragment = (UtilisateurFragment) getFragmentManager().findFragmentById(R.id.fragment_user);

        if (userFragment != null) {
            if(listUser.size()>0) {                                 //Si il reste des choses dans la liste
                mCallback.onUserSelected(listUser.get(0).getId());  //on envoie le premier user à l'affichage
            } else {
                mCallback.noUserSelected();                         //sinon on signale qu'il n'en reste plus
            }
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        User userSelected = listUser.get(position);     //Récupération de l'user sélectionné
        mCallback.onUserSelected(userSelected.getId()); //Envoi de l'id de l'user sélectionné au fragment d'affichage
    }
}