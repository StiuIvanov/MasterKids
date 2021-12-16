package com.example.demo.web;

import com.example.demo.model.binding.ActivityFormBindingModel;
import com.example.demo.model.dto.ActivityNameDTO;
import com.example.demo.model.dto.ChildFullInfoDTO;
import com.example.demo.model.entity.ActivityEntity;
import com.example.demo.model.entity.ChildEntity;
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

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ChildrenController {

    private final ChildService childService;
    private final ParentService parentService;
    private final ActivityService activityService;

    public ChildrenController(ChildService childService,
                              ParentService parentService,
                              ActivityService activityService) {
        this.childService = childService;
        this.parentService = parentService;
        this.activityService = activityService;
    }


    @PreAuthorize("isParentOrAdmin(#id)")
    @GetMapping("/child/{id}")
    public String childMenu(@PathVariable Long id,
                            Model model) {

        ChildEntity childById = childService.getChildById(id);

        if (childById == null) {
            throw new PersonNotFoundException(id);
        }

        ChildFullInfoDTO childDtoById = childService.getChildDtoById(id);

        List<String> collect = childById.getActivities().stream()
                .map(e -> e.getName().name()).collect(Collectors.toList());
        List<String> allActivities = activityService.getAllActivities().stream()
                .map(ActivityNameDTO::getActivity).collect(Collectors.toList());

        allActivities.removeAll(collect);


        model.addAttribute("activities", allActivities);
        model.addAttribute("child", childDtoById);

        return "details";
    }

    @PreAuthorize("isParentOrAdmin(#id)")
    @GetMapping("/child/{id}/remove-activity")
    public ModelAndView childRemoveActivity(@PathVariable Long id) {
        ChildFullInfoDTO childDtoById = childService.getChildDtoById(id);

        if (childDtoById == null) {
            throw new PersonNotFoundException(id);
        }

        List<String> activitiesD = childDtoById.getActivities().stream()
                .map(ActivityNameDTO::getActivity)
                .collect(Collectors.toList());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("childActivities", activitiesD);
        modelAndView.addObject("childId", id);

        modelAndView.setViewName("remove-activity");
        return modelAndView;
    }

    @PostMapping("/child/save")
    public String childMenu(ActivityFormBindingModel activityFormBindingModel) {
        childService.addActivity(activityFormBindingModel);
        return "redirect:/";
    }

    @ModelAttribute
    public ActivityFormBindingModel activityFormBindingModel() {
        return new ActivityFormBindingModel();
    }


    @GetMapping("/child/{id}/info")
    @ResponseBody
    public ResponseEntity<List<ActivityNameDTO>> getInfoForParents(@PathVariable Long id) {
        List<ActivityNameDTO> activityNameDTOS = childService
                .getChildById(id).getActivities().stream()
                .map(e -> new ActivityNameDTO().setActivity(e.getName().name()))
                .collect(Collectors.toList());
        return ResponseEntity
                .ok(activityNameDTOS);

    }

    @PreAuthorize("isParentOrAdmin(#id)")
    @DeleteMapping("/activity/remove/{activity}/{id}")
    public String removeActivityFromChild(@PathVariable("activity") String activity,
                                          @PathVariable("id") Long id,
                                          Principal principal) {


        ChildEntity childById = childService.getChildById(id);

        if (childById == null) {
            throw new PersonNotFoundException(id);
        }

        ActivityEntity activityToRemove = childById.getActivities().stream()
                .filter(e -> e.getName().name().equals(activity)).findFirst().orElse(null);
        childById.getActivities().remove(activityToRemove);
        childService.saveToDB(childById);
        return "redirect:/";
    }
}
