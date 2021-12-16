package com.example.demo.model.service;

public class ParentWithoutChildrenServiceModel {
    private String username;

    public ParentWithoutChildrenServiceModel(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public ParentWithoutChildrenServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }
}
