package com.example.demo.web;

import com.example.demo.model.dto.*;
import com.example.demo.service.ActivityService;
import com.example.demo.service.ChildService;
import com.example.demo.service.ParentService;
import com.example.demo.web.exception.PersonNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@PreAuthorize("isAdmin()")
@RequestMapping("/admin")
public class AdminController {
    private final ActivityService activityService;
    private final ChildService childService;
    private final ParentService parentService;

    public AdminController(ActivityService activityService,
                           ChildService childService,
                           ParentService parentService) {
        this.activityService = activityService;
        this.childService = childService;
        this.parentService = parentService;
    }

    @GetMapping("/get-all")
    @ResponseBody
    public ResponseEntity<List<ChildAndActivitiesDTO>> getAllActivities() {
        List<ChildAndActivitiesDTO> allChildren = childService.getAllChildren();

        return ResponseEntity.ok(allChildren);
    }

    @GetMapping("/sort-activity/{name}")
    @ResponseBody
    public ResponseEntity<ActivityDTO> sortActivities(@PathVariable("name") String name) {
        ActivityDTO activityByName = activityService.getActivityByName(name);

        return ResponseEntity.ok(activityByName);
    }


    @GetMapping("/parents-info")
    public String parentsInfoAdminPanel() {
        return "parents-admin-panel";
    }

    @GetMapping("/get-parents")
    @ResponseBody
    public ResponseEntity<List<ParentDTO>> getAllParent() {
        List<ParentDTO> allParents = parentService.getAllParents();

        return ResponseEntity.ok(allParents);
    }

    @GetMapping("/children-info/{username}")
    public String childrenInfoAdminPanel(@PathVariable String username,
                                         Model model) {
        List<ChildDTO> children = parentService.getChildren(username);
        if (children == null) {
            throw new PersonNotFoundException(Long.getLong("0"));
        }

        model.addAttribute("children", children);
        return "children-admin-panel";
    }

    @GetMapping("/roles")
    public ModelAndView menageRoles() {
        ModelAndView modelAndView = new ModelAndView();
        List<ParentAndRolesDTO> parentsNamesAndRoles = parentService.getParentsNamesAndRoles();

        modelAndView.addObject("parents", parentsNamesAndRoles);

        modelAndView.setViewName("manage-roles-admin-panel");
        return modelAndView;
    }

    @PostMapping("/role/{id}/removeAdminRole")
    public String removeAdminRights(@PathVariable Long id) {
        parentService.removeAdminRightsById(id);

        return "redirect:/admin/roles";
    }

    @PostMapping("/role/{id}/makeAdmin")
    public String makeAdmin(@PathVariable Long id) {
        parentService.makeAdmin(id);

        return "redirect:/admin/roles";

    }


    @DeleteMapping("/delete/child/{id}")
    public String deleteChild(@PathVariable Long id) {
        childService.deleteChildById(id);

        return "redirect:/admin/parents-info";
    }

    @GetMapping("/activities")
    public String activitiesInfoAdminPanel() {
        return "activities-info-admin-panel";
    }
}

