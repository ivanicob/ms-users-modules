package com.ivanicob.userservice.controller.v1.user;

import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivanicob.userservice.dto.controller.EmailResponse;
import com.ivanicob.userservice.dto.controller.EmailResponseWeb;
import com.ivanicob.userservice.dto.model.user.UserEmailDTO;

@CrossOrigin
@RequestMapping
@RestController
public class EmailProducerController {
    
	@Autowired
    private RabbitTemplate rabbitTemplate;
	
	@Value("${queue.mail.waiting}")
	private String nameQueue;
	    
    @PostMapping("/email")
    public ResponseEntity<EmailResponseWeb> recieveEmailRequest(@Valid @NotNull @RequestBody UserEmailDTO userEmailDTO) throws Exception {
    	
    	EmailResponse emailResponse = new EmailResponse();
    	emailResponse.setKey(UUID.randomUUID().toString());
		emailResponse.setUserEmailDTO(userEmailDTO);

		ObjectMapper obj = new ObjectMapper();

		String payload = obj.writeValueAsString(emailResponse);
		
		rabbitTemplate.convertAndSend(nameQueue, payload);
		
		EmailResponseWeb retorno = new EmailResponseWeb();
		retorno.setMessage("Request registered successfully. Wait for confirmation of processing.");
		retorno.setKeySearch(emailResponse.getKey());
		
		return new ResponseEntity<EmailResponseWeb>(retorno, HttpStatus.OK);
	}
}

