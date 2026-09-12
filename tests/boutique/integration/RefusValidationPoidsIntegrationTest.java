package boutique.tests.integration;

import boutique.config.ConfigurationSingleton;
import boutique.livraison.LivraisonStandard;
import boutique.modele.Commande;
import boutique.modele.EtatCommande;
import boutique.modele.Produit;
import boutique.modele.exception.ValidationCommandeException;
import boutique.tests.support.NotificationSpy;

/**
 * Scenario d'Integration 2 :
 * Tenter de valider une commande depassant le poids maximal ; verifier le refus,
 * l'etat BROUILLON et l'absence de notification.
 */
public class RefusValidationPoidsIntegrationTest {

    public static void run() {

        // Initialisation
        ConfigurationSingleton config = ConfigurationSingleton.getInstance();
        NotificationSpy spyNotification = new NotificationSpy();

        // Produit lourd depassant la limite de 30 000 g
        Produit produitTresLourd = new Produit("ENCLUME", 15_000, 31_000); // 31 kg

        Commande commande = new Commande("CMD-LOURDE", "client_lourd", new LivraisonStandard());
        commande.ajouterLigne(produitTresLourd, 1);

        // Verification
        boolean exceptionLevee = false;
        try {
            commande.valider(spyNotification, config);
        } catch (ValidationCommandeException e) {
            exceptionLevee = true;
            System.out.printf("    (Refus attendu capture : %s)%n", e.getMessage());
        }

        if (!exceptionLevee) {
            throw new AssertionError("La validation aurait du etre refusee pour exces de poids (> 30 000 g)");
        }

        // Verifier que l'etat reste strictement BROUILLON
        if (commande.getEtat() != EtatCommande.BROUILLON) {
            throw new AssertionError("L'etat de la commande doit rester BROUILLON en cas de refus");
        }

        // Verifier l'absence totale de notification
        if (spyNotification.getAppelCount() != 0) {
            throw new AssertionError("Aucune notification ne doit etre declenchee en cas de refus metier");
        }
    }
}
