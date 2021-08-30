package com.ivanicob.userservice.dto.model.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ivanicob.userservice.enums.RoleEnum;
import com.ivanicob.userservice.model.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserEmailDTO extends RepresentationModel<UserEmailDTO> {
			
	@NotNull(message = "Name is required.")
	@Length(min=3, max=60, message="Name must contain between 3 and 60 characters.")
	private String name;	
		
	@NotNull(message="Email is required.")
	@Length(max=80, message="Email must be a maximum of 80 characters.")
	@Email(message="Invalid email.")
	private String email;
	
	@NotNull(message="The User Email access role is required.")
	@Pattern(regexp="^(ROLE_ADMIN|ROLE_USER)$", message="For the access role only the values ROLE_ADMIN or ROLE_USER are accepted.")
	private String role;
				
	@ApiModelProperty(hidden = true)
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	public void setLinks(final Link... links) {
	    super.add(links);
	}	
	
	public User convertDTOToEntity() {
		return new ModelMapper().map(this, User.class);
	}
	
	public User convertDTOToEntity(User user) {
		user.setName(this.getName());
		user.setEmail(this.getEmail());
		user.setRole(RoleEnum.forName(this.getRole()));
		
		return user;
	}	
}
