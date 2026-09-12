package fr.univ.boutique.tests.unitaires;

import fr.univ.boutique.config.ConfigurationProvider;
import fr.univ.boutique.livraison.LivraisonStrategy;
import fr.univ.boutique.modele.Commande;
import fr.univ.boutique.modele.Produit;
import fr.univ.boutique.tests.support.TestConfiguration;

/**
 * Scenario Unitaire 3 :
 * Verifier le changement de total avec deux strategies de test a frais fixes.
 */
public class CommandeTotalTest {

    public static void run() {
        System.out.println("  -> Execution : CommandeTotalTest");

        // PREPARATION (Arrange)
        ConfigurationProvider config = new TestConfiguration(10_000, 30_000);
        Produit p = new Produit("P1", 2_000, 500); // 20,00 EUR

        // Deux strategies bouchons a frais fixes pour isoler le test unitaire
        LivraisonStrategy strategieFixe150 = new LivraisonStrategy() {
            @Override public int calculerFrais(int sousTotal, int poids, ConfigurationProvider c) { return 150; }
            @Override public String getNom() { return "Fixe 150c"; }
        };

        LivraisonStrategy strategieFixe400 = new LivraisonStrategy() {
            @Override public int calculerFrais(int sousTotal, int poids, ConfigurationProvider c) { return 400; }
            @Override public String getNom() { return "Fixe 400c"; }
        };

        Commande cmd = new Commande("C1", "client_test", strategieFixe150);
        cmd.ajouterLigne(p, 2); // Sous-total: 4 000 c

        // ACTION & VERIFICATION 1 : Avec strategie fixe 150c
        int total1 = cmd.getTotalCentimes(config);
        if (total1 != 4_150) {
            throw new AssertionError(String.format("Echec total strategie 1 : attendu 4150 c, obtenu %d c", total1));
        }

        // ACTION & VERIFICATION 2 : Changement vers strategie fixe 400c
        cmd.setModeLivraison(strategieFixe400);
        int total2 = cmd.getTotalCentimes(config);
        if (total2 != 4_400) {
            throw new AssertionError(String.format("Echec total apres changement : attendu 4400 c, obtenu %d c", total2));
        }
    }
}
