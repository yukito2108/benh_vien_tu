package com.tuannq.store.security;

import com.tuannq.store.entity.Users;
import com.tuannq.store.service.AppointmentService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AppointmentService appointmentService;

    public CustomAuthenticationSuccessHandler(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        Optional<Users> currentUsers = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails) return ((CustomUserDetails) u).getUser();
                    else return null;
                });

        if (currentUsers.get().hasRole("ROLE_ADMIN")) {
            appointmentService.updateAllAppointmentsStatuses();
        } else {
            appointmentService.updateUsersAppointmentsStatuses(currentUsers.get().getId());
        }
        response.sendRedirect(request.getContextPath() + "/");
    }

}
