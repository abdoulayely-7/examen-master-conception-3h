package fr.univ.boutique.livraison;

import fr.univ.boutique.config.ConfigurationProvider;

/**
 * Retrait en magasin : Gratuit (0 centime).
 */
public class RetraitEnMagasin implements LivraisonStrategy {
    @Override
    public int calculerFrais(int sousTotalCentimes, int poidsTotalGrammes, ConfigurationProvider config) {
        return 0;
    }

    @Override
    public String getNom() {
        return "Retrait en magasin";
    }
}
