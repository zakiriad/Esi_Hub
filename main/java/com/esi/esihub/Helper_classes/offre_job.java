package com.esi.esihub.Helper_classes;

public class offre_job {
    private String Nom, Societe,Email;
    private int Experience;

    public offre_job() { }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getSociete() {
        return Societe;
    }

    public void setSociete(String societe) {
        Societe = societe;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getExperience() {
        return Experience;
    }

    public void setExperience(int experience) {
        Experience = experience;
    }
}
