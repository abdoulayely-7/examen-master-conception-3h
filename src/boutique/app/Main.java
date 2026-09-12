package boutique.app;

import boutique.config.ConfigurationSingleton;
import boutique.livraison.LivraisonExpress;
import boutique.livraison.LivraisonStandard;
import boutique.livraison.RetraitEnMagasin;
import boutique.modele.Commande;
import boutique.modele.Produit;
import boutique.modele.ProduitFragile;
import boutique.modele.exception.CommandeModifieeException;
import boutique.notification.ConsoleNotificationService;

// Classe principale de demonstration pour tester le fonctionnement global
public class Main {
    public static void main(String[] args) {
        System.out.println("=== DEMONSTRATION GESTION DES COMMANDES ET LIVRAISONS ===");

        ConfigurationSingleton config = ConfigurationSingleton.getInstance();
        ConsoleNotificationService notificationService = new ConsoleNotificationService();

        // 1. Creation des produits (dont un ProduitFragile specialise)
        Produit livre = new Produit("LIVRE-001", 1_500, 300); // 15,00 EUR, 300g
        Produit vaseFragile = new ProduitFragile("VASE-002", 4_500, 1_200, 400); // 45,00 EUR, 1200g + 400g emballage

        // 2. Creation d'une commande initiale en mode Standard
        Commande commande = new Commande("CMD-2026-001", "client_lydevtech", new LivraisonStandard());
        commande.ajouterLigne(livre, 2);        // 3 000 c, 600 g
        commande.ajouterLigne(vaseFragile, 1);  // 4 500 c, 1 600 g

        System.out.printf("Commande creee : %s pour le client %s%n", commande.getId(), commande.getClient());
        System.out.printf("Etat initial : %s%n", commande.getEtat());
        System.out.printf("Sous-total : %d c (%.2f EUR) | Poids total : %d g%n",
                commande.getSousTotalCentimes(), commande.getSousTotalCentimes() / 100.0, commande.getPoidsTotalGrammes());
        System.out.printf("Mode de livraison : %s | Frais : %d c%n",
                commande.getModeLivraison().getNom(), commande.getFraisLivraisonCentimes(config));
        System.out.printf("Total avec livraison Standard : %d c (%.2f EUR)%n",
                commande.getTotalCentimes(config), commande.getTotalCentimes(config) / 100.0);

        // 3. Changement dynamique de la strategie de livraison vers Express
        System.out.println("\nChangement du mode de livraison vers 'Express'...");
        commande.setModeLivraison(new LivraisonExpress());
        System.out.printf("Nouveau mode : %s | Nouveaux frais : %d c%n",
                commande.getModeLivraison().getNom(), commande.getFraisLivraisonCentimes(config));
        System.out.printf("Nouveau total : %d c (%.2f EUR)%n",
                commande.getTotalCentimes(config), commande.getTotalCentimes(config) / 100.0);

        // 4. Validation de la commande (declenche la notification et fige le total)
        System.out.println("\nValidation de la commande...");
        commande.valider(notificationService, config);
        System.out.printf("Etat apres validation : %s%n", commande.getEtat());
        System.out.printf("Total fige : %d c (%.2f EUR)%n",
                commande.getTotalCentimes(config), commande.getTotalCentimes(config) / 100.0);

        // 5. Tentative de modification post-validation (doit etre refusee)
        System.out.println("\nTentative de modification apres validation (doit lever une exception)...");
        try {
            commande.setModeLivraison(new RetraitEnMagasin());
            System.err.println("ERREUR : La modification aurait du etre refusee !");
        } catch (CommandeModifieeException e) {
            System.out.printf("Succes du verrouillage : exception interceptee -> %s%n", e.getMessage());
        }

        System.out.println("\n=== DEMONSTRATION TERMINEE AVEC SUCCES ===");
    }
}
