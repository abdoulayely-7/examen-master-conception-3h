package boutique.tests.unitaires;

import boutique.config.ConfigurationProvider;
import boutique.livraison.LivraisonStandard;
import boutique.tests.support.TestConfiguration;

/**
 * Scenario Unitaire 1 :
 * Verifier les frais Standard juste sous le seuil et au seuil de gratuite.
 */
public class LivraisonStandardTest {

    public static void run() {

        // Initialisation
        ConfigurationProvider config = new TestConfiguration(10_000, 30_000);
        LivraisonStandard standard = new LivraisonStandard();

        // Cas 1 : Juste sous le seuil (9 999 c)
        int fraisSousSeuil = standard.calculerFrais(9_999, 1_000, config);
        if (fraisSousSeuil != 500) {
            throw new AssertionError(String.format("Echec sous le seuil : attendu 500 c, obtenu %d c", fraisSousSeuil));
        }

        // Cas 2 : Exactement au seuil (10 000 c)
        int fraisAuSeuil = standard.calculerFrais(10_000, 1_000, config);
        if (fraisAuSeuil != 0) {
            throw new AssertionError(String.format("Echec au seuil : attendu 0 c (gratuit), obtenu %d c", fraisAuSeuil));
        }

        // Cas 3 : Au-dessus du seuil (15 000 c)
        int fraisAuDessus = standard.calculerFrais(15_000, 2_000, config);
        if (fraisAuDessus != 0) {
            throw new AssertionError(String.format("Echec au dessus du seuil : attendu 0 c, obtenu %d c", fraisAuDessus));
        }
    }
}
