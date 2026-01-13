# Instructions de Correction de la Base de Données - URGENT

## Problème Identifié

Votre application Spring Boot rencontre l'erreur suivante lors de l'inscription d'un utilisateur :

```
InvalidDataAccessApiUsageException: For input string: "076e4aea-f013-11f0-be0a-0a002700001b"
```

### Cause Racine

Il y a une **incohérence entre les entités JPA et la structure de la base de données** :

| Composant | Type d'ID Attendu |
|-----------|-------------------|
| **Entités JPA** (`RoleJpaEntity`, `UserJpaEntity`, etc.) | `BIGINT AUTO_INCREMENT` (Long) |
| **Base de données actuelle** | `VARCHAR(36)` (UUID String) |

Les entités JPA utilisent toutes `@GeneratedValue(strategy = GenerationType.IDENTITY)` avec `Long id`, ce qui nécessite des colonnes `BIGINT AUTO_INCREMENT` dans MySQL, **PAS** des UUID.

---

## Solution : Recréer la Base de Données avec la Bonne Structure

### Option 1 : Utiliser le Script SQL Automatique (Recommandé)

J'ai créé un script SQL complet qui va :
1. Supprimer toutes les tables existantes (⚠️ PERTE DE DONNÉES)
2. Recréer toutes les tables avec les bons types (`BIGINT AUTO_INCREMENT`)
3. Insérer les données initiales (rôles et catégories)

**Étapes :**

1. **Ouvrir MySQL en ligne de commande ou dans MySQL Workbench**

2. **Exécuter le script de correction :**

   **Via ligne de commande (Windows) :**
   ```bash
   mysql -u root -p"Jeux@joneA56" big_shop < database_fix_script.sql
   ```

   **Via MySQL Workbench :**
   - Ouvrir MySQL Workbench
   - Se connecter à votre serveur MySQL
   - File → Open SQL Script
   - Sélectionner `database_fix_script.sql`
   - Cliquer sur Execute (l'icône éclair)

3. **Vérifier que les tables ont été créées correctement :**
   ```sql
   USE big_shop;
   SHOW TABLES;
   DESCRIBE roles;
   DESCRIBE users;
   SELECT * FROM roles;
   ```

   Vous devriez voir :
   - Table `roles` avec `id` de type `bigint`
   - 3 rôles insérés : ADMIN, USER, MODERATOR
   - Plusieurs catégories par défaut

4. **Redémarrer votre application Spring Boot**

   ```bash
   mvn clean spring-boot:run
   ```

5. **Tester l'inscription d'un utilisateur via Postman**

   ```
   POST http://localhost:8081/auth/register
   ```

   Body :
   ```json
   {
     "username": "testuser",
     "email": "test@example.com",
     "password": "Test123!",
     "firstName": "Test",
     "lastName": "User"
   }
   ```

   Résultat attendu : **201 Created** ✅

---

### Option 2 : Laisser Hibernate Recréer les Tables

Si vous n'avez **aucune donnée importante** à conserver, vous pouvez laisser Hibernate recréer automatiquement les tables :

1. **Modifier `application.properties` :**

   ```properties
   spring.jpa.hibernate.ddl-auto=create
   ```

   ⚠️ **ATTENTION** : Ceci supprimera toutes les données existantes !

2. **Redémarrer l'application**

   Hibernate va automatiquement :
   - Supprimer toutes les tables existantes
   - Recréer les tables avec la bonne structure

3. **Insérer les rôles manuellement :**

   ```sql
   USE big_shop;

   INSERT INTO roles (name, description) VALUES
       ('ADMIN', 'Administrator with full access'),
       ('USER', 'Regular user with limited access'),
       ('MODERATOR', 'Moderator with medium access');
   ```

4. **Revenir à `spring.jpa.hibernate.ddl-auto=update` :**

   ```properties
   spring.jpa.hibernate.ddl-auto=update
   ```

5. **Redémarrer l'application**

---

## Vérification Post-Correction

### 1. Vérifier la Structure de la Table `roles`

```sql
DESCRIBE big_shop.roles;
```

Résultat attendu :
```
+-------------+--------------+------+-----+---------+----------------+
| Field       | Type         | Null | Key | Default | Extra          |
+-------------+--------------+------+-----+---------+----------------+
| id          | bigint       | NO   | PRI | NULL    | auto_increment |
| name        | varchar(20)  | NO   | UNI | NULL    |                |
| description | text         | YES  |     | NULL    |                |
| created_at  | timestamp    | YES  |     | CURRENT_TIMESTAMP |      |
+-------------+--------------+------+-----+---------+----------------+
```

✅ **L'ID doit être de type `bigint` avec `auto_increment`**

### 2. Vérifier les Données Initiales

```sql
SELECT * FROM big_shop.roles;
```

Résultat attendu :
```
+----+-----------+----------------------------------+---------------------+
| id | name      | description                      | created_at          |
+----+-----------+----------------------------------+---------------------+
|  1 | ADMIN     | Administrator with full access   | 2026-01-13 00:30:00 |
|  2 | USER      | Regular user with limited access | 2026-01-13 00:30:00 |
|  3 | MODERATOR | Moderator with medium access     | 2026-01-13 00:30:00 |
+----+-----------+----------------------------------+---------------------+
```

### 3. Tester l'Inscription

Utilisez Postman pour tester l'endpoint `/auth/register` comme indiqué dans le guide Postman.

---

## Structure Correcte de Toutes les Tables

Après la correction, toutes vos tables doivent avoir des IDs `BIGINT AUTO_INCREMENT` :

| Table | Colonne ID | Type Correct |
|-------|------------|--------------|
| `roles` | `id` | `BIGINT AUTO_INCREMENT` |
| `users` | `id` | `BIGINT AUTO_INCREMENT` |
| `addresses` | `id` | `BIGINT AUTO_INCREMENT` |
| `categories` | `id` | `BIGINT AUTO_INCREMENT` |
| `products` | `id` | `BIGINT AUTO_INCREMENT` |
| `product_images` | `id` | `BIGINT AUTO_INCREMENT` |
| `carts` | `id` | `BIGINT AUTO_INCREMENT` |
| `cart_items` | `id` | `BIGINT AUTO_INCREMENT` |
| `orders` | `id` | `BIGINT AUTO_INCREMENT` |
| `order_items` | `id` | `BIGINT AUTO_INCREMENT` |
| `payments` | `id` | `BIGINT AUTO_INCREMENT` |

---

## Questions Fréquentes

### Q: Vais-je perdre mes données ?

**R:** Oui, si vous utilisez le script SQL ou l'option `spring.jpa.hibernate.ddl-auto=create`. C'est pourquoi ces solutions sont recommandées **uniquement en développement**.

Si vous avez des données de production, vous devrez :
1. Sauvegarder les données existantes
2. Recréer les tables avec la bonne structure
3. Migrer les données manuellement avec conversion des IDs

### Q: Pourquoi l'erreur mentionne "076e4aea-f013-11f0-be0a-0a002700001b" ?

**R:** C'est un UUID stocké dans votre table `roles`. Hibernate essaie de le lire comme un `Long` (nombre), mais c'est une chaîne de caractères, d'où l'erreur `NumberFormatException`.

### Q: Le guide MySQL initial était-il incorrect ?

**R:** Oui, désolé pour cette confusion. Le guide initial suggérait d'utiliser des UUID (`VARCHAR(36)`), mais les entités JPA de votre projet utilisent des `Long` avec auto-incrémentation. Le guide a maintenant été corrigé.

### Q: Dois-je modifier le code Java ?

**R:** Non ! Le code Java est correct. C'est la base de données qui doit correspondre aux entités JPA.

### Q: Que faire si l'erreur persiste ?

**R:** Vérifiez :
1. Que la table `roles` a bien un `id` de type `BIGINT`
2. Que les rôles ont été insérés (IDs 1, 2, 3)
3. Que votre `application.properties` pointe vers la bonne base de données
4. Redémarrez complètement votre application Spring Boot

---

## Support

Si vous rencontrez des problèmes après avoir suivi ces instructions :

1. Vérifiez les logs de l'application Spring Boot
2. Vérifiez la structure des tables avec `DESCRIBE table_name;`
3. Consultez le guide MySQL mis à jour (`MYSQL_DATABASE_GUIDE.md`)

---

**Mise à jour :** 2026-01-13

**Fichiers Associés :**
- `database_fix_script.sql` - Script de correction automatique
- `MYSQL_DATABASE_GUIDE.md` - Guide mis à jour avec la bonne structure
- `POSTMAN_TESTING_GUIDE.md` - Guide de test des APIs
