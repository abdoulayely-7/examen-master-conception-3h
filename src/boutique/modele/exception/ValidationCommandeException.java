package boutique.modele.exception;

/**
 * Exception levee lors d'un refus metier a la validation d'une commande.
 */
public class ValidationCommandeException extends RuntimeException {
    public ValidationCommandeException(String message) {
        super(message);
    }
}
