package com.example.demo.model.service;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class ChildRegisterServiceModel {
    private String name;
    private Integer age;
    private LocalDate birthdate;
    private MultipartFile picture;

    public ChildRegisterServiceModel() {
    }

    public String getName() {
        return name;
    }

    public ChildRegisterServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public ChildRegisterServiceModel setAge(Integer age) {
        this.age = age;
        return this;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public ChildRegisterServiceModel setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public ChildRegisterServiceModel setPicture(MultipartFile picture) {
        this.picture = picture;
        return this;
    }
}
