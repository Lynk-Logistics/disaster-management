package com.guru.sishyan.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Coordinate implements Serializable {
    Double latitude, longitude;
}
