package com.reIntern.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MailStructure {
    private String email;
    private String fullname;
    private String association;
    private String projectname;
    private String mentor;
    private Date startDate;
    private Date endDate;
    private String domainId;
    private String location;
    private Mentor mentorDetails;

	// Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getMentor() {
        return mentor;
    }

    public void setMentor(String mentor) {
        this.mentor = mentor;
    }

    public Mentor getMentorDetails() { // Getter for mentorDetails
        return mentorDetails;
    }

    public void setMentorDetails(Mentor mentorDetails) { // Setter for mentorDetails
        this.mentorDetails = mentorDetails;
    }
    
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }
    
    public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}