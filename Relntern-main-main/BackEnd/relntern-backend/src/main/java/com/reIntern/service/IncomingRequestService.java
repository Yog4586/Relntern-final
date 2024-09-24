package com.reIntern.service;

import com.reIntern.model.IncomingRequest;
import com.reIntern.repository.IncomingRequestRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomingRequestService {

    @Autowired
    private IncomingRequestRepository incomingRequestRepository;

    public IncomingRequest saveIncomingRequest(IncomingRequest incomingRequest) {
        return incomingRequestRepository.save(incomingRequest);  // Return saved entity
    }

    public List<IncomingRequest> getAllIncomingRequests() {
        return incomingRequestRepository.findAll();
    }
}
