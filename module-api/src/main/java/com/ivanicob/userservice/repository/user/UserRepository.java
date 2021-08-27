package com.ivanicob.userservice.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ivanicob.userservice.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByEmail(String email);
	
	User findByLogin(String login);
	
}

