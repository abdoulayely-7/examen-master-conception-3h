package boutique.notification;

/**
 * Implementation console du service de notification.
 */
public class ConsoleNotificationService implements NotificationService {
    @Override
    public void notifierValidation(String clientId, int montantTotalCentimes) {
        System.out.printf("[NOTIFICATION CONSOLE] Commande validee pour le client '%s' | Total: %d centimes (%.2f EUR)%n",
                clientId, montantTotalCentimes, montantTotalCentimes / 100.0);
    }
}
