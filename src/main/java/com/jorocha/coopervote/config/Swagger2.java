package com.jorocha.coopervote.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class Swagger2 {
	
	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
        	.apis(RequestHandlerSelectors.basePackage("com.jorocha.coopervote.resources"))
            .paths(PathSelectors.any())
            .build()            
            .pathMapping("/")
            .apiInfo(apiInfo())
            .useDefaultResponseMessages(false)
            .globalResponses(HttpMethod.GET, Arrays.asList(
                new ResponseBuilder().code("500")
                    .description("500 message").build(),
                new ResponseBuilder().code("403")
                    .description("Forbidden").build()
            ));
    } 
	
    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("REST API - COOPERVOTE", "", "", "", new Contact("Jossane Rocha", "https://www.linkedin.com/in/jossane-rocha/", ""), "", "", Collections.emptyList());
        return apiInfo;
    }	
}