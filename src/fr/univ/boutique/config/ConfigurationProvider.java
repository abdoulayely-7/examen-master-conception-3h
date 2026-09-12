package fr.univ.boutique.config;

/**
 * Abstraction du fournisseur de configuration.
 * Permet d'inverser les dependances et de substituer la configuration en test.
 */
public interface ConfigurationProvider {
    int getSeuilGratuiteStandardCentimes();
    int getPoidsMaximalGrammes();
}
