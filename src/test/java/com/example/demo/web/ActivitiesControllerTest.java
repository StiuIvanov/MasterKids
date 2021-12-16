//package com.example.demo.web;
//
//import com.example.demo.model.entity.ParentEntity;
//import com.example.demo.repository.ChildRepository;
//import com.example.demo.repository.ParentRepository;
//import org.hamcrest.Matchers;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class ActivitiesControllerTest {
//    private static final String TEST_USERNAME = "TestU";
//    private static final String TEST_FIRSTNAME = "TestFN";
//    private static final String TEST_LASTNAME = "TestLN";
//    private static final String TEST_EMAIL = "test@email.com";
//    private static final String TEST_PASSWORD = "1234";
//    private static final String TEST_USERNAME2 = "TestU2";
//    private static final String TEST_FIRSTNAME2 = "TestFN2";
//    private static final String TEST_LASTNAME2 = "TestLN2";
//    private static final String TEST_EMAIL2 = "test@email.com2";
//    private static final String TEST_PASSWORD2 = "1234";
//
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ParentRepository parentRepository;
//    @Autowired
//    private ChildRepository childRepository;
//    private ParentEntity parent1;
//    private ParentEntity parent2;
//
//    @BeforeEach
//    void setUp() {
//        childRepository.deleteAll();
//        parentRepository.deleteAll();
//
//        parent1 = new ParentEntity()
//                .setUsername(TEST_USERNAME)
//                .setFirstName(TEST_FIRSTNAME)
//                .setLastName(TEST_LASTNAME)
//                .setEmail(TEST_EMAIL)
//                .setPassword(TEST_PASSWORD);
//        parentRepository.save(parent1);
//
//        parent2 = new ParentEntity()
//                .setUsername(TEST_USERNAME2)
//                .setFirstName(TEST_FIRSTNAME2)
//                .setLastName(TEST_LASTNAME2)
//                .setEmail(TEST_EMAIL2)
//                .setPassword(TEST_PASSWORD2);
//        parentRepository.save(parent2);
//
//    }
//
//    @AfterEach
//    void tearDown() {
//        childRepository.deleteAll();
//        parentRepository.deleteAll();
//    }
//
//    @Test
//    @WithMockUser
//    void getInfoForParents() throws Exception {
//        mockMvc
//                .perform(get("/gallery/info"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", Matchers.hasSize(2)))
//                .andExpect(jsonPath("$.[0].username", Matchers.is(TEST_USERNAME)))
//                .andExpect(jsonPath("$.[1].username", Matchers.is(TEST_USERNAME2)));
//    }
//}