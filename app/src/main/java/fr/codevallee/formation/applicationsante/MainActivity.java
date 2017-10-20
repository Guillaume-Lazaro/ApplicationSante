package fr.codevallee.formation.applicationsante;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import fr.codevallee.formation.applicationsante.fragment.ListeUtilisateurFragment;
import fr.codevallee.formation.applicationsante.fragment.UtilisateurFragment;

public class MainActivity extends AppCompatActivity
        implements ListeUtilisateurFragment.OnHeadlineSelectedListener,
        NavigationView.OnNavigationItemSelectedListener,
        UtilisateurFragment.UserDeleted{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Gestion des fragments
        if (findViewById(R.id.fragment_container) != null) { //Smartphone:
            if (savedInstanceState != null) {
                return;
            }

            ListeUtilisateurFragment firstFragment = new ListeUtilisateurFragment();
            firstFragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
        } else {    //Tablette:
            //On récupére le fragment d'affichage de l'utilisateur
            UtilisateurFragment userFragment = (UtilisateurFragment) getFragmentManager().findFragmentById(R.id.fragment_user);

            //Récupération de la liste des utilisateurs et affichage du premier sur le fragment
            UserDataSource userDataSource = new UserDataSource(this);
            UserDAO userDAO = new UserDAO(userDataSource);
            ArrayList<User>listUser = (ArrayList<User>) userDAO.readAll();

            if(listUser.size()>0) {
                userFragment.updateUserView(listUser.get(0).getId());
            } else {
                noUserSelected();
            }
        }

        //ça c'est la barre tout en haut
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //ça c'est le tiroir qui s'ouvre à gauche
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);

        //ça c'est le bouton burger:
        //Toolbar toolbar = ((ListeUtilisateurFragment) getFragmentManager().findFragmentById(R.id.fragment_list_user)).getToolbar();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //ça c'est juste le header du drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Ici c'est le menu en haut à droite ...
        int id = item.getItemId();

        // ... vide et inutile pour le moment
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_options) {
            Intent intent = new Intent(this, PreferencesActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_aide) {
            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_ajouter_user) {
            Intent intent = new Intent(this, AddUserActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onUserSelected(int userId) {
        UtilisateurFragment userFragment = (UtilisateurFragment) getFragmentManager().findFragmentById(R.id.fragment_user);

        if (userFragment != null) {
            //Tablette:
            userFragment.updateUserView(userId);
        } else {
            //Téléphone:
            UtilisateurFragment newUserFragment = new UtilisateurFragment();
            Bundle args = new Bundle();
            args.putInt("position", userId);
            newUserFragment.setArguments(args);

            android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, newUserFragment);
            transaction.addToBackStack(null);

            transaction.commit();
        }
    }

    @Override
    public void noUserSelected() {
        UtilisateurFragment userFragment = (UtilisateurFragment) getFragmentManager().findFragmentById(R.id.fragment_user);

        if (userFragment != null) {
            //Tablette:
            userFragment.showNoUser();
        }
    }

    @Override
    public void refresh() {
        ListeUtilisateurFragment listeUtilisateurFragment = (ListeUtilisateurFragment) getFragmentManager().findFragmentById(R.id.fragment_list_user);

        if (listeUtilisateurFragment != null) {
            //Tablette:
            listeUtilisateurFragment.refresh();
        } else {
            //Téléphone:
            listeUtilisateurFragment = new ListeUtilisateurFragment();

            android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, listeUtilisateurFragment);
            transaction.addToBackStack(null);

            transaction.commit();
        }
    }
}
