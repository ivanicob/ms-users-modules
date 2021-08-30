package com.ivanicob.userservice.service;

import java.io.IOException;

import com.ivanicob.userservice.dto.controller.EmailQueueDTO;
import com.ivanicob.userservice.dto.controller.EmailSentQueueDTO;

public interface EmailProcessService {

	public EmailSentQueueDTO sentToKafkaQueue(EmailQueueDTO emailQueueDTO) throws IOException;	
	
}
