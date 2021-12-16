package com.example.demo.web;

import com.example.demo.model.view.StatsView;
import com.example.demo.service.StatsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @PreAuthorize("isAdmin()")
    @GetMapping("/statistics")
    public ModelAndView statistics() {
        ModelAndView modelAndView = new ModelAndView();
        StatsView stats = statsService.getStats();
        modelAndView.addObject("stats",stats );
        modelAndView.setViewName("stats");


        return modelAndView;
    }
}
