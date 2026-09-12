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
- Preuve : Fichiers `docs/analyse.md`, `docs/classes.puml` et `docs/validation.puml`.

## Entr?e 2 ? R?alisation

- Heure : [?]
- Fonctionnalit? r?alis?e : [?]
- Difficult? rencontr?e, cause identifi?e et r?solution : [?]
- V?rification effectu?e et r?sultat : [?]
- Preuve : [classe / test / commit existant]

## Entr?e 3 ? V?rification finale

- Heure : [?]
- Commande de compilation ex?cut?e : [?]
- Commande de tests ex?cut?e : [?]
- R?sultat observ? : [nombre de sc?narios r?ussis / ?chou?s / non ex?cut?s]
- Fonctionnalit?s incompl?tes et limites : [?]
- Preuve : [test / sortie r?sum?e / commit existant]

## Bilan critique ? six ? dix lignes

[Une limite du Singleton ; comportement retenu en cas d'?chec de notification apr?s validation ; risque r?siduel et am?lioration propos?e.]
