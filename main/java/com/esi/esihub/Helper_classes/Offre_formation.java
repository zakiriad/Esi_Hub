package com.esi.esihub.Helper_classes;

public class Offre_formation {
    private String Nom, Domaine, DateLimiteInscrp, DateDebut;

    public Offre_formation() { }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getDomaine() {
        return Domaine;
    }

    public void setDomaine(String domaine) {
        Domaine = domaine;
    }

    public String getDateLimiteInscrp() {
        return DateLimiteInscrp;
    }

    public void setDateLimiteInscrp(String dateLimiteInscrp) {
        DateLimiteInscrp = dateLimiteInscrp;
    }

    public String getDateDebut() {
        return DateDebut;
    }

    public void setDateDebut(String dateDebut) {
        DateDebut = dateDebut;
    }
}
