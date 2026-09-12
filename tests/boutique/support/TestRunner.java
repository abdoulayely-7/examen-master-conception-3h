package boutique.tests.support;

import boutique.tests.integration.RefusValidationPoidsIntegrationTest;
import boutique.tests.integration.ValidationCommandeIntegrationTest;
import boutique.tests.unitaires.CommandeImmutabiliteTest;
import boutique.tests.unitaires.CommandeTotalTest;
import boutique.tests.unitaires.LivraisonExpressTest;
import boutique.tests.unitaires.LivraisonStandardTest;

import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    private record TestEntry(String nom, Runnable testAction) {}

    public static void main(String[] args) {
        System.out.println("=== EXECUTION DES TESTS AUTOMATISES ===");

        List<TestEntry> tests = new ArrayList<>();

        // Tests unitaires
        tests.add(new TestEntry("Livraison standard (seuil de gratuite)", LivraisonStandardTest::run));
        tests.add(new TestEntry("Livraison express (calcul au kg commence)", LivraisonExpressTest::run));
        tests.add(new TestEntry("Calcul total et changement de mode", CommandeTotalTest::run));
        tests.add(new TestEntry("Interdiction de modification post-validation", CommandeImmutabiliteTest::run));

        // Tests d'integration
        tests.add(new TestEntry("Cycle complet de validation et notification", ValidationCommandeIntegrationTest::run));
        tests.add(new TestEntry("Refus de validation si commande > 30 kg", RefusValidationPoidsIntegrationTest::run));

        int passes = 0;
        int fails = 0;

        for (TestEntry test : tests) {
            System.out.print("- " + test.nom() + " : ");
            try {
                test.testAction().run();
                System.out.println("OK");
                passes++;
            } catch (Throwable t) {
                System.out.println("ECHEC -> " + t.getMessage());
                fails++;
            }
        }

        System.out.println("---------------------------------------------");
        System.out.println("Resultats : " + passes + " reussis, " + fails + " echoues.");

        if (fails > 0) {
            System.exit(1);
        }
    }
}
