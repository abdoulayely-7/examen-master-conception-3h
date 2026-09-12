package boutique.config;

// Singleton stockant la configuration par defaut
public final class ConfigurationSingleton implements ConfigurationProvider {

    private static final ConfigurationSingleton INSTANCE = new ConfigurationSingleton();

    private final int seuilGratuiteStandardCentimes;
    private final int poidsMaximalGrammes;

    private ConfigurationSingleton() {
        this.seuilGratuiteStandardCentimes = 10_000; // 100,00 EUR
        this.poidsMaximalGrammes = 30_000;            // 30 kg
    }

    public static ConfigurationSingleton getInstance() {
        return INSTANCE;
    }

    @Override
    public int getSeuilGratuiteStandardCentimes() {
        return seuilGratuiteStandardCentimes;
    }

    @Override
    public int getPoidsMaximalGrammes() {
        return poidsMaximalGrammes;
    }
}
