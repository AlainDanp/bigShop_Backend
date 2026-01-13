# Quick Start - Tests Postman (5 Minutes)

## Étape 1 : Vérifier que le Backend Fonctionne (1 min)

```bash
# Démarrer l'application
cd "C:\Users\alain\OneDrive\Documents\X4(ESIEA)\architecture application\Backend\big_shop_backend"
mvn spring-boot:run
```

**Vérification :** Ouvrir http://localhost:8081/swagger-ui.html dans votre navigateur

---

## Étape 2 : Importer la Collection dans Postman (1 min)

1. Ouvrir Postman
2. Cliquer sur **Import**
3. Sélectionner le fichier : `Big_Shop_Postman_Collection.json`
4. Cliquer sur **Import**

✅ Vous voyez maintenant la collection "Big Shop E-Commerce API"

---

## Étape 3 : Créer l'Environnement (1 min)

1. Cliquer sur **Environments** → **Create Environment**
2. Nom : **Big Shop - Local**
3. Ajouter UNE SEULE variable pour commencer :
   - Variable : `baseUrl`
   - Initial Value : `http://localhost:8081`
   - Current Value : `http://localhost:8081`
4. Cliquer sur **Save**
5. **Sélectionner cet environnement** dans le menu déroulant en haut à droite

---

## Étape 4 : Premiers Tests (2 min)

### Test 1 : Inscription

1. Ouvrir : **01. Authentication → Register User**
2. Cliquer sur **Send**

**✅ Résultat Attendu :** Status 201 Created

---

### Test 2 : Connexion

1. Ouvrir : **01. Authentication → Login User**
2. Cliquer sur **Send**

**✅ Résultat Attendu :**
- Status 200 OK
- Vous recevez un token JWT
- Le token est automatiquement sauvegardé dans l'environnement

---

### Test 3 : Voir Mon Profil (Route Protégée)

1. Ouvrir : **07. User Profile → Get My Profile**
2. Cliquer sur **Send**

**✅ Résultat Attendu :**
- Status 200 OK
- Vous voyez vos informations de profil

---

## C'est Tout ! ✅

Vous avez testé :
- ✅ L'inscription
- ✅ La connexion
- ✅ L'authentification JWT
- ✅ Une route protégée

---

## Suite des Tests

Pour continuer, consultez le guide complet : **GUIDE_TEST_POSTMAN.md**

Ou exécutez toute la collection automatiquement :
1. Clic droit sur **Big Shop E-Commerce API**
2. **Run collection**
3. **Run Big Shop E-Commerce API**

---

## Assigner le Rôle ADMIN (Pour Tests Admin)

Pour tester les fonctionnalités admin, vous devez d'abord :

1. **S'inscrire en tant qu'admin :**
   - Exécuter : **01. Authentication → Register Admin User**

2. **Assigner le rôle ADMIN dans MySQL :**
   ```sql
   USE big_shop;

   -- Trouver l'ID de l'utilisateur admin
   SELECT id FROM users WHERE username = 'admin';
   -- Résultat : supposons id = 2

   -- Assigner le rôle ADMIN (role_id = 1)
   INSERT INTO user_roles (user_id, role_id) VALUES (2, 1);
   ```

3. **Se connecter en tant qu'admin :**
   - Exécuter : **01. Authentication → Login Admin**

4. **Tester une fonction admin :**
   - Exécuter : **02. Categories → Create Category**

---

## Scénario de Test Complet (Recommandé)

### Ordre des Tests pour un Flux E2E

1. **Register User** → Créer compte utilisateur
2. **Login User** → Se connecter
3. **Login Admin** → Se connecter en admin (après avoir assigné le rôle)
4. **Create Category** → Créer une catégorie (ADMIN)
5. **Create Product** → Créer un produit (ADMIN)
6. **Login User** → Revenir au compte utilisateur
7. **Create Address** → Créer une adresse de livraison
8. **Get All Products** → Voir les produits
9. **Add Item to Cart** → Ajouter au panier
10. **Get Cart** → Voir le panier
11. **Create Order** → Créer une commande
12. **Get My Orders** → Voir mes commandes
13. **Login Admin** → Revenir en admin
14. **Confirm Order** → Confirmer la commande (ADMIN)
15. **Ship Order** → Expédier la commande (ADMIN)
16. **Get Dashboard Stats** → Voir les statistiques (ADMIN)

---

## Variables Automatiques

Les scripts de test sauvent automatiquement ces variables :

| Variable | Sauvegardée Après |
|----------|-------------------|
| `token` | Login User / Login Admin |
| `userId` | Register User |
| `categoryId` | Create Category |
| `productId` | Create Product |
| `addressId` | Create Address |
| `orderId` | Create Order |

Vous n'avez **rien à faire**, tout est automatique ! 🎉

---

## En Cas de Problème

### Erreur 401 Unauthorized
→ Reconnectez-vous (Login User ou Login Admin)

### Erreur 403 Forbidden
→ Vous n'avez pas les permissions (utilisez Login Admin pour les routes admin)

### Erreur 500 Internal Server Error
→ Vérifiez les logs de Spring Boot et que la base de données est bien configurée

### Cannot connect to localhost:8081
→ Démarrez l'application : `mvn spring-boot:run`

---

## Documentation Complète

- **Guide de Test Détaillé :** `GUIDE_TEST_POSTMAN.md` (60+ tests)
- **Guide MySQL :** `MYSQL_DATABASE_GUIDE.md`
- **Guide de Correction DB :** `DATABASE_FIX_INSTRUCTIONS.md`
- **Guide Postman API :** `POSTMAN_TESTING_GUIDE.md`

---

**Prêt à tester ! 🚀**
