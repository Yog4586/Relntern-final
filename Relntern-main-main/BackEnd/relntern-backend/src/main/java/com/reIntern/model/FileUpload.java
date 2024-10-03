package com.reIntern.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class FileUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fileName;

    @Lob
    private byte[] fileData;

//    @ManyToOne
//    private IncomingRequest incomingRequest; // Foreign key to the parent IncomingRequest

    // Getters and setters...

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

//    public IncomingRequest getIncomingRequest() {
//        return incomingRequest;
//    }
//
//    public void setIncomingRequest(IncomingRequest incomingRequest) {
//        this.incomingRequest = incomingRequest;
//    }
}
