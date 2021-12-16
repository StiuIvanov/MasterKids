package com.example.demo.web;

import com.example.demo.model.binding.PictureBindingModel;
import com.example.demo.model.dto.ChildDTO;
import com.example.demo.service.ParentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {

    private final ParentService parentService;

    public HomeController(ParentService parentService) {
        this.parentService = parentService;
    }

    @GetMapping("/")
    public ModelAndView index(@AuthenticationPrincipal UserDetails userDetails) {
        ModelAndView modelAndView = new ModelAndView();

        if (userDetails != null) {
            String picURL = parentService.findParentPicByUsername(userDetails.getUsername());
            modelAndView.addObject("userPicture", picURL);
            List<ChildDTO> childDTOList= parentService.getChildren(userDetails.getUsername());
            modelAndView.addObject("children", childDTOList);
            modelAndView.setViewName("home");
            return modelAndView;
        }

        modelAndView.setViewName("index");
        return modelAndView;
    }

    @Transactional
    @PostMapping("/home/add")
    public String addPicture(@AuthenticationPrincipal UserDetails userDetails,
                             PictureBindingModel bindingModel) throws IOException {
        bindingModel.setTitle("parent-avatar");
        parentService.saveParentAvatar(bindingModel,userDetails.getUsername());
        return "redirect:/";
    }






}
