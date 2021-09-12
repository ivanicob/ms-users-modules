package com.ivanicob.userservice.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.modelmapper.ModelMapper;

import com.ivanicob.userservice.dto.model.user.UserDTO;
import com.ivanicob.userservice.enums.RoleEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(catalog = "modules", name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 203841311828749559L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NonNull
	private String name;
	
	@NonNull
	private String login;	
	
	@JsonIgnore
	private String password;
	
	@NonNull
	private String email;
	
	@CreationTimestamp
	private LocalDateTime createdDate;
	
	@UpdateTimestamp	
	private LocalDateTime updatedDate;	
	
	@Enumerated(EnumType.STRING)
	private RoleEnum role;
	
	@Transient
	private String token;	
		
	public User(Long id) {
		this.id = id;	
	}
	
	public boolean isAdmin() {
		return RoleEnum.ROLE_ADMIN.toString().equals(this.role.toString());
	}
	
	public UserDTO convertEntityToDTO() {
		return new ModelMapper().map(this, UserDTO.class);
	}
	
	public User(String name, String login, String password, String email, RoleEnum role) {
		this.name = name;
		this.login = login;
		this.password = password;
		this.email = email;
		this.role = role;
	}

	

}
