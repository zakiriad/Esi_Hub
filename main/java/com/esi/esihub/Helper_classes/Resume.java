package com.esi.esihub.Helper_classes;


public class Resume extends User {
    private String Apropos;

    public Resume(String nom, String prenom, String email, int niveau, Boolean confirmer_Dossier, String apropos, String genre) {
        super(nom, prenom, email, niveau,genre, confirmer_Dossier);
        Apropos = apropos;
    }
    public Resume(){

    }

    public String getApropos() {
        return Apropos;
    }

    public void setApropos(String apropos) {
        Apropos = apropos;
    }
}