package boutique.modele;

import boutique.config.ConfigurationProvider;
import boutique.livraison.LivraisonStrategy;
import boutique.modele.exception.CommandeModifieeException;
import boutique.modele.exception.ValidationCommandeException;
import boutique.notification.NotificationService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represente une commande client.
 * Gere la composition des lignes, l'etat (BROUILLON, VALIDEE), et la validation.
 */
public class Commande {
    private final String id;
    private final String client;
    private final List<LigneCommande> lignes;
    private LivraisonStrategy modeLivraison;
    private EtatCommande etat;
    private Integer totalValide; // Fige apres validation

    public Commande(String id, String client, LivraisonStrategy modeLivraison) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("L'identifiant de la commande ne peut pas etre vide");
        }
        if (client == null || client.trim().isEmpty()) {
            throw new IllegalArgumentException("L'identifiant client ne peut pas etre vide");
        }
        this.id = id.trim();
        this.client = client.trim();
        this.modeLivraison = Objects.requireNonNull(modeLivraison, "Le mode de livraison ne peut pas etre null");
        this.lignes = new ArrayList<>();
        this.etat = EtatCommande.BROUILLON;
        this.totalValide = null;
    }

    public String getId() {
        return id;
    }

    public String getClient() {
        return client;
    }

    public EtatCommande getEtat() {
        return etat;
    }

    public LivraisonStrategy getModeLivraison() {
        return modeLivraison;
    }

    public List<LigneCommande> getLignes() {
        return Collections.unmodifiableList(lignes);
    }

    /**
     * Composition : les lignes sont instanciees et gerees exclusivement par la commande.
     */
    public void ajouterLigne(Produit produit, int quantite) {
        verifierModifiable();
        Objects.requireNonNull(produit, "Le produit ne peut pas etre null");
        lignes.add(new LigneCommande(produit, quantite));
    }

    /**
     * Modification de la strategie de livraison avant validation.
     */
    public void setModeLivraison(LivraisonStrategy modeLivraison) {
        verifierModifiable();
        this.modeLivraison = Objects.requireNonNull(modeLivraison, "Le mode de livraison ne peut pas etre null");
    }

    public int getSousTotalCentimes() {
        int total = 0;
        for (LigneCommande ligne : lignes) {
            total += ligne.getSousTotalCentimes();
        }
        return total;
    }

    public int getPoidsTotalGrammes() {
        int poids = 0;
        for (LigneCommande ligne : lignes) {
            poids += ligne.getPoidsTotalGrammes();
        }
        return poids;
    }

    public int getFraisLivraisonCentimes(ConfigurationProvider config) {
        return modeLivraison.calculerFrais(getSousTotalCentimes(), getPoidsTotalGrammes(), config);
    }

    public int getTotalCentimes(ConfigurationProvider config) {
        if (etat == EtatCommande.VALIDEE && totalValide != null) {
            return totalValide;
        }
        return getSousTotalCentimes() + getFraisLivraisonCentimes(config);
    }

    /**
     * Processus de validation.
     * Verifie les preconditions, fige le montant et notifie via l'abstraction.
     */
    public void valider(NotificationService notificationService, ConfigurationProvider config) {
        Objects.requireNonNull(notificationService, "Le service de notification ne peut pas etre null");
        Objects.requireNonNull(config, "Le fournisseur de configuration ne peut pas etre null");

        if (this.etat == EtatCommande.VALIDEE) {
            throw new CommandeModifieeException("Impossible de valider une commande deja validee");
        }

        if (this.lignes.isEmpty()) {
            throw new ValidationCommandeException("Refus de validation : la commande est vide");
        }

        int poidsTotal = getPoidsTotalGrammes();
        int poidsMax = config.getPoidsMaximalGrammes();
        if (poidsTotal > poidsMax) {
            throw new ValidationCommandeException(String.format(
                    "Refus de validation : poids total (%d g) superieur au poids maximal autorise (%d g)",
                    poidsTotal, poidsMax));
        }

        // Figeage du total et transition d'etat
        this.totalValide = getTotalCentimes(config);
        this.etat = EtatCommande.VALIDEE;

        // Notification externe (uniquement en cas de succes)
        notificationService.notifierValidation(this.client, this.totalValide);
    }

    private void verifierModifiable() {
        if (this.etat == EtatCommande.VALIDEE) {
            throw new CommandeModifieeException("Toute modification est interdite sur une commande validee");
        }
    }
}
