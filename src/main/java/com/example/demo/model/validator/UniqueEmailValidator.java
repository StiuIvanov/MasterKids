package com.example.demo.model.validator;

import com.example.demo.service.ParentService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

  private final ParentService parentService;

  public UniqueEmailValidator(ParentService parentService) {
    this.parentService = parentService;
  }

  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    if (email == null) {
      return true;
    }
    return parentService.isEmailFree(email);
  }
}
