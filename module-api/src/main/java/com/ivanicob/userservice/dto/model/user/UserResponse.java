package com.ivanicob.userservice.dto.model.user;

import com.ivanicob.userservice.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserResponse {
	
	private Long id;
	private String name;
	private String login;	
	private String email;
	private String role;
	private String token;
			
	public UserResponse convertEntityToUserResponse(User user, String token) {
		this.setId(user.getId());
		this.setName(user.getName());
		this.setLogin(user.getLogin());
		this.setEmail(user.getEmail());
		this.setRole(user.getRole().getValue());
		this.setToken(token);
		
		return this;
	}	
}
