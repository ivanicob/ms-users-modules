package com.ivanicob.userservice.dto.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ivanicob.userservice.dto.model.user.UserEmailDTO;
import com.ivanicob.userservice.dto.model.user.UserEmailQueueDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailQueueDTO {

    private String key;
    private UserEmailQueueDTO userEmailQueueDTO;
    
	public void setUserEmailDTO(UserEmailDTO userEmailDTO) {
		this.userEmailQueueDTO  = new UserEmailQueueDTO(userEmailDTO.getName(), userEmailDTO.getEmail(), userEmailDTO.getRole());
	}

}
