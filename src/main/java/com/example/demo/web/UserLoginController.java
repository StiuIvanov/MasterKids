package com.example.demo.web;

import com.example.demo.model.binding.UserLoginBindingModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class UserLoginController {

    @GetMapping("/users/login")
    public String login(){
        return "login";
    }

    @ModelAttribute("userLoginModel")
    public UserLoginBindingModel userLoginBindingModel(){
        return new UserLoginBindingModel();
    }
}
