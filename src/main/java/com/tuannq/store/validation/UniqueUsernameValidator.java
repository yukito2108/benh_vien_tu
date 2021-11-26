//package com.tuannq.store.validation;
//
//import com.tuannq.store.service.UsersService;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//
//public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, Object> {
//
//    @Autowired
//    private UsersService usersService;
//
//    @Override
//    public void initialize(final UniqueUsername constraintAnnotation) {
//    }
//
//    @Override
//    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
//        String userName = (String) obj;
//        return !usersService.usersExists(userName);
//    }
//
//}
