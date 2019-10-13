package com.guru.sishyan.service;

import com.guru.sishyan.models.HubRequest;
import com.guru.sishyan.repository.HubRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HubRequestService {
    @Autowired
    HubRequestRepository hubRequestRepository;

    public void save(HubRequest hubRequest) {
        hubRequestRepository.save(hubRequest);
    }
}
