package com.reIntern.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class IncomingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fullname;
    private String association;
    private String endDate;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FileUpload> files = new HashSet<>();



    // Other fields...

    // Getters and setters...

    public Set<FileUpload> getFiless() {
		return files;
	}

	public void setFiless(Set<FileUpload> files) {
		this.files = files;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "IncomingRequest [id=" + id + ", fullname=" + fullname + ", association=" + association + ", endDate=" + endDate + "]";
    }
}
