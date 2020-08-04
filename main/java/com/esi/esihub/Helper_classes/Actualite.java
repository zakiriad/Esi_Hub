package com.esi.esihub.Helper_classes;

public class Actualite {
    private String Titre, sousTitre, lien;

    public Actualite() {}

    public String getTitre() {
        return Titre;
    }

    public void setTitre(String titre) {
        Titre = titre;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getSousTitre() {
        return sousTitre;
    }

    public void setSousTitre(String sousTitre) {
        this.sousTitre = sousTitre;
    }
}
