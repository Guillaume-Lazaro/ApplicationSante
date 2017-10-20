package fr.codevallee.formation.applicationsante;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillaumelazaro on 16/10/2017.
 */

public class UserDAO {

    private UserDataSource lib;

    private static final String COL_ID = "id";
    private static final String COL_NOM = "nom";
    private static final String COL_PRENOM = "prenom";
    private static final String COL_SEXE= "sexe";
    private static final String COL_METIER = "metier";
    private static final String COL_SERVICE = "service";
    private static final String COL_MAIL = "mail";
    private static final String COL_TEL = "tel";
    private static final String COL_CV = "cv";
    private static final String TABLE_NAME = "users";

    public UserDAO(UserDataSource userDataSource) {
        this.lib = userDataSource;
    }

    public User create(User user) {
        ContentValues values = new ContentValues();
        values.put(COL_NOM, user.getNom());
        values.put(COL_PRENOM, user.getPrenom());
        values.put(COL_SEXE, user.getSexe());
        values.put(COL_METIER, user.getMetier());
        values.put(COL_SERVICE, user.getService());
        values.put(COL_MAIL, user.getMail());
        values.put(COL_TEL, user.getTel());
        values.put(COL_CV, user.getCV());

        //Requete insert
        int id = (int) lib.getDB().insert(TABLE_NAME, null, values);
        user.setId(id);

        return user;
    }

    public User update(User user) {
        ContentValues values = new ContentValues();
        values.put(COL_NOM, user.getNom());
        values.put(COL_PRENOM, user.getPrenom());
        values.put(COL_SEXE, user.getSexe());
        values.put(COL_METIER, user.getMetier());
        values.put(COL_SERVICE, user.getService());
        values.put(COL_MAIL, user.getMail());
        values.put(COL_TEL, user.getTel());
        values.put(COL_CV, user.getCV());

        //Création de la clause where
        String clause = COL_ID + " = ?";
        String[] clauseArgs = new String[]{String.valueOf(user.getId())};

        //On update la table
        lib.getDB().update(TABLE_NAME, values, clause, clauseArgs);

        return user;
    }

    public void delete(User user) {
        //Création de la clause where
        String clause = COL_ID + " = ?";
        String[] clauseArgs = new String[]{String.valueOf(user.getId())};

        //On update la table
        lib.getDB().delete(TABLE_NAME, clause, clauseArgs);
    }

    public void delete(int id) {
        //Création de la clause where
        String clause = COL_ID + " = ?";
        String[] clauseArgs = new String[]{String.valueOf(id)};

        //On update la table
        lib.getDB().delete(TABLE_NAME, clause, clauseArgs);
    }

    public User read(int userId) {
        //Définition des colonnes utilisés:
        String[] allColumns = new String[]{COL_ID, COL_NOM, COL_PRENOM, COL_SEXE, COL_METIER, COL_SERVICE,COL_MAIL,COL_TEL,COL_CV};

        //Création de la clause where pour la requéte SELECT
        String clause = COL_ID + " = ?";
        String[] clauseArgs = new String[]{String.valueOf(userId)};

        Cursor cursor = lib.getDB().query(TABLE_NAME, allColumns, clause, clauseArgs, null, null, null);

        //On lit le cursor récupéré:
        cursor.moveToFirst();

        User user = new User(cursor.getInt(0), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)
                , cursor.getString(6), cursor.getString(7), cursor.getString(8));
        cursor.close();

        return user;
    }

    public List<User> readAll() {
        //Définition des colonnes utilisés:
        String[] allColumns = new String[]{COL_ID, COL_NOM, COL_PRENOM, COL_SEXE, COL_METIER, COL_SERVICE,COL_MAIL,COL_TEL,COL_CV};

        //Création de la clause where pour la requéte SELECT
        Cursor cursor = lib.getDB().query(TABLE_NAME, allColumns, null, null, null, null, null);

        // iterate on cursor and retreive result
        List<User> users = new ArrayList<User>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            users.add(new User(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)
                    , cursor.getString(6), cursor.getString(7), cursor.getString(8)));
            cursor.moveToNext();
        }
        cursor.close();
        return users;
    }
}
