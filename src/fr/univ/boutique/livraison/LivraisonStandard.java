package fr.univ.boutique.livraison;

import fr.univ.boutique.config.ConfigurationProvider;
import java.util.Objects;

/**
 * Livraison Standard : 500 centimes, gratuit si sousTotal >= seuil de gratuit?.
 */
public class LivraisonStandard implements LivraisonStrategy {
    public static final int FRAIS_DE_BASE_CENTIMES = 500;

    @Override
    public int calculerFrais(int sousTotalCentimes, int poidsTotalGrammes, ConfigurationProvider config) {
        Objects.requireNonNull(config, "La configuration ne peut pas etre null");
        if (sousTotalCentimes >= config.getSeuilGratuiteStandardCentimes()) {
            return 0;
        }
        return FRAIS_DE_BASE_CENTIMES;
    }

    @Override
    public String getNom() {
        return "Standard";
    }
}
