package com.ivanicob.userservice.dto.controller;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown=true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {
	
	@NotNull(message = "Name is required.")
	@Length(min=3, max=255, message="Name must contain between 3 and 255 characters.")
	private String name;
		
	@NotNull(message="Email is required.")
	@Length(max=80, message="Email must be a maximum of 80 characters.")
	@Email(message="Invalid email.")
	private String email;
	
	@NotNull(message="The User access role is required.")
	@Pattern(regexp="^(ROLE_ADMIN|ROLE_USER)$", message="For the access role only the values ROLE_ADMIN or ROLE_USER are accepted.")
	private String role;	

}
