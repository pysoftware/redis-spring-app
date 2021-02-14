package com.redisfiledb.demo.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = FileSizeValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface FileSize {
    String message() default "File size must be less than {max}MB";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    long max() default 15L;
}
