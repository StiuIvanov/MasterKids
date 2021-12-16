package com.example.demo.model.binding;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class ChildRegisterBindingModel {

    private String name;
    private Integer age;
    private LocalDate birthdate;
    private MultipartFile picture;

    public ChildRegisterBindingModel() {
    }

    @Size(min = 3,max = 10, message = "Name length should be between 3 and 10 characters!")
    public String getName() {
        return name;
    }

    public ChildRegisterBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    @Positive(message = "Age must be positive number!")
    public Integer getAge() {
        return age;
    }

    public ChildRegisterBindingModel setAge(Integer age) {
        this.age = age;
        return this;
    }

    @PastOrPresent(message = "Date should be past or present!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getBirthdate() {
        return birthdate;
    }

    public ChildRegisterBindingModel setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public ChildRegisterBindingModel setPicture(MultipartFile picture) {
        this.picture = picture;
        return this;
    }
}
