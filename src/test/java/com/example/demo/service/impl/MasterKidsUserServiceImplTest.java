package com.example.demo.service.impl;

import com.example.demo.model.entity.ParentEntity;
import com.example.demo.model.entity.UserRoleEntity;
import com.example.demo.model.entity.enums.UserRoleEnum;
import com.example.demo.repository.ParentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class MasterKidsUserServiceImplTest {

    private ParentEntity testParent;
    private UserRoleEntity adminRole;
    private UserRoleEntity userRole;

    private MasterKidsUserServiceImpl serviceToTest;

    @Mock
    private ParentRepository mockParentRepository;

    @BeforeEach
    void init() {
        serviceToTest = new MasterKidsUserServiceImpl(mockParentRepository);

        adminRole = new UserRoleEntity()
                .setRole(UserRoleEnum.ADMIN);
        userRole = new UserRoleEntity()
                .setRole(UserRoleEnum.USER);

        testParent = new ParentEntity()
                .setUsername("TestUsername")
                .setFirstName("TestFirstName")
                .setLastName("TestLastName")
                .setPassword("1234")
                .setEmail("test@email.com")
                .setRoles(Set.of(adminRole, userRole));
    }

    @org.junit.jupiter.api.Test
    public void testUserNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> {
                    serviceToTest.loadUserByUsername("invalid-Parent");
                }
        );
    }

    @Test
    void testUserFound() {

        Mockito.when(mockParentRepository.findByUsername("TestUsername"))
                .thenReturn(Optional.of(testParent));

        UserDetails actual = serviceToTest.loadUserByUsername("TestUsername");

        Assertions.assertEquals(actual.getUsername(), testParent.getUsername());
        String actualRoles = actual.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));
        String expectedRoles = "ROLE_ADMIN, ROLE_USER";
        Assertions.assertEquals(actualRoles,expectedRoles);
    }
}