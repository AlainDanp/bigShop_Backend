# Guide de Test Complet - Big Shop API avec Postman

## Table des Matières
1. [Préparation](#1-préparation)
2. [Import de la Collection](#2-import-de-la-collection)
3. [Configuration de l'Environnement](#3-configuration-de-lenvironnement)
4. [Scénario de Test Complet](#4-scénario-de-test-complet)
5. [Tests Automatisés](#5-tests-automatisés)
6. [Résolution des Problèmes](#6-résolution-des-problèmes)

---

## 1. Préparation

### 1.1 Vérifier que le Backend est Lancé

```bash
# Dans le dossier du projet
cd "C:\Users\alain\OneDrive\Documents\X4(ESIEA)\architecture application\Backend\big_shop_backend"

# Lancer l'application
mvn clean spring-boot:run
```

**Vérification :**
- L'application démarre sur le port 8081
- Aucune erreur dans les logs
- Message de succès : "Started BigShopBackendApplication in X seconds"

### 1.2 Vérifier la Base de Données

Ouvrez MySQL et vérifiez :

```sql
USE big_shop;

-- Vérifier la structure de la table roles
DESCRIBE roles;

-- Vérifier que les rôles existent
SELECT * FROM roles;
```

**Résultat Attendu :**
- Table `roles` avec `id` de type `bigint`
- 3 rôles présents : ADMIN, USER, MODERATOR

### 1.3 Tester la Disponibilité de l'API

Ouvrez votre navigateur et allez sur :

```
http://localhost:8081/swagger-ui.html
```

Vous devriez voir la documentation Swagger de l'API.

---

## 2. Import de la Collection

### 2.1 Ouvrir Postman

Lancez Postman (téléchargez-le depuis https://www.postman.com/downloads/ si nécessaire)

### 2.2 Importer la Collection

1. Cliquez sur **Import** (en haut à gauche)
2. Sélectionnez **File**
3. Naviguez vers votre dossier de projet
4. Sélectionnez le fichier : `Big_Shop_Postman_Collection.json`
5. Cliquez sur **Import**

**Résultat :**
Vous verrez une nouvelle collection nommée **"Big Shop E-Commerce API"** avec 8 dossiers :
- 01. Authentication
- 02. Categories
- 03. Products
- 04. Addresses
- 05. Cart
- 06. Orders
- 07. User Profile
- 08. Admin Dashboard

### 2.3 Créer un Environnement

1. Cliquez sur **Environments** (à gauche)
2. Cliquez sur **Create Environment** ou **+**
3. Nommez-le : **Big Shop - Local**
4. Ajoutez les variables suivantes :

| Variable | Type | Initial Value | Current Value |
|----------|------|---------------|---------------|
| `baseUrl` | default | `http://localhost:8081` | `http://localhost:8081` |
| `token` | secret | | |
| `userId` | default | | |
| `productId` | default | | |
| `categoryId` | default | | |
| `orderId` | default | | |
| `addressId` | default | | |

5. Cliquez sur **Save**
6. Sélectionnez cet environnement dans le menu déroulant en haut à droite

---

## 3. Configuration de l'Environnement

L'environnement est maintenant actif. Les variables seront automatiquement remplies par les scripts de test lors de l'exécution des requêtes.

---

## 4. Scénario de Test Complet

### Phase 1 : Authentification et Création de Compte

#### Test 1.1 : Inscription d'un Utilisateur Normal

1. Ouvrez : **01. Authentication → Register User**
2. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **201 Created** ✅
- Body :
  ```json
  {
    "userId": 1,
    "username": "testuser",
    "email": "test@example.com",
    "message": "User registered successfully..."
  }
  ```
- La variable `userId` est automatiquement sauvegardée

**Scripts de Test Automatiques :**
- ✅ Status code is 201
- ✅ Response has userId
- ✅ Response has username and email

---

#### Test 1.2 : Inscription d'un Administrateur

1. Ouvrez : **01. Authentication → Register Admin User**
2. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **201 Created** ✅
- Un deuxième utilisateur est créé avec username "admin"

---

#### Test 1.3 : Connexion de l'Utilisateur Normal

1. Ouvrez : **01. Authentication → Login User**
2. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **200 OK** ✅
- Body :
  ```json
  {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
    "tokenType": "Bearer",
    "expiresIn": 86400000,
    "userId": 1,
    "username": "testuser",
    "email": "test@example.com",
    "roles": ["USER"]
  }
  ```
- La variable `token` est automatiquement sauvegardée ✅

**Vérification :**
Allez dans l'onglet **Environments** → **Big Shop - Local**
Vous devriez voir `token` rempli avec une longue chaîne JWT.

---

#### Test 1.4 : Vérifier le Token

Pour vérifier que le token fonctionne, essayons d'accéder à une route protégée :

1. Ouvrez : **07. User Profile → Get My Profile**
2. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **200 OK** ✅
- Vous voyez vos informations de profil

---

### Phase 2 : Gestion des Catégories

#### Test 2.1 : Se Connecter en Admin

Pour créer des catégories et des produits, vous devez être admin. Mais d'abord, assignez le rôle ADMIN à votre compte admin.

**IMPORTANT :** Par défaut, les nouveaux utilisateurs ont le rôle USER. Pour assigner le rôle ADMIN, vous devez le faire manuellement dans la base de données pour le premier admin.

```sql
-- Dans MySQL
USE big_shop;

-- Trouver l'ID du rôle ADMIN
SELECT * FROM roles WHERE name = 'ADMIN';
-- Résultat: id = 1 (par exemple)

-- Trouver l'ID de l'utilisateur admin
SELECT * FROM users WHERE username = 'admin';
-- Résultat: id = 2 (par exemple)

-- Assigner le rôle ADMIN
INSERT INTO user_roles (user_id, role_id) VALUES (2, 1);

-- Vérifier
SELECT u.username, r.name
FROM users u
JOIN user_roles ur ON u.id = ur.user_id
JOIN roles r ON ur.role_id = r.id
WHERE u.username = 'admin';
```

Maintenant, connectez-vous en tant qu'admin :

1. Ouvrez : **01. Authentication → Login Admin**
2. Cliquez sur **Send**
3. Le token admin est maintenant sauvegardé

---

#### Test 2.2 : Créer une Catégorie

1. Ouvrez : **02. Categories → Create Category**
2. Vérifiez le body :
   ```json
   {
     "name": "Smartphones",
     "description": "Latest smartphones and mobile devices",
     "parentId": null,
     "active": true
   }
   ```
3. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **201 Created** ✅
- Body contient `categoryId`
- La variable `categoryId` est automatiquement sauvegardée

---

#### Test 2.3 : Récupérer Toutes les Catégories

1. Ouvrez : **02. Categories → Get All Categories**
2. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **200 OK** ✅
- Liste paginée des catégories
- Vous voyez la catégorie "Smartphones" que vous venez de créer

---

#### Test 2.4 : Récupérer les Catégories Racines

1. Ouvrez : **02. Categories → Get Root Categories**
2. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **200 OK** ✅
- Liste des catégories sans parent (parentId = null)

---

### Phase 3 : Gestion des Produits

#### Test 3.1 : Créer un Produit

1. Ouvrez : **03. Products → Create Product**
2. Le body utilise automatiquement `{{categoryId}}` :
   ```json
   {
     "name": "iPhone 15 Pro",
     "description": "Latest Apple flagship smartphone...",
     "price": 1199.99,
     "discountPrice": 1099.99,
     "stockQuantity": 50,
     "categoryId": "{{categoryId}}",
     "active": true
   }
   ```
3. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **201 Created** ✅
- Body contient `id` du produit
- La variable `productId` est automatiquement sauvegardée

---

#### Test 3.2 : Récupérer Tous les Produits

1. Ouvrez : **03. Products → Get All Products**
2. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **200 OK** ✅
- Liste paginée des produits
- Votre iPhone 15 Pro apparaît

---

#### Test 3.3 : Récupérer un Produit par ID

1. Ouvrez : **03. Products → Get Product by ID**
2. L'URL utilise automatiquement `{{productId}}`
3. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **200 OK** ✅
- Détails complets du produit iPhone 15 Pro

---

#### Test 3.4 : Rechercher des Produits

1. Ouvrez : **03. Products → Search Products**
2. URL : `{{baseUrl}}/products/search?keyword=iphone`
3. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **200 OK** ✅
- Le produit iPhone 15 Pro apparaît dans les résultats

---

#### Test 3.5 : Produits en Promotion

1. Ouvrez : **03. Products → Get Products On Sale**
2. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **200 OK** ✅
- Tous les produits ayant un `discountPrice` (dont l'iPhone 15 Pro)

---

### Phase 4 : Gestion des Adresses

#### Test 4.1 : Revenir au Compte Utilisateur

Pour tester le panier et les commandes, revenons au compte utilisateur normal :

1. Ouvrez : **01. Authentication → Login User**
2. Cliquez sur **Send**
3. Le token utilisateur normal est maintenant actif

---

#### Test 4.2 : Créer une Adresse

1. Ouvrez : **04. Addresses → Create Address**
2. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **201 Created** ✅
- Body contient l'adresse créée avec `id`
- La variable `addressId` est automatiquement sauvegardée

---

#### Test 4.3 : Récupérer Mes Adresses

1. Ouvrez : **04. Addresses → Get My Addresses**
2. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **200 OK** ✅
- Liste de vos adresses
- L'adresse que vous venez de créer apparaît

---

#### Test 4.4 : Récupérer l'Adresse par Défaut

1. Ouvrez : **04. Addresses → Get Default Address**
2. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **200 OK** ✅
- L'adresse avec `isDefault: true`

---

### Phase 5 : Panier d'Achat

#### Test 5.1 : Récupérer Mon Panier (Vide)

1. Ouvrez : **05. Cart → Get Cart**
2. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **200 OK** ✅
- Panier vide ou panier créé automatiquement :
  ```json
  {
    "id": 1,
    "userId": 1,
    "items": [],
    "totalItems": 0,
    "totalAmount": 0.00
  }
  ```

---

#### Test 5.2 : Ajouter un Produit au Panier

1. Ouvrez : **05. Cart → Add Item to Cart**
2. Le body utilise automatiquement `{{productId}}` :
   ```json
   {
     "productId": "{{productId}}",
     "quantity": 2
   }
   ```
3. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **200 OK** ✅
- Body :
  ```json
  {
    "id": 1,
    "items": [
      {
        "id": 1,
        "productId": 1,
        "productName": "iPhone 15 Pro",
        "quantity": 2,
        "price": 1099.99,
        "subtotal": 2199.98
      }
    ],
    "totalAmount": 2199.98
  }
  ```

---

#### Test 5.3 : Mettre à Jour la Quantité

1. Ouvrez : **05. Cart → Update Cart Item Quantity**
2. Body :
   ```json
   {
     "quantity": 3
   }
   ```
3. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **200 OK** ✅
- Quantité mise à jour à 3
- Total = 3 × 1099.99 = 3299.97

---

#### Test 5.4 : Récupérer Mon Panier (Avec Articles)

1. Ouvrez : **05. Cart → Get Cart**
2. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **200 OK** ✅
- 3 iPhones dans le panier
- Total : 3299.97

---

### Phase 6 : Commandes

#### Test 6.1 : Créer une Commande

1. Ouvrez : **06. Orders → Create Order**
2. Le body utilise automatiquement `{{addressId}}` :
   ```json
   {
     "addressId": "{{addressId}}",
     "paymentMethod": "STRIPE",
     "notes": "Please deliver between 9AM-5PM"
   }
   ```
3. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **201 Created** ✅
- Body contient :
  ```json
  {
    "id": 1,
    "orderNumber": "ORD-2026-001234",
    "userId": 1,
    "status": "PENDING",
    "items": [
      {
        "productName": "iPhone 15 Pro",
        "quantity": 3,
        "price": 1099.99,
        "subtotal": 3299.97
      }
    ],
    "totalItems": 3,
    "totalAmount": 3299.97,
    "shippingAddress": {...},
    "createdAt": "2026-01-13T..."
  }
  ```
- La variable `orderId` est automatiquement sauvegardée
- Le panier est vidé automatiquement

---

#### Test 6.2 : Vérifier que le Panier est Vide

1. Ouvrez : **05. Cart → Get Cart**
2. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **200 OK** ✅
- Panier vide (items = [])

---

#### Test 6.3 : Récupérer la Commande par ID

1. Ouvrez : **06. Orders → Get Order by ID**
2. L'URL utilise `{{orderId}}`
3. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **200 OK** ✅
- Détails complets de la commande

---

#### Test 6.4 : Récupérer Mes Commandes

1. Ouvrez : **06. Orders → Get My Orders**
2. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **200 OK** ✅
- Liste paginée de vos commandes
- Votre commande apparaît

---

### Phase 7 : Gestion Admin des Commandes

#### Test 7.1 : Se Connecter en Admin

1. Ouvrez : **01. Authentication → Login Admin**
2. Cliquez sur **Send**

---

#### Test 7.2 : Récupérer Toutes les Commandes (ADMIN)

1. Ouvrez : **06. Orders → Get All Orders (ADMIN)**
2. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **200 OK** ✅
- Toutes les commandes de tous les utilisateurs

---

#### Test 7.3 : Confirmer la Commande (ADMIN)

1. Ouvrez : **06. Orders → Confirm Order (ADMIN)**
2. Body :
   ```json
   {
     "notes": "Payment verified, ready to ship"
   }
   ```
3. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **200 OK** ✅
- Status de la commande passe à "CONFIRMED"

---

#### Test 7.4 : Expédier la Commande (ADMIN)

1. Ouvrez : **06. Orders → Ship Order (ADMIN)**
2. Body :
   ```json
   {
     "trackingNumber": "TRACK123456789",
     "carrier": "DHL Express"
   }
   ```
3. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **200 OK** ✅
- Status de la commande passe à "SHIPPED"

---

#### Test 7.5 : Marquer comme Livrée (ADMIN)

1. Ouvrez : **06. Orders → Deliver Order (ADMIN)**
2. Body :
   ```json
   {
     "deliveredAt": "2026-01-15T14:30:00Z",
     "receivedBy": "Test User"
   }
   ```
3. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **200 OK** ✅
- Status de la commande passe à "DELIVERED"

---

### Phase 8 : Dashboard Admin

#### Test 8.1 : Statistiques du Dashboard

1. Ouvrez : **08. Admin Dashboard → Get Dashboard Stats (ADMIN)**
2. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **200 OK** ✅
- Statistiques globales :
  ```json
  {
    "totalUsers": 2,
    "totalOrders": 1,
    "totalRevenue": 3299.97,
    "pendingOrders": 0,
    "todayOrders": 1,
    "todayRevenue": 3299.97,
    "lowStockProducts": 0
  }
  ```

---

#### Test 8.2 : Statistiques de Revenus

1. Ouvrez : **08. Admin Dashboard → Get Revenue Stats (ADMIN)**
2. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **200 OK** ✅
- Statistiques de revenus avec breakdown quotidien

---

#### Test 8.3 : Produits les Plus Vendus

1. Ouvrez : **08. Admin Dashboard → Get Top Products (ADMIN)**
2. Cliquez sur **Send**

**Résultat Attendu :**
- Status : **200 OK** ✅
- Liste des produits les plus vendus (iPhone 15 Pro en tête)

---

## 5. Tests Automatisés

### 5.1 Runner de Collection

Pour exécuter tous les tests automatiquement :

1. Cliquez sur la collection **Big Shop E-Commerce API**
2. Cliquez sur **Run** (ou les trois points → **Run collection**)
3. Sélectionnez toutes les requêtes
4. Cliquez sur **Run Big Shop E-Commerce API**

Postman exécutera toutes les requêtes dans l'ordre et affichera les résultats des tests.

### 5.2 Scripts de Test Inclus

Chaque requête importante contient des tests automatiques :

**Exemple - Register User :**
```javascript
pm.test("Status code is 201", function () {
    pm.response.to.have.status(201);
});

pm.test("Response has userId", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData.userId).to.exist;
    pm.environment.set("userId", jsonData.userId);
});
```

Ces tests vérifient :
- ✅ Le code de statut HTTP
- ✅ La présence des champs requis
- ✅ La sauvegarde automatique des variables

---

## 6. Résolution des Problèmes

### Problème 1 : Erreur 401 Unauthorized

**Cause :** Token JWT expiré ou invalide

**Solution :**
1. Reconnectez-vous avec **Login User** ou **Login Admin**
2. Le nouveau token sera automatiquement sauvegardé

---

### Problème 2 : Erreur 403 Forbidden

**Cause :** Vous n'avez pas les permissions nécessaires (ex: essayer une action ADMIN avec un compte USER)

**Solution :**
1. Vérifiez que vous êtes connecté avec le bon compte
2. Pour les actions ADMIN, utilisez **Login Admin**

---

### Problème 3 : Erreur 404 Not Found avec {{productId}} ou {{categoryId}}

**Cause :** Les variables d'environnement ne sont pas définies

**Solution :**
1. Vérifiez que l'environnement **Big Shop - Local** est sélectionné
2. Exécutez d'abord les requêtes qui créent ces ressources (Create Category, Create Product)
3. Les scripts de test sauvegarderont automatiquement les IDs

---

### Problème 4 : Erreur 500 Internal Server Error

**Cause :** Erreur serveur (base de données, code)

**Solution :**
1. Vérifiez les logs de l'application Spring Boot
2. Vérifiez que la base de données est correctement configurée
3. Assurez-vous que le script `database_fix_script.sql` a été exécuté

---

### Problème 5 : Cannot connect to localhost:8081

**Cause :** L'application Spring Boot n'est pas démarrée

**Solution :**
```bash
cd "C:\Users\alain\OneDrive\Documents\X4(ESIEA)\architecture application\Backend\big_shop_backend"
mvn spring-boot:run
```

---

## 7. Ordre Recommandé pour les Tests Manuels

Si vous voulez tester manuellement étape par étape, suivez cet ordre :

### Ordre Minimum (Test Rapide - 10 requêtes)

1. **Register User** → Créer compte
2. **Login User** → Se connecter
3. **Login Admin** → (Après avoir assigné le rôle dans MySQL)
4. **Create Category** → Créer catégorie
5. **Create Product** → Créer produit
6. **Login User** → Revenir au compte user
7. **Create Address** → Créer adresse
8. **Add Item to Cart** → Ajouter au panier
9. **Create Order** → Créer commande
10. **Get My Orders** → Vérifier la commande

### Ordre Complet (Test Exhaustif - Toutes les requêtes)

Suivez l'ordre du **Scénario de Test Complet** (Phase 1 à 8) ci-dessus.

---

## 8. Variables d'Environnement Expliquées

| Variable | Sauvegardée Par | Utilisée Par |
|----------|----------------|--------------|
| `token` | Login User / Login Admin | Toutes les requêtes authentifiées |
| `userId` | Register User / Login User | (Info, pas utilisé dans les URLs) |
| `categoryId` | Create Category | Create Product, Get Products by Category |
| `productId` | Create Product | Add to Cart, Update Cart, Remove from Cart |
| `addressId` | Create Address | Create Order |
| `orderId` | Create Order | Get Order, Cancel Order, Confirm Order, etc. |

---

## 9. Conseils Pratiques

### Astuce 1 : Voir les Variables Sauvegardées

Après chaque requête qui sauvegarde une variable, cliquez sur l'icône "œil" en haut à droite pour voir les variables d'environnement actuelles.

### Astuce 2 : Dupliquer les Requêtes

Pour tester avec différentes données, dupliquez une requête :
- Clic droit sur la requête → **Duplicate**
- Modifiez les données du body
- Renommez la requête

### Astuce 3 : Sauvegarder les Réponses

Postman sauvegarde automatiquement les dernières réponses. Cliquez sur **Save Response** pour les garder comme exemples.

### Astuce 4 : Console Postman

Ouvrez la console Postman (View → Show Postman Console) pour voir :
- Toutes les requêtes envoyées
- Les headers complets
- Les erreurs détaillées

---

## 10. Checklist de Validation

Utilisez cette checklist pour valider que tout fonctionne :

### Authentification
- [ ] Inscription utilisateur normal fonctionne
- [ ] Inscription admin fonctionne
- [ ] Login utilisateur fonctionne
- [ ] Login admin fonctionne
- [ ] Token JWT est sauvegardé
- [ ] Accès aux routes protégées avec token fonctionne

### Catégories
- [ ] Création de catégorie (ADMIN) fonctionne
- [ ] Récupération de toutes les catégories fonctionne
- [ ] Récupération des catégories racines fonctionne
- [ ] CategoryId est sauvegardé

### Produits
- [ ] Création de produit (ADMIN) fonctionne
- [ ] Récupération de tous les produits fonctionne
- [ ] Recherche de produits fonctionne
- [ ] Produits en promotion visibles
- [ ] ProductId est sauvegardé

### Adresses
- [ ] Création d'adresse fonctionne
- [ ] Récupération des adresses fonctionne
- [ ] AddressId est sauvegardé

### Panier
- [ ] Récupération du panier fonctionne
- [ ] Ajout au panier fonctionne
- [ ] Mise à jour de quantité fonctionne
- [ ] Total calculé correctement

### Commandes
- [ ] Création de commande fonctionne
- [ ] Panier vidé après commande
- [ ] Récupération de mes commandes fonctionne
- [ ] OrderId est sauvegardé
- [ ] Confirmation par admin fonctionne
- [ ] Expédition par admin fonctionne
- [ ] Livraison par admin fonctionne

### Dashboard Admin
- [ ] Statistiques dashboard accessibles (ADMIN)
- [ ] Statistiques de revenus accessibles (ADMIN)
- [ ] Top produits accessible (ADMIN)

---

## 11. Support et Documentation

### Ressources

- **Swagger UI :** http://localhost:8081/swagger-ui.html
- **Guide MySQL :** `MYSQL_DATABASE_GUIDE.md`
- **Guide Postman Détaillé :** `POSTMAN_TESTING_GUIDE.md`
- **Instructions de Correction DB :** `DATABASE_FIX_INSTRUCTIONS.md`

### En Cas de Problème

1. Vérifiez les logs de Spring Boot
2. Vérifiez la console Postman
3. Vérifiez que l'environnement est sélectionné
4. Vérifiez que le token est valide (reconnectez-vous)
5. Consultez le guide de dépannage ci-dessus

---

**Bon Test ! 🚀**

Dernière mise à jour : 2026-01-13
