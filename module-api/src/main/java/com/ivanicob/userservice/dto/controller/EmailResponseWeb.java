package com.ivanicob.userservice.dto.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailResponseWeb {
	
	private String keySearch;
	private String message;

}
