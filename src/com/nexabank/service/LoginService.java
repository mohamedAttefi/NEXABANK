package com.nexabank.service;

import com.nexabank.model.Personne;

import java.util.ArrayList;

public class LoginService {

    public static ArrayList<Personne> users = new ArrayList<>();

    public LoginService() {
    }

    public void ajouterUtilisateur(Personne personne) {
        users.add(personne);
    }

    public Personne authentifier(String email, String password) {

        for (int i = 0; i < users.size(); i++) {

            Personne c = users.get(i);

            if (c.getEmail().equals(email)
                    && c.getMotDePasse().equals(password)) {

                return c;
            }
        }

        return null;
    }
}