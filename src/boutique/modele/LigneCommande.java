package boutique.modele;

import java.util.Objects;

// Ligne d'une commande associant un produit et sa quantite
public class LigneCommande {
    private final Produit produit;
    private final int quantite;

    LigneCommande(Produit produit, int quantite) {
        this.produit = Objects.requireNonNull(produit, "Le produit de la ligne ne peut pas etre null");
        if (quantite <= 0) {
            throw new IllegalArgumentException("La quantite doit etre strictement positive");
        }
        this.quantite = quantite;
    }

    public Produit getProduit() {
        return produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public int getSousTotalCentimes() {
        return produit.getPrixCentimes() * quantite;
    }

    public int getPoidsTotalGrammes() {
        return produit.getPoidsGrammes() * quantite;
    }

    @Override
    public String toString() {
        return String.format("%dx %s (sous-total: %d c, poids: %d g)",
                quantite, produit.getReference(), getSousTotalCentimes(), getPoidsTotalGrammes());
    }
}
