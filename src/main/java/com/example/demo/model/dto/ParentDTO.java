package com.example.demo.model.dto;

import com.example.demo.model.entity.ChildEntity;

import java.util.List;

public class ParentDTO {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;

    public ParentDTO() {
    }

    public Long getId() {
        return id;
    }

    public ParentDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public ParentDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public ParentDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ParentDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ParentDTO setEmail(String email) {
        this.email = email;
        return this;
    }
}
