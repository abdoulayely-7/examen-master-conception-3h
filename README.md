# Examen — LY Abdoulaye

## Environnement

- Version du JDK : OpenJDK 21 (Temurin-21+35)
- Dépendances éventuelles : Aucune dépendance externe (Java SE standard autonome).

## Exécution depuis la racine du dossier

- **Compiler :**
  ```bash
  ./scripts/compile.sh
  # Ou manuellement :
  # mkdir -p bin && javac -d bin $(find src tests -name "*.java")
  ```

- **Lancer la démonstration :**
  ```bash
  ./scripts/run.sh
  # Ou manuellement :
  # java -cp bin boutique.app.Main
  ```

- **Lancer les tests :**
  ```bash
  ./scripts/test.sh
  # Ou manuellement :
  # java -cp bin boutique.tests.support.TestRunner
  ```

## Remise

- **Tag annoté :** `v1.0.0`
- **Justification du numéro de version :** Tag `v1.0.0` correspondant à la version finale de remise de l'examen comprenant l'architecture demandée, l'implémentation Java et la suite de tests validée.
- **Fonctionnalités réalisées :**
  - Modèle métier complet avec protection des invariants (`Produit`, `Catalogue`, `LigneCommande`, `Commande`, `EtatCommande`).
  - Spécialisation par héritage (`ProduitFragile extends Produit`) avec prise en compte du surpoids d'emballage.
  - Patron **Strategy** pour les 3 modes de livraison (`LivraisonStandard`, `LivraisonExpress`, `RetraitEnMagasin`) permettant l'interchangeabilité dynamique avant validation.
  - Patron **Singleton** pour `ConfigurationSingleton`, injecté via l'interface `ConfigurationProvider` afin d'autoriser la substitution pour les tests.
  - Validation de commande : contrôle du panier vide, limitation du poids total à 30 kg, figeage du montant total et notification via `NotificationService`.
  - Suite de tests automatisés (4 scénarios unitaires et 2 scénarios d'intégration avec espion mémoire).
  - Conception et diagrammes PlantUML (`docs/analyse.md`, `docs/classes.puml`, `docs/validation.puml`).
- **Fonctionnalités incomplètes / défauts connus :**
  - Toutes les fonctionnalités demandées dans le sujet sont implémentées et fonctionnelles.
- **Résultats de tests réellement observés :**
  - 6 scénarios exécutés : **6 réussis (100%)**, 0 échec.
