package fr.codevallee.formation.applicationsante;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by guillaumelazaro on 16/10/2017.
 */

public class UserAdapter extends ArrayAdapter<User> {

    public UserAdapter(Context context, List<User> users) {
        super(context, 0, users);
    }

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_user,parent, false);
        }

        UserViewHolder viewHolder = (UserViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new UserViewHolder();
            viewHolder.nom_prenom = (TextView) convertView.findViewById(R.id.tv_nom_prenom);
            viewHolder.age = (TextView) convertView.findViewById(R.id.tv_age);
            viewHolder.metier = (TextView) convertView.findViewById(R.id.tv_metier);;
            convertView.setTag(viewHolder);
        }

        //Récupération de l'user :
        User user = getItem(position);

        //Remplissage des views:
        viewHolder.nom_prenom.setText(user.getNom()+" "+user.getPrenom());
        viewHolder.age.setText(user.getAge()+" ans");
        viewHolder.metier.setText(user.getMetier());

        return convertView;
    }*/

    private class UserViewHolder{
        public TextView nom_prenom;
        public TextView age;
        public TextView metier;
    }

}
