# Guide Complet - Commands & Queries Manquantes

## 📚 Table des Matières
1. [Patterns & Templates](#patterns--templates)
2. [Product Module](#product-module)
3. [User Module](#user-module)
4. [Order Module](#order-module)
5. [Category Module](#category-module)
6. [Cart Module](#cart-module)
7. [Address Module](#address-module)
8. [Payment Module](#payment-module)
9. [Admin Module](#admin-module)
10. [Checklist de Validation](#checklist-de-validation)

---

## Patterns & Templates

### 📝 Template Command (Écriture)
```java
package com.esia.big_shop_backend.application.usecase.{module}.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class {Action}{Entity}Command {
    private final Long {entity}Id;  // Pour update/delete
    private final String field1;    // Pour create/update
    private final String field2;
    // ... autres champs
}
```

**Règles:**
- ✅ `@AllArgsConstructor` - constructeur avec tous les paramètres
- ✅ `@Getter` - getters pour tous les champs
- ✅ `final` - tous les champs sont immutables
- ✅ Pas de `@Setter` - les commands sont immuables

### 🔍 Template Query (Lecture)

**Query Simple (par ID):**
```java
package com.esia.big_shop_backend.application.usecase.{module}.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Get{Entity}Query {
    private final Long {entity}Id;
}
```

**Query avec Pagination:**
```java
package com.esia.big_shop_backend.application.usecase.{module}.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAll{Entities}Query {
    private int page = 0;
    private int size = 20;
}
```

**Query avec Filtre + Pagination:**
```java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Get{Entities}By{Criteria}Query {
    private Long criteriaId;  // Le critère de filtrage
    private int page = 0;
    private int size = 20;
}
```

---

## Product Module

### ✅ Commands Existants
- `CreateProductCommand` ✅
- `UpdateProductCommand` ✅
- `ActivateProductCommand` ✅
- `DeactivateProductCommand` ✅
- `DeleteProductCommand` ✅
- `UpdateProductStockCommand` ✅
- `AddProductImageCommand` ✅

### ❌ Commands Manquants

#### DeleteProductImageCommand
**UseCase:** `DeleteProductImageUseCase`
```java
package com.esia.big_shop_backend.application.usecase.product.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteProductImageCommand {
    private final Long productImageId;
}
```

### ✅ Queries Existants
- `GetAllProductsQuery` ✅
- `GetProductQuery` ✅
- `GetActiveProductsQuery` ✅
- `GetProductsOnSaleQuery` ✅
- `GetNewProductsQuery` ✅
- `GetProductsByCategoryQuery` ✅
- `SearchProductsQuery` ✅

### ❌ Queries Manquants - Aucun ✅

---

## User Module

### ✅ Commands Existants
- `UpdateUserProfileCommand` ✅
- `ChangePasswordCommand` ✅
- `UpdateAvatarCommand` ✅
- `DeleteUserAccountCommand` ✅
- `ToggleUserStatusCommand` ✅
- `AssignRoleCommand` ✅

### ❌ Commands Manquants - Aucun ✅

### ❌ Queries Manquants

#### GetUserProfileQuery
**UseCase:** `GetUserProfileUseCase`
```java
package com.esia.big_shop_backend.application.usecase.user.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetUserProfileQuery {
    private final Long userId;
}
```

#### GetAllUsersQuery
**UseCase:** `GetAllUsersUseCase`
```java
package com.esia.big_shop_backend.application.usecase.user.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllUsersQuery {
    private int page = 0;
    private int size = 20;
}
```

---

## Order Module

### ✅ Commands Existants
- `CreateOrderCommand` ✅
- `ConfirmOrderCommand` ✅
- `CancelOrderCommand` ✅
- `ShipOrderCommand` ✅
- `DeliverOrderCommand` ✅
- `UpdateOrderStatusCommand` ✅

### ❌ Commands Manquants - Aucun ✅

### ✅ Queries Existants
- `GetOrderQuery` ✅
- `GetOrderByNumberQuery` ✅
- `GetAllOrdersQuery` ✅
- `GetUserOrdersQuery` ✅

### ❌ Queries Manquants - Aucun ✅

---

## Category Module

### ✅ Commands Existants
- `CreateCategoryCommand` ✅
- `UpdateCategoryCommand` ✅

### ❌ Commands Manquants

#### DeleteCategoryCommand
**UseCase:** `DeleteCategoryUseCase`
```java
package com.esia.big_shop_backend.application.usecase.category.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteCategoryCommand {
    private final Long categoryId;
}
```

#### ActivateCategoryCommand (optionnel)
```java
package com.esia.big_shop_backend.application.usecase.category.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ActivateCategoryCommand {
    private final Long categoryId;
}
```

#### DeactivateCategoryCommand (optionnel)
```java
package com.esia.big_shop_backend.application.usecase.category.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeactivateCategoryCommand {
    private final Long categoryId;
}
```

### ❌ Queries Manquants

#### GetAllCategoriesQuery
**UseCase:** `GetAllCategoriesUseCase`
```java
package com.esia.big_shop_backend.application.usecase.category.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllCategoriesQuery {
    private int page = 0;
    private int size = 20;
}
```

#### GetCategoryQuery
**UseCase:** `GetCategoryUseCase`
```java
package com.esia.big_shop_backend.application.usecase.category.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetCategoryQuery {
    private final Long categoryId;
}
```

#### GetRootCategoriesQuery
**UseCase:** `GetRootCategoriesUseCase`
```java
package com.esia.big_shop_backend.application.usecase.category.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetRootCategoriesQuery {
    private int page = 0;
    private int size = 20;
}
```

#### GetSubCategoriesQuery
**UseCase:** `GetSubCategoriesUseCase`
```java
package com.esia.big_shop_backend.application.usecase.category.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetSubCategoriesQuery {
    private Long parentCategoryId;
    private int page = 0;
    private int size = 20;
}
```

---

## Cart Module

### ✅ Commands Existants
- `AddToCartCommand` ✅
- `UpdateCartItemCommand` ✅
- `RemoveFromCartCommand` ✅

### ❌ Commands Manquants

#### ClearCartCommand
**UseCase:** `ClearCartUseCase`
```java
package com.esia.big_shop_backend.application.usecase.cart.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClearCartCommand {
    private final Long userId;
}
```

### ❌ Queries Manquants

#### GetOrCreateCartQuery
**UseCase:** `GetOrCreateCartUseCase`
```java
package com.esia.big_shop_backend.application.usecase.cart.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetOrCreateCartQuery {
    private final Long userId;
}
```

#### GetCartTotalQuery
**UseCase:** `GetCartTotalUseCase`
```java
package com.esia.big_shop_backend.application.usecase.cart.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetCartTotalQuery {
    private final Long userId;
}
```

---

## Address Module

### ✅ Commands Existants
- `CreateAddressCommand` ✅
- `UpdateAddressCommand` ✅
- `DeleteAddressCommand` ✅
- `SetDefaultAddressCommand` ✅

### ❌ Commands Manquants - Aucun ✅

### ✅ Queries Existants
- `GetAddressQuery` ✅

### ❌ Queries Manquants

#### GetUserAddressesQuery
**UseCase:** `GetUserAddressesUseCase`
```java
package com.esia.big_shop_backend.application.usecase.address.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetUserAddressesQuery {
    private final Long userId;
}
```

---

## Payment Module

### ✅ Commands Existants
- `ProcessStripePaymentCommand` ✅
- `ProcessMtnPaymentCommand` ✅
- `ProcessOrangeMoneyPaymentCommand` ✅
- `RefundPaymentCommand` ✅

### ❌ Commands Manquants - Aucun ✅

### ❌ Queries Manquants

#### CheckPaymentStatusQuery
**UseCase:** `CheckPaymentStatusUseCase`
```java
package com.esia.big_shop_backend.application.usecase.payment.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckPaymentStatusQuery {
    private final Long paymentId;
}
```

OU si par transaction ID:
```java
@Getter
@AllArgsConstructor
public class CheckPaymentStatusQuery {
    private final String transactionId;
}
```

---

## Admin Module

### ❌ Queries Manquants

#### GetDashboardStatisticsQuery
**UseCase:** `GetDashboardStatisticsUseCase`
```java
package com.esia.big_shop_backend.application.usecase.admin.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetDashboardStatisticsQuery {
    private LocalDate startDate;
    private LocalDate endDate;
}
```

#### GetTopSellingProductsQuery
**UseCase:** `GetTopSellingProductsUseCase`
```java
package com.esia.big_shop_backend.application.usecase.admin.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetTopSellingProductsQuery {
    private int limit = 10;
}
```

#### GetLowStockProductsQuery
**UseCase:** `GetLowStockProductsUseCase`
```java
package com.esia.big_shop_backend.application.usecase.admin.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetLowStockProductsQuery {
    private int threshold = 10;  // Seuil de stock faible
    private int page = 0;
    private int size = 20;
}
```

---

## Auth Module

### ✅ Commands Existants
- `RegisterUserCommand` ✅
- `LoginCommand` ✅
- `ForgotPasswordCommand` ✅
- `ResetPasswordCommand` ✅

### ❌ Commands Manquants

#### VerifyEmailCommand
**UseCase:** `VerifyEmailUseCase`
```java
package com.esia.big_shop_backend.application.usecase.auth.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VerifyEmailCommand {
    private final String token;
}
```

#### ResendVerificationEmailCommand
**UseCase:** `ResendVerificationEmailUseCase`
```java
package com.esia.big_shop_backend.application.usecase.auth.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResendVerificationEmailCommand {
    private final String email;
}
```

#### LogoutCommand
**UseCase:** `LogoutUseCase`
```java
package com.esia.big_shop_backend.application.usecase.auth.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LogoutCommand {
    private final String token;
}
```

#### RefreshTokenCommand
**UseCase:** `RefreshTokenUseCase`
```java
package com.esia.big_shop_backend.application.usecase.auth.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshTokenCommand {
    private final String refreshToken;
}
```

---

## Checklist de Validation

### ✅ Pour chaque Command:
- [ ] Package correct: `.../usecase/{module}/command/`
- [ ] Nom: `{Action}{Entity}Command.java`
- [ ] Annotations: `@Getter`, `@AllArgsConstructor`
- [ ] Tous les champs sont `final`
- [ ] Pas de `@Setter`
- [ ] Pas de logique métier

### ✅ Pour chaque Query:
- [ ] Package correct: `.../usecase/{module}/query/`
- [ ] Nom: `Get{Entity}Query.java` ou `Get{Entities}By{Criteria}Query.java`
- [ ] Si pagination: `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`
- [ ] Si simple: `@Getter`, `@AllArgsConstructor` + champs `final`
- [ ] Valeurs par défaut pour pagination: `page = 0`, `size = 20`

### ✅ Pour chaque UseCase:
- [ ] A un objet Command OU Query
- [ ] N'accepte PAS de paramètres primitifs (Long, String, int...)
- [ ] Signature: `public Result execute(XxxCommand/Query command/query)`
- [ ] Utilise les Domain Services pour la logique métier
- [ ] Retourne `List<>` au lieu de `Page<>`

---

## Ordre de Création Recommandé

### 1️⃣ Product Module (Déjà complet ✅)

### 2️⃣ User Module
```bash
1. GetUserProfileQuery
2. GetAllUsersQuery
```

### 3️⃣ Category Module
```bash
1. DeleteCategoryCommand
2. GetAllCategoriesQuery
3. GetCategoryQuery
4. GetRootCategoriesQuery
5. GetSubCategoriesQuery
```

### 4️⃣ Cart Module
```bash
1. ClearCartCommand
2. GetOrCreateCartQuery
3. GetCartTotalQuery
```

### 5️⃣ Address Module
```bash
1. GetUserAddressesQuery
```

### 6️⃣ Payment Module
```bash
1. CheckPaymentStatusQuery
```

### 7️⃣ Admin Module
```bash
1. GetDashboardStatisticsQuery
2. GetTopSellingProductsQuery
3. GetLowStockProductsQuery
```

### 8️⃣ Auth Module
```bash
1. VerifyEmailCommand
2. ResendVerificationEmailCommand
3. LogoutCommand
4. RefreshTokenCommand
```

---

## Exemple Complet: Création d'une Query

### Étape 1: Créer le fichier Query
**Fichier:** `GetUserProfileQuery.java`
```java
package com.esia.big_shop_backend.application.usecase.user.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetUserProfileQuery {
    private final Long userId;
}
```

### Étape 2: Modifier le UseCase
**Avant:**
```java
public User execute(Long userId) {
    return userRepository.findById(UserId.of(userId))...
}
```

**Après:**
```java
public User execute(GetUserProfileQuery query) {
    return userRepository.findById(UserId.of(query.getUserId()))...
}
```

### Étape 3: Ajouter l'import
```java
import com.esia.big_shop_backend.application.usecase.user.query.GetUserProfileQuery;
```

---

## Commandes Shell pour créer rapidement

### Linux/Mac:
```bash
# Créer un Command
cat > "src/main/java/com/esia/big_shop_backend/application/usecase/user/query/GetUserProfileQuery.java" << 'EOF'
package com.esia.big_shop_backend.application.usecase.user.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetUserProfileQuery {
    private final Long userId;
}
EOF
```

### Windows PowerShell:
```powershell
# Créer un Command
@"
package com.esia.big_shop_backend.application.usecase.user.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetUserProfileQuery {
    private final Long userId;
}
"@ | Out-File -FilePath "src/main/java/com/esia/big_shop_backend/application/usecase/user/query/GetUserProfileQuery.java" -Encoding UTF8
```

---

## Résumé des Manquants

| Module   | Commands Manquants | Queries Manquants |
|----------|-------------------|-------------------|
| Product  | 1                 | 0                 |
| User     | 0                 | 2                 |
| Order    | 0                 | 0                 |
| Category | 3                 | 4                 |
| Cart     | 1                 | 2                 |
| Address  | 0                 | 1                 |
| Payment  | 0                 | 1                 |
| Admin    | 0                 | 3                 |
| Auth     | 4                 | 0                 |
| **TOTAL**| **9**             | **13**            |

**Grand Total: 22 fichiers à créer** 🎯

---

Bon courage! 💪
