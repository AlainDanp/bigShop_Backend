# Guide de Configuration et Utilisation de la Base de Données MySQL - Big Shop Backend

## Table des Matières
1. [Prérequis et Installation](#1-prérequis-et-installation)
2. [Configuration de la Base de Données](#2-configuration-de-la-base-de-données)
3. [Schéma de la Base de Données](#3-schéma-de-la-base-de-données)
4. [Structure des Tables](#4-structure-des-tables)
5. [Relations entre les Tables](#5-relations-entre-les-tables)
6. [Scripts SQL Utiles](#6-scripts-sql-utiles)
7. [Indexation et Performance](#7-indexation-et-performance)
8. [Sauvegarde et Restauration](#8-sauvegarde-et-restauration)
9. [Requêtes Courantes](#9-requêtes-courantes)
10. [Dépannage](#10-dépannage)

---

## 1. Prérequis et Installation

### 1.1 Installer MySQL

**Windows:**
1. Téléchargez MySQL Community Server depuis: https://dev.mysql.com/downloads/mysql/
2. Installez avec MySQL Installer
3. Configurez le mot de passe root pendant l'installation

**macOS (avec Homebrew):**
```bash
brew install mysql
brew services start mysql
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install mysql-server
sudo systemctl start mysql
sudo systemctl enable mysql
```

### 1.2 Vérifier l'Installation

```bash
mysql --version
```

### 1.3 Se Connecter à MySQL

```bash
mysql -u root -p
```
Entrez le mot de passe root configuré lors de l'installation.

---

## 2. Configuration de la Base de Données

### 2.1 Créer la Base de Données

```sql
CREATE DATABASE big_shop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2.2 Créer un Utilisateur Dédié (Recommandé)

```sql
-- Créer l'utilisateur
CREATE USER 'bigshop_user'@'localhost' IDENTIFIED BY 'VotreMotDePasseSecurise123!';

-- Accorder tous les privilèges sur la base de données
GRANT ALL PRIVILEGES ON big_shop.* TO 'bigshop_user'@'localhost';

-- Appliquer les changements
FLUSH PRIVILEGES;
```

### 2.3 Configuration dans application.properties

Mettez à jour votre fichier `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/big_shop?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=bigshop_user
spring.datasource.password=VotreMotDePasseSecurise123!
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.jdbc.time_zone=UTC

# Connection Pool Configuration
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=20000
spring.datasource.hikari.idle-timeout=300000
spring.datasource.hikari.max-lifetime=1200000
```

### 2.4 Modes DDL Auto

| Mode | Description | Usage Recommandé |
|------|-------------|------------------|
| `create` | Supprime et recrée les tables à chaque démarrage | Développement initial |
| `create-drop` | Crée les tables au démarrage, les supprime à l'arrêt | Tests |
| `update` | Met à jour le schéma sans supprimer les données | Développement |
| `validate` | Valide que le schéma correspond aux entités | Production |
| `none` | Aucune action automatique | Production avec Flyway/Liquibase |

**Pour la Production**, utilisez:
```properties
spring.jpa.hibernate.ddl-auto=validate
```
Et gérez les migrations avec Flyway ou Liquibase.

---

## 3. Schéma de la Base de Données

### 3.1 Diagramme Entité-Relation (Textuel)

```
┌─────────────┐       ┌──────────────┐       ┌─────────────┐
│    User     │──────<│   Address    │       │    Role     │
│             │       │              │       │             │
│ - id (PK)   │       │ - id (PK)    │       │ - id (PK)   │
│ - username  │       │ - user_id    │       │ - name      │
│ - email     │       │ - street     │       │ - desc      │
│ - password  │       │ - city       │       └─────────────┘
│ - active    │       │ - is_default │              ∧
└─────────────┘       └──────────────┘              │
      │                                             │
      │                                    ┌────────┴────────┐
      │                                    │   User_Roles    │
      │                                    │                 │
      │                                    │ - user_id (FK)  │
      │                                    │ - role_id (FK)  │
      ├─────────────┐                      └─────────────────┘
      │             │
      ∨             ∨
┌──────────┐  ┌───────────┐
│   Cart   │  │   Order   │
│          │  │           │
│ - id (PK)│  │ - id (PK) │
│ - user_id│  │ - number  │
└──────────┘  │ - status  │
      │       │ - total   │
      ∨       └───────────┘
┌─────────────┐      │
│  CartItem   │      ∨
│             │ ┌────────────┐      ┌─────────────┐
│ - id (PK)   │ │ OrderItem  │──────│   Payment   │
│ - cart_id   │ │            │      │             │
│ - product_id│ │ - id (PK)  │      │ - id (PK)   │
│ - quantity  │ │ - order_id │      │ - order_id  │
└─────────────┘ │ - prod_id  │      │ - amount    │
                │ - quantity │      │ - status    │
                └────────────┘      │ - provider  │
                      ∧             └─────────────┘
                      │
           ┌──────────┴──────────┐
           │                     │
      ┌─────────┐         ┌─────────────┐
      │ Product │─────────│  Category   │
      │         │         │             │
      │ - id    │         │ - id (PK)   │
      │ - name  │         │ - parent_id │
      │ - price │         │ - name      │
      │ - stock │         └─────────────┘
      └─────────┘
           │
           ∨
    ┌──────────────┐
    │ProductImage  │
    │              │
    │ - id (PK)    │
    │ - product_id │
    │ - image_url  │
    │ - order      │
    └──────────────┘
```

---

## 4. Structure des Tables

### 4.1 Table: users

**IMPORTANT:** Les entités JPA utilisent `BIGINT AUTO_INCREMENT` pour les IDs, PAS des UUID VARCHAR(36).

```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    date_of_birth DATE,
    avatar_url VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE NOT NULL,
    email_verified BOOLEAN DEFAULT FALSE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_email (email),
    INDEX idx_is_active (is_active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

### 4.2 Table: roles

```sql
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_role_name CHECK (name IN ('ADMIN', 'USER', 'MODERATOR'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

### 4.3 Table: user_roles (Many-to-Many)

```sql
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_role_id (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

### 4.4 Table: addresses

```sql
CREATE TABLE addresses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    zip_code VARCHAR(20) NOT NULL,
    country VARCHAR(50) NOT NULL,
    is_default BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_is_default (is_default)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

### 4.5 Table: categories

```sql
CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    parent_id BIGINT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES categories(id) ON DELETE SET NULL,
    INDEX idx_parent_id (parent_id),
    INDEX idx_name (name),
    INDEX idx_is_active (is_active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

### 4.6 Table: products

```sql
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    discount_price DECIMAL(10, 2),
    stock_quantity INT NOT NULL DEFAULT 0,
    category_id BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL,
    INDEX idx_category_id (category_id),
    INDEX idx_name (name),
    INDEX idx_price (price),
    INDEX idx_is_active (is_active),
    INDEX idx_stock (stock_quantity),
    INDEX idx_category_active (category_id, is_active),
    CONSTRAINT chk_price_positive CHECK (price >= 0),
    CONSTRAINT chk_discount_valid CHECK (discount_price IS NULL OR discount_price < price),
    CONSTRAINT chk_stock_positive CHECK (stock_quantity >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

### 4.7 Table: product_images

```sql
CREATE TABLE product_images (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    display_order INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    INDEX idx_product_id (product_id),
    INDEX idx_display_order (display_order)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

### 4.8 Table: carts

```sql
CREATE TABLE carts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

### 4.9 Table: cart_items

```sql
CREATE TABLE cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    price DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (cart_id) REFERENCES carts(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    INDEX idx_cart_id (cart_id),
    INDEX idx_product_id (product_id),
    CONSTRAINT chk_quantity_positive CHECK (quantity > 0),
    CONSTRAINT uk_cart_product UNIQUE (cart_id, product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

### 4.10 Table: orders

```sql
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_number VARCHAR(50) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    total_items INT NOT NULL,
    total_amount DECIMAL(12, 2) NOT NULL,
    shipping_full_name VARCHAR(100),
    shipping_phone VARCHAR(20),
    shipping_street VARCHAR(255),
    shipping_city VARCHAR(100),
    shipping_zip_code VARCHAR(20),
    shipping_country VARCHAR(50),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_order_number (order_number),
    INDEX idx_user_id (user_id),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at),
    INDEX idx_user_status (user_id, status),
    CONSTRAINT chk_order_status CHECK (status IN ('PENDING', 'CONFIRMED', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

### 4.11 Table: order_items

```sql
CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(12, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE RESTRICT,
    INDEX idx_order_id (order_id),
    INDEX idx_product_id (product_id),
    CONSTRAINT chk_order_quantity_positive CHECK (quantity > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

### 4.12 Table: payments

```sql
CREATE TABLE payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    amount DECIMAL(12, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL DEFAULT 'EUR',
    provider VARCHAR(20) NOT NULL,
    provider_reference VARCHAR(255),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_order_id (order_id),
    INDEX idx_user_id (user_id),
    INDEX idx_status (status),
    INDEX idx_provider (provider),
    INDEX idx_status_created (status, created_at),
    CONSTRAINT chk_payment_provider CHECK (provider IN ('STRIPE', 'MTN', 'ORANGE')),
    CONSTRAINT chk_payment_status CHECK (status IN ('PENDING', 'PAID', 'FAILED', 'SUCCEEDED', 'REFUNDED', 'CANCELLED', 'COMPLETED'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

---

## 5. Relations entre les Tables

### 5.1 Relations One-to-Many

| Table Parent | Table Enfant | Type | Clé Étrangère |
|--------------|--------------|------|---------------|
| users | addresses | 1:N | addresses.user_id |
| users | carts | 1:1 | carts.user_id |
| users | orders | 1:N | orders.user_id |
| users | payments | 1:N | payments.user_id |
| categories | categories | 1:N | categories.parent_id (auto-référence) |
| categories | products | 1:N | products.category_id |
| products | product_images | 1:N | product_images.product_id |
| products | cart_items | 1:N | cart_items.product_id |
| products | order_items | 1:N | order_items.product_id |
| carts | cart_items | 1:N | cart_items.cart_id |
| orders | order_items | 1:N | order_items.order_id |
| orders | payments | 1:N | payments.order_id |

### 5.2 Relations Many-to-Many

| Table 1 | Table 2 | Table Jonction | Description |
|---------|---------|----------------|-------------|
| users | roles | user_roles | Un utilisateur peut avoir plusieurs rôles |

### 5.3 Contraintes d'Intégrité Référentielle

**ON DELETE CASCADE:**
- Utilisé pour les relations où la suppression du parent doit supprimer les enfants
- Exemples: user → addresses, cart → cart_items, order → order_items

**ON DELETE RESTRICT:**
- Empêche la suppression si des enregistrements liés existent
- Exemple: products dans order_items (ne peut pas supprimer un produit commandé)

**ON DELETE SET NULL:**
- Met la clé étrangère à NULL si le parent est supprimé
- Exemples: category → products, parent_category → categories

---

## 6. Scripts SQL Utiles

### 6.1 Insertion des Données Initiales

#### Insérer les Rôles

```sql
INSERT INTO roles (id, name, description, created_at)
VALUES
    (UUID(), 'ADMIN', 'Administrator with full access', NOW()),
    (UUID(), 'USER', 'Regular user with limited access', NOW());
```

#### Insérer des Catégories

```sql
-- Catégories racines
INSERT INTO categories (category_id, parent_id, name, description, active, created_at)
VALUES
    ('cat-electronics', NULL, 'Electronics', 'Electronic devices and accessories', TRUE, NOW()),
    ('cat-clothing', NULL, 'Clothing', 'Fashion and apparel', TRUE, NOW()),
    ('cat-home', NULL, 'Home & Garden', 'Home improvement and gardening', TRUE, NOW());

-- Sous-catégories
INSERT INTO categories (category_id, parent_id, name, description, active, created_at)
VALUES
    ('cat-smartphones', 'cat-electronics', 'Smartphones', 'Mobile phones', TRUE, NOW()),
    ('cat-laptops', 'cat-electronics', 'Laptops', 'Portable computers', TRUE, NOW()),
    ('cat-mens', 'cat-clothing', 'Men''s Clothing', 'Clothing for men', TRUE, NOW()),
    ('cat-womens', 'cat-clothing', 'Women''s Clothing', 'Clothing for women', TRUE, NOW());
```

#### Insérer des Produits

```sql
INSERT INTO products (id, name, description, price, discount_price, stock_quantity, category_id, active, created_at)
VALUES
    (UUID(), 'iPhone 15 Pro', 'Latest Apple flagship with A17 Pro chip', 1199.99, 1099.99, 50, 'cat-smartphones', TRUE, NOW()),
    (UUID(), 'Samsung Galaxy S24', 'Samsung flagship with AI features', 999.99, 899.99, 45, 'cat-smartphones', TRUE, NOW()),
    (UUID(), 'MacBook Pro 16"', 'Powerful laptop with M3 Max chip', 2499.99, NULL, 30, 'cat-laptops', TRUE, NOW()),
    (UUID(), 'Dell XPS 15', 'High-performance Windows laptop', 1799.99, 1699.99, 25, 'cat-laptops', TRUE, NOW());
```

#### Créer un Utilisateur Admin

```sql
-- L'utilisateur (le mot de passe doit être hashé avec BCrypt dans l'application)
INSERT INTO users (id, username, email, password, first_name, last_name, active, email_verified, created_at)
VALUES
    ('admin-001', 'admin', 'admin@bigshop.com', '$2a$10$...', 'Admin', 'User', TRUE, TRUE, NOW());

-- Assigner le rôle ADMIN
INSERT INTO user_roles (user_id, role_id)
SELECT 'admin-001', id FROM roles WHERE name = 'ADMIN';
```

### 6.2 Requêtes de Nettoyage

#### Supprimer les Paniers Vides

```sql
DELETE FROM carts
WHERE id NOT IN (
    SELECT DISTINCT cart_id FROM cart_items
);
```

#### Supprimer les Commandes Annulées de Plus de 90 Jours

```sql
DELETE FROM orders
WHERE status = 'CANCELLED'
  AND created_at < DATE_SUB(NOW(), INTERVAL 90 DAY);
```

#### Nettoyer les Images de Produits Sans Produits

```sql
DELETE FROM product_images
WHERE product_id NOT IN (
    SELECT id FROM products
);
```

### 6.3 Requêtes de Maintenance

#### Réinitialiser le Stock (Attention!)

```sql
UPDATE products
SET stock_quantity = 100
WHERE stock_quantity < 10;
```

#### Désactiver les Produits Sans Stock

```sql
UPDATE products
SET active = FALSE
WHERE stock_quantity = 0;
```

#### Mettre à Jour les Prix avec une Réduction de 10%

```sql
UPDATE products
SET discount_price = ROUND(price * 0.9, 2)
WHERE discount_price IS NULL
  AND active = TRUE;
```

---

## 7. Indexation et Performance

### 7.1 Index Existants

Tous les index ont été créés dans les définitions de tables ci-dessus. Voici un résumé :

**users:**
- idx_username (username)
- idx_email (email)
- idx_active (active)

**addresses:**
- idx_user_id (user_id)
- idx_is_default (is_default)

**categories:**
- idx_parent_id (parent_id)
- idx_name (name)
- idx_active (active)

**products:**
- idx_category_id (category_id)
- idx_name (name)
- idx_price (price)
- idx_active (active)
- idx_stock (stock_quantity)

**orders:**
- idx_order_number (order_number)
- idx_user_id (user_id)
- idx_status (status)
- idx_created_at (created_at)

### 7.2 Index Composés Additionnels (Optionnel)

Pour améliorer les performances des requêtes fréquentes :

```sql
-- Recherche de produits actifs par catégorie
CREATE INDEX idx_products_category_active ON products(category_id, active);

-- Recherche de produits avec stock disponible
CREATE INDEX idx_products_active_stock ON products(active, stock_quantity);

-- Requêtes de commandes par utilisateur et statut
CREATE INDEX idx_orders_user_status ON orders(user_id, status);

-- Recherche de paiements par statut et date
CREATE INDEX idx_payments_status_created ON payments(status, created_at);
```

### 7.3 Analyser les Performances

#### EXPLAIN pour une Requête

```sql
EXPLAIN SELECT p.*
FROM products p
INNER JOIN categories c ON p.category_id = c.category_id
WHERE c.name = 'Smartphones' AND p.active = TRUE;
```

#### Statistiques des Tables

```sql
SHOW TABLE STATUS FROM big_shop;
```

#### Analyser une Table

```sql
ANALYZE TABLE products;
```

---

## 8. Sauvegarde et Restauration

### 8.1 Sauvegarde Complète

```bash
mysqldump -u bigshop_user -p big_shop > backup_big_shop_$(date +%Y%m%d_%H%M%S).sql
```

### 8.2 Sauvegarde avec Compression

```bash
mysqldump -u bigshop_user -p big_shop | gzip > backup_big_shop_$(date +%Y%m%d_%H%M%S).sql.gz
```

### 8.3 Sauvegarde de Tables Spécifiques

```bash
mysqldump -u bigshop_user -p big_shop users products orders > backup_critical_tables.sql
```

### 8.4 Sauvegarde Structure Uniquement (Sans Données)

```bash
mysqldump -u bigshop_user -p --no-data big_shop > schema_only.sql
```

### 8.5 Restauration

```bash
mysql -u bigshop_user -p big_shop < backup_big_shop_20260113_143000.sql
```

### 8.6 Restauration depuis Fichier Compressé

```bash
gunzip < backup_big_shop_20260113_143000.sql.gz | mysql -u bigshop_user -p big_shop
```

### 8.7 Script de Sauvegarde Automatique (Cron)

Créez un fichier `backup_bigshop.sh`:

```bash
#!/bin/bash
BACKUP_DIR="/var/backups/bigshop"
DATE=$(date +%Y%m%d_%H%M%S)
MYSQL_USER="bigshop_user"
MYSQL_PASSWORD="VotreMotDePasse"
DATABASE="big_shop"

mkdir -p $BACKUP_DIR
mysqldump -u $MYSQL_USER -p$MYSQL_PASSWORD $DATABASE | gzip > $BACKUP_DIR/backup_$DATE.sql.gz

# Garder seulement les 7 derniers jours
find $BACKUP_DIR -name "backup_*.sql.gz" -mtime +7 -delete
```

Ajoutez au crontab (sauvegarde quotidienne à 2h du matin):

```bash
crontab -e
0 2 * * * /path/to/backup_bigshop.sh
```

---

## 9. Requêtes Courantes

### 9.1 Statistiques Business

#### Total des Ventes par Mois

```sql
SELECT
    DATE_FORMAT(created_at, '%Y-%m') AS month,
    COUNT(*) AS total_orders,
    SUM(total_amount) AS total_revenue
FROM orders
WHERE status IN ('DELIVERED', 'SHIPPED')
GROUP BY DATE_FORMAT(created_at, '%Y-%m')
ORDER BY month DESC;
```

#### Produits les Plus Vendus

```sql
SELECT
    p.id,
    p.name,
    SUM(oi.quantity) AS total_sold,
    SUM(oi.subtotal) AS total_revenue
FROM order_items oi
INNER JOIN products p ON oi.product_id = p.id
INNER JOIN orders o ON oi.order_id = o.id
WHERE o.status IN ('DELIVERED', 'SHIPPED')
GROUP BY p.id, p.name
ORDER BY total_sold DESC
LIMIT 10;
```

#### Revenus par Catégorie

```sql
SELECT
    c.name AS category_name,
    COUNT(DISTINCT o.id) AS total_orders,
    SUM(oi.subtotal) AS total_revenue
FROM order_items oi
INNER JOIN products p ON oi.product_id = p.id
INNER JOIN categories c ON p.category_id = c.category_id
INNER JOIN orders o ON oi.order_id = o.id
WHERE o.status IN ('DELIVERED', 'SHIPPED')
GROUP BY c.category_id, c.name
ORDER BY total_revenue DESC;
```

#### Utilisateurs les Plus Actifs

```sql
SELECT
    u.id,
    u.username,
    u.email,
    COUNT(o.id) AS total_orders,
    SUM(o.total_amount) AS total_spent
FROM users u
LEFT JOIN orders o ON u.id = o.user_id
WHERE o.status != 'CANCELLED'
GROUP BY u.id, u.username, u.email
ORDER BY total_spent DESC
LIMIT 20;
```

### 9.2 Gestion des Stocks

#### Produits en Rupture de Stock

```sql
SELECT
    p.id,
    p.name,
    c.name AS category_name,
    p.stock_quantity,
    p.price
FROM products p
LEFT JOIN categories c ON p.category_id = c.category_id
WHERE p.stock_quantity = 0 AND p.active = TRUE
ORDER BY c.name, p.name;
```

#### Produits avec Stock Faible (< 10)

```sql
SELECT
    p.id,
    p.name,
    c.name AS category_name,
    p.stock_quantity,
    COALESCE(SUM(ci.quantity), 0) AS in_carts
FROM products p
LEFT JOIN categories c ON p.category_id = c.category_id
LEFT JOIN cart_items ci ON p.id = ci.product_id
WHERE p.stock_quantity < 10 AND p.active = TRUE
GROUP BY p.id, p.name, c.name, p.stock_quantity
ORDER BY p.stock_quantity ASC;
```

#### Valeur Totale du Stock

```sql
SELECT
    SUM(price * stock_quantity) AS total_inventory_value,
    COUNT(*) AS total_products,
    SUM(stock_quantity) AS total_units
FROM products
WHERE active = TRUE;
```

### 9.3 Analyse des Commandes

#### Commandes en Attente

```sql
SELECT
    o.order_number,
    u.username,
    u.email,
    o.total_amount,
    o.status,
    o.created_at,
    DATEDIFF(NOW(), o.created_at) AS days_pending
FROM orders o
INNER JOIN users u ON o.user_id = u.id
WHERE o.status = 'PENDING'
ORDER BY o.created_at ASC;
```

#### Taux de Conversion du Panier

```sql
SELECT
    COUNT(DISTINCT c.id) AS total_carts,
    COUNT(DISTINCT o.user_id) AS converted_carts,
    ROUND(COUNT(DISTINCT o.user_id) * 100.0 / COUNT(DISTINCT c.id), 2) AS conversion_rate
FROM carts c
LEFT JOIN orders o ON c.user_id = o.user_id AND o.created_at > c.created_at;
```

#### Valeur Moyenne du Panier

```sql
SELECT
    AVG(total_amount) AS average_order_value,
    MIN(total_amount) AS min_order_value,
    MAX(total_amount) AS max_order_value
FROM orders
WHERE status != 'CANCELLED';
```

### 9.4 Analyse des Utilisateurs

#### Nouveaux Utilisateurs par Mois

```sql
SELECT
    DATE_FORMAT(created_at, '%Y-%m') AS month,
    COUNT(*) AS new_users
FROM users
GROUP BY DATE_FORMAT(created_at, '%Y-%m')
ORDER BY month DESC;
```

#### Utilisateurs Inactifs (Sans Commande depuis 6 Mois)

```sql
SELECT
    u.id,
    u.username,
    u.email,
    MAX(o.created_at) AS last_order_date,
    DATEDIFF(NOW(), MAX(o.created_at)) AS days_since_last_order
FROM users u
LEFT JOIN orders o ON u.id = o.user_id
GROUP BY u.id, u.username, u.email
HAVING MAX(o.created_at) < DATE_SUB(NOW(), INTERVAL 6 MONTH)
   OR MAX(o.created_at) IS NULL
ORDER BY last_order_date DESC;
```

### 9.5 Analyse des Paiements

#### Statut des Paiements

```sql
SELECT
    status,
    COUNT(*) AS count,
    SUM(amount) AS total_amount
FROM payments
GROUP BY status
ORDER BY count DESC;
```

#### Paiements par Fournisseur

```sql
SELECT
    provider,
    COUNT(*) AS total_transactions,
    SUM(amount) AS total_amount,
    AVG(amount) AS average_amount
FROM payments
WHERE status IN ('PAID', 'SUCCEEDED', 'COMPLETED')
GROUP BY provider
ORDER BY total_amount DESC;
```

---

## 10. Dépannage

### 10.1 Problèmes de Connexion

#### Vérifier si MySQL est en Cours d'Exécution

```bash
# Linux/macOS
sudo systemctl status mysql

# Windows (dans PowerShell en tant qu'administrateur)
Get-Service MySQL*
```

#### Tester la Connexion

```bash
mysql -u bigshop_user -p -h localhost -P 3306
```

#### Vérifier les Privilèges

```sql
SHOW GRANTS FOR 'bigshop_user'@'localhost';
```

### 10.2 Problèmes de Performance

#### Voir les Requêtes Lentes

```sql
SHOW VARIABLES LIKE 'slow_query_log%';
SET GLOBAL slow_query_log = 'ON';
SET GLOBAL long_query_time = 2;
```

#### Processus en Cours

```sql
SHOW PROCESSLIST;
```

#### Tuer une Requête Bloquante

```sql
KILL QUERY process_id;
```

### 10.3 Problèmes de Verrouillage

#### Voir les Verrous

```sql
SHOW OPEN TABLES WHERE In_use > 0;
```

#### Transactions en Cours

```sql
SELECT * FROM information_schema.INNODB_TRX;
```

### 10.4 Problèmes d'Espace Disque

#### Taille de Chaque Table

```sql
SELECT
    table_name,
    ROUND(((data_length + index_length) / 1024 / 1024), 2) AS size_mb
FROM information_schema.TABLES
WHERE table_schema = 'big_shop'
ORDER BY (data_length + index_length) DESC;
```

#### Taille Totale de la Base

```sql
SELECT
    ROUND(SUM(data_length + index_length) / 1024 / 1024, 2) AS total_size_mb
FROM information_schema.TABLES
WHERE table_schema = 'big_shop';
```

### 10.5 Réparation de Tables

#### Vérifier une Table

```sql
CHECK TABLE products;
```

#### Réparer une Table

```sql
REPAIR TABLE products;
```

#### Optimiser une Table

```sql
OPTIMIZE TABLE products;
```

---

## 11. Configuration Avancée

### 11.1 Paramètres MySQL Recommandés pour Production

Ajoutez ces paramètres dans `/etc/mysql/my.cnf` (Linux) ou `my.ini` (Windows):

```ini
[mysqld]
# General
max_connections = 200
max_allowed_packet = 64M
default_storage_engine = InnoDB

# InnoDB Settings
innodb_buffer_pool_size = 2G
innodb_log_file_size = 512M
innodb_flush_log_at_trx_commit = 2
innodb_file_per_table = 1

# Query Cache (disabled in MySQL 8.0+)
# query_cache_type = 1
# query_cache_size = 128M

# Logging
slow_query_log = 1
slow_query_log_file = /var/log/mysql/slow-query.log
long_query_time = 2

# Character Set
character_set_server = utf8mb4
collation_server = utf8mb4_unicode_ci

# Timezone
default_time_zone = '+00:00'
```

### 11.2 Monitoring

#### Surveiller les Connexions

```sql
SHOW STATUS LIKE 'Threads_connected';
SHOW STATUS LIKE 'Max_used_connections';
```

#### Cache Hit Rate

```sql
SHOW STATUS LIKE 'Innodb_buffer_pool%';
```

---

## 12. Migrations avec Flyway (Recommandé pour Production)

### 12.1 Ajouter Flyway au projet

Ajoutez dans `pom.xml`:

```xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-mysql</artifactId>
</dependency>
```

### 12.2 Configuration

Dans `application.properties`:

```properties
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true
spring.flyway.locations=classpath:db/migration
```

### 12.3 Structure des Migrations

Créez `src/main/resources/db/migration/`:

- `V1__create_initial_schema.sql`
- `V2__add_payment_tables.sql`
- `V3__add_indexes.sql`

---

**Pour toute question ou problème concernant la base de données, consultez la documentation MySQL: https://dev.mysql.com/doc/**
