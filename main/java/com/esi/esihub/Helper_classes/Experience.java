package com.esi.esihub.Helper_classes;

public class Experience {
    private String Nom, Etablissment, dateDebut, dateFin;

    public Experience(String nom, String etablissment, String dateDebut, String dateFin) {
        Nom = nom;
        Etablissment = etablissment;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getEtablissment() {
        return Etablissment;
    }

    public void setEtablissment(String etablissment) {
        Etablissment = etablissment;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }
}
