package com.demo.project.nitin.ecommerce.utils.annotations;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidPhoneNumberValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatePhoneNumber {
    String message() default MessageKeys.CONTACT_INVALID;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}