package com.ivanicob.userservice.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ivanicob.userservice.model.User;
import com.ivanicob.userservice.util.exceptions.UserNotFoundException;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
    @Autowired
    private UserService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    	User user = null;
    	
		try {
			user = service.findByLogin(username);
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
    	
        if (user == null)
            throw new UsernameNotFoundException("No user found with username: " + username);
			
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), new ArrayList<>());
    }
}