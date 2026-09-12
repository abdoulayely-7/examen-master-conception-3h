package fr.univ.boutique.tests.support;

import fr.univ.boutique.tests.integration.RefusValidationPoidsIntegrationTest;
import fr.univ.boutique.tests.integration.ValidationCommandeIntegrationTest;
import fr.univ.boutique.tests.unitaires.CommandeImmutabiliteTest;
import fr.univ.boutique.tests.unitaires.CommandeTotalTest;
import fr.univ.boutique.tests.unitaires.LivraisonExpressTest;
import fr.univ.boutique.tests.unitaires.LivraisonStandardTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Executeur de tests automatises.
 * Execute chaque scenario et produit un rapport d'execution avec assertions strictes.
 */
public class TestRunner {

    private record TestEntry(String nom, Runnable testAction, boolean isIntegration) {}

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("    SUITE DE TESTS AUTOMATISES (EXAMEN POO)      ");
        System.out.println("=================================================");

        List<TestEntry> tests = new ArrayList<>();

        // 4 Scenarios Unitaires
        tests.add(new TestEntry("TU 1 - Frais Standard (sous/au seuil)", LivraisonStandardTest::run, false));
        tests.add(new TestEntry("TU 2 - Frais Express (1000g / 1001g)", LivraisonExpressTest::run, false));
        tests.add(new TestEntry("TU 3 - Commande changement total strategies fixes", CommandeTotalTest::run, false));
        tests.add(new TestEntry("TU 4 - Commande refus modification apres validation", CommandeImmutabiliteTest::run, false));

        // 2 Scenarios d'Integration
        tests.add(new TestEntry("TI 1 - Validation reussie, total fige, notification unique", ValidationCommandeIntegrationTest::run, true));
        tests.add(new TestEntry("TI 2 - Refus validation poids > 30kg, etat BROUILLON, 0 notification", RefusValidationPoidsIntegrationTest::run, true));

        int reussis = 0;
        int echoues = 0;

        for (TestEntry entry : tests) {
            String type = entry.isIntegration() ? "[INTEGRATION]" : "[UNITAIRE]   ";
            System.out.printf("%n%s %s%n", type, entry.nom());
            try {
                entry.testAction().run();
                System.out.println("  ==> SUCCES (OK)");
                reussis++;
            } catch (Throwable t) {
                System.err.printf("  ==> ECHEC : %s%n", t.getMessage());
                t.printStackTrace();
                echoues++;
            }
        }

        System.out.println("\n=================================================");
        System.out.printf("BILAN DES TESTS : %d execute(s) | %d reussi(s) | %d echoue(s)%n",
                tests.size(), reussis, echoues);
        System.out.println("=================================================");

        if (echoues > 0) {
            System.exit(1);
        }
    }
}
