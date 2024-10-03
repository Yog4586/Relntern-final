package com.reIntern.controller;

import com.reIntern.model.FileUpload;
import com.reIntern.model.IncomingRequest;
import com.reIntern.service.FileUploadService;
import com.reIntern.service.IncomingRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/incoming-request")
@CrossOrigin(origins = "http://localhost:4200")
public class IncomingRequestController {

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
        @RequestParam("uploads") List<MultipartFile> uploads
    ) {
        try {
            if (uploads == null || uploads.isEmpty()) {
                return new ResponseEntity<>("No files uploaded.", HttpStatus.BAD_REQUEST);
            }

            // Create and save the incoming request
            IncomingRequest incomingRequest = new IncomingRequest();
            incomingRequest.setId(id);
            incomingRequest.setFullname(fullname);
            incomingRequest.setAssociation(association);
            incomingRequest.setEndDate(endDate);
            
            List<FileUpload> fileUploadlist = new ArrayList<FileUpload>();
            for (MultipartFile file : uploads) {
                if (file.isEmpty()) {
                    continue; // Skip empty files
                }
                FileUpload fileUpload = new FileUpload();
                fileUpload.setFileName(file.getOriginalFilename());
                fileUpload.setFileData(file.getBytes());
                fileUploadlist.add(fileUpload);
//                fileUpload.setIncomingRequest(savedRequest); // Link file to IncomingRequest

                // Save the file to the database
//                fileUploadService.saveFileUpload(fileUpload);
            }
            incomingRequest.setFiles(fileUploadlist);

            // Save IncomingRequest
            incomingRequestService.saveIncomingRequest(incomingRequest);

            // Loop through each file and store them
           

            return new ResponseEntity<>("Incoming Request and files saved successfully.", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while saving Incoming Request", e);
            return new ResponseEntity<>("Error while saving Incoming Request.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to get all incoming requests along with their associated file uploads
    @GetMapping("/all")
    public List<IncomingRequest> getAllIncomingRequests() {
        return incomingRequestService.getAllIncomingRequests(); 
    }
    
}

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
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("/incoming-request")
//@CrossOrigin(origins = "http://localhost:4200")
//public class IncomingRequestController {
//
//    @Autowired
//    private IncomingRequestService incomingRequestService;
//
//    @Autowired
//    private FileUploadService fileUploadService;
//
//    @PostMapping("/add")
//    public ResponseEntity<String> addIncomingRequest(
//        @RequestParam("id") int id,
//        @RequestParam("fullname") String fullname,
//        @RequestParam("association") String association,
//        @RequestParam("endDate") String endDate,
//        @RequestParam("uploads") List<MultipartFile> uploads
//    ) {
//        try {
//            IncomingRequest incomingRequest = new IncomingRequest();
//            incomingRequest.setId(id);
//            incomingRequest.setFullname(fullname);
//            incomingRequest.setAssociation(association);
//            incomingRequest.setEndDate(endDate);
//
//            // Save IncomingRequest first
//            IncomingRequest savedRequest = incomingRequestService.saveIncomingRequest(incomingRequest);
//
//            // Loop through each file in the uploads list and store them
//            for (MultipartFile file : uploads) {
//                FileUpload fileUpload = new FileUpload();
//                fileUpload.setFileName(file.getOriginalFilename());
//                fileUpload.setFileData(file.getBytes());
//                fileUpload.setIncomingRequest(savedRequest); // Link to IncomingRequest
//
//                // Save the file to the database
//                fileUploadService.saveFileUpload(fileUpload);
//            }
//
//            return new ResponseEntity<>("Incoming Request and files saved successfully.", HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();  // Handle the exception appropriately
//            return new ResponseEntity<>("Error while saving Incoming Request.", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    // Endpoint to get all incoming requests along with their associated file uploads
//    @GetMapping("/all")
//    public List<IncomingRequest> getAllIncomingRequests() {
//        return incomingRequestService.getAllIncomingRequests(); 
//    }
//}
