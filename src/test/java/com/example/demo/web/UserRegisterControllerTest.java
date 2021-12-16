package com.example.demo.web;

import com.example.demo.model.entity.ParentEntity;
import com.example.demo.repository.ChildRepository;
import com.example.demo.repository.ParentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class UserRegisterControllerTest {
    private static final String TEST_USERNAME = "TestU";
    private static final String TEST_FIRSTNAME = "TestFN";
    private static final String TEST_LASTNAME = "TestLN";
    private static final String TEST_EMAIL = "test@email.com";
    private static final String TEST_PASSWORD = "1234";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private ChildRepository childRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        childRepository.deleteAll();
        parentRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        childRepository.deleteAll();
        parentRepository.deleteAll();
    }

    @Test
    void testOpenRegisterForm() throws Exception {
        mockMvc
                .perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    void testRegisterUser() throws Exception {
        mockMvc
                .perform(post("/users/register")
                        .param("username", TEST_USERNAME)
                        .param("firstName", TEST_FIRSTNAME)
                        .param("lastName", TEST_LASTNAME)
                        .param("email", TEST_EMAIL)
                        .param("password", TEST_PASSWORD)
                        .param("confirmPassword", TEST_PASSWORD)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().is3xxRedirection());

        assertEquals(1, parentRepository.count());

        Optional<ParentEntity> newParentOpt = parentRepository.findByUsername(TEST_USERNAME);

        assertTrue(newParentOpt.isPresent());

        ParentEntity newParent = newParentOpt.get();

        assertEquals(TEST_USERNAME, newParent.getUsername());
        assertEquals(TEST_FIRSTNAME, newParent.getFirstName());
        assertEquals(TEST_LASTNAME, newParent.getLastName());
        assertEquals(TEST_EMAIL, newParent.getEmail());
        assertTrue(passwordEncoder.matches(TEST_PASSWORD, newParent.getPassword()));
    }


    @Test
    @WithMockUser(username = "testUser")
    void registerChild() throws Exception {
        mockMvc
                .perform(get("/users/register-child"))
                .andExpect(status().isOk())
                .andExpect(view().name("register-child"));
    }
}