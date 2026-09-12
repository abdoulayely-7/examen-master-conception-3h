# Analyse et Contrats Métier

## 1. Contrats Métier

### A. Trois Invariants Fondamentaux
1. **Invariant Produit :** Un produit possède une référence non vide, un prix en centimes supérieur ou égal à zéro (prix >= 0) et un poids en grammes strictement positif (poids > 0).
2. **Invariant Commande & Lignes :** Une commande est rattachée à un client identifié par une chaîne non vide. Chaque ligne de commande associe un produit à une quantité strictement positive (quantite > 0). Les lignes sont composées exclusivement au sein de la commande et ne sont jamais partagées.
3. **Invariant d'Intégrité post-validation :** Une commande à l'état VALIDEE fige définitivement son montant total (totalValide). Aucune modification ultérieure (ajout/retrait de ligne, modification de quantité, changement de stratégie de livraison) ni aucune ré-exécution de validation ne sont permises.

### B. Préconditions de la Validation
- L'état courant de la commande doit être BROUILLON (non encore validée).
- La commande doit comporter au moins une ligne (nombreDeLignes > 0, panier non vide).
- Le poids total cumulé de la commande ne doit pas excéder la limite maximale fixée par la configuration :
  somme(poidsUnitaire * quantite) <= PoidsMaximalConfig (30 000 g par défaut).
- Une stratégie de livraison non nulle et un service de notification valide doivent être fournis.

### C. Postconditions de la Validation
- L'état de la commande passe irréversiblement à VALIDEE.
- Le montant total définitif est calculé (Total = SousTotal + FraisLivraison) et stocké de manière immuable.
- Le service de notification externe est invoqué exactement une fois avec les paramètres (clientId, totalValide).
- En cas de violation d'une précondition (refus métier), l'état demeure strictement BROUILLON, aucun montant n'est figé, aucune notification n'est émise et une exception explicite (ValidationCommandeException) est levée.

---

## 2. Justification des Choix de Conception

### A. Justification des Quatre Relations Objet
1. **Héritage de classe (ProduitFragile hérite de Produit) :** Permet une spécialisation comportementale réelle et justifiée : un produit fragile nécessite un emballage protecteur amortissant dont le surpoids s'ajoute dynamiquement au poids transporté (getPoidsGrammes()). Cela influe directement sur le calcul des frais express et sur le seuil critique des 30 000 g sans dupliquer la gestion des références et prix.
2. **Composition (Commande -> LigneCommande) :** Les lignes de commande sont créées, gérées et détruites exclusivement par leur commande parente. Leur cycle de vie est strictement subordonné à la commande, garantissant qu'aucune ligne n'est partagée ni modifiée indépendamment.
3. **Agrégation (Catalogue o-> Produit) :** Un catalogue regroupe des produits existants. Les produits possèdent une existence autonome et peuvent appartenir simultanément à plusieurs catalogues ; la disparition d'un catalogue ne détruit pas les produits.
4. **Dépendance ponctuelle (Commande ..> NotificationService, ConfigurationProvider) :** La commande ne conserve pas de référence persistante vers le service de notification ni vers le singleton de configuration : ils sont transmis ponctuellement en argument lors de l'opération valider(...) pour minimiser le couplage.

### B. Application Concrète de Deux Principes SOLID
1. **Open/Closed Principle (OCP) :** Le noyau métier (Commande) est fermé à la modification mais ouvert à l'extension grâce au patron **Strategy**. L'ajout d'un nouveau mode de livraison (ex. livraison express par coursier) s'effectue par création d'une nouvelle classe implémentant LivraisonStrategy sans altérer une seule ligne du code de calcul ou de validation de la commande.
2. **Dependency Inversion Principle (DIP) :** La commande dépend d'abstractions (LivraisonStrategy, NotificationService, ConfigurationProvider) et non d'implémentations concrètes ni d'un appel statique global direct au Singleton. Cela permet une flexibilité totale et autorise la substitution aisée par des bouchons ou espions en test unitaire.

### C. Contrat Substituable des Stratégies (Liskov Substitution Principle - LSP)
Toutes les stratégies concrètes (LivraisonStandard, LivraisonExpress, RetraitEnMagasin) respectent scrupuleusement le contrat de l'interface LivraisonStrategy. Pour tout sous-total valide (>= 0) et tout poids valide (> 0), la méthode calculerFrais(...) garantit en retour un montant en centimes positif ou nul (>= 0) sans effets de bord inattendus, permettant ainsi d'interchanger n'importe quelle stratégie à l'exécution de manière transparente et sans régression.
