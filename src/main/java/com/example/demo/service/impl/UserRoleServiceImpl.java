package com.example.demo.service.impl;

import com.example.demo.model.entity.UserRoleEntity;
import com.example.demo.model.entity.enums.UserRoleEnum;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void initRoles() {
        UserRoleEnum userRoleEnum = UserRoleEnum.USER;
        UserRoleEnum adminRoleEnum = UserRoleEnum.ADMIN;

        UserRoleEntity userRoleEntity = new UserRoleEntity().setRole(userRoleEnum);
        UserRoleEntity userRoleEntityAdmin = new UserRoleEntity().setRole(adminRoleEnum);
        userRoleRepository.save(userRoleEntity);
        userRoleRepository.save(userRoleEntityAdmin);
    }
}
