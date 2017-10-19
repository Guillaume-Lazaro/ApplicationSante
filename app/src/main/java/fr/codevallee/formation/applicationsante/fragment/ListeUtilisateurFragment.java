package fr.codevallee.formation.applicationsante.fragment;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import fr.codevallee.formation.applicationsante.MainActivity;
import fr.codevallee.formation.applicationsante.R;
import fr.codevallee.formation.applicationsante.User;
import fr.codevallee.formation.applicationsante.UserAdapter;

import static fr.codevallee.formation.applicationsante.R.id.toolbar;

public class ListeUtilisateurFragment extends ListFragment {

    OnHeadlineSelectedListener mCallback;

    public interface OnHeadlineSelectedListener {
        public void onUserSelected(int position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        //Remplissage:
        User user1 = new User("Jean","Patrick",26,"Menuisier");
        User user2 = new User("Lazaro","Guillaume",24,"Chomeur");

        final ArrayList<User> listUser = new ArrayList<>();
        listUser.add(user1);
        listUser.add(user2);

        ArrayList<String> listName = new ArrayList<>();
        for (int i=0 ; i<listUser.size() ; i++) {
            listName.add(i, listUser.get(i).getNom()+" "+listUser.get(i).getPrenom());
        }

        UserAdapter userAdapter = new UserAdapter(getContext(), listUser);

        setListAdapter(userAdapter);*/
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

        /*if(getFragmentManager().findFragmentById(R.id.fragment_user) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_liste_utilisateur, container, false);


        /* deprecated
        //Toolbar:
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        //Action Bar:
        DrawerLayout drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(((AppCompatActivity)getActivity())); //Chelou
        */

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Send the event to the host activity
        mCallback.onUserSelected(position);
    }
}
