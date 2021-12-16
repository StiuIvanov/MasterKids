package com.example.demo.web.exception;

import com.example.demo.web.exception.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PersonNotFoundException.class)
    public ModelAndView handleObjectNotFoundException(PersonNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("person-not-found");
        modelAndView.addObject("personId", e.getPersonId());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

}
