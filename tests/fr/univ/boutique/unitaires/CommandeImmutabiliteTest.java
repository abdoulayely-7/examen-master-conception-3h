package fr.univ.boutique.tests.unitaires;

import fr.univ.boutique.config.ConfigurationProvider;
import fr.univ.boutique.livraison.LivraisonStandard;
import fr.univ.boutique.livraison.RetraitEnMagasin;
import fr.univ.boutique.modele.Commande;
import fr.univ.boutique.modele.Produit;
import fr.univ.boutique.modele.exception.CommandeModifieeException;
import fr.univ.boutique.tests.support.NotificationSpy;
import fr.univ.boutique.tests.support.TestConfiguration;

/**
 * Scenario Unitaire 4 :
 * Verifier qu'une modification est refusee apres validation.
 */
public class CommandeImmutabiliteTest {

    public static void run() {
        System.out.println("  -> Execution : CommandeImmutabiliteTest");

        // PREPARATION (Arrange)
        ConfigurationProvider config = new TestConfiguration(10_000, 30_000);
        NotificationSpy notifSpy = new NotificationSpy();
        Produit p = new Produit("P1", 1_000, 200);
        Commande cmd = new Commande("C1", "client_test", new LivraisonStandard());
        cmd.ajouterLigne(p, 1);
        cmd.valider(notifSpy, config);

        // ACTION & VERIFICATION 1 (Act & Assert) : Tentative d'ajout de ligne
        boolean ajoutRefuse = false;
        try {
            cmd.ajouterLigne(new Produit("P2", 500, 100), 1);
        } catch (CommandeModifieeException e) {
            ajoutRefuse = true;
        }
        if (!ajoutRefuse) {
            throw new AssertionError("Echec : l'ajout de ligne apres validation aurait du etre refuse");
        }

        // ACTION & VERIFICATION 2 (Act & Assert) : Tentative de changement de mode de livraison
        boolean changementModeRefuse = false;
        try {
            cmd.setModeLivraison(new RetraitEnMagasin());
        } catch (CommandeModifieeException e) {
            changementModeRefuse = true;
        }
        if (!changementModeRefuse) {
            throw new AssertionError("Echec : le changement de mode apres validation aurait du etre refuse");
        }

        // ACTION & VERIFICATION 3 (Act & Assert) : Tentative de seconde validation
        boolean secondeValidationRefusee = false;
        try {
            cmd.valider(notifSpy, config);
        } catch (CommandeModifieeException e) {
            secondeValidationRefusee = true;
        }
        if (!secondeValidationRefusee) {
            throw new AssertionError("Echec : une seconde validation aurait du etre refusee");
        }
    }
}
