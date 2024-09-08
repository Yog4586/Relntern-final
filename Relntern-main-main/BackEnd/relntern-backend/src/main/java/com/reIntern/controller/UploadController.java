package com.reIntern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.reIntern.model.upload;
import com.reIntern.service.UploadService;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "http://localhost:4200")
@RequestMapping("/uploaddocument")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/uploadfile")
    public upload uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("fileType") String fileType,
                             @RequestParam("internId") int internId) {
        return uploadService.uploadFile(file, fileType, internId);
    }
}
