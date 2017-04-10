package com.backend.web.validation;

import com.backend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameExistsValidator implements ConstraintValidator<UsernameExists,Object> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(UsernameExists usernameExists) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.isExists(o.toString());
    }
}
