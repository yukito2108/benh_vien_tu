package com.tuannq.store.service;

import com.tuannq.store.entity.Users;
import com.tuannq.store.entity.user.customer.Customer;
import com.tuannq.store.exception.ArgumentException;
import com.tuannq.store.model.request.*;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    Authentication authenticate(LoginForm form);

    Optional<Users> findById(long id);

    Customer addUserByCustomer(UserFormCustomer form) throws ArgumentException;

    Users addUserByAdmin(UserFormAdmin form) throws ArgumentException;

    Users editUserByAdmin(Users user, UserFormAdmin form) throws ArgumentException;

    Users updateProfile(Users user, UpdateProfileForm form) throws ArgumentException;

    void changePassword(Users user, ChangePasswordForm form) throws ArgumentException;

    Page<Users> search(String keyword, Pageable pageable);

    void deleteById(long id);

    Users findByEmail(String email);

    String sendOTPToEmail(String email);

    Users changePasswordWithOTP(ChangePasswordWithOTPRequest form);
}
