package com.esi.esihub.Helper_classes;

public class Verification_Dossier {
    private Boolean id, Documents, Livres;

    public Verification_Dossier(Boolean id, Boolean documents, Boolean livres) {
        this.id = id;
        Documents = documents;
        Livres = livres;
    }

    public Verification_Dossier() {
    }

    public Boolean getId() {
        return id;
    }

    public void setId(Boolean id) {
        this.id = id;
    }

    public Boolean getDocuments() {
        return Documents;
    }

    public void setDocuments(Boolean documents) {
        Documents = documents;
    }

    public Boolean getLivres() {
        return Livres;
    }

    public void setLivres(Boolean livres) {
        Livres = livres;
    }
}
