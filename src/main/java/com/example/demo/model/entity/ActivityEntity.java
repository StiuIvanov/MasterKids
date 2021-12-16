package com.example.demo.model.entity;

import com.example.demo.model.entity.enums.ActivityEnum;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "activities")
public class ActivityEntity extends BaseEntity{

    private ActivityEnum name;
//    private Teacher teacher;
    private List<ChildEntity> students;

    public ActivityEntity() {
    }

    @Enumerated(EnumType.STRING)
    public ActivityEnum getName() {
        return name;
    }

    public ActivityEntity setName(ActivityEnum name) {
        this.name = name;
        return this;
    }

    @ManyToMany(mappedBy = "activities", fetch = FetchType.EAGER)
    public List<ChildEntity> getStudents() {
        return students;
    }

    public ActivityEntity setStudents(List<ChildEntity> students) {
        this.students = students;
        return this;
    }
}
