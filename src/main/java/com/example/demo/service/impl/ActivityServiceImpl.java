package com.example.demo.service.impl;

import com.example.demo.model.dto.ActivityDTO;
import com.example.demo.model.dto.ActivityNameDTO;
import com.example.demo.model.dto.ChildActivityDTO;
import com.example.demo.model.entity.ActivityEntity;
import com.example.demo.model.entity.enums.ActivityEnum;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.service.ActivityService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;
    private final ModelMapper modelMapper;

    public ActivityServiceImpl(ActivityRepository activityRepository,
                               ModelMapper modelMapper) {
        this.activityRepository = activityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ActivityEntity getByName(String activityString) {
        return activityRepository.findByName(ActivityEnum.valueOf(activityString)).orElse(null);
    }

    @Override
    public void initActivities() {
        Arrays.stream(ActivityEnum.values())
                .forEach(a -> {
                    ActivityEntity activityEntity = new ActivityEntity()
                            .setName(a);
                    activityRepository.save(activityEntity);
                });
    }

    @Override
    public List<ActivityNameDTO> getAllActivities() {
        return activityRepository.findAll().stream()
                .map(a -> new ActivityNameDTO()
                        .setActivity(a.getName().name()))
                .collect(Collectors.toList());
    }

    @Override
    public ActivityDTO getActivityByName(String name) {
        ActivityEntity activityEntity = activityRepository
                .findByName(ActivityEnum.valueOf(name)).orElse(null);


        List<ChildActivityDTO> childActivityDTOList = activityEntity.getStudents().stream()
                .map(s -> {
                    return new ChildActivityDTO().setName(s.getName());
                })
                .collect(Collectors.toList());


        ActivityDTO activityDTO = new ActivityDTO().setName(activityEntity.getName())
                .setStudents(childActivityDTOList);

        return activityDTO;
    }


}
