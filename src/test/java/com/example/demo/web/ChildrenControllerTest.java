package com.example.demo.web;

import com.example.demo.model.dto.ActivityDTO;
import com.example.demo.model.entity.ActivityEntity;
import com.example.demo.model.entity.ChildEntity;
import com.example.demo.model.entity.enums.ActivityEnum;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.ChildRepository;
import com.example.demo.repository.ParentRepository;
import com.example.demo.service.ActivityService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ChildrenControllerTest {
    private ChildEntity child1;
    private ChildEntity child2;
    private ChildEntity child3;
    private ChildEntity child4;
    private ChildEntity child5;
    private static final Long NOT_EXISTING_ID = Long.parseLong("99");

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ChildRepository childRepository;
    @Autowired
    private ActivityRepository activityRepository;

    @BeforeEach
    void setUp() {
        childRepository.deleteAll();
        activityRepository.deleteAll();
        initChildren();


    }

    private void initChildren() {
        ActivityEntity footballActivity = new ActivityEntity().setName(ActivityEnum.Football);
        activityRepository.save(footballActivity);


        child1 = new ChildEntity()
                .setName("Test1")
                .setAge(1)
                .setBirthdate(LocalDate.now())
                .setActivities(Set.of(footballActivity));
        childRepository.save(child1);

        child2 = new ChildEntity()
                .setName("Test2")
                .setAge(1)
                .setBirthdate(LocalDate.now())
                .setActivities(Set.of(footballActivity));
        childRepository.save(child2);

        child3 = new ChildEntity()
                .setName("Test3")
                .setAge(1)
                .setBirthdate(LocalDate.now())
                .setActivities(Set.of(footballActivity));
        childRepository.save(child3);

        child4 = new ChildEntity()
                .setName("Test4")
                .setAge(1)
                .setBirthdate(LocalDate.now())
                .setActivities(Set.of(footballActivity));
        childRepository.save(child4);

        child5 = new ChildEntity()
                .setName("Test5")
                .setAge(1)
                .setBirthdate(LocalDate.now())
                .setActivities(Set.of(footballActivity));
        childRepository.save(child5);

    }

    @AfterEach
    void tearDown() {
        childRepository.deleteAll();
        activityRepository.deleteAll();
    }

    @Test
    @WithMockUser
    void getInfoForParents() throws Exception {
        mockMvc

                .perform(get("/child/"+child1.getId()+"/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.[0].activity", Matchers.is("Football")));
    }

    @Test
    @WithMockUser
    void childMenuUserShouldThrowWhenIdNotExisting() throws Exception {
        mockMvc
                .perform(get("/child/" + NOT_EXISTING_ID))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void childMenuShouldRedirect() throws Exception {
        mockMvc
                .perform(get("/child/" + NOT_EXISTING_ID))
                .andExpect(status().is3xxRedirection());
    }
}