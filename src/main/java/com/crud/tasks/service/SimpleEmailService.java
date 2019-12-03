package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailCreatorService mailCreatorService;

    public void send(final Mail mail) {
        LOGGER.info("Starting email preparation...");
        try {
            javaMailSender.send(createMimeMessage(mail));
            LOGGER.info("Email has been sent.");
        } catch (MailException e) {
            LOGGER.error("Failed to process email sending: ", e.getMessage(), e);
        }
    }

    public void sendFormattedUsingScheduler(final Mail mail) {
        LOGGER.info("Starting formatted scheduled email preparation...");
        try {
            javaMailSender.send(createMimeMessageForScheduler(mail));
            LOGGER.info("Formatted scheduled email has been sent.");
        } catch (MailException e) {
            LOGGER.error("Failed to process formatted scheduled email sending: ", e.getMessage(), e);
        }
    }

    public void sendSimpleMail(final Mail mail) {
        LOGGER.info("Starting simple email preparation...");
        try {
            javaMailSender.send(createMailMessage(mail));
            LOGGER.info("Simple email has been sent.");
        } catch (MailException e) {
            LOGGER.error("Failed to process simple email sending: ", e.getMessage(), e);
        }
    }

    private MimeMessagePreparator createMimeMessage(final Mail mail) {
        return mimeMessage -> {
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setTo(mail.getMailTo());
        messageHelper.setSubject(mail.getSubject());
        messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()), true);
        };
    }

    private MimeMessagePreparator createMimeMessageForScheduler(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildFormattedScheduledEmail(mail.getMessage()), true);
        };
    }

    private SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        if (mail.getToCc() != null) {mailMessage.setCc(mail.getToCc());}
        return mailMessage;
    }
}
