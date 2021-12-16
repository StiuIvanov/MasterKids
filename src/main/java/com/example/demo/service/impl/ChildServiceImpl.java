package com.example.demo.service.impl;

import com.example.demo.model.binding.ActivityFormBindingModel;
import com.example.demo.model.dto.ActivityNameDTO;
import com.example.demo.model.dto.ChildAndActivitiesDTO;
import com.example.demo.model.dto.ChildFullInfoDTO;
import com.example.demo.model.entity.ActivityEntity;
import com.example.demo.model.entity.ChildEntity;
import com.example.demo.repository.ChildRepository;
import com.example.demo.service.ActivityService;
import com.example.demo.service.ChildService;
import com.example.demo.web.exception.PersonNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChildServiceImpl implements ChildService {

    private ChildRepository childRepository;
    private final ActivityService activityService;

    public ChildServiceImpl(ChildRepository childRepository,
                            ActivityService activityService) {
        this.childRepository = childRepository;
        this.activityService = activityService;
    }


    @Override
    public ChildEntity getChildById(Long id) {

        return childRepository.findById(id).orElse(null);
    }

    @Override
    public void registerChild(ChildEntity childEntity) {
        childRepository.save(childEntity);
    }

    @Override
    public void addActivity(ActivityFormBindingModel activityFormBindingModel) {
        ChildEntity childEntity = childRepository.findById(activityFormBindingModel.getId()).orElse(null);
        String activityString = activityFormBindingModel.getActivity();

        ActivityEntity activityEntity = activityService.getByName(activityString);

        childEntity.getActivities().add(activityEntity);

        childRepository.save(childEntity);


    }

    @Override
    public void saveToDB(ChildEntity childById) {
        childRepository.save(childById);
    }

    @Override
    public List<ChildAndActivitiesDTO> getAllChildren() {

        return childRepository.findAll().stream()
                .map(this::getAsChildAndActivitiesDTO)
                .collect(Collectors.toList());
    }

    private ChildAndActivitiesDTO getAsChildAndActivitiesDTO(ChildEntity e) {
        ChildAndActivitiesDTO childAndActivitiesDTO = new ChildAndActivitiesDTO()
                .setName(e.getName());

        Set<ActivityNameDTO> activityNameDTOSet = e.getActivities().stream()
                .map(a -> new ActivityNameDTO().setActivity(a.getName().name()))
                .collect(Collectors.toSet());
        childAndActivitiesDTO.setActivityDTO(activityNameDTOSet);

        return childAndActivitiesDTO;
    }

    @Override
    public void deleteChildById(Long id) {
        childRepository.deleteById(id);
    }

    @Override
    public ChildFullInfoDTO getChildDtoById(Long id) {
        Optional<ChildEntity> byId = childRepository.findById(id);
        if (byId.isEmpty()) {
            throw new PersonNotFoundException(id);
        }

        ChildEntity childEntity = byId.get();
        Set<ActivityNameDTO> activitiesDTO = byId.get().getActivities().stream()
                .map(a -> new ActivityNameDTO().setActivity(a.getName().name()))
                .collect(Collectors.toSet());

        return new ChildFullInfoDTO()
                .setId(childEntity.getId())
                .setName(childEntity.getName())
                .setBirthdate(childEntity.getBirthdate())
                .setActivities(activitiesDTO)
                .setAvatarURL(childEntity.getAvatar().getUrl());
    }
}
