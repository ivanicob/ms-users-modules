package com.ivanicob.userservice.util.security;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ivanicob.userservice.enums.RoleEnum;
import com.ivanicob.userservice.model.User;
import com.ivanicob.userservice.repository.user.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class JwtUtil {
	
    @Autowired
    private UserRepository repository;
    
	@Autowired
	private PasswordEncoder bcryptEncoder;  	

    private String secret = "ivanicob@2021";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    public void initUsers() {
    	
        List<User> users = Stream.of(
                new User("Ivan Carlos", "ivan", bcryptEncoder.encode("secret456"), "ivanicob@gmail.com", RoleEnum.ROLE_ADMIN),
                new User("Andréa Freitas", "andrea", bcryptEncoder.encode("secret123"), "andrea@gmail.com", RoleEnum.ROLE_USER),
                new User("Luana Carlos", "luana", bcryptEncoder.encode("secret789"), "luana@gmail.com", RoleEnum.ROLE_USER),
                new User("Suely Therezinha", "suely", bcryptEncoder.encode("secret890"), "suely@gmail.com", RoleEnum.ROLE_ADMIN)
        ).collect(Collectors.toList());
        
        if(!repository.existsById(1L)) {
        	repository.saveAll(users);
        	log.info("Save success!");
        }
    }    
}