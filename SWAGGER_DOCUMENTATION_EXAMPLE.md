# 📚 Guide de Documentation Swagger pour Big Shop Backend

## 🚀 Accéder à Swagger UI

Une fois l'application démarrée, accédez à Swagger via :

**URL Swagger UI :** http://localhost:8081/swagger-ui.html
**URL OpenAPI JSON :** http://localhost:8081/v3/api-docs

---

## 🔐 Tester l'API avec Swagger

### 1. S'authentifier

1. Allez sur **`POST /auth/login`**
2. Cliquez sur **"Try it out"**
3. Entrez vos identifiants :
```json
{
  "username": "admin",
  "password": "password123"
}
```
4. Cliquez sur **"Execute"**
5. Copiez le **token** dans la réponse

### 2. Autoriser les requêtes

1. Cliquez sur le bouton **🔓 Authorize** en haut à droite
2. Entrez : `Bearer <votre-token>`
3. Cliquez sur **Authorize**
4. Toutes vos requêtes utiliseront maintenant ce token !

---

## 📝 Exemple d'Annotations Swagger

Voici comment documenter un endpoint dans votre controller :

```java
package com.esia.big_shop_backend.presentation.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "API de gestion des produits")
public class ProductController {

    @Operation(
        summary = "Récupérer un produit par ID",
        description = "Récupère les détails complets d'un produit en utilisant son identifiant unique."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Produit trouvé avec succès",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProductResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Produit non trouvé",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(
        @Parameter(description = "ID du produit", required = true, example = "1")
        @PathVariable Long id
    ) {
        // Implementation
    }

    @Operation(
        summary = "Créer un nouveau produit",
        description = "Crée un nouveau produit. Nécessite le rôle ADMIN.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Produit créé avec succès"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Données invalides"
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Non authentifié"
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Non autorisé (rôle ADMIN requis)"
        )
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> createProduct(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Détails du produit à créer",
            required = true,
            content = @Content(
                schema = @Schema(implementation = CreateProductRequest.class)
            )
        )
        @RequestBody @Valid CreateProductRequest request
    ) {
        // Implementation
    }
}
```

---

## 🎨 Annotations Principales

| Annotation | Usage | Exemple |
|------------|-------|---------|
| `@Tag` | Grouper les endpoints | `@Tag(name = "Products")` |
| `@Operation` | Décrire un endpoint | `@Operation(summary = "...")` |
| `@ApiResponses` | Décrire les réponses | `@ApiResponses(value = {...})` |
| `@Parameter` | Décrire un paramètre | `@Parameter(description = "...")` |
| `@Schema` | Décrire un modèle | `@Schema(implementation = ProductResponse.class)` |
| `@SecurityRequirement` | Indiquer l'authentification requise | `@SecurityRequirement(name = "bearerAuth")` |

---

## 📦 Documenter vos DTOs

Ajoutez des annotations dans vos classes DTO :

```java
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Requête de création d'un produit")
public class CreateProductRequest {

    @Schema(
        description = "Nom du produit",
        example = "iPhone 15 Pro",
        required = true,
        minLength = 3,
        maxLength = 100
    )
    private String name;

    @Schema(
        description = "Prix du produit en euros",
        example = "1199.99",
        required = true,
        minimum = "0"
    )
    private Double price;

    @Schema(
        description = "Quantité en stock",
        example = "50",
        defaultValue = "0"
    )
    private Integer stockQuantity;

    // Getters and Setters
}
```

---

## 🔧 Personnalisation Swagger

Vous pouvez personnaliser davantage dans `application.properties` :

```properties
# Trier les opérations par méthode HTTP
springdoc.swagger-ui.operationsSorter=method

# Trier les tags alphabétiquement
springdoc.swagger-ui.tagsSorter=alpha

# Activer le filtre de recherche
springdoc.swagger-ui.filter=true

# Activer "Try it out" par défaut
springdoc.swagger-ui.tryItOutEnabled=true

# Personnaliser le chemin
springdoc.swagger-ui.path=/api-docs

# Désactiver Swagger en production
springdoc.swagger-ui.enabled=${SWAGGER_ENABLED:true}
```

---

## 🎯 Bonnes Pratiques

### ✅ À FAIRE
- ✅ Documenter tous les endpoints publics
- ✅ Fournir des exemples réalistes
- ✅ Décrire tous les codes de réponse possibles
- ✅ Grouper les endpoints par fonctionnalité (Tags)
- ✅ Indiquer les endpoints qui nécessitent une authentification

### ❌ À ÉVITER
- ❌ Laisser des endpoints sans description
- ❌ Oublier de documenter les paramètres
- ❌ Ne pas spécifier les codes d'erreur
- ❌ Utiliser des exemples non réalistes

---

## 🚦 Endpoints de Test Rapides

### Sans Authentification
- `GET /products` - Liste des produits
- `GET /products/{id}` - Détails d'un produit
- `GET /categories` - Liste des catégories
- `POST /auth/register` - Inscription
- `POST /auth/login` - Connexion

### Avec Authentification (User)
- `GET /cart` - Mon panier
- `POST /cart/items` - Ajouter au panier
- `GET /orders` - Mes commandes
- `POST /orders` - Créer une commande

### Avec Authentification (Admin)
- `POST /products` - Créer un produit
- `PUT /products/{id}` - Modifier un produit
- `DELETE /products/{id}` - Supprimer un produit
- `GET /admin/dashboard` - Statistiques

---

## 🔗 Ressources Utiles

- [Documentation SpringDoc](https://springdoc.org/)
- [Annotations OpenAPI](https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Annotations)
- [Spécification OpenAPI 3.0](https://swagger.io/specification/)

---

✨ **Votre Swagger est maintenant configuré et prêt à l'emploi !**
