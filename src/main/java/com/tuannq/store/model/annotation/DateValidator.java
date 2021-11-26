package com.tuannq.store.model.annotation;


import com.tuannq.store.util.ConverterUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.tuannq.store.model.type.RegexType.DATE_PATTERN;

public class DateValidator implements ConstraintValidator<Date, String> {

    @Override
    public boolean isValid(String dateStr, ConstraintValidatorContext constraintValidatorContext) {
        if (dateStr == null || dateStr.isEmpty()) return true;
        var instant = ConverterUtils.formatStringToInstant(dateStr);
        if (instant != null) return true;

        var matcher = DATE_PATTERN.matcher(dateStr);

        return matcher.matches();
    }
}
