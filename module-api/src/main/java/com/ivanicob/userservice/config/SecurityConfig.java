package com.ivanicob.userservice.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ivanicob.userservice.filter.JwtFilter;
import com.ivanicob.userservice.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        //return new BCryptPasswordEncoder();
    	return NoOpPasswordEncoder.getInstance();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
        
    private static final String[] AUTH_WHITELIST = {
    		"/api/v1/authenticate", 
			"/api/v1/users/*",
			"/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };  
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
        //.configurationSource(corsConfigurationSource())
        .and()
            .authorizeRequests()
                .antMatchers("/api/v1/authenticate","/api/v1/users/*")
                    .permitAll()
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
                    .permitAll()
                .anyRequest()
                    .authenticated()
                .and()
                    .csrf().disable()
                    .exceptionHandling()
                .and()
                    .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    } 
    
//	    CorsConfigurationSource corsConfigurationSource() {
//	        CorsConfiguration configuration = new CorsConfiguration();
//	        configuration.setAllowedMethods(Arrays.asList(
//	                HttpMethod.GET.name(),
//	                HttpMethod.PUT.name(),
//	                HttpMethod.POST.name(),
//	                HttpMethod.DELETE.name()
//            ));
//
//	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	        source.registerCorsConfiguration("/api/v1/**", configuration.applyPermitDefaultValues());
//	        return source;
//	    }    
//    
		@Bean
		CorsConfigurationSource corsConfigurationSource() {
			CorsConfiguration configuration = new CorsConfiguration();
			configuration.setAllowedOrigins(Arrays.asList("https://example.com"));
			configuration.setAllowedMethods(Arrays.asList("GET","POST"));
			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			source.registerCorsConfiguration("/**", configuration);
			return source;
		} 
	    
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//    	
//    	http.cors().and()
//    			.csrf().disable()
//    			.authorizeRequests()
//    			.antMatchers(AUTH_WHITELIST).permitAll()               
//    			.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//    			.anyRequest().authenticated()
//    			.and().headers()
//    			// the headers you want here. This solved all my CORS problems! 
//    			.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "*"))
//    			.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Methods", "*"))
//    			.addHeaderWriter(new StaticHeadersWriter("Access-Control-Max-Age", "3600"))
//    			.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Credentials", "false"))
//    			.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Headers", "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization"))
//                .and().httpBasic()
//                .and().headers().frameOptions().disable()
//                .and().exceptionHandling()
//                .and().sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);;
//    }
}

