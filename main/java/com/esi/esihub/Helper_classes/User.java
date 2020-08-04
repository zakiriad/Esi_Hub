package com.esi.esihub.Helper_classes;

import android.widget.LinearLayout;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String Nom, Prenom, Date_de_naissance, Lieu_de_naissance,email, other_email, telephone, wilaya;
    private String Promotion, lien_Memoire, lien_Projet;
    private int Niveau, Numero_Projet;
    private Boolean confirmer_Dossier;


    public User(String nom, String prenom, String date_de_naissance, String lieu_de_naissance, String email, String other_email, String telephone, String wilaya) {
        Nom = nom;
        Prenom = prenom;
        Date_de_naissance = date_de_naissance;
        Lieu_de_naissance = lieu_de_naissance;
        this.email = email;
        this.other_email = other_email;
        this.telephone = telephone;
        this.wilaya = wilaya;
    }

    public User(String nom, String prenom, String email, int niveau, Boolean confirmer_Dossier) {
        Nom = nom;
        Prenom = prenom;
        this.email = email;
        Niveau = niveau;
        this.confirmer_Dossier = confirmer_Dossier;
        Promotion = "";
        lien_Memoire = "";
        lien_Projet = "";
        Date_de_naissance = "";
        Numero_Projet = 0;
        other_email = "";
        telephone = "";
        wilaya = "";
        Lieu_de_naissance = "";
    }
    public User(){

    }
    /*@Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("Nom",Nom);
        result.put("Prenom",Prenom);
        result.put("email", email);
        result.put("other_email", other_email);
        result.put("telephone", telephone);
        result.put("wilaya",wilaya);
        result.put("Lieu_de_naissance", Lieu_de_naissance);
        result.put("Date_de_naissance", Date_de_naissance);

        return result;
    }*/
    public String getLieu_de_naissance() {
        return Lieu_de_naissance;
    }

    public void setLieu_de_naissance(String lieu_de_naissance) {
        Lieu_de_naissance = lieu_de_naissance;
    }

    public String getWilaya() {
        return wilaya;
    }

    public void setWilaya(String wilaya) {
        this.wilaya = wilaya;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getOther_email() {
        return other_email;
    }

    public void setOther_email(String other_email) {
        this.other_email = other_email;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getDate_de_naissance() {
        return Date_de_naissance;
    }

    public void setDate_de_naissance(String date_de_naissance) {
        Date_de_naissance = date_de_naissance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPromotion() {
        return Promotion;
    }

    public void setPromotion(String promotion) {
        Promotion = promotion;
    }

    public String getLien_Memoire() {
        return lien_Memoire;
    }

    public void setLien_Memoire(String lien_Memoire) {
        this.lien_Memoire = lien_Memoire;
    }

    public String getLien_Projet() {
        return lien_Projet;
    }

    public void setLien_Projet(String lien_Projet) {
        this.lien_Projet = lien_Projet;
    }

    public int getNiveau() {
        return Niveau;
    }

    public void setNiveau(int niveau) {
        Niveau = niveau;
    }

    public int getNumero_Projet() {
        return Numero_Projet;
    }

    public void setNumero_Projet(int numero_Projet) {
        Numero_Projet = numero_Projet;
    }

    public Boolean getConfirmer_Dossier() {
        return confirmer_Dossier;
    }

    public void setConfirmer_Dossier(Boolean confirmer_Dossier) {
        this.confirmer_Dossier = confirmer_Dossier;
    }
}
