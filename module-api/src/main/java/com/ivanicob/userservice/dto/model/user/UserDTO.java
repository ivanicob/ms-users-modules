package com.ivanicob.userservice.dto.model.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import com.ivanicob.userservice.enums.RoleEnum;
import com.ivanicob.userservice.model.User;
import com.ivanicob.userservice.util.security.BcryptUtil;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends RepresentationModel<UserDTO> {
	
	private Long id;
	
	@NotNull(message = "Name is required.")
	@Length(min=3, max=255, message="Name must contain between 3 and 255 characters.")
	private String name;
	
	@NotNull(message = "Login is required.")
	@Length(min=3, max=60, message="Login must contain between 3 and 60 characters.")
	private String login;	
	
	@NotNull(message = "Password is required.")
	@Length(min=6, message="Password must contain at least 6 characters.")
	private String password;
	
	@NotNull(message="Email is required.")
	@Length(max=80, message="Email must be a maximum of 80 characters.")
	@Email(message="Invalid email.")
	private String email;
	
	@NotNull(message="The User access role is required.")
	@Pattern(regexp="^(ROLE_ADMIN|ROLE_USER)$", 
		message="For the access role only the values ROLE_ADMIN or ROLE_USER are accepted.")
	private String role;
			
	@ApiModelProperty(hidden = true)
	public void setLinks(final Link... links) {
	    super.add(links);
	}	
	
	public User convertDTOToEntity() {
		return new ModelMapper().map(this, User.class);
	}
	
	public User convertDTOToEntity(User userToUpdate) {
		userToUpdate.setName(this.getName());
		userToUpdate.setLogin(this.getLogin());
		userToUpdate.setEmail(this.getEmail());
		userToUpdate.setPassword(BcryptUtil.getHash(this.password));
		userToUpdate.setRole(RoleEnum.forName(this.getRole()));
		
		return userToUpdate;
	}	
}
