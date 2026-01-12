#!/bin/bash

# Script de génération automatique des Commands et Queries manquants
# Usage: ./create-commands-queries.sh

BASE_PATH="src/main/java/com/esia/big_shop_backend/application/usecase"

echo "🚀 Génération des Commands et Queries manquants..."

# ==================== PRODUCT MODULE ====================
echo "📦 Product Module..."

# DeleteProductImageCommand
cat > "${BASE_PATH}/product/command/DeleteProductImageCommand.java" << 'EOF'
package com.esia.big_shop_backend.application.usecase.product.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteProductImageCommand {
    private final Long productImageId;
}
EOF
echo "  ✅ DeleteProductImageCommand créé"

# ==================== USER MODULE ====================
echo "👤 User Module..."

# GetUserProfileQuery
mkdir -p "${BASE_PATH}/user/query"
cat > "${BASE_PATH}/user/query/GetUserProfileQuery.java" << 'EOF'
package com.esia.big_shop_backend.application.usecase.user.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetUserProfileQuery {
    private final Long userId;
}
EOF
echo "  ✅ GetUserProfileQuery créé"

# GetAllUsersQuery
cat > "${BASE_PATH}/user/query/GetAllUsersQuery.java" << 'EOF'
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
EOF
echo "  ✅ GetAllUsersQuery créé"

# ==================== CATEGORY MODULE ====================
echo "📁 Category Module..."

# DeleteCategoryCommand
cat > "${BASE_PATH}/category/command/DeleteCategoryCommand.java" << 'EOF'
package com.esia.big_shop_backend.application.usecase.category.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteCategoryCommand {
    private final Long categoryId;
}
EOF
echo "  ✅ DeleteCategoryCommand créé"

# GetAllCategoriesQuery
mkdir -p "${BASE_PATH}/category/query"
cat > "${BASE_PATH}/category/query/GetAllCategoriesQuery.java" << 'EOF'
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
EOF
echo "  ✅ GetAllCategoriesQuery créé"

# GetCategoryQuery
cat > "${BASE_PATH}/category/query/GetCategoryQuery.java" << 'EOF'
package com.esia.big_shop_backend.application.usecase.category.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetCategoryQuery {
    private final Long categoryId;
}
EOF
echo "  ✅ GetCategoryQuery créé"

# GetRootCategoriesQuery
cat > "${BASE_PATH}/category/query/GetRootCategoriesQuery.java" << 'EOF'
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
EOF
echo "  ✅ GetRootCategoriesQuery créé"

# GetSubCategoriesQuery
cat > "${BASE_PATH}/category/query/GetSubCategoriesQuery.java" << 'EOF'
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
EOF
echo "  ✅ GetSubCategoriesQuery créé"

# ==================== CART MODULE ====================
echo "🛒 Cart Module..."

# ClearCartCommand
cat > "${BASE_PATH}/cart/command/ClearCartCommand.java" << 'EOF'
package com.esia.big_shop_backend.application.usecase.cart.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClearCartCommand {
    private final Long userId;
}
EOF
echo "  ✅ ClearCartCommand créé"

# GetOrCreateCartQuery
mkdir -p "${BASE_PATH}/cart/query"
cat > "${BASE_PATH}/cart/query/GetOrCreateCartQuery.java" << 'EOF'
package com.esia.big_shop_backend.application.usecase.cart.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetOrCreateCartQuery {
    private final Long userId;
}
EOF
echo "  ✅ GetOrCreateCartQuery créé"

# GetCartTotalQuery
cat > "${BASE_PATH}/cart/query/GetCartTotalQuery.java" << 'EOF'
package com.esia.big_shop_backend.application.usecase.cart.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetCartTotalQuery {
    private final Long userId;
}
EOF
echo "  ✅ GetCartTotalQuery créé"

# ==================== ADDRESS MODULE ====================
echo "📍 Address Module..."

# GetUserAddressesQuery
cat > "${BASE_PATH}/address/query/GetUserAddressesQuery.java" << 'EOF'
package com.esia.big_shop_backend.application.usecase.address.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetUserAddressesQuery {
    private final Long userId;
}
EOF
echo "  ✅ GetUserAddressesQuery créé"

# ==================== PAYMENT MODULE ====================
echo "💳 Payment Module..."

# CheckPaymentStatusQuery
mkdir -p "${BASE_PATH}/payment/query"
cat > "${BASE_PATH}/payment/query/CheckPaymentStatusQuery.java" << 'EOF'
package com.esia.big_shop_backend.application.usecase.payment.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckPaymentStatusQuery {
    private final String transactionId;
}
EOF
echo "  ✅ CheckPaymentStatusQuery créé"

# ==================== ADMIN MODULE ====================
echo "👨‍💼 Admin Module..."

# GetDashboardStatisticsQuery
cat > "${BASE_PATH}/admin/query/GetDashboardStatisticsQuery.java" << 'EOF'
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
EOF
echo "  ✅ GetDashboardStatisticsQuery créé"

# GetTopSellingProductsQuery
cat > "${BASE_PATH}/admin/query/GetTopSellingProductsQuery.java" << 'EOF'
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
EOF
echo "  ✅ GetTopSellingProductsQuery créé"

# GetLowStockProductsQuery
cat > "${BASE_PATH}/admin/query/GetLowStockProductsQuery.java" << 'EOF'
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
EOF
echo "  ✅ GetLowStockProductsQuery créé"

# ==================== AUTH MODULE ====================
echo "🔐 Auth Module..."

# VerifyEmailCommand
cat > "${BASE_PATH}/auth/command/VerifyEmailCommand.java" << 'EOF'
package com.esia.big_shop_backend.application.usecase.auth.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VerifyEmailCommand {
    private final String token;
}
EOF
echo "  ✅ VerifyEmailCommand créé"

# ResendVerificationEmailCommand
cat > "${BASE_PATH}/auth/command/ResendVerificationEmailCommand.java" << 'EOF'
package com.esia.big_shop_backend.application.usecase.auth.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResendVerificationEmailCommand {
    private final String email;
}
EOF
echo "  ✅ ResendVerificationEmailCommand créé"

# LogoutCommand
cat > "${BASE_PATH}/auth/command/LogoutCommand.java" << 'EOF'
package com.esia.big_shop_backend.application.usecase.auth.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LogoutCommand {
    private final String token;
}
EOF
echo "  ✅ LogoutCommand créé"

# RefreshTokenCommand
cat > "${BASE_PATH}/auth/command/RefreshTokenCommand.java" << 'EOF'
package com.esia.big_shop_backend.application.usecase.auth.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshTokenCommand {
    private final String refreshToken;
}
EOF
echo "  ✅ RefreshTokenCommand créé"

echo ""
echo "✨ Génération terminée!"
echo "📊 Résumé:"
echo "  - Product: 1 fichier"
echo "  - User: 2 fichiers"
echo "  - Category: 5 fichiers"
echo "  - Cart: 3 fichiers"
echo "  - Address: 1 fichier"
echo "  - Payment: 1 fichier"
echo "  - Admin: 3 fichiers"
echo "  - Auth: 4 fichiers"
echo ""
echo "  TOTAL: 20 fichiers créés ✅"
echo ""
echo "📝 Prochaines étapes:"
echo "  1. Vérifier que tous les fichiers sont bien créés"
echo "  2. Modifier les UseCases pour utiliser ces Commands/Queries"
echo "  3. Consulter GUIDE_COMMANDS_QUERIES.md pour les détails"
