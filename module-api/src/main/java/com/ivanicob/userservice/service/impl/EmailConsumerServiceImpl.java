package com.ivanicob.userservice.service.impl;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ivanicob.userservice.dto.controller.EmailQueueDTO;
import com.ivanicob.userservice.dto.controller.EmailSentDTO;
import com.ivanicob.userservice.dto.controller.EmailSentQueueDTO;
import com.ivanicob.userservice.service.EmailConssumerService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class EmailConsumerServiceImpl implements EmailConssumerService {
    
	@Autowired
	private EmailProcessServiceImpl emailProcessController;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Value("${queue.mail.waiting}")
	private String nameQueueWaiting;

	@Value("${queue.mail.sent}")
	private String nameQueueSent;

	//@HystrixCommand(fallbackMethod = "republicOnMessage")
	@RabbitListener(queues="${queue.mail.waiting}")
    public void onMessage(Message message) throws JsonParseException, JsonMappingException, IOException  {
		
		String messageBody = new String(message.getBody(), "UTF-8");
		
		log.info("Message received: " + messageBody);
		
		//workaround
		messageBody = messageBody.replace("\"links\":{\"empty\":true},", "");
		
		ObjectMapper mapper = new ObjectMapper();
		EmailQueueDTO emailQueueDTO = mapper.readValue(messageBody, EmailQueueDTO.class);
		
		//TODO check if user is ADMIN

		EmailSentQueueDTO emailSentQueueDTO = emailProcessController.sentToKafkaQueue(emailQueueDTO);

		EmailSentDTO emailSentDTO = new EmailSentDTO();
		emailSentDTO.setEmailQueueDTO(emailQueueDTO);
		emailSentDTO.setSentMailOK(emailSentQueueDTO.isSentMailOK());
		emailSentDTO.setMessage(emailSentQueueDTO.getMessage());

		ObjectMapper objectMapper = new ObjectMapper();
		String emailSent = objectMapper.writeValueAsString(emailSentDTO);

		rabbitTemplate.convertAndSend(nameQueueSent, emailSent);
    }

	public void republicOnMessage(Message message) throws JsonParseException, JsonMappingException, IOException  {
		log.info("Republic message...");
		rabbitTemplate.convertAndSend(nameQueueWaiting, message);
	}
}

