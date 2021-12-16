package com.example.demo.model.dto;

import java.util.Set;

public class ChildAndActivitiesDTO {
    private String name;
    private Set<ActivityNameDTO> activityNameDTO;

    public ChildAndActivitiesDTO() {
    }

    public String getName() {
        return name;
    }

    public ChildAndActivitiesDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Set<ActivityNameDTO> getActivityDTO() {
        return activityNameDTO;
    }

    public ChildAndActivitiesDTO setActivityDTO(Set<ActivityNameDTO> activityNameDTO) {
        this.activityNameDTO = activityNameDTO;
        return this;
    }
}
