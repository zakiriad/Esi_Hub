package com.esi.esihub.Helper_classes;

public class Actualite {
    private String titre, soustitre, lien, lien_photo;

    public Actualite() {}

    public String getLien_photo() {
        return lien_photo;
    }

    public void setLien_photo(String lien_photo) {
        this.lien_photo = lien_photo;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String Titre) {
        titre = Titre;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getSousTitre() {
        return soustitre;
    }

    public void setSousTitre(String sousTitre) {
        this.soustitre = soustitre;
    }
}
