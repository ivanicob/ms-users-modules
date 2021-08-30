package com.ivanicob.userservice.dto.controller;

import com.ivanicob.userservice.dto.model.user.UserEmailDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailResponse {

    private String key;
    private UserEmailDTO userEmailDTO;

}
