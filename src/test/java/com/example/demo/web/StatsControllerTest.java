package com.example.demo.web;

import com.example.demo.model.entity.ParentEntity;
import com.example.demo.model.entity.UserRoleEntity;
import com.example.demo.model.entity.enums.UserRoleEnum;
import com.example.demo.repository.ChildRepository;
import com.example.demo.repository.ParentRepository;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.service.StatsService;
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

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StatsControllerTest {
    private static final String TEST_USERNAME = "admin";
    private static final String TEST_FIRSTNAME = "Admin";
    private static final String TEST_LASTNAME = "Adminov";
    private static final String TEST_EMAIL = "admin@email.com";
    private static final String TEST_PASSWORD = "1234";
    private ParentEntity admin;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private ChildRepository childRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;


    @BeforeEach
    void init() {
        childRepository.deleteAll();
        parentRepository.deleteAll();
        initAdmin();
    }

    @AfterEach
    void tearDown() {
        childRepository.deleteAll();
        parentRepository.deleteAll();
    }

    private void initAdmin() {
        UserRoleEntity adminRole = new UserRoleEntity().setRole(UserRoleEnum.ADMIN);
        userRoleRepository.save(adminRole);

        admin = new ParentEntity()
                .setUsername(TEST_USERNAME)
                .setFirstName(TEST_FIRSTNAME)
                .setLastName(TEST_LASTNAME)
                .setEmail(TEST_EMAIL)
                .setPassword(TEST_PASSWORD)
                .setRoles(Set.of(adminRole));

        parentRepository.save(admin);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void statistics() throws Exception {
        mockMvc
                .perform(get("/statistics"))
                .andExpect(status().isOk())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("stats"))
                .andExpect(view().name("stats"));
    }

    @Test
    void statisticsNonAdminShouldRedirect302() throws Exception {
        mockMvc
                .perform(get("/statistics"))
                .andExpect(status().is3xxRedirection());
    }
}