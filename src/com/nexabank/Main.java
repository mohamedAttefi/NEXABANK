// File: src/com/nexabank/Main.java
package com.nexabank;

import com.nexabank.exeption.*;
import com.nexabank.model.*;
import com.nexabank.service.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Client client1 = new Client(Personne.count, "Attefi", "Mohamed", "mohamed@test.com", "1234");

        Gestionnaire g1 = new Gestionnaire(Personne.count, "Admin", "Banque", "admin@nexabank.com", "admin");

        CompteService compteService = new CompteService();
        TransactionService transactionService = new TransactionService();
        ClientService clientService = new ClientService();
        LoginService loginService = new LoginService();
        loginService.ajouterUtilisateur(client1);
        loginService.ajouterUtilisateur(g1);

        compteService.creerCompte(client1, 1000.0, TypeCompte.COURANT);
        compteService.creerCompte(client1, 500.0, TypeCompte.EPARGNE);

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== BIENVENUE SUR NEXABANK ===");

        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Register");
            System.out.println("3. Quitter");
            System.out.print("Choix : ");
            int choix = scanner.nextInt();

            if (choix == 3) break;

            if (choix == 1) {
                System.out.print("Email : ");
                String id = scanner.next();
                System.out.print("Mot de passe: ");
                String pass = scanner.next();
                System.out.print(pass);


                Personne loggedClient = loginService.authentifier(id, pass);
                if (loggedClient != null) {
                    if (loggedClient instanceof Client) {
                        Client client = (Client) loggedClient;
                        menuClient(client, transactionService, scanner);
                    } else {
                        menuGestionnaire(clientService, compteService, transactionService, scanner);
                    }
                } else {
                    System.out.println("Identifiants client incorrects.");
                }
            } else if (choix == 2) {
                System.out.print("Prenom: ");
                String prenom = scanner.next();
                System.out.print("Nom: ");
                String nom = scanner.next();
                System.out.print("Email: ");
                String email = scanner.next();
                System.out.print("Mot de passe : ");
                String pass = scanner.next();

                Client newClient = new Client(Client.count, nom, prenom, email, pass);

                loginService.ajouterUtilisateur(newClient);
                menuClient(newClient, transactionService, scanner);
            }
        }
        System.out.println("Fermeture de l'application NexaBank.");
    }

    private static void menuClient(Client client, TransactionService ts, Scanner sc) {
        while (true) {
            System.out.println("\n--- ESPACE CLIENT (" + client.getPrenom() + ") ---");
            System.out.println("1. Consulter mes comptes & soldes");
            System.out.println("2. Effectuer un dépôt");
            System.out.println("3. Effectuer un retrait");
            System.out.println("4. Effectuer un virement");
            System.out.println("5. Consulter le relevé bancaire (Fichier .txt)");
            System.out.println("6. Déconnexion");
            System.out.print("Choix : ");
            int c = sc.nextInt();
            if (c == 6) break;

            try {
                switch (c) {
                    case 1:
                        client.getComptes().forEach((num, compte) -> System.out.println("Compte: " + num + " | Type: " + compte.getTypeCompte() + " | Solde: " + compte.getSolde() + " €"));
                        break;
                    case 2:
                        System.out.print("N° de compte : ");
                        String numD = sc.next();
                        System.out.print("Montant : ");
                        double mD = sc.nextDouble();
                        Compte cD = client.getComptes().get(numD);
                        if (cD != null) ts.effectuerDepot(cD, mD);
                        else System.out.println("Compte introuvable.");
                        break;
                    case 3:
                        System.out.print("N° de compte : ");
                        String numR = sc.next();
                        System.out.print("Montant : ");
                        double mR = sc.nextDouble();
                        Compte cR = client.getComptes().get(numR);
                        if (cR != null) ts.effectuerRetrait(cR, mR);
                        else System.out.println("Compte introuvable.");
                        break;
                    case 4:
                        System.out.print("N° de compte Source : ");
                        String src = sc.next();
                        System.out.print("N° de compte Destination : ");
                        String dest = sc.next();
                        System.out.print("Montant : ");
                        double mV = sc.nextDouble();
                        Compte cSrc = client.getComptes().get(src);
                        Compte cDest = client.getComptes().get(dest);
                        if (cSrc != null && cDest != null) ts.effectuerVirement(cSrc, cDest, mV);
                        else System.out.println("Un des comptes n'existe pas.");
                        break;
                    case 5:
                        System.out.print("N° de compte : ");
                        String numF = sc.next();
                        ts.afficherReleveFichier(numF);
                        break;
                }
            } catch (Exception e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        }
    }

    private static void menuGestionnaire(ClientService cs, CompteService compS, TransactionService ts, Scanner sc) {
        while (true) {
            System.out.println("\n--- ESPACE GESTIONNAIRE ---");
            System.out.println("1. Créer un compte client");
            System.out.println("2. Clôturer un compte client");
            System.out.println("3. Consulter le relevé .txt d'un client");
            System.out.println("4. Déconnexion");
            System.out.print("Choix : ");
            int c = sc.nextInt();
            if (c == 4) break;

            try {
                switch (c) {
                    case 1:
                        cs.afficherClients();
                        System.out.print("ID Client : ");
                        int id = sc.nextInt();
                        Client cl = cs.trouverParId(id);
                        if (cl != null) {
                            System.out.print("Solde initial : ");
                            double s = sc.nextDouble();
                            System.out.println("Type de compte : \n");
                            System.out.println("1.COURANT");
                            System.out.println("2.EPARGNE");
                            System.out.print("Your choice : ");
                            int ct = sc.nextInt();
                            if (ct == 1) compS.creerCompte(cl, s, TypeCompte.COURANT);
                            if (ct == 2) compS.creerCompte(cl, s, TypeCompte.EPARGNE);
                            System.out.println("Compte créé !");
                        } else System.out.println("Client introuvable.");
                        break;
                    case 2:
                        cs.afficherClients();
                        System.out.print("ID Client : ");
                        int idCl = sc.nextInt();
                        Client client = cs.trouverParId(idCl);
                        client.getComptes().forEach((num, compte) -> System.out.println("Compte: " + num + " | Type: " + compte.getTypeCompte() + " | Solde: " + compte.getSolde() + " €"));
                        if (client != null) {
                            System.out.print("N° de compte à fermer : ");
                            String num = sc.next();
                            compS.cloturerCompte(client, num);
                            System.out.println("Compte clôturé.");
                        }
                        break;
                    case 3:
                        cs.afficherClients();
                        System.out.print("ID Client : ");
                        int idClient = sc.nextInt();
                        Client copmpteClient = cs.trouverParId(idClient);
                        copmpteClient   .getComptes().forEach((num, compte) -> System.out.println("Compte: " + num + " | Type: " + compte.getTypeCompte() + " | Solde: " + compte.getSolde() + " €"));
                        System.out.print("N° de compte : ");
                        String num = sc.next();
                        ts.afficherReleveFichier(num);
                        break;
                }
            } catch (Exception e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        }
    }
}