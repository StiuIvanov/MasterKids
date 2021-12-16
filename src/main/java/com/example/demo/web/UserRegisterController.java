package com.example.demo.web;

import com.example.demo.events.ParentRegisteredEvent;
import com.example.demo.model.binding.ChildRegisterBindingModel;
import com.example.demo.model.binding.UserRegisterBindingModel;
import com.example.demo.model.service.ChildRegisterServiceModel;
import com.example.demo.model.service.UserServiceModel;
import com.example.demo.service.ParentService;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Period;

@Controller
@RequestMapping("/users")
public class UserRegisterController {

    private final ParentService userService;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher eventPublisher;

    public UserRegisterController(ParentService userService,
                                  ModelMapper modelMapper,
                                  ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/register")
    public String register() {

        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid UserRegisterBindingModel userRegisterBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        boolean passwordsDontMatch = !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword());

        if (bindingResult.hasErrors() || passwordsDontMatch) {

            redirectAttributes.addFlashAttribute("userModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userModel", bindingResult);

            return "redirect:register";
        }

        UserServiceModel serviceModel = modelMapper
                .map(userRegisterBindingModel, UserServiceModel.class);

        userService.register(serviceModel);
        return "redirect:/";
    }

    @ModelAttribute("userModel")
    public UserRegisterBindingModel userModel() {
        return new UserRegisterBindingModel();
    }

    @ModelAttribute("childModel")
    public ChildRegisterBindingModel childModel() {
        return new ChildRegisterBindingModel();
    }

    @GetMapping("/register-child")
    public String registerChild() {
        return "register-child";
    }

    @PostMapping("/register-child")
    public String registerChildConfirm(@Valid ChildRegisterBindingModel childRegisterBindingModel,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes,
                                       @AuthenticationPrincipal UserDetails userDetails) {


        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("childModel", childRegisterBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.childModel", bindingResult);

            return "redirect:register-child";
        }

        ChildRegisterServiceModel childRegisterServiceModel = modelMapper.map(childRegisterBindingModel, ChildRegisterServiceModel.class);

        int age = Period.between(childRegisterServiceModel.getBirthdate(), LocalDate.now()).getYears();
        childRegisterServiceModel.setAge(age);
        userService.registerChild(childRegisterServiceModel, userDetails.getUsername());


        return "redirect:/";
    }


}
