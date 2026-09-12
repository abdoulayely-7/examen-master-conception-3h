package boutique.modele;

import java.util.Objects;

/**
 * Represente un produit de la boutique.
 * Protege ses invariants a l'instanciation : reference non vide, prix >= 0, poids > 0.
 */
public class Produit {
    private final String reference;
    private final int prixCentimes;
    private final int poidsGrammes;

    public Produit(String reference, int prixCentimes, int poidsGrammes) {
        if (reference == null || reference.trim().isEmpty()) {
            throw new IllegalArgumentException("La reference du produit ne peut pas etre vide");
        }
        if (prixCentimes < 0) {
            throw new IllegalArgumentException("Le prix du produit doit etre positif ou nul");
        }
        if (poidsGrammes <= 0) {
            throw new IllegalArgumentException("Le poids du produit doit etre strictement positif");
        }
        this.reference = reference.trim();
        this.prixCentimes = prixCentimes;
        this.poidsGrammes = poidsGrammes;
    }

    public String getReference() {
        return reference;
    }

    public int getPrixCentimes() {
        return prixCentimes;
    }

    public int getPoidsGrammes() {
        return poidsGrammes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produit other)) return false;
        return Objects.equals(reference, other.reference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference);
    }

    @Override
    public String toString() {
        return String.format("Produit[%s, %d c, %d g]", reference, prixCentimes, getPoidsGrammes());
    }
}
