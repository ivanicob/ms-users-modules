package com.ivanicob.userservice.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

	private T data;
	private Object errors;
	
    public void addErrorMsgToResponse(String msgError) {
        ResponseError error = new ResponseError();
        error.setDetails(msgError);
        error.setTimestamp(LocalDateTime.now());
        setErrors(error);
    }
    
}
