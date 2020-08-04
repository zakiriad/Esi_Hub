package com.esi.esihub.Helper_classes;

public class Formation {
    private String Nom, Domaine, Etablissement, Certificat, DateFin;

    public Formation(String nom, String domaine, String etablissement, String certificat, String date) {
        Nom = nom;
        Domaine = domaine;
        Etablissement = etablissement;
        Certificat = certificat;
        DateFin = date;
    }

    public String getDate() {
        return DateFin;
    }

    public void setDate(String date) {
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
