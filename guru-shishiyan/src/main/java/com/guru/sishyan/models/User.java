package com.guru.sishyan.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@Document(collection = "Users")
public class User implements Serializable {

    @Id
    String id;

    String username;

    String password;

    String role;
}
