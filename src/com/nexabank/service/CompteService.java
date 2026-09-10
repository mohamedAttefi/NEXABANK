// File: src/com/nexabank/service/CompteService.java
package com.nexabank.service;

import com.nexabank.exeption.CompteInexistantException;
import com.nexabank.model.Client;
import com.nexabank.model.Compte;
import com.nexabank.model.TypeCompte;

public class CompteService {

    public Compte creerCompte(Client client, String numeroCompte, double soldeInitial, TypeCompte type) {
        Compte nouveauCompte = new Compte(numeroCompte, soldeInitial, type);
        client.getComptes().put(numeroCompte, nouveauCompte);
        return nouveauCompte;
    }

    public void cloturerCompte(Client client, String numeroCompte) throws CompteInexistantException {
        if (!client.getComptes().containsKey(numeroCompte)) {
            throw new CompteInexistantException("Le compte " + numeroCompte + " n'existe pas pour ce client.");
        }
        client.getComptes().remove(numeroCompte);
    }
}