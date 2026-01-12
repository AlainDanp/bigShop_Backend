# Script PowerShell de génération automatique des Commands et Queries manquants
# Usage: .\create-commands-queries.ps1

$BasePath = "src\main\java\com\esia\big_shop_backend\application\usecase"

Write-Host "🚀 Génération des Commands et Queries manquants..." -ForegroundColor Cyan

# ==================== PRODUCT MODULE ====================
Write-Host "`n📦 Product Module..." -ForegroundColor Yellow

# DeleteProductImageCommand
$content = @"
package com.esia.big_shop_backend.application.usecase.product.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteProductImageCommand {
    private final Long productImageId;
}
"@
$content | Out-File -FilePath "$BasePath\product\command\DeleteProductImageCommand.java" -Encoding UTF8
Write-Host "  ✅ DeleteProductImageCommand créé" -ForegroundColor Green

# ==================== USER MODULE ====================
Write-Host "`n👤 User Module..." -ForegroundColor Yellow

# Créer le dossier query s'il n'existe pas
New-Item -ItemType Directory -Force -Path "$BasePath\user\query" | Out-Null

# GetUserProfileQuery
$content = @"
package com.esia.big_shop_backend.application.usecase.user.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetUserProfileQuery {
    private final Long userId;
}
"@
$content | Out-File -FilePath "$BasePath\user\query\GetUserProfileQuery.java" -Encoding UTF8
Write-Host "  ✅ GetUserProfileQuery créé" -ForegroundColor Green

# GetAllUsersQuery
$content = @"
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
"@
$content | Out-File -FilePath "$BasePath\user\query\GetAllUsersQuery.java" -Encoding UTF8
Write-Host "  ✅ GetAllUsersQuery créé" -ForegroundColor Green

# ==================== CATEGORY MODULE ====================
Write-Host "`n📁 Category Module..." -ForegroundColor Yellow

# DeleteCategoryCommand
$content = @"
package com.esia.big_shop_backend.application.usecase.category.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteCategoryCommand {
    private final Long categoryId;
}
"@
$content | Out-File -FilePath "$BasePath\category\command\DeleteCategoryCommand.java" -Encoding UTF8
Write-Host "  ✅ DeleteCategoryCommand créé" -ForegroundColor Green

# Créer le dossier query s'il n'existe pas
New-Item -ItemType Directory -Force -Path "$BasePath\category\query" | Out-Null

# GetAllCategoriesQuery
$content = @"
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
"@
$content | Out-File -FilePath "$BasePath\category\query\GetAllCategoriesQuery.java" -Encoding UTF8
Write-Host "  ✅ GetAllCategoriesQuery créé" -ForegroundColor Green

# GetCategoryQuery
$content = @"
package com.esia.big_shop_backend.application.usecase.category.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetCategoryQuery {
    private final Long categoryId;
}
"@
$content | Out-File -FilePath "$BasePath\category\query\GetCategoryQuery.java" -Encoding UTF8
Write-Host "  ✅ GetCategoryQuery créé" -ForegroundColor Green

# GetRootCategoriesQuery
$content = @"
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
"@
$content | Out-File -FilePath "$BasePath\category\query\GetRootCategoriesQuery.java" -Encoding UTF8
Write-Host "  ✅ GetRootCategoriesQuery créé" -ForegroundColor Green

# GetSubCategoriesQuery
$content = @"
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
"@
$content | Out-File -FilePath "$BasePath\category\query\GetSubCategoriesQuery.java" -Encoding UTF8
Write-Host "  ✅ GetSubCategoriesQuery créé" -ForegroundColor Green

# ==================== CART MODULE ====================
Write-Host "`n🛒 Cart Module..." -ForegroundColor Yellow

# ClearCartCommand
$content = @"
package com.esia.big_shop_backend.application.usecase.cart.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClearCartCommand {
    private final Long userId;
}
"@
$content | Out-File -FilePath "$BasePath\cart\command\ClearCartCommand.java" -Encoding UTF8
Write-Host "  ✅ ClearCartCommand créé" -ForegroundColor Green

# Créer le dossier query s'il n'existe pas
New-Item -ItemType Directory -Force -Path "$BasePath\cart\query" | Out-Null

# GetOrCreateCartQuery
$content = @"
package com.esia.big_shop_backend.application.usecase.cart.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetOrCreateCartQuery {
    private final Long userId;
}
"@
$content | Out-File -FilePath "$BasePath\cart\query\GetOrCreateCartQuery.java" -Encoding UTF8
Write-Host "  ✅ GetOrCreateCartQuery créé" -ForegroundColor Green

# GetCartTotalQuery
$content = @"
package com.esia.big_shop_backend.application.usecase.cart.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetCartTotalQuery {
    private final Long userId;
}
"@
$content | Out-File -FilePath "$BasePath\cart\query\GetCartTotalQuery.java" -Encoding UTF8
Write-Host "  ✅ GetCartTotalQuery créé" -ForegroundColor Green

# ==================== ADDRESS MODULE ====================
Write-Host "`n📍 Address Module..." -ForegroundColor Yellow

# GetUserAddressesQuery
$content = @"
package com.esia.big_shop_backend.application.usecase.address.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetUserAddressesQuery {
    private final Long userId;
}
"@
$content | Out-File -FilePath "$BasePath\address\query\GetUserAddressesQuery.java" -Encoding UTF8
Write-Host "  ✅ GetUserAddressesQuery créé" -ForegroundColor Green

# ==================== PAYMENT MODULE ====================
Write-Host "`n💳 Payment Module..." -ForegroundColor Yellow

# Créer le dossier query s'il n'existe pas
New-Item -ItemType Directory -Force -Path "$BasePath\payment\query" | Out-Null

# CheckPaymentStatusQuery
$content = @"
package com.esia.big_shop_backend.application.usecase.payment.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckPaymentStatusQuery {
    private final String transactionId;
}
"@
$content | Out-File -FilePath "$BasePath\payment\query\CheckPaymentStatusQuery.java" -Encoding UTF8
Write-Host "  ✅ CheckPaymentStatusQuery créé" -ForegroundColor Green

# ==================== ADMIN MODULE ====================
Write-Host "`n👨‍💼 Admin Module..." -ForegroundColor Yellow

# GetDashboardStatisticsQuery
$content = @"
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
"@
$content | Out-File -FilePath "$BasePath\admin\query\GetDashboardStatisticsQuery.java" -Encoding UTF8
Write-Host "  ✅ GetDashboardStatisticsQuery créé" -ForegroundColor Green

# GetTopSellingProductsQuery
$content = @"
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
"@
$content | Out-File -FilePath "$BasePath\admin\query\GetTopSellingProductsQuery.java" -Encoding UTF8
Write-Host "  ✅ GetTopSellingProductsQuery créé" -ForegroundColor Green

# GetLowStockProductsQuery
$content = @"
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
    private int threshold = 10;
    private int page = 0;
    private int size = 20;
}
"@
$content | Out-File -FilePath "$BasePath\admin\query\GetLowStockProductsQuery.java" -Encoding UTF8
Write-Host "  ✅ GetLowStockProductsQuery créé" -ForegroundColor Green

# ==================== AUTH MODULE ====================
Write-Host "`n🔐 Auth Module..." -ForegroundColor Yellow

# VerifyEmailCommand
$content = @"
package com.esia.big_shop_backend.application.usecase.auth.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VerifyEmailCommand {
    private final String token;
}
"@
$content | Out-File -FilePath "$BasePath\auth\command\VerifyEmailCommand.java" -Encoding UTF8
Write-Host "  ✅ VerifyEmailCommand créé" -ForegroundColor Green

# ResendVerificationEmailCommand
$content = @"
package com.esia.big_shop_backend.application.usecase.auth.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResendVerificationEmailCommand {
    private final String email;
}
"@
$content | Out-File -FilePath "$BasePath\auth\command\ResendVerificationEmailCommand.java" -Encoding UTF8
Write-Host "  ✅ ResendVerificationEmailCommand créé" -ForegroundColor Green

# LogoutCommand
$content = @"
package com.esia.big_shop_backend.application.usecase.auth.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LogoutCommand {
    private final String token;
}
"@
$content | Out-File -FilePath "$BasePath\auth\command\LogoutCommand.java" -Encoding UTF8
Write-Host "  ✅ LogoutCommand créé" -ForegroundColor Green

# RefreshTokenCommand
$content = @"
package com.esia.big_shop_backend.application.usecase.auth.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshTokenCommand {
    private final String refreshToken;
}
"@
$content | Out-File -FilePath "$BasePath\auth\command\RefreshTokenCommand.java" -Encoding UTF8
Write-Host "  ✅ RefreshTokenCommand créé" -ForegroundColor Green

Write-Host "`n✨ Génération terminée!" -ForegroundColor Cyan
Write-Host "📊 Résumé:" -ForegroundColor White
Write-Host "  - Product: 1 fichier" -ForegroundColor Gray
Write-Host "  - User: 2 fichiers" -ForegroundColor Gray
Write-Host "  - Category: 5 fichiers" -ForegroundColor Gray
Write-Host "  - Cart: 3 fichiers" -ForegroundColor Gray
Write-Host "  - Address: 1 fichier" -ForegroundColor Gray
Write-Host "  - Payment: 1 fichier" -ForegroundColor Gray
Write-Host "  - Admin: 3 fichiers" -ForegroundColor Gray
Write-Host "  - Auth: 4 fichiers" -ForegroundColor Gray
Write-Host "`n  TOTAL: 20 fichiers créés ✅" -ForegroundColor Green
Write-Host "`n📝 Prochaines étapes:" -ForegroundColor Yellow
Write-Host "  1. Vérifier que tous les fichiers sont bien créés"
Write-Host "  2. Modifier les UseCases pour utiliser ces Commands/Queries"
Write-Host "  3. Consulter GUIDE_COMMANDS_QUERIES.md pour les détails"
