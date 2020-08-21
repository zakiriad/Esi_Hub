package com.esi.esihub.Helper_classes;

public class Offre_formation {
    private String nom, domaine, datelimiteinscrp, datedebut, etablissement, lien_image;

    public Offre_formation() { }

    public String getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(String Etablissement) {
        etablissement = Etablissement;
    }

    public String getLien_Image() {
        return lien_image;
    }

    public void setLien_Image(String lien_Image) {
        this.lien_image = lien_Image;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String Nom) {
        nom = Nom;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String Domaine) {
        domaine = Domaine;
    }

    public String getDateLimiteInscrp() {
        return datelimiteinscrp;
    }

    public void setDateLimiteInscrp(String dateLimiteInscrp) {
        datelimiteinscrp = dateLimiteInscrp;
    }

    public String getDateDebut() {
        return datedebut;
    }

    public void setDateDebut(String dateDebut) {
        datedebut = dateDebut;
    }
}
