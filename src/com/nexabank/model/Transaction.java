package com.nexabank.model;

import java.time.LocalDateTime;

public class Transaction {

    private int idTransaction;
    private double montant;
    private LocalDateTime date;
    private TypeTransaction typeTransaction;
    private String compteSource;
    private String compteDestination;
    public static int count = 1;

    public Transaction(int idTransaction, double montant, LocalDateTime date, String compteSource, String compteDestination, TypeTransaction typeTransaction) {

        this.idTransaction = idTransaction;
        this.montant = montant;
        this.date = date;
        this.typeTransaction = typeTransaction;
        this.compteSource = compteSource;
        this.compteDestination = compteDestination;
        count++;
    }

    public int getIdTransaction() {
        return idTransaction;
    }

    public TypeTransaction getTypeTransaction() {
        return typeTransaction;
    }

    public double getMontant() {
        return montant;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getCompteSource() {
        return compteSource;
    }

    public String getCompteDestination() {
        return compteDestination;
    }
}
