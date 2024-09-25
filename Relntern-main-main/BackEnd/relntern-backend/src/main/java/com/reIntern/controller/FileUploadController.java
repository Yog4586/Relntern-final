//package com.reIntern.controller;
//
//import com.reIntern.model.FileUpload;
//import com.reIntern.model.IncomingRequest;
//import com.reIntern.service.FileUploadService;
//import com.reIntern.service.IncomingRequestService;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("/file-upload")
//@CrossOrigin(allowedHeaders = "*", origins = "http://localhost:4200")
//public class FileUploadController {
//
//    @Autowired
//    private FileUploadService fileUploadService;
//
//    @Autowired
//    private IncomingRequestService incomingRequestService;  // To link files with IncomingRequest
//
//    // Endpoint to upload multiple files with a given IncomingRequest ID
//    @PostMapping("/upload/{incomingRequestId}")
//    public void uploadFiles(
//        @PathVariable("incomingRequestId") int incomingRequestId, 
//        @RequestParam("files") MultipartFile[] files
//    ) {
//        IncomingRequest incomingRequest = incomingRequestService.getIncomingRequestById(incomingRequestId);
//        
//        if (incomingRequest != null) {
//            for (MultipartFile file : files) {
//                try {
//                    FileUpload fileUpload = new FileUpload();
//                    fileUpload.setFileName(file.getOriginalFilename());
//                    fileUpload.setFileData(file.getBytes());
//                    fileUpload.setIncomingRequest(incomingRequest);  // Link the file to the IncomingRequest
//
//                    fileUploadService.saveFileUpload(fileUpload);  // Save each file
//                } catch (Exception e) {
//                    e.printStackTrace();  // Handle exceptions
//                }
//            }
//        } else {
//            throw new RuntimeException("IncomingRequest with ID " + incomingRequestId + " not found.");
//        }
//    }
//
//    // Optional: Fetch all files related to an IncomingRequest
//    @GetMapping("/files/{incomingRequestId}")
//    public List<FileUpload> getFilesByIncomingRequest(@PathVariable int incomingRequestId) {
//        return fileUploadService.getFilesByIncomingRequestId(incomingRequestId);
//    }
//}