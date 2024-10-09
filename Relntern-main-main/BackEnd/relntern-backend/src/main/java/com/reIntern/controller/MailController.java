package com.reIntern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reIntern.model.MailStructure;
import com.reIntern.service.EmailService;

import jakarta.mail.MessagingException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendToIntern")
    public void sendEmail(@RequestBody MailStructure mailStructure) throws MessagingException {
        // Extract details from mailStructure and send email
        emailService.sendEmail(
            mailStructure.getEmail(), 
            mailStructure.getFullname(), 
            mailStructure.getAssociation(), 
            mailStructure.getProjectname(), 
            mailStructure.getMentor(),
            mailStructure.getStartDate(), 
            mailStructure.getEndDate(),
            mailStructure.getLocation()
        );
    }

    @PostMapping("/sendToHR")
    public void sendEmailToHR(@RequestBody MailStructure mailStructure) throws MessagingException {
        // Extract details from mailStructure and send email to HR
        emailService.sendEmailtoHR(
            mailStructure.getFullname(), 
            mailStructure.getEmail(), 
            mailStructure.getStartDate(), 
            mailStructure.getEndDate(), 
            mailStructure.getDomainId(), 
            mailStructure.getProjectname(), 
            mailStructure.getMentor()
        );
    }
    
    @PostMapping("/sendToMentor")
    public void sendEmailToMentor(@RequestBody MailStructure mailStructure) throws MessagingException {
        // Extract details from mailStructure and send email to mentor
        emailService.sendEmailtoMentor(
            mailStructure.getFullname(), 
            mailStructure.getEmail(), 
            mailStructure.getStartDate(), 
            mailStructure.getEndDate(), 
            mailStructure.getDomainId(), 
            mailStructure.getProjectname(), 
            mailStructure.getMentor()
        );
    }

    @PostMapping("/sendDeactivate")
    public void sendEmailToDeactivate(@RequestBody MailStructure mailStructure) throws MessagingException {
        // Extract details from mailStructure and send email to deactivate account
        emailService.sendEmailtoDeactivate(
            mailStructure.getFullname(), 
            mailStructure.getStartDate(), 
            mailStructure.getEndDate(), 
            mailStructure.getDomainId()
        );
    }
}