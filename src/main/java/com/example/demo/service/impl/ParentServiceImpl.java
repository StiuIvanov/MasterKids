package com.example.demo.service.impl;

import com.example.demo.events.ParentRegisteredEvent;
import com.example.demo.model.binding.PictureBindingModel;
import com.example.demo.model.dto.*;
import com.example.demo.model.entity.*;
import com.example.demo.model.entity.enums.UserRoleEnum;
import com.example.demo.model.service.ChildRegisterServiceModel;
import com.example.demo.model.service.ParentWithoutChildrenServiceModel;
import com.example.demo.model.service.UserServiceModel;
import com.example.demo.repository.ParentRepository;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.service.ActivityService;
import com.example.demo.service.ChildService;
import com.example.demo.service.ParentService;
import com.example.demo.service.PictureService;
import com.example.demo.web.exception.PersonNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ParentServiceImpl implements ParentService {
    private final ParentRepository parentRepository;
    private final ModelMapper modelMapper;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final MasterKidsUserServiceImpl masterKidsUserService;
    private final PictureService pictureService;
    private final CloudinaryServiceImpl cloudinaryService;
    private final ChildService childService;
    private final ActivityService activityService;
    private final ApplicationEventPublisher eventPublisher;

    public ParentServiceImpl(ParentRepository parentRepository,
                             ModelMapper modelMapper,
                             UserRoleRepository userRoleRepository,
                             PasswordEncoder passwordEncoder,
                             MasterKidsUserServiceImpl masterKidsUserService,
                             PictureService pictureService,
                             CloudinaryServiceImpl cloudinaryService,
                             ChildService childService,
                             ActivityService activityService,
                             ApplicationEventPublisher eventPublisher) {
        this.parentRepository = parentRepository;
        this.modelMapper = modelMapper;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.masterKidsUserService = masterKidsUserService;
        this.pictureService = pictureService;
        this.cloudinaryService = cloudinaryService;
        this.childService = childService;
        this.activityService = activityService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void register(UserServiceModel userServiceModel) {

        UserRoleEnum userRoleEnum = UserRoleEnum.USER;
        UserRoleEntity userRole = userRoleRepository.findByRole(userRoleEnum);

        ParentEntity newUser = new ParentEntity()
                .setUsername(userServiceModel.getUsername())
                .setFirstName(userServiceModel.getFirstName())
                .setLastName(userServiceModel.getLastName())
                .setEmail(userServiceModel.getEmail())
                .setPassword(passwordEncoder.encode(userServiceModel.getPassword()))
                .setRoles(Set.of(userRole));

        parentRepository.save(newUser);

        ParentRegisteredEvent parentRegisteredEvent = new ParentRegisteredEvent(this, newUser.getUsername(), newUser.getFirstName(), newUser.getLastName());
        eventPublisher.publishEvent(parentRegisteredEvent);

        UserDetails principal = masterKidsUserService.loadUserByUsername(newUser.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                newUser.getPassword(),
                principal.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public UserServiceModel findUserByUsernameAndPassword(String username, String password) {
        return parentRepository.findUserByUsernameAndPassword(username, password)
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }


    @Override
    public ParentEntity findParentById(Long id) {
        return parentRepository
                .findById(id)
                .orElse(null);
    }

    @Override
    public void initParents() {

        UserRoleEntity adminRole = userRoleRepository.findByRole(UserRoleEnum.ADMIN);
        UserRoleEntity userRole = userRoleRepository.findByRole(UserRoleEnum.USER);

        ParentEntity parentEntityLyubo = new ParentEntity()
                .setUsername("lyoubo")
                .setFirstName("Lubo")
                .setLastName("Ivanov")
                .setPassword(passwordEncoder.encode("1234"))
                .setEmail("lyoub@email.bg")
                .setRoles(Set.of(userRole));
        parentRepository.save(parentEntityLyubo);

        ParentEntity parentEntityDora = new ParentEntity()
                .setUsername("dora")
                .setFirstName("Dora")
                .setLastName("Ivanova")
                .setPassword(passwordEncoder.encode("1234"))
                .setEmail("dora@email.bg")
                .setRoles(Set.of(adminRole, userRole));
        parentRepository.save(parentEntityDora);

        ParentEntity parentEntityAdmin = new ParentEntity()
                .setUsername("admin")
                .setFirstName("admin")
                .setLastName("admin ")
                .setPassword(passwordEncoder.encode("1234"))
                .setEmail("admin@abv.bg")
                .setRoles(Set.of(adminRole, userRole));
        parentRepository.save(parentEntityAdmin);

        ParentEntity parentEntityTest = new ParentEntity()
                .setUsername("test")
                .setFirstName("test")
                .setLastName("test")
                .setPassword(passwordEncoder.encode("1234"))
                .setEmail("test@abv.bg")
                .setRoles(Set.of(userRole));
        parentRepository.save(parentEntityTest);
    }

    @Override
    public List<ParentDTO> getAllParents() {
        return parentRepository
                .findAll()
                .stream()
                .map(this::asParent)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteParentById(String publicId) {
        parentRepository.deleteById(Long.parseLong(publicId));
    }

    @Override
    public ParentEntity findParentByUsername(String username) {
        return parentRepository.findByUsername(username).orElse(null);
    }

    @Override
    public String findParentPicByUsername(String username) {

        ParentEntity byUsername = parentRepository.findByUsername(username).orElse(null);
        if (byUsername != null) {
            if (byUsername.getPictureEntity() == null) {
                return "https://4xucy2kyby51ggkud2tadg3d-wpengine.netdna-ssl.com/wp-content/uploads/sites/37/2017/02/IAFOR-Blank-Avatar-Image.jpg";
            }

            return byUsername.getPictureEntity().getUrl();
        }
        return "https://4xucy2kyby51ggkud2tadg3d-wpengine.netdna-ssl.com/wp-content/uploads/sites/37/2017/02/IAFOR-Blank-Avatar-Image.jpg";
    }

    @Override
    public void saveParentAvatar(PictureBindingModel bindingModel, String username) {
        PictureEntity picture = null;
        try {
            picture =
                    createPictureEntity(bindingModel.getPicture(), bindingModel.getTitle());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ParentEntity parentEntity = parentRepository.findByUsername(username).orElse(null);
        pictureService.saveToDB(picture);
        parentEntity.setPictureEntity(picture);
        parentRepository.save(parentEntity);
    }

    @Override
    public void registerChild(ChildRegisterServiceModel childRegisterServiceModel, String username) {

        PictureEntity pictureEntity = null;
        try {
            pictureEntity = createPictureEntity(childRegisterServiceModel.getPicture(), "child-avatar");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ChildEntity childEntity = modelMapper.map(childRegisterServiceModel, ChildEntity.class);
        ParentEntity parentEntity1 = parentRepository.findByUsername(username).orElse(null);
        childEntity.setParentEntity(parentEntity1);

        pictureService.saveToDB(pictureEntity);
        childEntity.setAvatar(pictureEntity);

        childService.registerChild(childEntity);

        ParentEntity parentEntity = parentRepository.findByUsername(username).orElse(null);
        parentEntity.getChildren().add(childEntity);

        parentRepository.save(parentEntity);
    }

    @Override
    public List<ChildDTO> getChildren(String username) {

        ParentEntity parentEntity = parentRepository.findByUsername(username).orElse(null);
        return parentEntity.getChildren()
                .stream()
                .map(e -> modelMapper.map(e, ChildDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void initAdminChild() {
        ActivityEntity footballActivity = activityService.getByName("Football");

        ChildEntity childEntity = new ChildEntity()
                .setAge(1)
                .setName("Martin")
                .setBirthdate(LocalDate.now())
                .setParentEntity(parentRepository.findByUsername("admin").orElse(null))
                .setActivities(Set.of(footballActivity));
        PictureEntity pictureEntity = new PictureEntity().setTitle("avatar").setUrl("https://image.freepik.com/free-vector/little-kid-avatar-profile_18591-50926.jpg");
        pictureService.saveToDB(pictureEntity);
        childEntity.setAvatar(pictureEntity);
        childService.registerChild(childEntity);

        ChildEntity childEntity2 = new ChildEntity()
                .setAge(1)
                .setName("Ivan")
                .setBirthdate(LocalDate.now())
                .setParentEntity(parentRepository.findByUsername("admin").orElse(null))
                .setActivities(Set.of(footballActivity));
        PictureEntity pictureEntity2 = new PictureEntity().setTitle("avatar").setUrl("https://www.shareicon.net/data/512x512/2016/06/25/786530_people_512x512.png");
        pictureService.saveToDB(pictureEntity2);
        childEntity2.setAvatar(pictureEntity2);
        childService.registerChild(childEntity2);

        ParentEntity admin = parentRepository.findByUsername("admin").orElse(null);
        admin.setChildren(List.of(childEntity, childEntity2));

        parentRepository.save(admin);
    }

    @Override
    public ParentEntity getParentByUsername(String username) {

        return parentRepository.findByUsername(username).orElse(null);
    }

    @Override
    public ParentEntity getParentById(Long id) {
        return parentRepository.findById(id).orElse(null);
    }

    @Override
    public boolean isUserNameFree(String userName) {
        return parentRepository.findByUsernameIgnoreCase(userName).isEmpty();
    }

    @Override
    public boolean isParentOrAdmin(String parentName, Long id) {
        ChildEntity childById = childService.getChildById(id);
        ParentEntity parentEntity = parentRepository.findByUsernameIgnoreCase(parentName).orElse(null);

        if (childById == null || parentEntity == null) {
            throw new PersonNotFoundException(id);
        }
        boolean isParent = childById.getParentEntity().getUsername().equals(parentName);


        return isAdmin(parentEntity) || isParent;
    }

    @Override
    public boolean isAdmin(ParentEntity parentEntity) {

        return parentEntity
                .getRoles()
                .stream()
                .map(UserRoleEntity::getRole)
                .anyMatch(role -> role == UserRoleEnum.ADMIN);
    }

    @Override
    public void initTestChild() {
        ActivityEntity footballActivity = activityService.getByName("Football");
        ActivityEntity mathActivity = activityService.getByName("Math");
        ActivityEntity musicActivity = activityService.getByName("Music");

        ChildEntity childEntity = new ChildEntity()
                .setAge(1)
                .setName("TestChild")
                .setBirthdate(LocalDate.now())
                .setParentEntity(parentRepository.findByUsername("test").orElse(null))
                .setActivities(Set.of(footballActivity, mathActivity, musicActivity));


        PictureEntity pictureEntity = new PictureEntity().setTitle("avatar").setUrl("https://image.freepik.com/free-vector/little-kid-avatar-profile_18591-50926.jpg");
        pictureService.saveToDB(pictureEntity);
        childEntity.setAvatar(pictureEntity);

        childService.registerChild(childEntity);
        ParentEntity testParent = parentRepository.findByUsername("test").orElse(null);
        testParent.setChildren(List.of(childEntity));
        parentRepository.save(testParent);
    }

    @Override
    public boolean isEmailFree(String email) {
        return parentRepository.findByEmail(email).isEmpty();
    }

    @Override
    public void cleanDatabaseFromParentsWithoutChildren() {
        List<ParentEntity> parentEntitiesByChildEntitiesIsNull = parentRepository.findParentEntitiesByChildEntitiesIsNull();

        parentEntitiesByChildEntitiesIsNull
                .forEach(parentRepository::delete);
    }

    @Override
    public List<ParentWithoutChildrenServiceModel> getParentsNamesWithoutChildren() {
        List<ParentWithoutChildrenServiceModel> inactiveParents = parentRepository.findInactiveParents();

        return inactiveParents;
    }

    @Override
    public List<ParentAndRolesDTO> getParentsNamesAndRoles() {
        return parentRepository.findAll().stream()
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
    }

    @Override
    public void removeAdminRightsById(Long id) {
        ParentEntity parentToRemoveAdminRights = parentRepository.findById(id).orElse(null);

        if (parentToRemoveAdminRights == null ||
                parentToRemoveAdminRights.getUsername().equals("admin")) {
            throw new AccessDeniedException("Access Denied");
        }

        UserRoleEntity userRole = userRoleRepository.findByRole(UserRoleEnum.USER);
        parentToRemoveAdminRights.setRoles(Set.of(userRole));

        parentRepository.save(parentToRemoveAdminRights);
    }

    @Override
    public void makeAdmin(Long id) {
        ParentEntity parentToMakeAdmin = parentRepository.findById(id).orElse(null);

        if (parentToMakeAdmin == null) {
            return;
        }

        UserRoleEntity adminRole = userRoleRepository.findByRole(UserRoleEnum.ADMIN);
        parentToMakeAdmin.getRoles().add(adminRole);

        parentRepository.save(parentToMakeAdmin);
    }


    private ParentDTO asParent(ParentEntity parentEntity) {
        return modelMapper.map(parentEntity, ParentDTO.class);
    }


    private PictureEntity createPictureEntity(MultipartFile file, String title) throws IOException {
        final CloudinaryImage uploaded = this.cloudinaryService.upload(file);

        return new PictureEntity()
                .setPublicId(uploaded.getPublicId())
                .setTitle(title)
                .setUrl(uploaded.getUrl());
    }
}
