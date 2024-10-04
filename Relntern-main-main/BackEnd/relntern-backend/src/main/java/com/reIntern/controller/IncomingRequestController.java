package com.reIntern.controller;

import com.reIntern.model.FileUpload;
import com.reIntern.model.IncomingRequest;
import com.reIntern.service.FileUploadService;
import com.reIntern.service.IncomingRequestService;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/incoming-request")
@CrossOrigin(origins = "http://localhost:4200")
public class IncomingRequestController {
	
    // Initialize the logger
    private static final Logger logger = LoggerFactory.getLogger(IncomingRequestController.class);

    @Autowired
    private IncomingRequestService incomingRequestService;

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/add")
    public ResponseEntity<String> addIncomingRequest(
            @RequestParam("id") int id,
            @RequestParam("fullname") String fullname,
            @RequestParam("association") String association,
            @RequestParam("endDate") String endDate,
            @RequestParam("uploads") List<MultipartFile> uploads) {

        try {
            if (uploads == null || uploads.isEmpty()) {
                return new ResponseEntity<>("No files uploaded.", HttpStatus.BAD_REQUEST);
            }

            IncomingRequest incomingRequest = new IncomingRequest();
            incomingRequest.setId(id);
            incomingRequest.setFullname(fullname);
            incomingRequest.setAssociation(association);
            incomingRequest.setEndDate(endDate);

            List<FileUpload> fileUploadList = new ArrayList<>();
            for (MultipartFile file : uploads) {
                if (file.isEmpty()) {
                    continue;
                }
                FileUpload fileUpload = new FileUpload();
                fileUpload.setFileName(file.getOriginalFilename());
                fileUpload.setFileData(file.getBytes());
                fileUploadList.add(fileUpload);
            }

            incomingRequest.setFiles(fileUploadList);
            incomingRequestService.saveIncomingRequest(incomingRequest);

            return new ResponseEntity<>("Incoming Request and files saved successfully.", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while saving Incoming Request", e);
            return new ResponseEntity<>("Error while saving Incoming Request.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to download a file
    @GetMapping("/files/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
        try {
            Optional<FileUpload> fileUpload = fileUploadService.getFileByName(fileName);
            if (fileUpload.isPresent()) {
                String fileType = getFileType(fileName);
                return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.parseMediaType(fileType))
                    .body(fileUpload.get().getFileData());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Helper method to determine file MIME type based on file extension
    private String getFileType(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        switch (extension) {
            case "pdf": return "application/pdf";
            case "ppt": return "application/vnd.ms-powerpoint";
            case "pptx": return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            case "zip": return "application/zip";
            case "txt": return "text/plain";
            default: return "application/octet-stream";
        }
    }
    
    @GetMapping("/all")
    public List<IncomingRequest> getAllIncomingRequests() {
        return incomingRequestService.getAllIncomingRequests();
    }
}