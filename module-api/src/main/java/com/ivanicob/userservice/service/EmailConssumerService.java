package com.ivanicob.userservice.service;

import java.io.IOException;

import org.springframework.amqp.core.Message;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface EmailConssumerService {

	public void onMessage(Message message) throws JsonParseException, JsonMappingException, IOException;	
	
	public void republicOnMessage(Message message) throws JsonParseException, JsonMappingException, IOException;
	
}
