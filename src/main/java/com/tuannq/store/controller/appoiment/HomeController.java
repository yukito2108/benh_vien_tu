package com.tuannq.store.controller.appoiment;

import com.tuannq.store.entity.Users;
import com.tuannq.store.security.CustomUserDetails;
import com.tuannq.store.security.CustomUsersDetails;
import com.tuannq.store.service.UsersService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final UsersService usersService;

    public HomeController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/")
    public String showHome(Model model) {
        Optional<Users> currentUsers = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails) return ((CustomUserDetails) u).getUser();
                    else return null;
                });
        model.addAttribute("users", usersService.getUsersById(currentUsers.get().getId()));
        if(currentUsers.get().hasRole("ROLE_CUSTOMER")){
            return "medikal/appointment_home";
        }
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model, @AuthenticationPrincipal CustomUsersDetails currentUsers) {
        if (currentUsers != null) {
            return "redirect:/";
        }
        return "users/login";
    }

    @GetMapping("/access-denied")
    public String showAccessDeniedPage() {
        return "access-denied";
    }


}
