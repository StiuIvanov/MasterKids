package com.example.demo.service;

import com.example.demo.model.dto.ActivityDTO;
import com.example.demo.model.dto.ActivityNameDTO;
import com.example.demo.model.entity.ActivityEntity;

import java.util.List;

public interface ActivityService {
    ActivityEntity getByName(String activityString);

    void initActivities();

    List<ActivityNameDTO> getAllActivities();


    ActivityDTO getActivityByName(String name);
}
