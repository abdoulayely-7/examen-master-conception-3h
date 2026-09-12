package fr.univ.boutique.modele.exception;

/**
 * Exception levee lors d'une tentative de modification ou re-validation
 * d'une commande deja validee.
 */
public class CommandeModifieeException extends IllegalStateException {
    public CommandeModifieeException(String message) {
        super(message);
    }
}
