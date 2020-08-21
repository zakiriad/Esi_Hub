package com.esi.esihub.Helper_classes;

public class offre_job {
    private String nom, societe,email, lien_logo;
    private int experience;

    public offre_job() { }

    public String getLien_logo() {
        return lien_logo;
    }

    public void setLien_logo(String lien_logo) {
        this.lien_logo = lien_logo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String Nom) {
        nom = Nom;
    }

    public String getSociete() {
        return societe;
    }

    public void setSociete(String Societe) {
        societe = Societe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String Email) {
        email = Email;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int Experience) {
        experience = Experience;
    }
}
