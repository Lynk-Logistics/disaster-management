package com.guru.sishyan.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document
public class Request {
    String description;
    Coordinate location;
    String area;
    Boolean isResolved=false;
    RequestType requestType;
    Hub hub=null;

}
