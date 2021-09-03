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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends RepresentationModel<UserDTO> {
	
	@Getter
	private Long id;
	
	@Getter
	@NotNull(message = "Name is required.")
	@Length(min=3, max=255, message="Name must contain between 3 and 255 characters.")
	private String name;
	
	@Getter
	@NotNull(message = "Login is required.")
	@Length(min=3, max=60, message="Login must contain between 3 and 60 characters.")
	private String login;	
	
	@NotNull(message = "Password is required.")
	@Length(min=6, message="Password must contain at least 6 characters.")
	private String password;
	
	@Getter
	@NotNull(message="Email is required.")
	@Length(max=80, message="Email must be a maximum of 80 characters.")
	@Email(message="Invalid email.")
	private String email;
	
	@Getter
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
	
	public User convertDTOToEntity(User user) {
		user.setName(this.getName());
		user.setLogin(this.getLogin());
		user.setEmail(this.getEmail());
		user.setPassword(BcryptUtil.getHash(this.password));
		user.setRole(RoleEnum.forName(this.getRole()));
		
		return user;
	}	
}
