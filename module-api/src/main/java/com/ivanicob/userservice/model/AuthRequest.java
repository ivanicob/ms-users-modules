package com.ivanicob.userservice.model;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

	@NotNull(message = "Login is required.")
	@Length(min=3, max=60, message="Login must contain between 3 and 60 characters.")
    private String login;
	
	@NotNull(message = "Password is required.")
	@Length(min=6, message="Password must contain at least 6 characters.")
    private String password;
      
}