package com.esia.big_shop_backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI bigShopOpenAPI() {

        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("Big Shop Backend API")
                        .description("""
                                plateforme e-commerce Big Shop.

                                ## Fonctionnalités principales:
                                -  Authentification JWT
                                -  Gestion des utilisateurs et rôles
                                - ️ Gestion des produits et catégories
                                -  Panier d'achat
                                -  Gestion des commandes
                                -  Paiements multiples (Stripe)
                                -  Tableau de bord admin

                                ## Authentification:
                                1. Créez un compte via `/auth/register`
                                2. Connectez-vous via `/auth/login` pour obtenir un token JWT
                                3. Utilisez le token dans le header `Authorization: Bearer <token>`
                                4. Cliquez sur le bouton  "Authorize" en haut à droite
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Big Shop Team")
                                .email("support@bigshop.com")
                                .url("https://bigshop.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Serveur de développement local"),
                        new Server()
                                .url("https://api.bigshop.com")
                                .description("Serveur de production")
                ))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Entrez le token JWT obtenu après login. Format: Bearer <token>")
                        )
                );
    }
}
