package com.example.demo.model.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "parents")
public class ParentEntity extends BaseEntity {

    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private List<ChildEntity> childEntities;
    private Set<UserRoleEntity> roles = new HashSet<>();
    private PictureEntity pictureEntity;

    public ParentEntity() {
    }

    @Column(unique = true,nullable = false)
    public String getUsername() {
        return username;
    }

    public ParentEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    @Column(nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public ParentEntity setFirstName(String fullName) {
        this.firstName = fullName;
        return this;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public ParentEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    @Column(unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    public ParentEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    @Column(nullable = false)
    public String getLastName() {
        return lastName;
    }

    public ParentEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parentEntity")
    public List<ChildEntity> getChildren() {
        return childEntities;
    }

    public ParentEntity setChildren(List<ChildEntity> childEntities) {
        this.childEntities = childEntities;
        return this;
    }


    @ManyToMany(fetch = FetchType.EAGER)
    public Set<UserRoleEntity> getRoles() {
        return roles;
    }

    public ParentEntity setRoles(Set<UserRoleEntity> roles) {
        this.roles = roles;
        return this;
    }

    @OneToOne
    public PictureEntity getPictureEntity() {
        return pictureEntity;
    }

    public ParentEntity setPictureEntity(PictureEntity pictureEntity) {
        this.pictureEntity = pictureEntity;
        return this;
    }
}
