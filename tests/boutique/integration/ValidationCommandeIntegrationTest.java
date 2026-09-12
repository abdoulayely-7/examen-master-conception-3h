package boutique.tests.integration;

import boutique.config.ConfigurationSingleton;
import boutique.livraison.LivraisonExpress;
import boutique.modele.Commande;
import boutique.modele.EtatCommande;
import boutique.modele.Produit;
import boutique.modele.ProduitFragile;
import boutique.tests.support.NotificationSpy;

/**
 * Scenario d'Integration 1 :
 * Assembler les vrais composants metier et une vraie strategie ; valider une commande
 * et verifier le total, l'etat et une unique notification contenant le bon client et le bon montant.
 */
public class ValidationCommandeIntegrationTest {

    public static void run() {

        // Initialisation
        ConfigurationSingleton config = ConfigurationSingleton.getInstance();
        NotificationSpy spyNotification = new NotificationSpy();

        Produit livre = new Produit("REF-LIVRE", 2_000, 400); // 20 EUR, 400g
        Produit vase = new ProduitFragile("REF-VASE", 5_000, 1_200, 300); // 50 EUR, 1500g reel
        LivraisonExpress vraieStrategie = new LivraisonExpress();

        Commande commande = new Commande("CMD-INT-001", "client_integration", vraieStrategie);
        commande.ajouterLigne(livre, 2); // 4 000 c, 800 g
        commande.ajouterLigne(vase, 1);  // 5 000 c, 1 500 g
        // Poids total: 2 300 g -> 3 kg commences -> 1000 + 3*200 = 1600 c de frais
        // Sous-total: 9 000 c. Total attendu: 10 600 c.

        if (commande.getEtat() != EtatCommande.BROUILLON) {
            throw new AssertionError("L'etat initial doit etre BROUILLON");
        }

        // Validation
        commande.valider(spyNotification, config);

        // Verifications
        // 1. Verifier l'etat
        if (commande.getEtat() != EtatCommande.VALIDEE) {
            throw new AssertionError("L'etat apres validation doit etre VALIDEE");
        }

        // 2. Verifier le total fige
        if (commande.getTotalCentimes(config) != 10_600) {
            throw new AssertionError(String.format("Total attendu 10600 c, obtenu %d c", commande.getTotalCentimes(config)));
        }

        // 3. Verifier la notification unique avec bon client et bon montant
        if (spyNotification.getAppelCount() != 1) {
            throw new AssertionError(String.format("Nombre d'appels de notification attendu: 1, obtenu: %d",
                    spyNotification.getAppelCount()));
        }
        if (!"client_integration".equals(spyNotification.getDernierClientId())) {
            throw new AssertionError(String.format("Client notifie incorrect : attendu 'client_integration', obtenu '%s'",
                    spyNotification.getDernierClientId()));
        }
        if (spyNotification.getDernierMontantCentimes() != 10_600) {
            throw new AssertionError(String.format("Montant notifie incorrect : attendu 10600 c, obtenu %d c",
                    spyNotification.getDernierMontantCentimes()));
        }
    }
}
