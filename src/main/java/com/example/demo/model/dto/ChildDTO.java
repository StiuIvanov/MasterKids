package com.example.demo.model.dto;

import com.example.demo.model.entity.PictureEntity;

import java.time.LocalDate;

public class ChildDTO {
    private Long id;
    private String name;
    private Integer age;
    private LocalDate birthdate;
    private PictureEntity avatar;

    public ChildDTO() {
    }

    public String getName() {
        return name;
    }

    public ChildDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public ChildDTO setAge(Integer age) {
        this.age = age;
        return this;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public ChildDTO setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public PictureEntity getAvatar() {
        return avatar;
    }

    public ChildDTO setAvatar(PictureEntity avatar) {
        this.avatar = avatar;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ChildDTO setId(Long id) {
        this.id = id;
        return this;
    }
}
