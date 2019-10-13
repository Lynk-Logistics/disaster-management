package com.guru.sishyan.models;

import lombok.Getter;

import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@Document(collection = "Hubs")
public class Hub implements Serializable {

    @Id
    String id;

    String placeId;

    String ngoName;

    Map<String, Integer> resourceDetails;

}
