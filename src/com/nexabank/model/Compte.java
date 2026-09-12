package com.nexabank.model;

import java.util.HashSet;

public class Compte {
    public  static int cpt = 1000;
    private String numeroCompte;
    private double solde;
    private TypeCompte typeCompte;

    private HashSet<Transaction> historiqueTransactions;

    public Compte(double solde, TypeCompte typeCompte) {

        this.numeroCompte = "C"+(++Compte.cpt);
        this.solde = solde;
        this.typeCompte = typeCompte;

        this.historiqueTransactions = new HashSet<>();
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public double getSolde() {
        return solde;
    }

    public TypeCompte getTypeCompte() {
        return typeCompte;
    }

    public HashSet<Transaction> getHistoriqueTransactions() {
        return historiqueTransactions;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }
}