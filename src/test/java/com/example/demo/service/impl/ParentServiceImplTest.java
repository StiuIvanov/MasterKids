package com.example.demo.service.impl;

import com.example.demo.model.dto.ParentAndRolesDTO;
import com.example.demo.model.dto.RoleDTO;
import com.example.demo.model.entity.ParentEntity;
import com.example.demo.model.entity.PictureEntity;
import com.example.demo.model.entity.UserRoleEntity;
import com.example.demo.model.entity.enums.UserRoleEnum;
import com.example.demo.repository.ParentRepository;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.service.ActivityService;
import com.example.demo.service.ChildService;
import com.example.demo.service.PictureService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ParentServiceImplTest {
    private ParentServiceImpl testService;
    private ParentEntity testParent;

    @Mock
    private ParentRepository parentRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private UserRoleRepository userRoleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private MasterKidsUserServiceImpl masterKidsUserService;
    @Mock
    private PictureService pictureService;
    @Mock
    private CloudinaryServiceImpl cloudinaryService;
    @Mock
    private ChildService childService;
    @Mock
    private ActivityService activityService;
    @Mock
    private ApplicationEventPublisher eventPublisher;

    @BeforeEach
    void setUp() {
        testService = new ParentServiceImpl(parentRepository,
                modelMapper,
                userRoleRepository,
                passwordEncoder,
                masterKidsUserService,
                pictureService,
                cloudinaryService,
                childService,
                activityService,
                eventPublisher);

        PictureEntity testPicture = new PictureEntity()
                .setUrl("www.TestUrl.com");

        UserRoleEntity adminRole = new UserRoleEntity()
                .setRole(UserRoleEnum.ADMIN);

        testParent = new ParentEntity()
                .setUsername("TestUsername")
                .setFirstName("TestFirstName")
                .setLastName("TestLastName")
                .setEmail("testEMail@abv.bg")
                .setPassword("1234")
                .setPictureEntity(testPicture)
                .setRoles(Set.of(adminRole));


    }

    @Test
    void findParentById() {
        Mockito.when(parentRepository.findById(Long.parseLong("1")))
                .thenReturn(Optional.of(testParent));

        ParentEntity actual = testService.findParentById(Long.parseLong("1"));

        Assertions.assertEquals(actual.getUsername(), testParent.getUsername());
        Assertions.assertEquals(actual.getFirstName(), testParent.getFirstName());
        Assertions.assertEquals(actual.getLastName(), testParent.getLastName());
        Assertions.assertEquals(actual.getEmail(), testParent.getEmail());
        Assertions.assertEquals(actual.getPassword(), testParent.getPassword());
    }

    @Test
    void findParentByUsername() {
        Mockito.when(parentRepository.findByUsername("TestUsername"))
                .thenReturn(Optional.of(testParent));

        ParentEntity actual = testService.findParentByUsername("TestUsername");

        Assertions.assertEquals(actual.getUsername(), testParent.getUsername());
        Assertions.assertEquals(actual.getFirstName(), testParent.getFirstName());
        Assertions.assertEquals(actual.getLastName(), testParent.getLastName());
        Assertions.assertEquals(actual.getEmail(), testParent.getEmail());
        Assertions.assertEquals(actual.getPassword(), testParent.getPassword());
    }


    @Test
    void findParentPicByUsernameShouldReturnCorrectUrl() {
        Mockito.when(parentRepository.findByUsername("TestUsername"))
                .thenReturn(Optional.of(testParent));

        String actual = testService.findParentPicByUsername("TestUsername");

        Assertions.assertEquals(actual, testParent.getPictureEntity().getUrl());
    }

    @Test
    void findParentPicByUsernameShouldReturnURLWithInvailUsername() {
        Mockito.when(parentRepository.findByUsername("invalid-username"))
                .thenReturn(Optional.empty());

        String actual = testService.findParentPicByUsername("invalid-username");

        String expected = "https://4xucy2kyby51ggkud2tadg3d-wpengine.netdna-ssl.com/wp-content/uploads/sites/37/2017/02/IAFOR-Blank-Avatar-Image.jpg";
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void isEmailFree() {
        Mockito.when(parentRepository.findByEmail("testEMail@abv.bg"))
                .thenReturn(Optional.of(testParent));

        boolean actual = testService.isEmailFree("testEMail@abv.bg");

        Assertions.assertFalse(actual);
    }

    @Test
    void isAdmin() {
        boolean actual = testService.isAdmin(testParent);
        boolean expected = testParent.getRoles().stream()
                .anyMatch(r -> r.getRole().name().equals(UserRoleEnum.ADMIN.name()));
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void getParentNamesAndRoles() {

        List<ParentEntity> testParentList = List.of(this.testParent);
        List<ParentAndRolesDTO> expectedList = testParentList.stream()
                .map(p -> {
                    List<RoleDTO> roleDTOList = p.getRoles().stream()
                            .map(r -> new RoleDTO().setRoleName(r.getRole().name()))
                            .collect(Collectors.toList());

                    return new ParentAndRolesDTO()
                            .setId(p.getId())
                            .setUsername(p.getUsername())
                            .setFirstName(p.getFirstName())
                            .setLastName(p.getLastName())
                            .setRoles(roleDTOList);

                }).collect(Collectors.toList());

        Mockito.when(parentRepository.findAll())
                .thenReturn(testParentList);

        List<ParentAndRolesDTO> actualResult = testService.getParentsNamesAndRoles();

        assertEquals(actualResult.size(), expectedList.size());
        assertEquals(actualResult.get(0).getFirstName(), testParent.getFirstName());
        assertEquals(actualResult.get(0).getId(), testParent.getId());
        assertEquals(actualResult.get(0).getUsername(), testParent.getUsername());
        assertEquals(actualResult.get(0).getLastName(), testParent.getLastName());
        assertTrue(actualResult.get(0).isAdmin());
    }
}