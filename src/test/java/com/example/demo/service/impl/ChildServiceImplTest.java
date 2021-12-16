package com.example.demo.service.impl;

import com.example.demo.model.dto.ActivityNameDTO;
import com.example.demo.model.dto.ChildAndActivitiesDTO;
import com.example.demo.model.entity.ActivityEntity;
import com.example.demo.model.entity.ChildEntity;
import com.example.demo.model.entity.enums.ActivityEnum;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.ChildRepository;
import com.example.demo.service.ActivityService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class ChildServiceImplTest {

    private ChildServiceImpl testService;
    private ChildEntity testChild;
    @Mock
    private ChildRepository mockChildRepository;
    @Mock
    private ActivityService mockActivityService;

    @BeforeEach
    void setUp() {
        testService = new ChildServiceImpl(mockChildRepository, mockActivityService);

        ActivityEntity footballActivity = new ActivityEntity()
                .setName(ActivityEnum.Football);
        Mockito.when(mockActivityService.getByName("Football"))
                .thenReturn(footballActivity);
        ActivityEntity football = mockActivityService.getByName("Football");
        Set<ActivityEntity> testActivities = new HashSet<>(Set.of(football));

        testChild = new ChildEntity()
                .setName("TestChild")
                .setAge(2)
                .setBirthdate(LocalDate.now())
                .setActivities(testActivities);
    }

    @Test
    void getChildById() {

        Mockito.when(mockChildRepository.findById(Long.parseLong("1")))
                .thenReturn(Optional.of(testChild));

        ChildEntity actual = testService.getChildById(Long.parseLong("1"));

        Assertions.assertEquals(actual, testChild);
    }


    @Test
    void getAllChildren() {
        List<ChildEntity> list = new ArrayList<>(List.of(testChild));
        Mockito.when(mockChildRepository.findAll())
                .thenReturn(list);

        List<ChildAndActivitiesDTO> actual = mockChildRepository.findAll()
                .stream()
                .map(e -> {
                    ChildAndActivitiesDTO childAndActivitiesDTO = new ChildAndActivitiesDTO()
                            .setName(e.getName());

                    Set<ActivityNameDTO> activityNameDTOSet = e.getActivities().stream()
                            .map(a -> new ActivityNameDTO().setActivity(a.getName().name()))
                            .collect(Collectors.toSet());
                    childAndActivitiesDTO.setActivityDTO(activityNameDTOSet);

                    return childAndActivitiesDTO;
                })
                .collect(Collectors.toList());

        List<ChildAndActivitiesDTO> expected = list
                .stream()
                .map(e -> {
                    ChildAndActivitiesDTO childAndActivitiesDTO = new ChildAndActivitiesDTO()
                            .setName(e.getName());

                    Set<ActivityNameDTO> activityNameDTOSet = e.getActivities().stream()
                            .map(a -> new ActivityNameDTO().setActivity(a.getName().name()))
                            .collect(Collectors.toSet());
                    childAndActivitiesDTO.setActivityDTO(activityNameDTOSet);

                    return childAndActivitiesDTO;
                })
                .collect(Collectors.toList());


        Assertions.assertEquals(actual.get(0).getName(), expected.get(0).getName());
        String actualActivityList = actual.get(0).getActivityDTO().stream()
                .map(ActivityNameDTO::getActivity)
                .collect(Collectors.joining(", "));
        String expectedList = expected.get(0).getActivityDTO().stream()
                .map(ActivityNameDTO::getActivity)
                .collect(Collectors.joining(", "));
        Assertions.assertEquals(actualActivityList, expectedList);
    }
}