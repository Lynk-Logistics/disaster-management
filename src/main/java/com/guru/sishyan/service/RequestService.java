package com.guru.sishyan.service;


import com.guru.sishyan.models.Request;
import com.guru.sishyan.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestService {
    @Autowired
    RequestRepository requestRepository;

    public void save(Request request) {
        requestRepository.save(request);
    }
}
