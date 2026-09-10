// File: src/com/nexabank/service/TransactionService.java
package com.nexabank.service;

import com.nexabank.exeption.CompteInexistantException;
import com.nexabank.exeption.MontantInvalideException;
import com.nexabank.exeption.SoldeInsuffisantException;
import com.nexabank.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionService {

    public void effectuerDepot(Compte compte, double montant) throws MontantInvalideException, IOException {
        if (montant <= 0) {
            throw new MontantInvalideException("Le montant du dépôt doit être strictement positif.");
        }
        compte.setSolde(compte.getSolde() + montant);

        Transaction t = new Transaction(
                Transaction.count,
                montant,
                LocalDateTime.now(),
                null,
                compte.getNumeroCompte(),
                TypeTransaction.DEPOT
        );

        compte.getHistoriqueTransactions().add(t);
        ecrireTransactionFichier(compte.getNumeroCompte(), t);
    }

    public void effectuerRetrait(Compte compte, double montant) throws MontantInvalideException, SoldeInsuffisantException, IOException {
        if (montant <= 0) {
            throw new MontantInvalideException("Le montant du retrait doit être strictement positif.");
        }
        if (compte.getSolde() < montant) {
            throw new SoldeInsuffisantException("Solde insuffisant pour effectuer ce retrait.");
        }
        compte.setSolde(compte.getSolde() - montant);

        Transaction t = new Transaction(
                Transaction.count,
                montant,
                LocalDateTime.now(),
                compte.getNumeroCompte(),
                null,
                TypeTransaction.RETRAIT

                );

        compte.getHistoriqueTransactions().add(t);
        ecrireTransactionFichier(compte.getNumeroCompte(), t);
    }

    public void effectuerVirement(Compte source, Compte destination, double montant) throws MontantInvalideException, SoldeInsuffisantException, IOException {
        if (montant <= 0) {
            throw new MontantInvalideException("Le montant du virement doit être supérieur à zéro.");
        }
        if (source.getSolde() < montant) {
            throw new SoldeInsuffisantException("Solde insuffisant pour effectuer le virement.");
        }

        source.setSolde(source.getSolde() - montant);
        destination.setSolde(destination.getSolde() + montant);

        Transaction t = new Transaction(
                Transaction.count,
                montant,
                LocalDateTime.now(),
                source.getNumeroCompte(),
                destination.getNumeroCompte(),
                TypeTransaction.VIREMENT
                );

        source.getHistoriqueTransactions().add(t);
        destination.getHistoriqueTransactions().add(t);

        ecrireTransactionFichier(source.getNumeroCompte(), t);
        ecrireTransactionFichier(destination.getNumeroCompte(), t);
    }

    private void ecrireTransactionFichier(String numeroCompte, Transaction t) throws IOException {
        String filename = "releve_" + numeroCompte + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            String source = (t.getCompteSource() != null) ? t.getCompteSource() : "null";
            String dest = (t.getCompteDestination() != null) ? t.getCompteDestination() : "null";
            String ligne = String.format("%-12s | %-8s | %-8.2f € | %-12s | %-12s",
                    t.getDate().toString(),
                    t.getTypeTransaction().name(),
                    t.getMontant(),
                    source,
                    dest);
            writer.write(ligne);
            writer.newLine();
        }
    }

    public void afficherReleveFichier(String numeroCompte) throws IOException, CompteInexistantException {
        String filename = "releve_" + numeroCompte + ".txt";
        File file = new File(filename);

        if (!file.exists()) {
            throw new CompteInexistantException("Aucun relevé trouvé pour le compte " + numeroCompte);
        }

        System.out.println("\n--- RELEVÉ BANCAIRE (Compte " + numeroCompte + ") ---");
        System.out.printf("%-12s | %-8s | %-10s | %-12s | %-12s\n", "Date", "Type", "Montant", "Source", "Destination");
        System.out.println("---------------------------------------------------------------");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        System.out.println("---------------------------------------------------------------\n");
    }
}