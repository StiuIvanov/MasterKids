package com.example.demo.init;

import com.example.demo.service.ActivityService;
import com.example.demo.service.ParentService;
import com.example.demo.service.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final ParentService parentService;
    private final UserRoleService userRoleService;
    private final ActivityService activityService;


    public DatabaseInitializer(ParentService parentService,
                               UserRoleService userRoleService,
                               ActivityService activityService) {
        this.parentService = parentService;
        this.userRoleService = userRoleService;
        this.activityService = activityService;
    }

    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
        initializeParents();
        initializeActivities();
        initializeAdmin2ChildsAnd1ActivityFootbal();
        initializeTestChildAnd3Activities();
    }



    private void initializeTestChildAnd3Activities() {
        parentService.initTestChild();
    }


    private void initializeActivities() {
        activityService.initActivities();

    }

    private void initializeAdmin2ChildsAnd1ActivityFootbal() {
        parentService.initAdminChild();

    }


    private void initializeParents() {
        parentService.initParents();
    }

    private void initializeRoles() {
        userRoleService.initRoles();
    }
}
