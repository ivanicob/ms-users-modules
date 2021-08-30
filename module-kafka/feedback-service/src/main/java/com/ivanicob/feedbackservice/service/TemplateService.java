package com.ivanicob.feedbackservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.ivanicob.feedbackservice.dto.EmailContentDTO;
import com.ivanicob.feedbackservice.dto.ProjectStatusChangeDTO;

@Service
@RequiredArgsConstructor
public class TemplateService {
    private final SpringTemplateEngine templateEngine;

    public EmailContentDTO generateProjectStatusChangeEmail(ProjectStatusChangeDTO projectStatus) {
        Context context = new Context();
        context.setVariable("fullName", projectStatus.getAuthorFullName());
        context.setVariable("productName", projectStatus.getProductName());

        return EmailContentDTO
            .builder()
            .text(templateEngine.process("project-status-change.txt", context))
            .html(templateEngine.process("project-status-change.html", context))
            .build();
    }
}
