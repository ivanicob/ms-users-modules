package com.ivanicob.userservice.dto.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailSentDTO {

    private boolean sentMailOK;
    private EmailQueueDTO emailQueueDTO;
    private String message;

}
