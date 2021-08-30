package com.ivanicob.feedbackservice.controller;

import com.ivanicob.feedbackservice.config.KafkaProperties;
import com.ivanicob.feedbackservice.dto.ProjectStatusChangeDTO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RequestMapping
@RestController
public class GatewayKafkaController {
    
    private KafkaTemplate<String, ProjectStatusChangeDTO> kakfaProducer;
    private KafkaProperties kafkaProperties;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendProjectStatusEmail(@RequestBody ProjectStatusChangeDTO statusChange) {
        log.info("Sending mailing request: " + statusChange.toString());
        kakfaProducer.send(kafkaProperties.getTopics().getProjectStatusChanged(), statusChange);
    }
}
