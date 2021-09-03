package com.ivanicob.userservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ivanicob.userservice.model.User;
import com.ivanicob.userservice.repository.user.UserRepository;
import com.ivanicob.userservice.service.UserService;
import com.ivanicob.userservice.util.exceptions.UserNotFoundException;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository repository;

	/**
	 * @see UserService#findById(Long)
	 * @throws UserNotFoundException 
	 */
	@Override
	public User findById(Long id) throws UserNotFoundException {
		return repository.findById(id).orElseThrow(() -> 
			new UserNotFoundException("User id=" + id + " not found"));
	}
	
	/**
	 * @see UserService#findAll()
	 */
	@Override
	public List<User> findAll() {
		return (List<User>) repository.findAll();
	}
	
	/**
	 * @see UserService#save()
	 */		
	@Override
	public User save(User user) {
		return repository.save(user);
	}

	
	/**
	 * @see UserService#deleteById(Long)
	 */
	@Override
	public void deleteById(Long userId) {
		repository.deleteById(userId);		
	}
	
}
