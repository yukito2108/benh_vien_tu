package com.tuannq.store.controller.anonymous;

import com.tuannq.store.entity.Users;
import com.tuannq.store.exception.ArgumentException;
import com.tuannq.store.model.dto.UserDTO;
import com.tuannq.store.model.request.ChangePasswordWithOTPRequest;
import com.tuannq.store.model.request.ForgotPasswordForm;
import com.tuannq.store.model.request.LoginForm;
import com.tuannq.store.model.request.UserFormCustomer;
import com.tuannq.store.model.response.SuccessResponse;
import com.tuannq.store.security.CustomUserDetails;
import com.tuannq.store.security.JwtTokenUtil;
import com.tuannq.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.tuannq.store.config.DefaultVariable.JWT_TOKEN;
import static com.tuannq.store.config.DefaultVariable.MAX_AGE_COOKIE;

@Controller
public class AuthenticationController {
    private final UserService userService;
    private final MessageSource messageSource;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthenticationController(
            UserService userService,
            MessageSource messageSource,
            JwtTokenUtil jwtTokenUtil
    ) {
        this.userService = userService;
        this.messageSource = messageSource;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("/login")
    public String login(
            HttpServletResponse httpResponse
    ) throws IOException {
        var user = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails)
                        return ((CustomUserDetails) u).getUser();
                    else return null;
                });

        if (user.isPresent()) {
            httpResponse.sendRedirect("/");
            return null;
        }
        return "/auth/login";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "/auth/forgot-password";
    }

    @GetMapping("/forgot-password/{email}")
    public String changePasswordWithOTP(
            @PathVariable String email,
            @RequestParam(required = false) String otp,
            Model model,
            HttpServletResponse httpResponse
    ) throws IOException {
        Users user = userService.findByEmail(email);
        if (user == null) {
            httpResponse.sendRedirect("/forgot-password");
            return null;
        }
        if (otp != null)
            model.addAttribute("otp", otp);
        model.addAttribute("email", email);
        return "/auth/change-password-with-otp";
    }

    @GetMapping("/register")
    public String register(
            HttpServletResponse httpResponse
    ) throws IOException {
        var user = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails)
                        return ((CustomUserDetails) u).getUser();
                    else return null;
                });

        if (user.isPresent()) {
            httpResponse.sendRedirect("/");
            return null;
        }
        return "/auth/register";
    }

    @PostMapping("/api/register")
    public ResponseEntity<SuccessResponse<UserDTO>> register(
            @Validated @RequestBody UserFormCustomer form,
            HttpServletResponse response
    ) throws ArgumentException {
        Users user = userService.addUserByCustomer(form);

        // Gen token
        UserDetails principal = new CustomUserDetails(user);
        String token = jwtTokenUtil.generateToken(principal);

        // Add token to cookie to login
        Cookie cookie = new Cookie(JWT_TOKEN, token);
        cookie.setMaxAge(MAX_AGE_COOKIE);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok(new SuccessResponse<>(
                messageSource.getMessage("register.success", null, LocaleContextHolder.getLocale()),
                new UserDTO(user)
        ));
    }

    @PostMapping("/api/login")
    public ResponseEntity<SuccessResponse<UserDTO>> login(
            @Validated @RequestBody LoginForm form,
            HttpServletResponse response
    ) throws IOException {
        // Authenticate
        Authentication authentication = userService.authenticate(form);

        // Gen token
        String token = jwtTokenUtil.generateToken((CustomUserDetails) authentication.getPrincipal());

        // Add token to cookie to login
        Cookie cookie = new Cookie(JWT_TOKEN, token);
        cookie.setMaxAge(MAX_AGE_COOKIE);
        cookie.setPath("/");
        response.addCookie(cookie);

        UserDTO userDTO = new UserDTO(((CustomUserDetails) authentication.getPrincipal()).getUser());
        return ResponseEntity.ok(new SuccessResponse<>(
                messageSource.getMessage("login.success", null, LocaleContextHolder.getLocale()),
                userDTO
        ));
    }


    @PostMapping("/api/forgot-password")
    public ResponseEntity<SuccessResponse<String>> sendOTPToEmail(
            @Validated @RequestBody ForgotPasswordForm form
    ) {
        String email = userService.sendOTPToEmail(form.getEmail());
        return ResponseEntity.ok(new SuccessResponse<>(email));
    }

    @PostMapping("/api/forgot-password/{email}")
    public ResponseEntity<SuccessResponse<String>> changePasswordWithOTP(
            @PathVariable String email,
            @Validated @RequestBody ChangePasswordWithOTPRequest form,
            HttpServletResponse httpResponse
    ) throws IOException {
        Users user = userService.findByEmail(email);
        if (user == null || !email.equalsIgnoreCase(form.getEmail())) {
            httpResponse.sendRedirect("/forgot-password");
            return null;
        }
        userService.changePasswordWithOTP(form);
        return ResponseEntity.ok(new SuccessResponse<>(
                messageSource.getMessage("change-password.success", null, LocaleContextHolder.getLocale()),
                email
        ));
    }

}
