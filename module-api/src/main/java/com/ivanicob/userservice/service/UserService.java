package com.ivanicob.userservice.service;

import java.util.List;

import com.ivanicob.userservice.model.User;
import com.ivanicob.userservice.util.exceptions.UserNotFoundException;

public interface UserService {

	User findById(Long id) throws UserNotFoundException;
	
	List<User> findAll();
	
	User save(User user);
	
	void deleteById(Long userId);	
	
}
