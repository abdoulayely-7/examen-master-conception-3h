# DevLog d'examen ? 3 heures

**Nom et pr?nom :** LY Abdoulaye  
**JDK :** OpenJDK 21 (Temurin-21+35)  
**Branche de travail :** feature/conception-et-realisation  
**Tag final :** v1.0.0

> Trois entr?es courtes suffisent. Compl?ter pendant l'?preuve. Ne pas inventer de commande ex?cut?e, de r?sultat ou de hash. Utiliser un chemin de fichier si le commit n'existe pas encore.

## Entr?e 1 ? Conception initiale

- Heure : 15:35
- D?cision de conception et raison : D?couplage strict de la configuration via une interface `ConfigurationProvider` impl?ment?e par un `ConfigurationSingleton`. Sp?cialisation par h?ritage de classe `ProduitFragile extends Produit` pour int?grer un surpoids d'emballage ayant un impact direct sur le calcul de la livraison et les seuils de validation.
- Autre option envisag?e et raison du rejet : Acc?s statique direct `ConfigurationSingleton.getInstance()` dans la commande et les strat?gies ; rejet? car cela violerait le principe d'inversion des d?pendances (DIP) et emp?cherait l'injection de configurations bouchonn?es pour les tests.
- Relation POO ou principe SOLID concern? : H?ritage de classe pertinent (`ProduitFragile` -> `Produit`), Composition (`Commande` -> `LigneCommande`), Agr?gation (`Catalogue` -> `Produit`), D?pendance ponctuelle (`Commande` -> `NotificationService`), Strategy (`LivraisonStrategy`), et principes DIP & OCP.
- Preuve : Fichiers `docs/analyse.md`, `docs/classes.puml` et `docs/validation.puml` (commit 2336923).

## Entr?e 2 ? R?alisation

- Heure : 15:38
- Fonctionnalit? r?alis?e : Impl?mentation compl?te du mod?le m?tier (`Produit`, `ProduitFragile`, `Catalogue`, `LigneCommande`, `Commande`), des trois strat?gies (`LivraisonStandard`, `LivraisonExpress`, `RetraitEnMagasin`), du `ConfigurationSingleton`, du service de notification et de la classe ex?cutable de d?monstration `Main`.
- Difficult? rencontr?e, cause identifi?e et r?solution : Prise en compte exacte de la r?gle d'arrondi au kilogramme commenc? pour le mode Express (ex. 1 000 g = 1 kg, mais 1 001 g = 2 kg). R?solu via l'arithm?tique enti?re sans perte de pr?cision `(poidsGrammes + 999) / 1000`.
- V?rification effectu?e et r?sultat : Ex?cution de `fr.univ.boutique.app.Main` via `./scripts/run.sh`. Affichage coh?rent du cycle de vie, changement de strat?gie ? chaud, notification et blocage de toute modification apr?s validation.
- Preuve : Classes dans `src/fr/univ/boutique/` et classe de d?monstration `Main.java` (commit 6920a5f).

## Entr?e 3 ? V?rification finale

- Heure : 15:42
- Commande de compilation ex?cut?e : `./scripts/compile.sh` (`javac -d bin $(find src tests -name "*.java")`)
- Commande de tests ex?cut?e : `./scripts/test.sh` (`java -cp bin fr.univ.boutique.tests.support.TestRunner`)
- R?sultat observ? : 6 sc?narios ex?cut?s, 6 r?ussis, 0 ?chou?, 0 non ex?cut? (100% de succ?s).
- Fonctionnalit?s incompl?tes et limites : Aucune sur le p?rim?tre demand?. Tous les cas nominaux et de refus (d?passement 30 kg, panier vide, re-validation, immutabilit? post-validation) sont valid?s.
- Preuve : Suite de tests `tests/fr/univ/boutique/support/TestRunner.java` (commit b8591d7).

## Bilan critique ? six ? dix lignes

Le Singleton pr?sente l'inconv?nient majeur d'introduire un ?tat global persistant par chargeur de classes, nuisant au parall?lisme des tests unitaires et cr?ant un couplage cach? s'il est acc?d? statiquement. Ce risque a ?t? neutralis? ici en for?ant les composants m?tier ? d?pendre de l'abstraction `ConfigurationProvider`. Si le composant de notification ?choue apr?s validation, nous retenons que la commande doit imp?rativement rester valid?e car l'accord transactionnel sur le montant fig? est act? : un rollback m?tier de la commande serait commercialement inacceptable. Le risque r?siduel est la non-information de l'utilisateur. Une am?lioration recommand?e consisterait ? d?coupler la notification par un mod?le Transactional Outbox (sauvegarde de l'?v?nement en base) ou une file de messages asynchrone (Dead Letter Queue avec rejeu automatique).
