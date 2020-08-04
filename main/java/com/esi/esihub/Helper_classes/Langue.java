package com.esi.esihub.Helper_classes;

public class Langue {
    private String Nom, Niveau, Nom_Certificat, lien_Certificat;

    public Langue(String nom, String niveau, String nom_Certificat, String lien_Certificat) {
        Nom = nom;
        Niveau = niveau;
        Nom_Certificat = nom_Certificat;
        this.lien_Certificat = lien_Certificat;
    }


    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getNiveau() {
        return Niveau;
    }

    public void setNiveau(String niveau) {
        Niveau = niveau;
    }

    public String getNom_Certificat() {
        return Nom_Certificat;
    }

    public void setNom_Certificat(String nom_Certificat) {
        Nom_Certificat = nom_Certificat;
    }

    public String getLien_Certificat() {
        return lien_Certificat;
    }

    public void setLien_Certificat(String lien_Certificat) {
        this.lien_Certificat = lien_Certificat;
    }
}
