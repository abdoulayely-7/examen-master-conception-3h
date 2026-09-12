package boutique.tests.support;

import boutique.notification.NotificationService;

/**
 * Espion en memoire permettant d'enregistrer et verifier les notifications.
 */
public class NotificationSpy implements NotificationService {
    private int appelCount = 0;
    private String dernierClientId = null;
    private int dernierMontantCentimes = -1;

    @Override
    public void notifierValidation(String clientId, int montantTotalCentimes) {
        this.appelCount++;
        this.dernierClientId = clientId;
        this.dernierMontantCentimes = montantTotalCentimes;
    }

    public int getAppelCount() {
        return appelCount;
    }

    public String getDernierClientId() {
        return dernierClientId;
    }

    public int getDernierMontantCentimes() {
        return dernierMontantCentimes;
    }

    public void reinitialiser() {
        this.appelCount = 0;
        this.dernierClientId = null;
        this.dernierMontantCentimes = -1;
    }
}
