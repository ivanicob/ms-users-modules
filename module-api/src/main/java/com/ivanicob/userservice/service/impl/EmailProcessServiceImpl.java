package com.ivanicob.userservice.service.impl;

import java.io.IOException;
import java.util.Random;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ivanicob.userservice.dto.controller.EmailQueueDTO;
import com.ivanicob.userservice.dto.controller.EmailSentQueueDTO;
import com.ivanicob.userservice.dto.controller.MessageResponseDTO;
import com.ivanicob.userservice.dto.controller.ProjectStatusChangeDTO;
import com.ivanicob.userservice.service.EmailProcessService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class EmailProcessServiceImpl implements EmailProcessService {
    
	@Value("${email.message.link}")
	private String kafkaLink;

	public EmailSentQueueDTO sentToKafkaQueue(EmailQueueDTO emailQueueDTO) throws IOException {
		
		ProjectStatusChangeDTO projectStatusChange = new ProjectStatusChangeDTO();
		projectStatusChange.setId(new Random().nextLong());
		projectStatusChange.setProductName("MS Users Modules");
		projectStatusChange.setAuthorEmailAddress(emailQueueDTO.getUserEmailQueueDTO().getEmail());
		projectStatusChange.setAuthorFullName(emailQueueDTO.getUserEmailQueueDTO().getName());
		
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ProjectStatusChangeDTO> entity = new HttpEntity<ProjectStatusChangeDTO>(projectStatusChange, headers);

		try {
			
			ResponseEntity<MessageResponseDTO> messageResponse = restTemplate.exchange(kafkaLink, HttpMethod.POST, entity, MessageResponseDTO.class);
			if(messageResponse != null)
				log.info("StatusCode: " + messageResponse.getStatusCode());

			return new EmailSentQueueDTO("Email sent with success!", true);
			
		}catch(HttpClientErrorException ex){
			if( ex.getStatusCode() == HttpStatus.BAD_REQUEST ) {
				log.info("Error sent email to Kafka Queue");
				ObjectMapper mapper = new ObjectMapper();
				MessageResponseDTO obj = mapper.readValue(ex.getResponseBodyAsString(), MessageResponseDTO.class);
				return new EmailSentQueueDTO(obj.getMensagem(), false);
			}
			throw ex;
		}catch (RuntimeException ex) {
			throw ex;
		}

	}
}

