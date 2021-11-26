package com.tuannq.store.controller.anonymous;

import com.tuannq.store.entity.Users;
import com.tuannq.store.exception.ArgumentException;
import com.tuannq.store.model.dto.UserDTO;
import com.tuannq.store.model.request.*;
import com.tuannq.store.model.response.SuccessResponse;
import com.tuannq.store.security.CustomUserDetails;
import com.tuannq.store.security.JwtTokenUtil;
import com.tuannq.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;


@Controller
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final MessageSource messageSource;

    @Autowired
    public UserController(
            AuthenticationManager authenticationManager,
            JwtTokenUtil jwtTokenUtil,
            MessageSource messageSource,
            UserService userService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @PostMapping("/api/change-password")
    public ResponseEntity<SuccessResponse<UserDTO>> changePassword(
            @Validated @RequestBody ChangePasswordForm form
    ) throws ArgumentException {
        Users user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        userService.changePassword(user, form);
        return ResponseEntity.ok(new SuccessResponse<>(
                messageSource.getMessage("register.success", null, LocaleContextHolder.getLocale()),
                null
        ));
    }

    @PostMapping("/api/account/update-profile")
    public ResponseEntity<SuccessResponse<UserDTO>> updateProfile(
            @Validated @RequestBody UpdateProfileForm form
    ) {
        Users user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        user = userService.updateProfile(user, form);

        UserDetails principal = new CustomUserDetails(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(new SuccessResponse<>(
                messageSource.getMessage("update.success", null, LocaleContextHolder.getLocale()),
                new UserDTO(user)));
    }


    @GetMapping("/account")
    public String account(
            HttpServletResponse httpResponse
    ) throws IOException {
        var userOpt = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails) return ((CustomUserDetails) u).getUser();
                    else return null;
                });
        if (userOpt.isEmpty()) {
            httpResponse.sendRedirect("/login");
            return null;
        }

        httpResponse.sendRedirect("/orders");
        return null;
    }

    @GetMapping("/account/profile")
    public String profile(
            Model model,
            HttpServletResponse httpResponse
    ) throws IOException {
        var userOpt = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails)
                        return ((CustomUserDetails) u).getUser();
                    else return null;
                });

        if (userOpt.isEmpty()) {
            httpResponse.sendRedirect("/login");
            return null;
        }
        model.addAttribute("user", userOpt.get());
        return "account/profile";
    }

    @GetMapping("/account/change-password")
    public String changePassword(
            Model model,
            HttpServletResponse httpResponse
    ) throws IOException {
        var userOpt = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails)
                        return ((CustomUserDetails) u).getUser();
                    else return null;
                });
        if (userOpt.isEmpty()) {
            httpResponse.sendRedirect("/login");
            return null;
        }
        return "account/change-password";
    }
}
