package com.example.demo.service;

import com.example.demo.model.binding.PictureBindingModel;
import com.example.demo.model.dto.*;
import com.example.demo.model.entity.ParentEntity;
import com.example.demo.model.service.ChildRegisterServiceModel;
import com.example.demo.model.service.ParentWithoutChildrenServiceModel;
import com.example.demo.model.service.UserServiceModel;

import java.util.List;

public interface ParentService {
    void register(UserServiceModel userServiceModel);

    UserServiceModel findUserByUsernameAndPassword(String username, String password);


    ParentEntity findParentById(Long id);


    void initParents();

    List<ParentDTO> getAllParents();

    void deleteParentById(String publicId);

    ParentEntity findParentByUsername(String username);

    String findParentPicByUsername(String username);

    void saveParentAvatar(PictureBindingModel bindingModel, String username);

    void registerChild(ChildRegisterServiceModel childRegisterServiceModel, String username);

    List<ChildDTO> getChildren(String username);

    void initAdminChild();

    ParentEntity getParentByUsername(String admin);

    ParentEntity getParentById(Long id);

    boolean isUserNameFree(String userName);

    boolean isParentOrAdmin(String name, Long id);

    boolean isAdmin(ParentEntity parentEntity);

    void initTestChild();


    boolean isEmailFree(String email);

    void cleanDatabaseFromParentsWithoutChildren();

    List<ParentWithoutChildrenServiceModel> getParentsNamesWithoutChildren();

    List<ParentAndRolesDTO> getParentsNamesAndRoles();

    void removeAdminRightsById(Long id);

    void makeAdmin(Long id);
}
