package com.springboot.bowling.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * Swagger Configurations
 *
 * @see <a href="http://localhost:8080/swagger-ui/index.html#/">Swagger Documentation</a>
 * when the app is running to read documentation and test endpoints
 */
@Configuration
public class SwaggerConfig {

    private ApiInfo apiInfo(){
        return new ApiInfo(
                "Bowling Api",
                "A web service server that responds to specific API calls that will simulate a bowling game server",
                "1",
                "",
                new Contact("Jonathan Ruel", "https://www.linkedin.com/in/jonruel/", ""),
                "",
                "",
                Collections.emptyList()
        );
    }

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

}
