# Tests Postman - Big Shop API

**Base URL:** `http://localhost:8081`

---

## 1. AUTHENTICATION

### 1.1 Inscription
```
POST /auth/register
Content-Type: application/json

Body:
{
  "username": "testuser",
  "email": "test@example.com",
  "password": "Test123!",
  "firstName": "Test",
  "lastName": "User"
}

Résultat: 201 Created
```

### 1.2 Connexion
```
POST /auth/login
Content-Type: application/json

Body:
{
  "usernameOrEmail": "testuser",
  "password": "Test123!"
}

Résultat: 200 OK
Récupérer le "accessToken" pour les requêtes suivantes
```

### 1.3 Refresh Token
```
POST /auth/refresh-token
Authorization: Bearer {token}

Résultat: 200 OK
```

### 1.4 Déconnexion
```
POST /auth/logout
Authorization: Bearer {token}

Résultat: 200 OK
```

### 1.5 Mot de passe oublié
```
POST /auth/forgot-password
Content-Type: application/json

Body:
{
  "email": "test@example.com"
}

Résultat: 200 OK
```

### 1.6 Réinitialiser mot de passe
```
POST /auth/reset-password
Content-Type: application/json

Body:
{
  "token": "reset-token-from-email",
  "newPassword": "NewPass123!"
}

Résultat: 200 OK
```

### 1.7 Vérifier email
```
GET /auth/verify-email?token=verification-token

Résultat: 200 OK
```

### 1.8 Renvoyer email de vérification
```
POST /auth/resend-verification
Content-Type: application/json

Body:
{
  "email": "test@example.com"
}

Résultat: 200 OK
```

---

## 2. CATEGORIES

### 2.1 Créer une catégorie (ADMIN)
```
POST /categories
Authorization: Bearer {token}
Content-Type: application/json

Body:
{
  "name": "Smartphones",
  "description": "Latest smartphones",
  "parentId": null,
  "active": true
}

Résultat: 201 Created
Récupérer le "categoryId"
```

### 2.2 Récupérer toutes les catégories
```
GET /categories?page=0&size=20

Résultat: 200 OK
```

### 2.3 Récupérer une catégorie par ID
```
GET /categories/{categoryId}

Résultat: 200 OK
```

### 2.4 Récupérer les catégories racines
```
GET /categories/root

Résultat: 200 OK
```

### 2.5 Récupérer les sous-catégories
```
GET /categories/{categoryId}/subcategories

Résultat: 200 OK
```

### 2.6 Mettre à jour une catégorie (ADMIN)
```
PUT /categories/{categoryId}
Authorization: Bearer {token}
Content-Type: application/json

Body:
{
  "name": "Smartphones Updated",
  "description": "Updated description",
  "active": true
}

Résultat: 200 OK
```

### 2.7 Supprimer une catégorie (ADMIN)
```
DELETE /categories/{categoryId}
Authorization: Bearer {token}

Résultat: 204 No Content
```

---

## 3. PRODUCTS

### 3.1 Créer un produit (ADMIN)
```
POST /products
Authorization: Bearer {token}
Content-Type: application/json

Body:
{
  "name": "iPhone 15 Pro",
  "description": "Latest Apple flagship",
  "price": 1199.99,
  "discountPrice": 1099.99,
  "stockQuantity": 50,
  "categoryId": 1,
  "active": true
}

Résultat: 201 Created
Récupérer le "id" (productId)
```

### 3.2 Récupérer tous les produits
```
GET /products?page=0&size=10

Résultat: 200 OK
```

### 3.3 Récupérer un produit par ID
```
GET /products/{productId}

Résultat: 200 OK
```

### 3.4 Rechercher des produits
```
GET /products/search?keyword=iphone&page=0&size=10

Résultat: 200 OK
```

### 3.5 Produits par catégorie
```
GET /products/category/{categoryId}?page=0&size=10

Résultat: 200 OK
```

### 3.6 Produits actifs
```
GET /products/active?page=0&size=10

Résultat: 200 OK
```

### 3.7 Produits en promotion
```
GET /products/on-sale?page=0&size=10

Résultat: 200 OK
```

### 3.8 Nouveaux produits
```
GET /products/new?page=0&size=10

Résultat: 200 OK
```

### 3.9 Mettre à jour un produit (ADMIN)
```
PUT /products/{productId}
Authorization: Bearer {token}
Content-Type: application/json

Body:
{
  "name": "iPhone 15 Pro Max",
  "description": "Updated",
  "price": 1299.99,
  "discountPrice": 1199.99,
  "stockQuantity": 45,
  "categoryId": 1,
  "active": true
}

Résultat: 200 OK
```

### 3.10 Supprimer un produit (ADMIN)
```
DELETE /products/{productId}
Authorization: Bearer {token}

Résultat: 204 No Content
```

---

## 4. CART

### 4.1 Récupérer mon panier
```
GET /cart
Authorization: Bearer {token}

Résultat: 200 OK
```

### 4.2 Ajouter un article au panier
```
POST /cart/items
Authorization: Bearer {token}
Content-Type: application/json

Body:
{
  "productId": 1,
  "quantity": 2
}

Résultat: 200 OK
```

### 4.3 Mettre à jour la quantité
```
PUT /cart/items/{productId}
Authorization: Bearer {token}
Content-Type: application/json

Body:
{
  "quantity": 3
}

Résultat: 200 OK
```

### 4.4 Supprimer un article
```
DELETE /cart/items/{productId}
Authorization: Bearer {token}

Résultat: 200 OK
```

### 4.5 Vider le panier
```
DELETE /cart
Authorization: Bearer {token}

Résultat: 200 OK
```

---

## 5. ADDRESSES

### 5.1 Créer une adresse
```
POST /addresses
Authorization: Bearer {token}
Content-Type: application/json

Body:
{
  "fullName": "John Doe",
  "phone": "+33612345678",
  "street": "123 Rue de Rivoli",
  "city": "Paris",
  "zipCode": "75001",
  "country": "France",
  "isDefault": true
}

Résultat: 201 Created
Récupérer le "id" (addressId)
```

### 5.2 Récupérer mes adresses
```
GET /addresses
Authorization: Bearer {token}

Résultat: 200 OK
```

### 5.3 Récupérer une adresse par ID
```
GET /addresses/{addressId}
Authorization: Bearer {token}

Résultat: 200 OK
```

### 5.4 Récupérer l'adresse par défaut
```
GET /addresses/default
Authorization: Bearer {token}

Résultat: 200 OK
```

### 5.5 Mettre à jour une adresse
```
PUT /addresses/{addressId}
Authorization: Bearer {token}
Content-Type: application/json

Body:
{
  "fullName": "John Doe",
  "phone": "+33698765432",
  "street": "456 Avenue des Champs",
  "city": "Paris",
  "zipCode": "75008",
  "country": "France",
  "isDefault": true
}

Résultat: 200 OK
```

### 5.6 Définir comme adresse par défaut
```
PUT /addresses/{addressId}/default
Authorization: Bearer {token}

Résultat: 200 OK
```

### 5.7 Supprimer une adresse
```
DELETE /addresses/{addressId}
Authorization: Bearer {token}

Résultat: 204 No Content
```

---

## 6. ORDERS

### 6.1 Créer une commande
```
POST /orders
Authorization: Bearer {token}
Content-Type: application/json

Body:
{
  "addressId": 1,
  "paymentMethod": "STRIPE",
  "notes": "Deliver between 9AM-5PM"
}

Résultat: 201 Created
Récupérer le "id" (orderId)
```

### 6.2 Récupérer une commande par ID
```
GET /orders/{orderId}
Authorization: Bearer {token}

Résultat: 200 OK
```

### 6.3 Récupérer mes commandes
```
GET /orders/my-orders?page=0&size=10
Authorization: Bearer {token}

Résultat: 200 OK
```

### 6.4 Récupérer toutes les commandes (ADMIN)
```
GET /orders?page=0&size=20&status=PENDING
Authorization: Bearer {token}

Résultat: 200 OK
```

### 6.5 Annuler une commande
```
PUT /orders/{orderId}/cancel
Authorization: Bearer {token}
Content-Type: application/json

Body:
{
  "reason": "Changed my mind"
}

Résultat: 200 OK
```

### 6.6 Confirmer une commande (ADMIN)
```
PUT /orders/{orderId}/confirm
Authorization: Bearer {token}
Content-Type: application/json

Body:
{
  "notes": "Payment verified"
}

Résultat: 200 OK
```

### 6.7 Expédier une commande (ADMIN)
```
PUT /orders/{orderId}/ship
Authorization: Bearer {token}
Content-Type: application/json

Body:
{
  "trackingNumber": "TRACK123456789",
  "carrier": "DHL Express"
}

Résultat: 200 OK
```

### 6.8 Marquer comme livrée (ADMIN)
```
PUT /orders/{orderId}/deliver
Authorization: Bearer {token}
Content-Type: application/json

Body:
{
  "deliveredAt": "2026-01-15T14:30:00Z",
  "receivedBy": "John Doe"
}

Résultat: 200 OK
```

---

## 7. PAYMENTS

### 7.1 Créer un Payment Intent Stripe
```
POST /payment/intent
Authorization: Bearer {token}
Content-Type: application/json

Body:
{
  "orderId": 1,
  "amount": 1099.99,
  "currency": "eur"
}

Résultat: 200 OK
Récupérer le "paymentIntentId"
```

### 7.2 Confirmer un paiement Stripe
```
POST /payment/confirm/{paymentIntentId}
Authorization: Bearer {token}
Content-Type: application/json

Body:
{
  "paymentMethodId": "pm_card_visa"
}

Résultat: 200 OK
```

### 7.3 Récupérer le paiement d'une commande
```
GET /payment/order/{orderId}
Authorization: Bearer {token}

Résultat: 200 OK
```

### 7.4 Rembourser un paiement (ADMIN)
```
POST /payment/refund/{paymentId}
Authorization: Bearer {token}
Content-Type: application/json

Body:
{
  "amount": 1099.99,
  "reason": "Product defective"
}

Résultat: 200 OK
```

### 7.5 Paiement MTN Mobile Money
```
POST /mobile-payment/mtn
Authorization: Bearer {token}
Content-Type: application/json

Body:
{
  "orderId": 1,
  "amount": 1099.99,
  "currency": "XAF",
  "payerMsisdn": "+237612345678",
  "payerReference": "Customer payment"
}

Résultat: 200 OK
```

### 7.6 Paiement Orange Money
```
POST /mobile-payment/orange
Authorization: Bearer {token}
Content-Type: application/json

Body:
{
  "orderId": 1,
  "amount": 1099.99,
  "currency": "XOF",
  "payerMsisdn": "+225012345678",
  "payerReference": "Customer payment"
}

Résultat: 200 OK
```

---

## 8. USER PROFILE

### 8.1 Récupérer mon profil
```
GET /users/profile
Authorization: Bearer {token}

Résultat: 200 OK
```

### 8.2 Mettre à jour mon profil
```
PUT /users/profile
Authorization: Bearer {token}
Content-Type: application/json

Body:
{
  "firstName": "Updated",
  "lastName": "Name",
  "phoneNumber": "+33698765432"
}

Résultat: 200 OK
```

---

## 9. ROLES (ADMIN)

### 9.1 Assigner un rôle (ADMIN)
```
POST /roles/assign
Authorization: Bearer {token}
Content-Type: application/json

Body:
{
  "userId": 2,
  "roleName": "ADMIN"
}

Résultat: 200 OK
```

---

## 10. ADMIN DASHBOARD (ADMIN)

### 10.1 Statistiques du dashboard
```
GET /dashboard/stats
Authorization: Bearer {token}

Résultat: 200 OK
```

### 10.2 Statistiques de revenus
```
GET /admin/revenue?startDate=2026-01-01&endDate=2026-01-31
Authorization: Bearer {token}

Résultat: 200 OK
```

### 10.3 Produits les plus vendus
```
GET /admin/top-products?limit=10
Authorization: Bearer {token}

Résultat: 200 OK
```

### 10.4 Croissance des utilisateurs
```
GET /admin/user-growth?period=MONTHLY&year=2026
Authorization: Bearer {token}

Résultat: 200 OK
```

### 10.5 Produits en rupture de stock
```
GET /admin/low-stock?threshold=10
Authorization: Bearer {token}

Résultat: 200 OK
```

---

## 11. FILES

### 11.1 Upload avatar
```
POST /files/upload/avatar
Authorization: Bearer {token}
Content-Type: multipart/form-data

Body (form-data):
file: [Select Image File]

Résultat: 200 OK
```

### 11.2 Upload image produit (ADMIN)
```
POST /files/upload/product
Authorization: Bearer {token}
Content-Type: multipart/form-data

Body (form-data):
file: [Select Image File]

Résultat: 200 OK
```

### 11.3 Upload fichier général (ADMIN)
```
POST /files/upload
Authorization: Bearer {token}
Content-Type: multipart/form-data

Body (form-data):
file: [Select File]

Résultat: 200 OK
```

### 11.4 Télécharger un fichier
```
GET /files/download/{directory}/{filename}

Résultat: File Download
```

### 11.5 Supprimer un fichier (ADMIN)
```
DELETE /files/{directory}/{filename}
Authorization: Bearer {token}

Résultat: 204 No Content
```

---

## NOTES IMPORTANTES

### Headers pour toutes les requêtes authentifiées:
```
Authorization: Bearer {votre_token_jwt}
Content-Type: application/json
```

### Obtenir le token:
1. S'inscrire avec `/auth/register`
2. Se connecter avec `/auth/login`
3. Récupérer le `accessToken` dans la réponse
4. Utiliser ce token dans le header `Authorization: Bearer {token}`

### Routes ADMIN:
Pour utiliser les routes ADMIN, vous devez:
1. S'inscrire
2. Assigner le rôle ADMIN dans MySQL:
```sql
INSERT INTO user_roles (user_id, role_id) VALUES ({votre_user_id}, 1);
```
3. Se reconnecter pour obtenir un nouveau token avec le rôle ADMIN

### Codes de statut HTTP:
- 200 OK - Requête réussie
- 201 Created - Ressource créée
- 204 No Content - Suppression réussie
- 400 Bad Request - Erreur de validation
- 401 Unauthorized - Token manquant/invalide
- 403 Forbidden - Permissions insuffisantes
- 404 Not Found - Ressource non trouvée
- 500 Internal Server Error - Erreur serveur
