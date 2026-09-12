package fr.univ.boutique.tests.unitaires;

import fr.univ.boutique.config.ConfigurationProvider;
import fr.univ.boutique.livraison.LivraisonExpress;
import fr.univ.boutique.tests.support.TestConfiguration;

/**
 * Scenario Unitaire 2 :
 * Verifier les frais Express pour 1 000 g et 1 001 g.
 */
public class LivraisonExpressTest {

    public static void run() {
        System.out.println("  -> Execution : LivraisonExpressTest");

        // PREPARATION (Arrange)
        ConfigurationProvider config = new TestConfiguration(10_000, 30_000);
        LivraisonExpress express = new LivraisonExpress();

        // ACTION & VERIFICATION (Act & Assert) - Cas 1 : 1 000 g (1 kg pile -> 1000 + 1 * 200 = 1200 c)
        int frais1000g = express.calculerFrais(5_000, 1_000, config);
        if (frais1000g != 1_200) {
            throw new AssertionError(String.format("Echec 1 000 g : attendu 1 200 c, obtenu %d c", frais1000g));
        }

        // ACTION & VERIFICATION (Act & Assert) - Cas 2 : 1 001 g (2eme kg entame -> 1000 + 2 * 200 = 1400 c)
        int frais1001g = express.calculerFrais(5_000, 1_001, config);
        if (frais1001g != 1_400) {
            throw new AssertionError(String.format("Echec 1 001 g : attendu 1 400 c, obtenu %d c", frais1001g));
        }

        // Cas 3 : Cas limite petit poids (500 g -> 1 kg commence -> 1200 c)
        int frais500g = express.calculerFrais(12_000, 500, config);
        if (frais500g != 1_200) {
            throw new AssertionError(String.format("Echec 500 g : attendu 1 200 c, obtenu %d c", frais500g));
        }
    }
}
