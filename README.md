# Examen ? LY Abdoulaye

## Environnement

- Version du JDK : OpenJDK 21 (Eclipse Temurin build 21+35-LTS)
- D?pendances ?ventuelles : Aucune d?pendance externe requise (Java SE standard autonome).

## Ex?cution depuis la racine du dossier

- **Compiler :**
  ```bash
  ./scripts/compile.sh
  # Ou manuellement :
  # mkdir -p bin && javac -d bin $(find src tests -name "*.java")
  ```

- **Lancer la d?monstration :**
  ```bash
  ./scripts/run.sh
  # Ou manuellement :
  # java -cp bin fr.univ.boutique.app.Main
  ```

- **Lancer les tests :**
  ```bash
  ./scripts/test.sh
  # Ou manuellement :
  # java -cp bin fr.univ.boutique.tests.support.TestRunner
  ```

## Remise

- **Tag annot? :** `v1.0.0`
- **Justification du num?ro de version :** Utilisation de la sp?cification Semantic Versioning (SemVer). Le tag `v1.0.0` d?signe la premi?re version de production stable, compl?te et conforme ? l'int?gralit? du cahier des charges de l'examen de conception POO.
- **Fonctionnalit?s r?alis?es :**
  - Mod?le m?tier complet et invariant prot?g? (`Produit`, `Catalogue`, `LigneCommande`, `Commande`, `EtatCommande`).
  - Sp?cialisation par h?ritage de classe pertinent (`ProduitFragile extends Produit`) avec surpoids d'emballage ayant un impact direct sur la livraison.
  - Patron **Strategy** pour les 3 modes de livraison (`Standard`, `Express`, `RetraitEnMagasin`) avec interversion dynamique ? l'ex?cution.
  - Patron **Singleton** pour `ConfigurationSingleton`, d?coupl? via l'abstraction `ConfigurationProvider` autorisant la substitution en test sans alt?rer l'instance globale.
  - Processus de validation avec r?gles d'exclusion (panier vide, d?passement de 30 kg), figeage irr?versible du total, et notification via abstraction (`NotificationService`).
  - Suite de tests automatis?s (4 sc?narios unitaires et 2 sc?narios d'int?gration avec espion en m?moire).
  - Documentation d'analyse et diagrammes PlantUML (`docs/analyse.md`, `docs/classes.puml`, `docs/validation.puml`).
- **Fonctionnalit?s incompl?tes / d?fauts connus :**
  - Aucun sur le p?rim?tre d?fini par le sujet.
- **R?sultats de tests r?ellement observ?s :**
  - 6 sc?narios ex?cut?s : **6 r?ussis (100%)**, 0 ?chec.
