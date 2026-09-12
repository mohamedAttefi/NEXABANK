// File: src/com/nexabank/service/ClientService.java
package com.nexabank.service;

import com.nexabank.model.Client;
import com.nexabank.model.Personne;

import java.util.HashMap;
import java.util.ArrayList;

public class ClientService {
    private ArrayList<Personne> clientsDb = LoginService.users;

    public void afficherClients() {
        for (int i = 0; i < clientsDb.size(); i++) {
            if (clientsDb.get(i) instanceof Client) {
                Client client = (Client) clientsDb.get(i);
                System.out.println(
                        "ID: " + client.getIdClient()
                                + " | Nom: " + client.getNom()
                                + " " + client.getPrenom()
                                + " | Email: " + client.getEmail()
                );
            }
        }
    }

    public Client trouverParId(int idClient) {
        return (Client) clientsDb.get(idClient-1);
    }

    public void mettreAJourInfos(int idClient, String nouveauNom, String nouveauPrenom, String nouveauEmail) {
        Client client = (Client) clientsDb.get(idClient);
        if (client != null) {
            client.setNom(nouveauNom);
            client.setPrenom(nouveauPrenom);
            client.setEmail(nouveauEmail);
        }
    }
}