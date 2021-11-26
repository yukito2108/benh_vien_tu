package com.tuannq.store.model.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DiscountTypeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DiscountType {
    String message() default "discount-type.invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
