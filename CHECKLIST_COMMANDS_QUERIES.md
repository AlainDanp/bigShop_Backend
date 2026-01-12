# ✅ Checklist Commands & Queries

## Instructions
1. Cochez [x] quand le fichier est créé
2. Cochez [x] quand le UseCase est modifié pour utiliser le Command/Query

---

## 📦 PRODUCT Module

### Commands
- [x] CreateProductCommand ✅
- [x] UpdateProductCommand ✅
- [x] ActivateProductCommand ✅
- [x] DeactivateProductCommand ✅
- [x] DeleteProductCommand ✅
- [x] UpdateProductStockCommand ✅
- [x] AddProductImageCommand ✅
- [ ] **DeleteProductImageCommand** ⚠️ À créer
  - [ ] Fichier créé
  - [ ] UseCase modifié: `DeleteProductImageUseCase`

### Queries
- [x] GetAllProductsQuery ✅
- [x] GetProductQuery ✅
- [x] GetActiveProductsQuery ✅
- [x] GetProductsOnSaleQuery ✅
- [x] GetNewProductsQuery ✅
- [x] GetProductsByCategoryQuery ✅
- [x] SearchProductsQuery ✅

---

## 👤 USER Module

### Commands
- [x] UpdateUserProfileCommand ✅
- [x] ChangePasswordCommand ✅
- [x] UpdateAvatarCommand ✅
- [x] DeleteUserAccountCommand ✅
- [x] ToggleUserStatusCommand ✅
- [x] AssignRoleCommand ✅

### Queries
- [ ] **GetUserProfileQuery** ⚠️ À créer
  - [ ] Fichier créé
  - [ ] UseCase modifié: `GetUserProfileUseCase`

- [ ] **GetAllUsersQuery** ⚠️ À créer
  - [ ] Fichier créé
  - [ ] UseCase modifié: `GetAllUsersUseCase`

---

## 📦 ORDER Module

### Commands
- [x] CreateOrderCommand ✅
- [x] ConfirmOrderCommand ✅
- [x] CancelOrderCommand ✅
- [x] ShipOrderCommand ✅
- [x] DeliverOrderCommand ✅
- [x] UpdateOrderStatusCommand ✅

### Queries
- [x] GetOrderQuery ✅
- [x] GetOrderByNumberQuery ✅
- [x] GetAllOrdersQuery ✅
- [x] GetUserOrdersQuery ✅

---

## 📁 CATEGORY Module

### Commands
- [x] CreateCategoryCommand ✅
- [x] UpdateCategoryCommand ✅

- [ ] **DeleteCategoryCommand** ⚠️ À créer
  - [ ] Fichier créé
  - [ ] UseCase modifié: `DeleteCategoryUseCase`

### Queries
- [ ] **GetAllCategoriesQuery** ⚠️ À créer
  - [ ] Fichier créé
  - [ ] UseCase modifié: `GetAllCategoriesUseCase`

- [ ] **GetCategoryQuery** ⚠️ À créer
  - [ ] Fichier créé
  - [ ] UseCase modifié: `GetCategoryUseCase`

- [ ] **GetRootCategoriesQuery** ⚠️ À créer
  - [ ] Fichier créé
  - [ ] UseCase modifié: `GetRootCategoriesUseCase`

- [ ] **GetSubCategoriesQuery** ⚠️ À créer
  - [ ] Fichier créé
  - [ ] UseCase modifié: `GetSubCategoriesUseCase`

---

## 🛒 CART Module

### Commands
- [x] AddToCartCommand ✅
- [x] UpdateCartItemCommand ✅
- [x] RemoveFromCartCommand ✅

- [ ] **ClearCartCommand** ⚠️ À créer
  - [ ] Fichier créé
  - [ ] UseCase modifié: `ClearCartUseCase`

### Queries
- [ ] **GetOrCreateCartQuery** ⚠️ À créer
  - [ ] Fichier créé
  - [ ] UseCase modifié: `GetOrCreateCartUseCase`

- [ ] **GetCartTotalQuery** ⚠️ À créer
  - [ ] Fichier créé
  - [ ] UseCase modifié: `GetCartTotalUseCase`

---

## 📍 ADDRESS Module

### Commands
- [x] CreateAddressCommand ✅
- [x] UpdateAddressCommand ✅
- [x] DeleteAddressCommand ✅
- [x] SetDefaultAddressCommand ✅

### Queries
- [x] GetAddressQuery ✅

- [ ] **GetUserAddressesQuery** ⚠️ À créer
  - [ ] Fichier créé
  - [ ] UseCase modifié: `GetUserAddressesUseCase`

---

## 💳 PAYMENT Module

### Commands
- [x] ProcessStripePaymentCommand ✅
- [x] ProcessMtnPaymentCommand ✅
- [x] ProcessOrangeMoneyPaymentCommand ✅
- [x] RefundPaymentCommand ✅

### Queries
- [ ] **CheckPaymentStatusQuery** ⚠️ À créer
  - [ ] Fichier créé
  - [ ] UseCase modifié: `CheckPaymentStatusUseCase`

---

## 👨‍💼 ADMIN Module

### Queries
- [x] GetRevenueStatisticsQuery ✅
- [x] GetUserGrowthQuery ✅

- [ ] **GetDashboardStatisticsQuery** ⚠️ À créer
  - [ ] Fichier créé
  - [ ] UseCase modifié: `GetDashboardStatisticsUseCase`

- [ ] **GetTopSellingProductsQuery** ⚠️ À créer
  - [ ] Fichier créé
  - [ ] UseCase modifié: `GetTopSellingProductsUseCase`

- [ ] **GetLowStockProductsQuery** ⚠️ À créer
  - [ ] Fichier créé
  - [ ] UseCase modifié: `GetLowStockProductsUseCase`

---

## 🔐 AUTH Module

### Commands
- [x] RegisterUserCommand ✅
- [x] LoginCommand ✅
- [x] ForgotPasswordCommand ✅
- [x] ResetPasswordCommand ✅

- [ ] **VerifyEmailCommand** ⚠️ À créer
  - [ ] Fichier créé
  - [ ] UseCase modifié: `VerifyEmailUseCase`

- [ ] **ResendVerificationEmailCommand** ⚠️ À créer
  - [ ] Fichier créé
  - [ ] UseCase modifié: `ResendVerificationEmailUseCase`

- [ ] **LogoutCommand** ⚠️ À créer
  - [ ] Fichier créé
  - [ ] UseCase modifié: `LogoutUseCase`

- [ ] **RefreshTokenCommand** ⚠️ À créer
  - [ ] Fichier créé
  - [ ] UseCase modifié: `RefreshTokenUseCase`

---

## 📊 Statistiques

### Fichiers à créer
- Product: 1
- User: 2
- Category: 5
- Cart: 3
- Address: 1
- Payment: 1
- Admin: 3
- Auth: 4

**TOTAL: 20 fichiers** 🎯

### Progression
- [ ] Product Module (1/1)
- [ ] User Module (2/2)
- [ ] Order Module (0/0) ✅ Complet
- [ ] Category Module (5/5)
- [ ] Cart Module (3/3)
- [ ] Address Module (1/1)
- [ ] Payment Module (1/1)
- [ ] Admin Module (3/3)
- [ ] Auth Module (4/4)

---

## 🚀 Actions Rapides

### Générer tous les fichiers manquants

**Windows:**
```powershell
.\create-commands-queries.ps1
```

**Linux/Mac:**
```bash
chmod +x create-commands-queries.sh
./create-commands-queries.sh
```

### Vérifier la création
```bash
# Compter les fichiers Command
find . -name "*Command.java" | wc -l

# Compter les fichiers Query
find . -name "*Query.java" | wc -l
```

---

## ✅ Validation Finale

Une fois tous les fichiers créés et les UseCases modifiés:

- [ ] Tous les Commands créés (36 fichiers)
- [ ] Toutes les Queries créées (27 fichiers)
- [ ] Tous les UseCases utilisent des Command/Query
- [ ] Aucun UseCase n'accepte de paramètres primitifs (Long, String, int)
- [ ] Tous retournent `List<>` au lieu de `Page<>`
- [ ] Tous utilisent les Domain Services
- [ ] Compilation réussie sans erreurs
- [ ] Tests unitaires passent

---

Bon courage! 💪
