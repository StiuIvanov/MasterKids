package com.example.demo.service;


import com.example.demo.model.binding.ActivityFormBindingModel;
import com.example.demo.model.dto.ChildAndActivitiesDTO;
import com.example.demo.model.dto.ChildFullInfoDTO;
import com.example.demo.model.entity.ChildEntity;

import java.util.List;

public interface ChildService {

    ChildEntity getChildById(Long id);

    void registerChild(ChildEntity childEntity);

    void addActivity(ActivityFormBindingModel activityFormBindingModel);

    void saveToDB(ChildEntity childById);

    List<ChildAndActivitiesDTO> getAllChildren();

    void deleteChildById(Long id);

    ChildFullInfoDTO getChildDtoById(Long id);
}
