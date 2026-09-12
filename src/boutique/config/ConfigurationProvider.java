package boutique.config;

// Interface pour recuperer les seuils de configuration
public interface ConfigurationProvider {
    int getSeuilGratuiteStandardCentimes();
    int getPoidsMaximalGrammes();
}
