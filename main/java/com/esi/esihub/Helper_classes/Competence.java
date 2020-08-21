package com.esi.esihub.Helper_classes;

public class Competence {
    private String Nom, Niveau;

    public Competence(String nom, String niveau) {
        Nom = nom;
        Niveau = niveau;
    }

    public Competence() {
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
}
