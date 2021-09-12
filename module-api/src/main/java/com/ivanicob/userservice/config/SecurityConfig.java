package com.ivanicob.userservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ivanicob.userservice.filter.JwtFilter;
import com.ivanicob.userservice.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@CrossOrigin(origins = "*")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
    @Autowired
    private CustomUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtFilter jwtFilter;
    
    @Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		// configure AuthenticationManager so that it knows from where to load		
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
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
            "/email",
            "/webjars/**"
    };  
    
  @Override
  protected void configure(HttpSecurity http) throws Exception {
 	  
  	http.cors().and()
  	 		.csrf().disable()
  			.authorizeRequests()
  			.antMatchers(AUTH_WHITELIST).permitAll()               
  			.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
  			.anyRequest().authenticated()
  			.and().headers()
  			// the headers you want here. This solved all my CORS problems! 
  			.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "*"))
  			.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS"))
  			.addHeaderWriter(new StaticHeadersWriter("Access-Control-Max-Age", "3600"))
  			.addHeaderWriter(new StaticHeadersWriter("Access-Control-Expose-Headers", "Authorization"))
  			.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Credentials", "true"))
  			.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Headers", "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization"))
            .and().httpBasic()
            .and().headers().frameOptions().disable()
            .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
      http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);;
  } 
  
}

