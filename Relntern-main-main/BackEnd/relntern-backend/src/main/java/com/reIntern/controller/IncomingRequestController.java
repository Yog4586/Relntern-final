package com.reIntern.controller;

import com.reIntern.model.IncomingRequest;
import com.reIntern.service.IncomingRequestService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        // To store the uploaded file paths
        StringBuilder uploadPaths = new StringBuilder();

        // Loop through each file in the uploads list and store it
        for (MultipartFile file : uploads) {
            // Logging the file name
            System.out.println("Received file: " + file.getOriginalFilename());

            try {
                // Define the directory where you want to save the files
                Path path = Paths.get("uploads/" + file.getOriginalFilename());

                // Create directories if they don't exist
                Files.createDirectories(path.getParent());

                // Save the file to the defined path
                Files.write(path, file.getBytes());

                // Append the saved file's path to the uploadPaths StringBuilder
                uploadPaths.append(path.toString()).append(",");
            } catch (Exception e) {
                e.printStackTrace();  // Log or handle the exception appropriately
            }
        }

        // Set the saved file paths (comma-separated) to the uploads field of IncomingRequest
        incomingRequest.setUploads(uploadPaths.toString());

        // Save the IncomingRequest entity to the database
        incomingRequestService.saveIncomingRequest(incomingRequest);
    }
}
