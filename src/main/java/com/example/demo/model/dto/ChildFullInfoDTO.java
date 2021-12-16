package com.example.demo.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Set;

public class ChildFullInfoDTO {
    private Long id;
    private String name;
    private LocalDate birthdate;
    private Set<ActivityNameDTO> activities;
    private String avatarURL;

    public ChildFullInfoDTO() {
    }

    public String getName() {
        return name;
    }

    public ChildFullInfoDTO setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public ChildFullInfoDTO setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public Set<ActivityNameDTO> getActivities() {
        return activities;
    }

    public ChildFullInfoDTO setActivities(Set<ActivityNameDTO> activities) {
        this.activities = activities;
        return this;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public ChildFullInfoDTO setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ChildFullInfoDTO setId(Long id) {
        this.id = id;
        return this;
    }


    public int getAge(){
        return Period.between(this.birthdate, LocalDate.now()).getYears();
    }
}
