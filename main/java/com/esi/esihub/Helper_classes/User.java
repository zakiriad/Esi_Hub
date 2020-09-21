package com.esi.esihub.Helper_classes;



public class User {
    private String Nom, Prenom, Date_de_naissance, Lieu_de_naissance,email, other_email, telephone, wilaya, genre;
    private String Promotion, lien_Memoire, lien_Projet, lien_photo_profil,lien_carte_etudiant, specialite;
    private int niveau, numero_Projet, numero_carte_etudiant;
    private Boolean confirmer_Dossier, Alumni;


    public User(String nom, String prenom, String date_de_naissance, String lieu_de_naissance, String email, String other_email, String telephone, String wilaya) {
        Nom = nom;
        Prenom = prenom;
        Date_de_naissance = date_de_naissance;
        Lieu_de_naissance = lieu_de_naissance;
        this.email = email;
        this.other_email = other_email;
        this.telephone = telephone;
        this.wilaya = wilaya;
    }

    public User(String nom, String prenom, String email, int Niveau, String genre, Boolean confirmer_Dossier) {
        Nom = nom;
        Prenom = prenom;
        this.email = email;
        niveau = Niveau;
        this.confirmer_Dossier = confirmer_Dossier;
        this.genre = genre;
    }


    public Boolean getAlumni() {
        return Alumni;
    }

    public void setAlumni(Boolean alumni) {
        Alumni = alumni;
    }

    public User(){

    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getNumero_Projet() {
        return numero_Projet;
    }

    public void setNumero_Projet(int numero_Projet) {
        this.numero_Projet = numero_Projet;
    }

    public int getNumero_carte_etudiant() {
        return numero_carte_etudiant;
    }

    public void setNumero_carte_etudiant(int numero_carte_etudiant) {
        this.numero_carte_etudiant = numero_carte_etudiant;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public int getnumero_carte_etudiant() {
        return numero_carte_etudiant;
    }

    public void setnumero_carte_etudiant(int Numero_carte_etudiant) {
        numero_carte_etudiant = Numero_carte_etudiant;
    }

    public String getLien_carte_etudiant() {
        return lien_carte_etudiant;
    }

    public void setLien_carte_etudiant(String lien_carte_etudiant) {
        this.lien_carte_etudiant = lien_carte_etudiant;
    }

    public String getLien_photo_profil() {
        return lien_photo_profil;
    }

    public void setLien_photo_profil(String lien_photo_profil) {
        this.lien_photo_profil = lien_photo_profil;
    }
    public String getLieu_de_naissance() {
        return Lieu_de_naissance;
    }

    public void setLieu_de_naissance(String lieu_de_naissance) {
        Lieu_de_naissance = lieu_de_naissance;
    }

    public String getWilaya() {
        return wilaya;
    }

    public void setWilaya(String wilaya) {
        this.wilaya = wilaya;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getOther_email() {
        return other_email;
    }

    public void setOther_email(String other_email) {
        this.other_email = other_email;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getDate_de_naissance() {
        return Date_de_naissance;
    }

    public void setDate_de_naissance(String date_de_naissance) {
        Date_de_naissance = date_de_naissance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPromotion() {
        return Promotion;
    }

    public void setPromotion(String promotion) {
        Promotion = promotion;
    }

    public String getLien_Memoire() {
        return lien_Memoire;
    }

    public void setLien_Memoire(String lien_Memoire) {
        this.lien_Memoire = lien_Memoire;
    }

    public String getLien_Projet() {
        return lien_Projet;
    }

    public void setLien_Projet(String lien_Projet) {
        this.lien_Projet = lien_Projet;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int Niveau) {
        niveau = Niveau;
    }

    public int getnumero_Projet() {
        return numero_Projet;
    }

    public void setnumero_Projet(int numero_Projet) {
        numero_Projet = numero_Projet;
    }

    public Boolean getConfirmer_Dossier() {
        return confirmer_Dossier;
    }

    public void setConfirmer_Dossier(Boolean confirmer_Dossier) {
        this.confirmer_Dossier = confirmer_Dossier;
    }




}
