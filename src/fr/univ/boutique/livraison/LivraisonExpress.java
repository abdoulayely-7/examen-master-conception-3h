package fr.univ.boutique.livraison;

import fr.univ.boutique.config.ConfigurationProvider;

/**
 * Livraison Express : 1 000 centimes + 200 centimes par kilogramme commence.
 */
public class LivraisonExpress implements LivraisonStrategy {
    public static final int FRAIS_DE_BASE_CENTIMES = 1_000;
    public static final int FRAIS_PAR_KG_CENTIMES = 200;

    @Override
    public int calculerFrais(int sousTotalCentimes, int poidsTotalGrammes, ConfigurationProvider config) {
        if (poidsTotalGrammes <= 0) {
            return FRAIS_DE_BASE_CENTIMES;
        }
        // Calcul du nombre de kg commences (ex. 1000g -> 1kg, 1001g -> 2kg)
        int kgCommences = (poidsTotalGrammes + 999) / 1000;
        return FRAIS_DE_BASE_CENTIMES + (kgCommences * FRAIS_PAR_KG_CENTIMES);
    }

    @Override
    public String getNom() {
        return "Express";
    }
}
