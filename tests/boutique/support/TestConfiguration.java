package boutique.tests.support;

import boutique.config.ConfigurationProvider;

/**
 * Configuration de test permettant de substituer les seuils sans toucher au Singleton.
 */
public class TestConfiguration implements ConfigurationProvider {
    private final int seuilGratuite;
    private final int poidsMax;

    public TestConfiguration(int seuilGratuite, int poidsMax) {
        this.seuilGratuite = seuilGratuite;
        this.poidsMax = poidsMax;
    }

    @Override
    public int getSeuilGratuiteStandardCentimes() {
        return seuilGratuite;
    }

    @Override
    public int getPoidsMaximalGrammes() {
        return poidsMax;
    }
}
