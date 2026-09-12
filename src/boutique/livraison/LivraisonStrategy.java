package boutique.livraison;

import boutique.config.ConfigurationProvider;

// Interface pour le calcul des frais de livraison (Pattern Strategy)
public interface LivraisonStrategy {
    int calculerFrais(int sousTotalCentimes, int poidsTotalGrammes, ConfigurationProvider config);
    String getNom();
}
