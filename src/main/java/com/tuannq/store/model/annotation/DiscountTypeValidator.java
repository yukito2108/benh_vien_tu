package com.tuannq.store.model.annotation;


import com.tuannq.store.model.type.DiscountType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class DiscountTypeValidator implements ConstraintValidator<com.tuannq.store.model.annotation.DiscountType, String> {

    @Override
    public boolean isValid(String discountType, ConstraintValidatorContext constraintValidatorContext) {
        if (discountType == null || discountType.isEmpty()) return true;
        return DiscountType.isExist(discountType);
    }
}
