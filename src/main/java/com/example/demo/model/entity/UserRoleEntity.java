package com.example.demo.model.entity;

import com.example.demo.model.entity.enums.UserRoleEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class UserRoleEntity extends BaseEntity {

    private UserRoleEnum role;

    public UserRoleEntity() {
    }

    @Enumerated(EnumType.STRING)
    public UserRoleEnum getRole() {
        return role;
    }

    public UserRoleEntity setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }
}
