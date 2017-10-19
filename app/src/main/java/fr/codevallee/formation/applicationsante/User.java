package fr.codevallee.formation.applicationsante;

/**
 * Created by guillaumelazaro on 16/10/2017.
 */

public class User {
    private int id;
    private String nom;
    private String prenom;
    private boolean isMale; //Sexe
    private String metier;
    private String spinner;
    private String mail;
    private String tel;
    private String CV;

    public User(int id, String nom, String prenom, boolean isMale, String metier, String spinner, String mail, String tel, String CV) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.isMale = isMale;
        this.metier = metier;
        this.spinner = spinner;
        this.mail = mail;
        this.tel = tel;
        this.CV = CV;
    }

    public User(String nom, String prenom, boolean isMale, String metier, String spinner, String mail, String tel, String CV) {
        this.nom = nom;
        this.prenom = prenom;
        this.isMale = isMale;
        this.metier = metier;
        this.spinner = spinner;
        this.mail = mail;
        this.tel = tel;
        this.CV = CV;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public String getMetier() {
        return metier;
    }

    public void setMetier(String metier) {
        this.metier = metier;
    }

    public String getSpinner() {
        return spinner;
    }

    public void setSpinner(String spinner) {
        this.spinner = spinner;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCV() {
        return CV;
    }

    public void setCV(String CV) {
        this.CV = CV;
    }
}
