package fr.codevallee.formation.applicationsante.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.codevallee.formation.applicationsante.R;

public class UtilisateurFragment extends Fragment {

    public static UtilisateurFragment newInstance() {
        UtilisateurFragment fragment = new UtilisateurFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_utilisateur, container, false);
    }

    public void updateUserView(int position) {
        TextView article = (TextView) getActivity().findViewById(R.id.et_nom);
        article.setText("Truc"+position);
        //mCurrentPosition = position;
    }
}
