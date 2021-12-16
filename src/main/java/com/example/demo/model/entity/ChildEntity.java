package com.example.demo.model.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "children")
public class ChildEntity extends BaseEntity {

    private String name;
    private Integer age;
    private LocalDate birthdate;
    private ParentEntity parentEntity;
    private Set<ActivityEntity> activities;
    private PictureEntity avatar;


    public ChildEntity() {
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public ChildEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Column(nullable = false)
    public Integer getAge() {
        return age;
    }

    public ChildEntity setAge(Integer age) {
        this.age = age;
        return this;
    }

    @Column(nullable = false)
    public LocalDate getBirthdate() {
        return birthdate;
    }

    public ChildEntity setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public Set<ActivityEntity> getActivities() {
        return activities;
    }

    public ChildEntity setActivities(Set<ActivityEntity> activities) {
        this.activities = activities;
        return this;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public ParentEntity getParentEntity() {
        return parentEntity;
    }

    public ChildEntity setParentEntity(ParentEntity parentEntity) {
        this.parentEntity = parentEntity;
        return this;
    }

    @OneToOne
    public PictureEntity getAvatar() {
        return avatar;
    }

    public ChildEntity setAvatar(PictureEntity avatar) {
        this.avatar = avatar;
        return this;
    }
}
