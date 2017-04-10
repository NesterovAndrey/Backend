package com.backend.web.validation;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UsernameExistsValidator.class)
public @interface UsernameExists {
    String message() default "Username allReady exists";
    Class[] groups() default {};
    Class[] payload() default {};
}
