package com.nexabank.model;

public class Gestionnaire extends Personne {

    private int idGestionnaire;

    public Gestionnaire(int idGestionnaire, String nom, String prenom, String email, String motDePasse) {

        super(nom, prenom, email, motDePasse);

        this.idGestionnaire = idGestionnaire;
    }

    public int getIdGestionnaire() {
        return idGestionnaire;
    }
}
