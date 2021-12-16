package com.example.demo.model.dto;

public class RoleDTO {
    private String role;

    public RoleDTO() {
    }

    public String getRoleName() {
        return role;
    }

    public RoleDTO setRoleName(String role) {
        this.role = role;
        return this;
    }
}
