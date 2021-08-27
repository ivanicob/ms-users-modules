package com.ivanicob.userservice;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ivanicob.userservice.enums.RoleEnum;
import com.ivanicob.userservice.model.User;
import com.ivanicob.userservice.repository.user.UserRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication
public class UserServiceApplication {

    @Autowired
    private UserRepository repository;
    
    //@PostConstruct
    public void initUsers() {
        List<User> users = Stream.of(
                new User("Ivan Carlos", "ivan", "secret456", "ivanicob@gmail.com", RoleEnum.ROLE_ADMIN),
                new User("Andréa Freitas", "andrea", "secret123", "andrea@gmail.com", RoleEnum.ROLE_USER),
                new User("Luana Carlos", "luana", "secret789", "luana@gmail.com", RoleEnum.ROLE_USER),
                new User("Suely Therezinha", "suely", "secret890", "suely@gmail.com", RoleEnum.ROLE_USER)
        ).collect(Collectors.toList());
        repository.saveAll(users);
    }
 
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/v1/**")
						.allowedOriginPatterns("http://localhost:4200", "http://localhost:8080");
			}
		};
	}    
    
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
