package com.example.demo.model.dto;

import java.util.List;
import java.util.stream.Collectors;

public class ParentAndRolesDTO {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private List<RoleDTO> roles;

    public ParentAndRolesDTO() {
    }

    public Long getId() {
        return id;
    }

    public ParentAndRolesDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public ParentAndRolesDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public ParentAndRolesDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ParentAndRolesDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public ParentAndRolesDTO setRoles(List<RoleDTO> roles) {
        this.roles = roles;
        return this;
    }

    public String getRolesNames() {
        return roles.stream().map(RoleDTO::getRoleName).collect(Collectors.joining(", "));
    }

    public boolean isAdmin() {
        return roles.stream().anyMatch(e -> e.getRoleName().equals("ADMIN"));
    }
}
