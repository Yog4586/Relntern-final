package com.reIntern.service;

import com.reIntern.model.FileUpload;
import com.reIntern.repository.FileUploadRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileUploadService {

    @Autowired
    private FileUploadRepository fileUploadRepository;

    public Optional<FileUpload> getFileByName(String fileName) {
        return fileUploadRepository.findByFileName(fileName);
    }

    public void saveFileUpload(FileUpload fileUpload) {
        fileUploadRepository.save(fileUpload);
    }
}


