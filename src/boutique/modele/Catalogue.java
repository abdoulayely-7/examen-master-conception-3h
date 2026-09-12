package boutique.modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

// Catalogue de produits
public class Catalogue {
    private final String nom;
    private final List<Produit> produits;

    public Catalogue(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du catalogue ne peut pas etre vide");
        }
        this.nom = nom.trim();
        this.produits = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public void ajouterProduit(Produit produit) {
        Objects.requireNonNull(produit, "Le produit a ajouter ne peut pas etre null");
        if (!produits.contains(produit)) {
            produits.add(produit);
        }
    }

    public List<Produit> getProduits() {
        return Collections.unmodifiableList(produits);
    }
}
