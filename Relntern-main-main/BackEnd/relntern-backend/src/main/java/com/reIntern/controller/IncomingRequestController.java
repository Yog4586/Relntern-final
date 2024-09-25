package com.reIntern.controller;

import com.reIntern.model.FileUpload;
import com.reIntern.model.IncomingRequest;
import com.reIntern.service.FileUploadService;
import com.reIntern.service.IncomingRequestService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/incoming-request")
@CrossOrigin(allowedHeaders = "*", origins = "http://localhost:4200")
public class IncomingRequestController {

    @Autowired
    private IncomingRequestService incomingRequestService;

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/add")
    public void addIncomingRequest(
        @RequestParam("id") int id,
        @RequestParam("fullname") String fullname,
        @RequestParam("association") String association,
        @RequestParam("endDate") String endDate,
        @RequestParam("uploads") List<MultipartFile> uploads
    ) {
        IncomingRequest incomingRequest = new IncomingRequest();
        incomingRequest.setId(id);
        incomingRequest.setFullname(fullname);
        incomingRequest.setAssociation(association);
        incomingRequest.setEndDate(endDate);

        // Save IncomingRequest first
        IncomingRequest savedRequest = incomingRequestService.saveIncomingRequest(incomingRequest);

        // Loop through each file in the uploads list and store them
        for (MultipartFile file : uploads) {
            try {
                FileUpload fileUpload = new FileUpload();
                fileUpload.setFileName(file.getOriginalFilename());
                fileUpload.setFileData(file.getBytes());
                fileUpload.setIncomingRequest(savedRequest); // Link to IncomingRequest

                // Save the file to the database
                fileUploadService.saveFileUpload(fileUpload);
            } catch (Exception e) {
                e.printStackTrace();  // Handle the exception appropriately
            }
        }
    }

    // Endpoint to get all incoming requests
    @GetMapping("/all")
    public List<IncomingRequest> getAllIncomingRequests() {
        return incomingRequestService.getAllIncomingRequests();
    }
}
