package com.reIntern.service;

import java.sql.Date;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.reIntern.model.Mentor;
import com.reIntern.utils.DeactivateEmailUtils;
import com.reIntern.utils.EmailUtils;
import com.reIntern.utils.HRemailUtils;
import com.reIntern.utils.MentorEmailUtils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;  // Import Gson classes
import com.google.gson.JsonSyntaxException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Async
    public void sendEmail(String mail, String fullname, String association, String projectname, String mentor, Mentor mentorDetails, Date startdate, Date enddate, String location) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromMail);
        helper.setTo(mail);
        helper.setSubject("Internship Confirmation mail");
        
        // Get l1email from mentorDetails
        String l1email = extractL1EmailFromMentorDetails(mentorDetails);
        
        // Add l1email to CC
        if (l1email != null && !l1email.isEmpty()) {
            helper.addCc(l1email);
        }
        
        // Set email content as HTML
        helper.setText(EmailUtils.getEmailMessage(fullname, association, projectname, mentor, mentorDetails, startdate, enddate, location), true);

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
        
//        // Get l1email from mentorDetails
//        String l1email = extractL1EmailFromMentorDetails(mentorDetails);
//        
//        // Add l1email to CC
//        if (l1email != null && !l1email.isEmpty()) {
//            helper.addCc(l1email);
//        }
//        
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
        
        // CC can be added here if needed, with logic to extract from mentorDetails
        // Example: helper.addCc(l1email);

        // Set email content as HTML
        helper.setText(DeactivateEmailUtils.getDeactivateEmailMessage(fullname, startdate, enddate, domainid), true);

        mailSender.send(message);
    }

    @Async
    public void sendEmailtoMentor(String fullname, String email, Date startDate, Date endDate, String domainId, String projectname, String mentor, Mentor mentorDetails) throws MessagingException {
        String to = "abc@gmail.com";  // Change to actual Mentor email
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromMail);
        helper.setTo(to);
        helper.setSubject("Request for Internship Certificate - " + fullname);

        // Get l1email directly from the Mentor object
        String l1email = extractL1EmailFromMentorDetails(mentorDetails);

        // Add l1email to CC
        if (l1email != null && !l1email.isEmpty()) {
            helper.addCc(l1email);
        }

        // Set email content as HTML
        helper.setText(MentorEmailUtils.getMentorEmailUtils(fullname, email, startDate, endDate, domainId, projectname, mentor, mentorDetails), true);

        mailSender.send(message);
    }

    
    private Mentor convertJsonStringToMentor(String mentorJson) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(mentorJson, Mentor.class); // Convert JSON string to Mentor object
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null; // Handle JSON parsing error
        }
    }

//    private String extractL1EmailFromMentorDetails(Mentor mentorDetails) {
//        try {
//            JsonObject jsonObject = JsonParser.parseString(mentorDetails).getAsJsonObject();
//            return jsonObject.get("l1email").getAsString(); // Adjust key as necessary
//        } catch (Exception e) {
//            // Handle parsing error
//            return null;
//        }
//    }
    private String extractL1EmailFromMentorDetails(Mentor mentorDetails) {
        if (mentorDetails != null) {
            return mentorDetails.getL1email(); // Directly access the l1email field
        }
        return null; // Return null if mentorDetails is null
    }

}
