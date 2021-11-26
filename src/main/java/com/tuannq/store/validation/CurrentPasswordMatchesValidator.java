package com.tuannq.store.validation;

import com.tuannq.store.entity.Users;
import com.tuannq.store.model.ChangePasswordForm;
import com.tuannq.store.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CurrentPasswordMatchesValidator implements ConstraintValidator<CurrentPasswordMatches, Object> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersService usersService;

    @Override
    public void initialize(final CurrentPasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        ChangePasswordForm form = (ChangePasswordForm) obj;
        boolean isValid = false;
        Users users = usersService.getUsersById(form.getId());
        if (passwordEncoder.matches(form.getCurrentPassword(), users.getPassword())) {
            isValid = true;
        }
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("currentPassword").addConstraintViolation();
        }
        return isValid;
    }
}
