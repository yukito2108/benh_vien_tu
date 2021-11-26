package com.tuannq.store.model.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

import static com.tuannq.store.model.type.RegexType.PASSWORD_PATTERN;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (password == null || password.isEmpty()) return true;
        System.out.println("password = " + password);
        var matcher = PASSWORD_PATTERN.matcher(password);

        return matcher.matches() && !password.contains("\"") && !password.contains("`") && !password.contains("'");
    }
}
