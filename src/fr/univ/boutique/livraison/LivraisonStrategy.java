package fr.univ.boutique.livraison;

import fr.univ.boutique.config.ConfigurationProvider;

/**
 * Contrat de strategie de livraison (Patron Strategy).
 * Substituable pour tout sous-total >= 0 et poids > 0 (Principe de Liskov).
 */
public interface LivraisonStrategy {
    int calculerFrais(int sousTotalCentimes, int poidsTotalGrammes, ConfigurationProvider config);
    String getNom();
}
