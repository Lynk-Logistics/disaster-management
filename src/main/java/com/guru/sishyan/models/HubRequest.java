package com.guru.sishyan.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document
public class HubRequest {
    @Id
    String id;
    Map<String, Integer > requestDetails;
}
