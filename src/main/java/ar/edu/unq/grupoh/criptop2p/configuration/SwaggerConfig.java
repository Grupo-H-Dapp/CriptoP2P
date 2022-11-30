package ar.edu.unq.grupoh.criptop2p.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Configuration
public class SwaggerConfig {

    private ApiInfo apiInfo() {
        return new ApiInfo("CriptoP2P",
                "APIs for CriptoP2P App.",
                "1.0",
                "Terms of service",
                new Contact("test", "www.org.com", "test@emaildomain.com"),
                "License of API",
                "API license URL",
                Collections.emptyList());
    }

    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return new ArrayList<>(
                Collections.singleton(
                        new SecurityReference("JWT", authorizationScopes))
        );
    }

    private ApiKey bearerToken() {
        return new ApiKey("JWT", "Authorization", "header");
    }
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .securitySchemes(new ArrayList<>(Collections.singleton(bearerToken())))
                .securityContexts(new ArrayList<>(Collections.singleton(securityContext())))
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("ar.edu.unq.grupoh.criptop2p.webservice"))
                .paths(PathSelectors.any())
                .build();
    }
}
