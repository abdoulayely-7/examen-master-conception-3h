package boutique.modele;

// Produit fragile necessitant un surpoids d'emballage
public class ProduitFragile extends Produit {
    private final int poidsEmballageSupplementaireGrammes;

    public ProduitFragile(String reference, int prixCentimes, int poidsGrammes, int poidsEmballageSupplementaireGrammes) {
        super(reference, prixCentimes, poidsGrammes);
        if (poidsEmballageSupplementaireGrammes <= 0) {
            throw new IllegalArgumentException("Le surpoids d'emballage doit etre strictement positif");
        }
        this.poidsEmballageSupplementaireGrammes = poidsEmballageSupplementaireGrammes;
    }

    public int getPoidsEmballageSupplementaireGrammes() {
        return poidsEmballageSupplementaireGrammes;
    }

    @Override
    public int getPoidsGrammes() {
        return super.getPoidsGrammes() + poidsEmballageSupplementaireGrammes;
    }

    @Override
    public String toString() {
        return String.format("ProduitFragile[%s, %d c, base=%d g, emballage=+%d g, total=%d g]",
                getReference(), getPrixCentimes(), super.getPoidsGrammes(),
                poidsEmballageSupplementaireGrammes, getPoidsGrammes());
    }
}
