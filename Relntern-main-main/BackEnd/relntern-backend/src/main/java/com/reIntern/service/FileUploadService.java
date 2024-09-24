package com.reIntern.service;

import com.reIntern.model.FileUpload;
import com.reIntern.repository.FileUploadRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileUploadService {

    @Autowired
    private FileUploadRepository fileUploadRepository;

    public void saveFileUpload(FileUpload fileUpload) {
        fileUploadRepository.save(fileUpload);
    }

    // Additional methods like retrieving files, etc.
}
