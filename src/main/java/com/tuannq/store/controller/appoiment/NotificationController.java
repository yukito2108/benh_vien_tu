package com.tuannq.store.controller.appoiment;

import com.tuannq.store.entity.Notification;
import com.tuannq.store.entity.Users;
import com.tuannq.store.security.CustomUserDetails;
import com.tuannq.store.security.CustomUsersDetails;
import com.tuannq.store.service.NotificationService;
import com.tuannq.store.service.UsersService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final UsersService usersService;

    public NotificationController(NotificationService notificationService, UsersService usersService) {
        this.notificationService = notificationService;
        this.usersService = usersService;
    }

    @GetMapping()
    public String showUsersNotificationList(Model model) {
        Optional<Users> currentUsers = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails) return ((CustomUserDetails) u).getUser();
                    else return null;
                });
        model.addAttribute("notifications", usersService.getUsersById(currentUsers.get().getId()).getNotifications());
        return "notifications/listNotifications";
    }

    @GetMapping("/{notificationId}")
    public String showNotification(@PathVariable("notificationId") Long notificationId) {
        Optional<Users> currentUsers = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails) return ((CustomUserDetails) u).getUser();
                    else return null;
                });
        Notification notification = notificationService.getNotificationById(notificationId);
        notificationService.markAsRead(notificationId, currentUsers.get().getId());
        return "redirect:" + notification.getUrl();
    }

    @PostMapping("/markAllAsRead")
    public String processMarkAllAsRead() {
        Optional<Users> currentUsers = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails) return ((CustomUserDetails) u).getUser();
                    else return null;
                });
        notificationService.markAllAsRead(currentUsers.get().getId());
        return "redirect:/notifications";
    }
}
