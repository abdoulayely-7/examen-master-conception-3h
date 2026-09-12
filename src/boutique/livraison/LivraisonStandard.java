package boutique.livraison;

import boutique.config.ConfigurationProvider;
import java.util.Objects;

// Livraison standard : 500 centimes, gratuite au-dessus du seuil
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
