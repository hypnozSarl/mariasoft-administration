
/*
 * Mariasoft - Enterprise Management Software
 * Copyright (c) [Year of creation] Hypnoz Technologie. All rights reserved.
 *
 * This software and associated documentation files (the "Software") is the
 * proprietary and confidential information of Hypnoz Technologie and is subject
 * to a license agreement. The Software is protected by international copyright
 * and other intellectual property laws and treaties.
 *
 * No part of this Software may be copied, modified, distributed, or reproduced
 * in any form or by any means without prior written permission from Hypnoz Technologie.
 *
 * ANY USE OF THE SOFTWARE NOT EXPRESSLY PERMITTED BY THE TERMS OF THE LICENSE
 * AGREEMENT IS A VIOLATION OF COPYRIGHT LAW AND MAY RESULT IN SEVERE
 * CIVIL AND CRIMINAL PENALTIES.
 */
package net.hypnoz.msadmin.config;


import net.hypnoz.msadmin.utils.MariasoftOpenApiCustomizer;
import net.hypnoz.msadmin.utils.MariasoftProperies;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    public static final String API_FIRST_PACKAGE = "net.mariasoft.administrations.web.api";

    /**
     * Configure et renvoie un bean GroupedOpenApi pour le groupe "openapi".
     *
     * @param mariasoftOpenApiCustomizer Une instance de MariasoftOpenApiCustomizer utilisée pour personnaliser les métadonnées OpenAPI.
     * @param mariasoftProperies Une instance de MariasoftProperies contenant les propriétés de documentation de l'API.
     * @return Le bean GroupedOpenApi pour le groupe "openapi".
     */
    @Bean
    @ConditionalOnMissingBean(name = "apiFirstGroupedOpenAPI")
    public GroupedOpenApi apiFirstGroupedOpenAPI(
        MariasoftOpenApiCustomizer mariasoftOpenApiCustomizer,
        MariasoftProperies mariasoftProperies
    ) {
        MariasoftProperies.ApiDocs properties = mariasoftProperies.getApiDocs();
        return GroupedOpenApi
            .builder()
            .group("openapi")
            .addOpenApiCustomizer(mariasoftOpenApiCustomizer)
            .packagesToScan(API_FIRST_PACKAGE)
            .pathsToMatch(properties.getDefaultIncludePattern())
            .build();
    }

    /**
     * Renvoie un bean GroupedOpenApi pour la documentation de l'API publique.
     *
     * @return Le bean GroupedOpenApi pour l'API publique.
     */
    @Bean
    public GroupedOpenApi publicAPI() {
        return GroupedOpenApi.builder().group("public")
                .pathsToMatch("/public/**")
                .build();
    }

    /**
     * Renvoie un bean GroupedOpenApi pour la documentation de l'API privée.
     *
     * @return Le bean GroupedOpenApi pour l'API privée.
     */
    @Bean
    public GroupedOpenApi privateAPI() {
        return GroupedOpenApi.builder().group("private")
                .pathsToMatch("/private/**")
                .build();
    }

}