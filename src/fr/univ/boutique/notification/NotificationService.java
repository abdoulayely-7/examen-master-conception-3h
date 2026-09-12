package fr.univ.boutique.notification;

/**
 * Abstraction du composant de notification.
 * Appele apres validation reussie d'une commande.
 */
public interface NotificationService {
    void notifierValidation(String clientId, int montantTotalCentimes);
}
