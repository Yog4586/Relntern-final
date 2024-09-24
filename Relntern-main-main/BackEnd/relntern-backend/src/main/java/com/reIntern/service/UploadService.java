package com.reIntern.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.reIntern.model.upload;
import com.reIntern.repository.UploadRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class UploadService {

    @Autowired
    private UploadRepository uploadRepository;

    public upload uploadFile(MultipartFile file, String fileType, int internId) {
        String fileName = file.getOriginalFilename();

        try {
            // Save file data directly to the database
            upload uploadedFile = new upload();
            uploadedFile.setFileName(fileName);
            uploadedFile.setFileType(fileType);
            uploadedFile.setInternId(internId);
            uploadedFile.setFileData(file.getBytes()); // Store the file as bytes

            return uploadRepository.save(uploadedFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
