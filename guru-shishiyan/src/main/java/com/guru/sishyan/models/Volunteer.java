package com.guru.sishyan.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class Volunteer extends User implements Serializable {

    @NotNull
    String Location;

    GeoJsonPoint coordinate;

    @NotNull
    String[] phoneNumbers;

    Boolean isAvailable = true;

    @Range(min = 18)
    String age;

    @NotNull
    String hubId;

    String supplyId;
}
