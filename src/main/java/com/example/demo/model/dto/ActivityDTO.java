package com.example.demo.model.dto;

import com.example.demo.model.entity.enums.ActivityEnum;

import java.util.List;

public class ActivityDTO {

    private ActivityEnum name;
    private List<ChildActivityDTO> students;

    public ActivityDTO() {
    }

    public ActivityEnum getName() {
        return name;
    }

    public ActivityDTO setName(ActivityEnum name) {
        this.name = name;
        return this;
    }


    public List<ChildActivityDTO> getStudents() {
        return students;
    }

    public ActivityDTO setStudents(List<ChildActivityDTO> students) {
        this.students = students;
        return this;
    }
}
