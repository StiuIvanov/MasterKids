package com.example.demo.model.validator;

import com.example.demo.service.ParentService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUserNameValidator implements ConstraintValidator<UniqueUserName, String> {

  private final ParentService parentService;

  public UniqueUserNameValidator(ParentService parentService) {
    this.parentService = parentService;
  }

  @Override
  public boolean isValid(String userName, ConstraintValidatorContext context) {
    if (userName == null) {
      return true;
    }
    return parentService.isUserNameFree(userName);
  }
}
