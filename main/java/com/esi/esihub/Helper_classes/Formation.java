package com.esi.esihub.Helper_classes;

public class Formation {
    private String Nom, Domaine, Etablissement, Certificat, DateFin, DateDebut;

    public Formation(String nom, String domaine, String etablissement, String certificat, String dateFin, String dateDebut) {
        Nom = nom;
        Domaine = domaine;
        Etablissement = etablissement;
        Certificat = certificat;
        DateFin = dateFin;
        DateDebut = dateDebut;
    }

    public Formation() {
    }

    public String getDateDebut() {
        return DateDebut;
    }

    public void setDateDebut(String dateDebut) {
        DateDebut = dateDebut;
    }

    public String getDateFin() {
        return DateFin;
    }

    public void setDateFin(String date) {
        DateFin  = date;
    }

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

    public String getEtablissement() {
        return Etablissement;
    }

    public void setEtablissement(String etablissement) {
        Etablissement = etablissement;
    }

    public String getCertificat() {
        return Certificat;
    }

    public void setCertificat(String certificat) {
        Certificat = certificat;
    }
}
