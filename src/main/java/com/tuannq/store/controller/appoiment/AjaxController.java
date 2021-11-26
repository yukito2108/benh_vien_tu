package com.tuannq.store.controller.appoiment;

import com.tuannq.store.entity.Appointment;
import com.tuannq.store.entity.Users;
import com.tuannq.store.model.AppointmentRegisterForm;
import com.tuannq.store.security.CustomUserDetails;
import com.tuannq.store.security.CustomUsersDetails;
import com.tuannq.store.service.AppointmentService;
import com.tuannq.store.service.NotificationService;
import com.google.common.collect.Lists;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RestController
public class AjaxController {

    private final AppointmentService appointmentService;
    private final NotificationService notificationService;

    public AjaxController(AppointmentService appointmentService, NotificationService notificationService) {
        this.appointmentService = appointmentService;
        this.notificationService = notificationService;
    }


    @GetMapping("/users/{usersId}/appointments")
    public List<Appointment> getAppointmentsForUsers(@PathVariable("usersId") Long usersId) {
        Optional<Users> currentUsers = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails) return ((CustomUserDetails) u).getUser();
                    else return null;
                });
        if (currentUsers.get().hasRole("ROLE_CUSTOMER")) {
            var x = appointmentService.getAppointmentByCustomerId((long) usersId);
            return x;
        } else if (currentUsers.get().hasRole("ROLE_PROVIDER")){
            var x = appointmentService.getAppointmentByProviderId(usersId);
            return appointmentService.getAppointmentByProviderId(usersId);}
        else if (currentUsers.get().hasRole("ROLE_ADMIN"))
            return appointmentService.getAllAppointments();
        else return Lists.newArrayList();
    }

    @GetMapping("/availableHours/{providerId}/{workId}/{date}")
    public List<AppointmentRegisterForm> getAvailableHours(@PathVariable("providerId") Long providerId, @PathVariable("workId") Long workId, @PathVariable("date") String date) {
        Optional<Users> currentUsers = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails) return ((CustomUserDetails) u).getUser();
                    else return null;
                });
        LocalDate localDate = LocalDate.parse(date);
        var x = appointmentService.getAvailableHours(providerId, currentUsers.get().getId(), workId, localDate)
                .stream()
                .map(timePeriod -> new AppointmentRegisterForm(workId, providerId, timePeriod.getStart().atDate(localDate), timePeriod.getEnd().atDate(localDate)))
                .collect(Collectors.toList());
        return x;
    }

//    @GetMapping("/users/notifications")
//    public int getUnreadNotificationsCount(@AuthenticationPrincipal CustomUsersDetails currentUsers) {
//        return notificationService.getUnreadNotifications(currentUsers.getId()).size();
//    }

}
