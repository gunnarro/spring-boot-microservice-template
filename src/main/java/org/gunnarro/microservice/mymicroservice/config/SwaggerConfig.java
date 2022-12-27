package org.gunnarro.microservice.mymicroservice.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * TODO Updated service rest uri(s)!
 * Swagger url:
 * <a href="https://localhost:port/api-docs/swagger-ui.html">https://localhost:port/api-docs/swagger-ui.html</a>
 */
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    private static final String BASE_PACKAGE = "org.gunnarro.microservice.mymicroservice";
    
    @Bean
    public Docket serviceApiv10() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("restservice-v1")
            .select()
            .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
            .paths(PathSelectors.regex("/restservice/v1.*"))
            .build()
            .apiInfo(apiInfo("My Service REST API", "API for restservice-v1", "v1.0.0"));
    }
	 
    private ApiInfo apiInfo(String title, String description, String version) {
        return new ApiInfo(
          title, 
          description, 
          version, 
          "Terms of service", 
          new Contact("FTV MicroService Support", "IT BSS Fixed&TV Development", ""),
          "License of API", "API license URL", Collections.emptyList());
   }
}
