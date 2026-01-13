# Guide de Test des APIs avec Postman - Big Shop Backend

## Table des Matières
1. [Configuration de l'Environnement Postman](#1-configuration-de-lenvironnement-postman)
2. [Authentification](#2-authentification)
3. [Gestion des Produits](#3-gestion-des-produits)
4. [Gestion des Catégories](#4-gestion-des-catégories)
5. [Gestion du Panier](#5-gestion-du-panier)
6. [Gestion des Commandes](#6-gestion-des-commandes)
7. [Gestion des Paiements](#7-gestion-des-paiements)
8. [Gestion des Adresses](#8-gestion-des-adresses)
9. [Gestion des Utilisateurs](#9-gestion-des-utilisateurs)
10. [Administration](#10-administration)
11. [Upload de Fichiers](#11-upload-de-fichiers)

---

## 1. Configuration de l'Environnement Postman

### Création de l'Environnement

1. Dans Postman, créez un nouvel environnement nommé "Big Shop - Local"
2. Ajoutez les variables suivantes :

| Variable | Valeur Initiale | Valeur Actuelle |
|----------|----------------|-----------------|
| `baseUrl` | `http://localhost:8081` | |
| `token` | | (sera rempli automatiquement après login) |
| `userId` | | (sera rempli automatiquement après login) |
| `productId` | | (pour les tests) |
| `categoryId` | | (pour les tests) |
| `orderId` | | (pour les tests) |
| `addressId` | | (pour les tests) |

### Headers Globaux

Pour toutes les requêtes authentifiées, ajoutez dans l'en-tête :
```
Authorization: Bearer {{token}}
Content-Type: application/json
```

---

## 2. Authentification

### 2.1 Inscription d'un Utilisateur

**Endpoint:** `POST {{baseUrl}}/auth/register`

**Headers:**
```
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "username": "johndoe",
  "email": "john.doe@example.com",
  "password": "SecurePass123!",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+33612345678"
}
```

**Réponse Attendue (201 Created):**
```json
{
  "userId": "123e4567-e89b-12d3-a456-426614174000",
  "username": "johndoe",
  "email": "john.doe@example.com",
  "message": "User registered successfully. Please verify your email."
}
```

**Script Post-Request (Test):**
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

---

### 2.2 Connexion (Login)

**Endpoint:** `POST {{baseUrl}}/auth/login`

**Body (JSON):**
```json
{
  "usernameOrEmail": "johndoe",
  "password": "SecurePass123!"
}
```

**Réponse Attendue (200 OK):**
```json
{
  "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
  "tokenType": "Bearer",
  "expiresIn": 86400000,
  "userId": "123e4567-e89b-12d3-a456-426614174000",
  "username": "johndoe",
  "email": "john.doe@example.com",
  "roles": ["USER"]
}
```

**Script Post-Request:**
```javascript
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Token is present", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData.accessToken).to.exist;
    pm.environment.set("token", jsonData.accessToken);
    pm.environment.set("userId", jsonData.userId);
});
```

---

### 2.3 Mot de Passe Oublié

**Endpoint:** `POST {{baseUrl}}/auth/forgot-password`

**Body (JSON):**
```json
{
  "email": "john.doe@example.com"
}
```

**Réponse Attendue (200 OK):**
```json
{
  "message": "Password reset link sent to your email."
}
```

---

### 2.4 Réinitialisation du Mot de Passe

**Endpoint:** `POST {{baseUrl}}/auth/reset-password`

**Body (JSON):**
```json
{
  "token": "reset-token-from-email",
  "newPassword": "NewSecurePass456!"
}
```

**Réponse Attendue (200 OK):**
```json
{
  "message": "Password reset successfully."
}
```

---

### 2.5 Vérification Email

**Endpoint:** `GET {{baseUrl}}/auth/verify-email?token=verification-token-from-email`

**Réponse Attendue (200 OK):**
```json
{
  "message": "Email verified successfully."
}
```

---

### 2.6 Rafraîchir le Token

**Endpoint:** `POST {{baseUrl}}/auth/refresh-token`

**Headers:**
```
Authorization: Bearer {{token}}
```

**Réponse Attendue (200 OK):**
```json
{
  "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
  "tokenType": "Bearer",
  "expiresIn": 86400000
}
```

---

### 2.7 Déconnexion (Logout)

**Endpoint:** `POST {{baseUrl}}/auth/logout`

**Headers:**
```
Authorization: Bearer {{token}}
```

**Réponse Attendue (200 OK):**
```json
{
  "message": "Logged out successfully."
}
```

---

## 3. Gestion des Produits

### 3.1 Créer un Produit (ADMIN uniquement)

**Endpoint:** `POST {{baseUrl}}/products`

**Headers:**
```
Authorization: Bearer {{token}}
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "name": "iPhone 15 Pro",
  "description": "Latest Apple flagship smartphone with A17 Pro chip",
  "price": 1199.99,
  "discountPrice": 1099.99,
  "stockQuantity": 50,
  "categoryId": "{{categoryId}}",
  "active": true
}
```

**Réponse Attendue (201 Created):**
```json
{
  "id": "prod-123e4567",
  "name": "iPhone 15 Pro",
  "description": "Latest Apple flagship smartphone with A17 Pro chip",
  "price": 1199.99,
  "discountPrice": 1099.99,
  "stockQuantity": 50,
  "categoryId": "cat-789",
  "active": true,
  "images": []
}
```

**Script Post-Request:**
```javascript
pm.test("Status code is 201", function () {
    pm.response.to.have.status(201);
});

var jsonData = pm.response.json();
pm.environment.set("productId", jsonData.id);
```

---

### 3.2 Récupérer Tous les Produits

**Endpoint:** `GET {{baseUrl}}/products?page=0&size=10&sort=name,asc`

**Réponse Attendue (200 OK):**
```json
{
  "content": [
    {
      "id": "prod-123",
      "name": "iPhone 15 Pro",
      "description": "Latest Apple flagship smartphone",
      "price": 1199.99,
      "discountPrice": 1099.99,
      "stockQuantity": 50,
      "categoryId": "cat-789",
      "active": true,
      "images": []
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 25,
  "totalPages": 3
}
```

---

### 3.3 Récupérer un Produit par ID

**Endpoint:** `GET {{baseUrl}}/products/{{productId}}`

**Réponse Attendue (200 OK):**
```json
{
  "id": "prod-123",
  "name": "iPhone 15 Pro",
  "description": "Latest Apple flagship smartphone",
  "price": 1199.99,
  "discountPrice": 1099.99,
  "stockQuantity": 50,
  "categoryId": "cat-789",
  "categoryName": "Smartphones",
  "active": true,
  "images": [
    {
      "id": "img-1",
      "imageUrl": "/files/download/products/iphone15pro.jpg",
      "displayOrder": 1
    }
  ]
}
```

---

### 3.4 Rechercher des Produits

**Endpoint:** `GET {{baseUrl}}/products/search?keyword=iphone&page=0&size=10`

**Réponse Attendue (200 OK):**
```json
{
  "content": [
    {
      "id": "prod-123",
      "name": "iPhone 15 Pro",
      "price": 1199.99
    }
  ],
  "totalElements": 3
}
```

---

### 3.5 Produits par Catégorie

**Endpoint:** `GET {{baseUrl}}/products/category/{{categoryId}}?page=0&size=10`

---

### 3.6 Produits Actifs

**Endpoint:** `GET {{baseUrl}}/products/active?page=0&size=10`

---

### 3.7 Produits en Promotion

**Endpoint:** `GET {{baseUrl}}/products/on-sale?page=0&size=10`

---

### 3.8 Nouveaux Produits

**Endpoint:** `GET {{baseUrl}}/products/new?page=0&size=10`

---

### 3.9 Mettre à Jour un Produit (ADMIN)

**Endpoint:** `PUT {{baseUrl}}/products/{{productId}}`

**Headers:**
```
Authorization: Bearer {{token}}
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "name": "iPhone 15 Pro Max",
  "description": "Updated description",
  "price": 1299.99,
  "discountPrice": 1199.99,
  "stockQuantity": 45,
  "categoryId": "{{categoryId}}",
  "active": true
}
```

---

### 3.10 Supprimer un Produit (ADMIN)

**Endpoint:** `DELETE {{baseUrl}}/products/{{productId}}`

**Headers:**
```
Authorization: Bearer {{token}}
```

**Réponse Attendue (204 No Content)**

---

## 4. Gestion des Catégories

### 4.1 Créer une Catégorie (ADMIN)

**Endpoint:** `POST {{baseUrl}}/categories`

**Headers:**
```
Authorization: Bearer {{token}}
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "name": "Electronics",
  "description": "Electronic devices and accessories",
  "parentId": null,
  "active": true
}
```

**Réponse Attendue (201 Created):**
```json
{
  "categoryId": "cat-123",
  "name": "Electronics",
  "description": "Electronic devices and accessories",
  "parentId": null,
  "active": true,
  "createdAt": "2026-01-13T10:00:00Z"
}
```

**Script Post-Request:**
```javascript
var jsonData = pm.response.json();
pm.environment.set("categoryId", jsonData.categoryId);
```

---

### 4.2 Créer une Sous-Catégorie

**Body (JSON):**
```json
{
  "name": "Smartphones",
  "description": "Mobile phones and smartphones",
  "parentId": "{{categoryId}}",
  "active": true
}
```

---

### 4.3 Récupérer Toutes les Catégories

**Endpoint:** `GET {{baseUrl}}/categories?page=0&size=20`

---

### 4.4 Récupérer une Catégorie par ID

**Endpoint:** `GET {{baseUrl}}/categories/{{categoryId}}`

---

### 4.5 Récupérer les Catégories Racines

**Endpoint:** `GET {{baseUrl}}/categories/root`

**Réponse Attendue (200 OK):**
```json
[
  {
    "categoryId": "cat-1",
    "name": "Electronics",
    "description": "Electronic devices",
    "parentId": null,
    "active": true
  },
  {
    "categoryId": "cat-2",
    "name": "Clothing",
    "description": "Fashion and apparel",
    "parentId": null,
    "active": true
  }
]
```

---

### 4.6 Récupérer les Sous-Catégories

**Endpoint:** `GET {{baseUrl}}/categories/{{categoryId}}/subcategories`

---

### 4.7 Mettre à Jour une Catégorie (ADMIN)

**Endpoint:** `PUT {{baseUrl}}/categories/{{categoryId}}`

**Body (JSON):**
```json
{
  "name": "Consumer Electronics",
  "description": "Updated description",
  "active": true
}
```

---

### 4.8 Supprimer une Catégorie (ADMIN)

**Endpoint:** `DELETE {{baseUrl}}/categories/{{categoryId}}`

---

## 5. Gestion du Panier

### 5.1 Récupérer le Panier

**Endpoint:** `GET {{baseUrl}}/cart`

**Headers:**
```
Authorization: Bearer {{token}}
```

**Réponse Attendue (200 OK):**
```json
{
  "id": "cart-123",
  "userId": "user-456",
  "items": [
    {
      "id": "item-1",
      "productId": "prod-789",
      "productName": "iPhone 15 Pro",
      "quantity": 2,
      "price": 1099.99,
      "subtotal": 2199.98
    }
  ],
  "totalItems": 2,
  "totalAmount": 2199.98
}
```

---

### 5.2 Ajouter un Article au Panier

**Endpoint:** `POST {{baseUrl}}/cart/items`

**Headers:**
```
Authorization: Bearer {{token}}
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "productId": "{{productId}}",
  "quantity": 1
}
```

**Réponse Attendue (200 OK):**
```json
{
  "id": "cart-123",
  "items": [
    {
      "id": "item-1",
      "productId": "prod-789",
      "productName": "iPhone 15 Pro",
      "quantity": 1,
      "price": 1099.99
    }
  ],
  "totalAmount": 1099.99
}
```

---

### 5.3 Mettre à Jour la Quantité d'un Article

**Endpoint:** `PUT {{baseUrl}}/cart/items/{{productId}}`

**Headers:**
```
Authorization: Bearer {{token}}
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "quantity": 3
}
```

---

### 5.4 Supprimer un Article du Panier

**Endpoint:** `DELETE {{baseUrl}}/cart/items/{{productId}}`

**Headers:**
```
Authorization: Bearer {{token}}
```

---

### 5.5 Vider le Panier

**Endpoint:** `DELETE {{baseUrl}}/cart`

**Headers:**
```
Authorization: Bearer {{token}}
```

---

## 6. Gestion des Commandes

### 6.1 Créer une Commande

**Endpoint:** `POST {{baseUrl}}/orders`

**Headers:**
```
Authorization: Bearer {{token}}
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "addressId": "{{addressId}}",
  "paymentMethod": "STRIPE",
  "notes": "Please deliver between 9AM-5PM"
}
```

**Réponse Attendue (201 Created):**
```json
{
  "id": "order-123",
  "orderNumber": "ORD-2026-001234",
  "userId": "user-456",
  "status": "PENDING",
  "items": [
    {
      "id": "item-1",
      "productId": "prod-789",
      "productName": "iPhone 15 Pro",
      "quantity": 1,
      "price": 1099.99
    }
  ],
  "totalItems": 1,
  "totalAmount": 1099.99,
  "shippingAddress": {
    "fullName": "John Doe",
    "street": "123 Main St",
    "city": "Paris",
    "zipCode": "75001",
    "country": "France",
    "phone": "+33612345678"
  },
  "createdAt": "2026-01-13T10:30:00Z"
}
```

**Script Post-Request:**
```javascript
var jsonData = pm.response.json();
pm.environment.set("orderId", jsonData.id);
```

---

### 6.2 Récupérer une Commande par ID

**Endpoint:** `GET {{baseUrl}}/orders/{{orderId}}`

**Headers:**
```
Authorization: Bearer {{token}}
```

---

### 6.3 Récupérer Mes Commandes

**Endpoint:** `GET {{baseUrl}}/orders/my-orders?page=0&size=10`

**Headers:**
```
Authorization: Bearer {{token}}
```

**Réponse Attendue (200 OK):**
```json
{
  "content": [
    {
      "id": "order-123",
      "orderNumber": "ORD-2026-001234",
      "status": "PENDING",
      "totalAmount": 1099.99,
      "createdAt": "2026-01-13T10:30:00Z"
    }
  ],
  "totalElements": 5,
  "totalPages": 1
}
```

---

### 6.4 Récupérer Toutes les Commandes (ADMIN)

**Endpoint:** `GET {{baseUrl}}/orders?page=0&size=20&status=PENDING`

**Headers:**
```
Authorization: Bearer {{token}}
```

---

### 6.5 Annuler une Commande

**Endpoint:** `PUT {{baseUrl}}/orders/{{orderId}}/cancel`

**Headers:**
```
Authorization: Bearer {{token}}
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "reason": "Changed my mind"
}
```

---

### 6.6 Confirmer une Commande (ADMIN)

**Endpoint:** `PUT {{baseUrl}}/orders/{{orderId}}/confirm`

**Headers:**
```
Authorization: Bearer {{token}}
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "notes": "Payment verified, ready to ship"
}
```

---

### 6.7 Expédier une Commande (ADMIN)

**Endpoint:** `PUT {{baseUrl}}/orders/{{orderId}}/ship`

**Headers:**
```
Authorization: Bearer {{token}}
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "trackingNumber": "TRACK123456789",
  "carrier": "DHL Express"
}
```

---

### 6.8 Marquer comme Livrée (ADMIN)

**Endpoint:** `PUT {{baseUrl}}/orders/{{orderId}}/deliver`

**Headers:**
```
Authorization: Bearer {{token}}
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "deliveredAt": "2026-01-15T14:30:00Z",
  "receivedBy": "John Doe"
}
```

---

## 7. Gestion des Paiements

### 7.1 Créer un Payment Intent Stripe

**Endpoint:** `POST {{baseUrl}}/payment/intent`

**Headers:**
```
Authorization: Bearer {{token}}
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "orderId": "{{orderId}}",
  "amount": 1099.99,
  "currency": "eur"
}
```

**Réponse Attendue (200 OK):**
```json
{
  "paymentIntentId": "pi_1234567890",
  "clientSecret": "pi_1234567890_secret_abcdefgh",
  "amount": 109999,
  "currency": "eur",
  "status": "PENDING"
}
```

---

### 7.2 Confirmer un Paiement Stripe

**Endpoint:** `POST {{baseUrl}}/payment/confirm/pi_1234567890`

**Headers:**
```
Authorization: Bearer {{token}}
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "paymentMethodId": "pm_card_visa"
}
```

---

### 7.3 Paiement MTN Mobile Money

**Endpoint:** `POST {{baseUrl}}/mobile-payment/mtn`

**Headers:**
```
Authorization: Bearer {{token}}
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "orderId": "{{orderId}}",
  "amount": 1099.99,
  "currency": "XAF",
  "payerMsisdn": "+237612345678",
  "payerReference": "Customer payment for order"
}
```

**Réponse Attendue (200 OK):**
```json
{
  "paymentId": "pay-mtn-123",
  "status": "PENDING",
  "providerReference": "MTN-REF-456789",
  "message": "Payment initiated successfully. Please approve on your phone."
}
```

---

### 7.4 Paiement Orange Money

**Endpoint:** `POST {{baseUrl}}/mobile-payment/orange`

**Headers:**
```
Authorization: Bearer {{token}}
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "orderId": "{{orderId}}",
  "amount": 1099.99,
  "currency": "XOF",
  "payerMsisdn": "+225012345678",
  "payerReference": "Customer payment for order"
}
```

---

### 7.5 Récupérer le Paiement d'une Commande

**Endpoint:** `GET {{baseUrl}}/payment/order/{{orderId}}`

**Headers:**
```
Authorization: Bearer {{token}}
```

---

### 7.6 Rembourser un Paiement (ADMIN)

**Endpoint:** `POST {{baseUrl}}/payment/refund/{{paymentId}}`

**Headers:**
```
Authorization: Bearer {{token}}
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "amount": 1099.99,
  "reason": "Product defective"
}
```

---

## 8. Gestion des Adresses

### 8.1 Créer une Adresse

**Endpoint:** `POST {{baseUrl}}/addresses`

**Headers:**
```
Authorization: Bearer {{token}}
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "fullName": "John Doe",
  "phone": "+33612345678",
  "street": "123 Rue de Rivoli",
  "city": "Paris",
  "zipCode": "75001",
  "country": "France",
  "isDefault": true
}
```

**Réponse Attendue (201 Created):**
```json
{
  "id": "addr-123",
  "userId": "user-456",
  "fullName": "John Doe",
  "phone": "+33612345678",
  "street": "123 Rue de Rivoli",
  "city": "Paris",
  "zipCode": "75001",
  "country": "France",
  "isDefault": true
}
```

**Script Post-Request:**
```javascript
var jsonData = pm.response.json();
pm.environment.set("addressId", jsonData.id);
```

---

### 8.2 Récupérer Mes Adresses

**Endpoint:** `GET {{baseUrl}}/addresses`

**Headers:**
```
Authorization: Bearer {{token}}
```

**Réponse Attendue (200 OK):**
```json
[
  {
    "id": "addr-123",
    "fullName": "John Doe",
    "street": "123 Rue de Rivoli",
    "city": "Paris",
    "zipCode": "75001",
    "country": "France",
    "isDefault": true
  },
  {
    "id": "addr-456",
    "fullName": "John Doe (Work)",
    "street": "456 Avenue des Champs-Élysées",
    "city": "Paris",
    "zipCode": "75008",
    "country": "France",
    "isDefault": false
  }
]
```

---

### 8.3 Récupérer une Adresse par ID

**Endpoint:** `GET {{baseUrl}}/addresses/{{addressId}}`

**Headers:**
```
Authorization: Bearer {{token}}
```

---

### 8.4 Récupérer l'Adresse par Défaut

**Endpoint:** `GET {{baseUrl}}/addresses/default`

**Headers:**
```
Authorization: Bearer {{token}}
```

---

### 8.5 Mettre à Jour une Adresse

**Endpoint:** `PUT {{baseUrl}}/addresses/{{addressId}}`

**Headers:**
```
Authorization: Bearer {{token}}
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "fullName": "John Doe",
  "phone": "+33612345678",
  "street": "789 New Street",
  "city": "Lyon",
  "zipCode": "69001",
  "country": "France",
  "isDefault": false
}
```

---

### 8.6 Définir une Adresse par Défaut

**Endpoint:** `PUT {{baseUrl}}/addresses/{{addressId}}/default`

**Headers:**
```
Authorization: Bearer {{token}}
```

---

### 8.7 Supprimer une Adresse

**Endpoint:** `DELETE {{baseUrl}}/addresses/{{addressId}}`

**Headers:**
```
Authorization: Bearer {{token}}
```

---

## 9. Gestion des Utilisateurs

### 9.1 Récupérer Mon Profil

**Endpoint:** `GET {{baseUrl}}/users/profile`

**Headers:**
```
Authorization: Bearer {{token}}
```

**Réponse Attendue (200 OK):**
```json
{
  "userId": "user-123",
  "username": "johndoe",
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+33612345678",
  "avatarUrl": "/files/download/avatars/johndoe.jpg",
  "emailVerified": true,
  "active": true,
  "roles": ["USER"],
  "createdAt": "2026-01-01T12:00:00Z"
}
```

---

### 9.2 Mettre à Jour Mon Profil

**Endpoint:** `PUT {{baseUrl}}/users/profile`

**Headers:**
```
Authorization: Bearer {{token}}
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "firstName": "Jonathan",
  "lastName": "Doe",
  "phoneNumber": "+33698765432"
}
```

---

### 9.3 Assigner un Rôle (ADMIN uniquement)

**Endpoint:** `POST {{baseUrl}}/roles/assign`

**Headers:**
```
Authorization: Bearer {{token}}
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "userId": "user-789",
  "roleName": "ADMIN"
}
```

---

## 10. Administration

### 10.1 Statistiques du Dashboard (ADMIN)

**Endpoint:** `GET {{baseUrl}}/dashboard/stats`

**Headers:**
```
Authorization: Bearer {{token}}
```

**Réponse Attendue (200 OK):**
```json
{
  "totalUsers": 1523,
  "totalOrders": 4821,
  "totalRevenue": 245678.90,
  "pendingOrders": 45,
  "todayOrders": 28,
  "todayRevenue": 3456.78,
  "lowStockProducts": 12
}
```

---

### 10.2 Statistiques de Revenus (ADMIN)

**Endpoint:** `GET {{baseUrl}}/admin/revenue?startDate=2026-01-01&endDate=2026-01-31`

**Headers:**
```
Authorization: Bearer {{token}}
```

**Réponse Attendue (200 OK):**
```json
{
  "period": {
    "startDate": "2026-01-01",
    "endDate": "2026-01-31"
  },
  "totalRevenue": 125678.90,
  "totalOrders": 456,
  "averageOrderValue": 275.61,
  "dailyRevenue": [
    {
      "date": "2026-01-01",
      "revenue": 4567.89,
      "orders": 18
    },
    {
      "date": "2026-01-02",
      "revenue": 3456.78,
      "orders": 15
    }
  ]
}
```

---

### 10.3 Produits les Plus Vendus (ADMIN)

**Endpoint:** `GET {{baseUrl}}/admin/top-products?limit=10`

**Headers:**
```
Authorization: Bearer {{token}}
```

**Réponse Attendue (200 OK):**
```json
[
  {
    "productId": "prod-123",
    "productName": "iPhone 15 Pro",
    "totalSold": 245,
    "totalRevenue": 269245.55
  },
  {
    "productId": "prod-456",
    "productName": "Samsung Galaxy S24",
    "totalSold": 189,
    "totalRevenue": 188811.00
  }
]
```

---

### 10.4 Croissance des Utilisateurs (ADMIN)

**Endpoint:** `GET {{baseUrl}}/admin/user-growth?period=MONTHLY&year=2026`

**Headers:**
```
Authorization: Bearer {{token}}
```

**Réponse Attendue (200 OK):**
```json
{
  "period": "MONTHLY",
  "year": 2026,
  "data": [
    {
      "month": "January",
      "newUsers": 234,
      "totalUsers": 1523
    },
    {
      "month": "February",
      "newUsers": 189,
      "totalUsers": 1712
    }
  ]
}
```

---

### 10.5 Produits en Rupture de Stock (ADMIN)

**Endpoint:** `GET {{baseUrl}}/admin/low-stock?threshold=10`

**Headers:**
```
Authorization: Bearer {{token}}
```

**Réponse Attendue (200 OK):**
```json
[
  {
    "productId": "prod-789",
    "productName": "MacBook Pro 16\"",
    "stockQuantity": 3,
    "category": "Laptops"
  },
  {
    "productId": "prod-101",
    "productName": "AirPods Pro 2",
    "stockQuantity": 7,
    "category": "Audio"
  }
]
```

---

## 11. Upload de Fichiers

### 11.1 Upload Avatar Utilisateur

**Endpoint:** `POST {{baseUrl}}/files/upload/avatar`

**Headers:**
```
Authorization: Bearer {{token}}
```

**Body (form-data):**
- Key: `file` (Type: File)
- Value: Select an image file (JPG, PNG, etc.)

**Réponse Attendue (200 OK):**
```json
{
  "fileName": "avatar_user123_1673612345.jpg",
  "fileUrl": "/files/download/avatars/avatar_user123_1673612345.jpg",
  "fileSize": 245678,
  "uploadedAt": "2026-01-13T10:45:00Z"
}
```

---

### 11.2 Upload Image Produit (ADMIN)

**Endpoint:** `POST {{baseUrl}}/files/upload/product`

**Headers:**
```
Authorization: Bearer {{token}}
```

**Body (form-data):**
- Key: `file` (Type: File)
- Value: Select a product image

**Réponse Attendue (200 OK):**
```json
{
  "fileName": "product_iphone15_main.jpg",
  "fileUrl": "/files/download/products/product_iphone15_main.jpg",
  "fileSize": 567890,
  "uploadedAt": "2026-01-13T10:50:00Z"
}
```

---

### 11.3 Télécharger un Fichier

**Endpoint:** `GET {{baseUrl}}/files/download/avatars/avatar_user123.jpg`

**Réponse:** Le fichier binaire sera téléchargé

---

### 11.4 Supprimer un Fichier (ADMIN)

**Endpoint:** `DELETE {{baseUrl}}/files/products/product_old_image.jpg`

**Headers:**
```
Authorization: Bearer {{token}}
```

---

## 12. Flux de Test Complet (Scénario E2E)

### Scénario: Achat d'un Produit du Début à la Fin

1. **Inscription** → `POST /auth/register`
2. **Vérification Email** → `GET /auth/verify-email?token=...`
3. **Connexion** → `POST /auth/login` (récupère le token)
4. **Créer une Adresse** → `POST /addresses`
5. **Parcourir les Catégories** → `GET /categories`
6. **Parcourir les Produits** → `GET /products/category/{categoryId}`
7. **Voir le Détail d'un Produit** → `GET /products/{productId}`
8. **Ajouter au Panier** → `POST /cart/items`
9. **Voir le Panier** → `GET /cart`
10. **Créer une Commande** → `POST /orders`
11. **Créer Payment Intent** → `POST /payment/intent`
12. **Confirmer le Paiement** → `POST /payment/confirm/{paymentIntentId}`
13. **Vérifier la Commande** → `GET /orders/{orderId}`
14. **Admin Confirme** → `PUT /orders/{orderId}/confirm` (ADMIN)
15. **Admin Expédie** → `PUT /orders/{orderId}/ship` (ADMIN)
16. **Admin Marque Livrée** → `PUT /orders/{orderId}/deliver` (ADMIN)

---

## 13. Codes de Statut HTTP

| Code | Signification |
|------|---------------|
| 200 | OK - Requête réussie |
| 201 | Created - Ressource créée avec succès |
| 204 | No Content - Suppression réussie |
| 400 | Bad Request - Erreur de validation |
| 401 | Unauthorized - Token manquant ou invalide |
| 403 | Forbidden - Accès refusé (permissions insuffisantes) |
| 404 | Not Found - Ressource non trouvée |
| 409 | Conflict - Conflit (ex: email déjà utilisé) |
| 500 | Internal Server Error - Erreur serveur |

---

## 14. Conseils pour les Tests

1. **Ordre des Tests**: Commencez toujours par l'inscription et la connexion
2. **Variables d'Environnement**: Utilisez les scripts post-request pour sauvegarder automatiquement les IDs
3. **Token JWT**: Le token expire après 24h, reconnectez-vous si nécessaire
4. **Pagination**: Testez avec différentes valeurs de `page`, `size`, et `sort`
5. **Validation**: Testez avec des données invalides pour voir les messages d'erreur
6. **Rôles**: Créez des environnements séparés pour USER et ADMIN
7. **Collections Postman**: Organisez vos requêtes par module (Auth, Products, Cart, etc.)

---

## 15. Collection Postman Recommandée

Structure suggérée pour votre collection:

```
Big Shop API
├── 01. Authentication
│   ├── Register
│   ├── Login
│   ├── Verify Email
│   ├── Forgot Password
│   ├── Reset Password
│   └── Logout
├── 02. Categories
│   ├── Create Category (ADMIN)
│   ├── Get All Categories
│   ├── Get Category by ID
│   └── Get Root Categories
├── 03. Products
│   ├── Create Product (ADMIN)
│   ├── Get All Products
│   ├── Get Product by ID
│   ├── Search Products
│   └── Update Product (ADMIN)
├── 04. Cart
│   ├── Get Cart
│   ├── Add to Cart
│   ├── Update Cart Item
│   └── Clear Cart
├── 05. Addresses
│   ├── Create Address
│   ├── Get My Addresses
│   └── Set Default Address
├── 06. Orders
│   ├── Create Order
│   ├── Get My Orders
│   ├── Get Order by ID
│   └── Cancel Order
├── 07. Payments
│   ├── Create Stripe Payment Intent
│   ├── Pay with MTN
│   └── Pay with Orange Money
├── 08. User Profile
│   ├── Get Profile
│   └── Update Profile
├── 09. Admin
│   ├── Dashboard Stats
│   ├── Revenue Stats
│   ├── Top Products
│   └── Low Stock Products
└── 10. Files
    ├── Upload Avatar
    └── Upload Product Image
```

---

**Bon Test! Pour toute question ou problème, consultez la documentation Swagger à: http://localhost:8081/swagger-ui.html**
