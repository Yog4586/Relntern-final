package com.reIntern.service;

import java.sql.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.reIntern.utils.DeactivateEmailUtils;
import com.reIntern.utils.EmailUtils;
import com.reIntern.utils.HRemailUtils;
import com.reIntern.utils.MentorEmailUtils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Async
    public void sendEmail(String mail, String fullname, String association, String projectname, String mentor, Date startdate, Date enddate, String location) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromMail);
        helper.setTo(mail);
        helper.setSubject("Internship Confirmation mail");
        // Set email content as HTML
        helper.setText(EmailUtils.getEmailMessage(fullname, association, projectname, mentor, startdate, enddate, location), true);

        mailSender.send(message);
    }

    @Async
    public void sendEmailtoHR(String fullname, String mail, Date startdate, Date enddate, String domainid, String projectname, String mentor) throws MessagingException {
        String to = "namanbajpai3003@gmail.com";  // Change to actual HR email
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromMail);
        helper.setTo(to);
        helper.setSubject("Request for Internship Certificate - " + fullname);
        // Set email content as HTML
        helper.setText(HRemailUtils.getHRemailMessage(fullname, mail, startdate, enddate, domainid, projectname, mentor), true);

        mailSender.send(message);
    }

    @Async
    public void sendEmailtoDeactivate(String fullname, Date startdate, Date enddate, String domainid) throws MessagingException {
        String to = "shiva9824@outlook.com";  // Change to actual GetIT email
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromMail);
        helper.setTo(to);
        helper.setSubject("Deactivate email account for - " + fullname);
        // Set email content as HTML
        helper.setText(DeactivateEmailUtils.getDeactivateEmailMessage(fullname, startdate, enddate, domainid), true);

        mailSender.send(message);
    }

    @Async
    public void sendEmailtoMentor(String fullname, String email, Date startDate, Date endDate, String domainId, String projectname, String mentor) throws MessagingException {
        String to = "ytiwari077@gmail.com";  // Change to actual Mentor email
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromMail);
        helper.setTo(to);
        helper.setSubject("Request for Internship Certificate - " + fullname);
        // Set email content as HTML
        helper.setText(MentorEmailUtils.getMentorEmailUtils(fullname, email, startDate, endDate, domainId, projectname, mentor), true);

        mailSender.send(message);
    }
}