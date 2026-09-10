package com.nexabank.model;

public abstract class Personne {
    protected String nom;
    protected String prenom;
    protected String email;
    protected String password;
    static public int count = 1;


    protected Personne(String nom, String prenom, String email, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = motDePasse;
        count++;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return password;
    }

    public void setMotDePasse(String password) {
        this.password = password;
    }

    public String getNomComplet() {
        return prenom + " " + nom;
    }
}
