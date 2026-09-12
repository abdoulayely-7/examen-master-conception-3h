package boutique.livraison;

import boutique.config.ConfigurationProvider;

// Retrait en magasin gratuit
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
