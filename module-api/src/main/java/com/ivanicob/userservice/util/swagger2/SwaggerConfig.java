package com.ivanicob.userservice.util.swagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Component
@Order(1)
@EnableSwagger2
//@EnableSwagger2WebFlux
@PropertySource({"classpath:swagger.properties"})
@ConditionalOnResource(resources = {"classpath:swagger.properties"})
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {
	
	@Value("${springfox.documentation.swagger.v2.path}")
	private String swagger2Endpoint;

	private ApiKey apiKey() { 
	    return new ApiKey("JWT", "Authorization", "header"); 
	}
	
	private SecurityContext securityContext() { 
	    return SecurityContext.builder().securityReferences(defaultAuth()).build(); 
	} 

	private List<SecurityReference> defaultAuth() { 
	    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything"); 
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1]; 
	    authorizationScopes[0] = authorizationScope; 
	    return Arrays.asList(new SecurityReference("JWT", authorizationScopes)); 
	}	
	
	@Primary
    @Bean
    public LinkDiscoverers discoverers() {
        List<LinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new CollectionJsonLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
    }	
	
    @Bean
    public Docket api() {
    	
        return new Docket(DocumentationType.SWAGGER_2)     		 
        	      .apiInfo(apiInfo())
        	      .securityContexts(Arrays.asList(securityContext()))
        	      .securitySchemes(Arrays.asList(apiKey()))
        	      .select()
  				  .apis(RequestHandlerSelectors.basePackage("com.ivanicob.userservice.controller"))  
  				  .paths(PathSelectors.any())        		
       			  .build();
    }
    
    private ApiInfo apiInfo() {
    	
    	return new ApiInfoBuilder()
    			.title("USER-SERVICE REST API")
    			.description("Development of a REST API for a User CRUD (Create, Read, Update and Delete) using Spring Boot 2, Hibernate, JPA and MySQL.")
    			.version("1.0")
    			.contact(new Contact("Ivan Carlos", "", "ivanicob@gmail.com"))
    			.build();  	
    }
	    
    @Bean
    public DispatcherServlet dispatcherServlet() {
    	return new DispatcherServlet();
    }
  
}
