package com.crud.tasks.service;

import com.crud.tasks.trello.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8080/tasks_frontend");
        context.setVariable("button", "Visit website");
        context.setVariable("show_button", true);
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("admin_company", adminConfig.getAdminCompany());
        context.setVariable("goodbye", "Thank you for using our services!");
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildFormattedScheduledEmail(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8080/tasks_frontend");
        context.setVariable("button", "Visit website");
        context.setVariable("show_button", false);
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("admin_company", adminConfig.getAdminCompany());
        context.setVariable("goodbye", "Next mail will be sent in 24 hours");
        return templateEngine.process("mail/formatted-scheduled-mail", context);
    }
}
