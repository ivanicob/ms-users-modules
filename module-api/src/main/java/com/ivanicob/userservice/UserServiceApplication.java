package com.ivanicob.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@ComponentScan(basePackages = {"com.ivanicob.userservice"})
public class UserServiceApplication {
 
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/api/v1/**")
//						.allowedOriginPatterns("http://localhost:4200", "http://localhost:8086");
//			}
//		};
//	}    
    
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
