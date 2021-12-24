package com.tuannq.store.controller.appoiment;

import com.tuannq.store.entity.Image;
import com.tuannq.store.entity.Users;
import com.tuannq.store.entity.WorkingPlan;
import com.tuannq.store.model.ChangePasswordForm;
import com.tuannq.store.model.TimePeroid;
import com.tuannq.store.model.UsersForm;
import com.tuannq.store.security.CustomUserDetails;
import com.tuannq.store.security.CustomUsersDetails;
import com.tuannq.store.service.*;
import com.tuannq.store.validation.groups.CreateProvider;
import com.tuannq.store.validation.groups.CreateUsers;
import com.tuannq.store.validation.groups.UpdateProvider;
import com.tuannq.store.validation.groups.UpdateUsers;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/providers")
public class ProviderController {

    private final UsersService usersService;
    private final WorkService workService;
    private final WorkingPlanService workingPlanService;
    private final AppointmentService appointmentService;
    private final ImageService imageService;

    public ProviderController(UsersService usersService, WorkService workService, WorkingPlanService workingPlanService, AppointmentService appointmentService, ImageService imageService) {
        this.usersService = usersService;
        this.workService = workService;
        this.workingPlanService = workingPlanService;
        this.appointmentService = appointmentService;
        this.imageService = imageService;
    }


    @GetMapping("/all")
    public String showAllProviders(Model model) {
        model.addAttribute("providers", usersService.getAllProviders());
        return "users/listProviders";
    }

    @GetMapping("/{id}")
    public String showProviderDetails(@PathVariable("id") Long providerId, Model model) {
        var provider = usersService.getProviderById(providerId);
        Optional<Users> currentUsers = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails) return ((CustomUserDetails) u).getUser();
                    else return null;
                });
        if (currentUsers.get().getId() == providerId || currentUsers.get().hasRole("ROLE_ADMIN")) {
            System.out.println("i'm in");
            if (!model.containsAttribute("users")) {
                System.out.println("i'm in 1");
                model.addAttribute("users", new UsersForm(usersService.getProviderById(providerId)));
            }
            if (!model.containsAttribute("passwordChange")) {
                System.out.println("i'm in 2");
                model.addAttribute("passwordChange", new ChangePasswordForm(providerId));
            }
            var images = imageService.getAll().stream().map(Image::getLink).collect(Collectors.toList());
            System.out.println("i'm in 3");
            model.addAttribute("images", images);
            model.addAttribute("provider", provider);
            model.addAttribute("account_type", "provider");
            model.addAttribute("formActionProfile", "/providers/update/profile");
            model.addAttribute("formActionPassword", "/providers/update/password");
            model.addAttribute("allWorks", workService.getAllWorks());
            model.addAttribute("numberOfScheduledAppointments", appointmentService.getNumberOfScheduledAppointmentsForUsers(providerId));
            model.addAttribute("numberOfCanceledAppointments", appointmentService.getNumberOfCanceledAppointmentsForUsers(providerId));
            return "users/updateUsersForm";

        } else {
            throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
        }
    }

    @PostMapping("/update/profile")
    public String processProviderUpdate(@Validated({UpdateUsers.class, UpdateProvider.class}) @ModelAttribute("users") UsersForm usersUpdateData, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.users", bindingResult);
            redirectAttributes.addFlashAttribute("users", usersUpdateData);
            return "redirect:/providers/" + usersUpdateData.getId();
        }
        usersService.updateProviderProfile(usersUpdateData);
        return "redirect:/providers/" + usersUpdateData.getId();
    }

    @GetMapping("/new")
    public String showProviderRegistrationForm(Model model) {
        if (!model.containsAttribute("users")) model.addAttribute("users", new UsersForm());
        var images = imageService.getAll().stream().map(Image::getLink).collect(Collectors.toList());
        model.addAttribute("account_type", "provider");
        model.addAttribute("registerAction", "/providers/new");
        model.addAttribute("allWorks", workService.getAllWorks());
        model.addAttribute("images", images);
        return "users/createUsersForm";
    }

    @PostMapping("/new")
    public String processProviderRegistrationForm(@Validated({CreateUsers.class, CreateProvider.class}) @ModelAttribute("users") UsersForm usersForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.users", bindingResult);
            redirectAttributes.addFlashAttribute("users", usersForm);
            return "redirect:/providers/new";
        }
        usersService.saveNewProvider(usersForm);
        return "redirect:/providers/all";
    }

    @PostMapping("/delete")
    public String processDeleteProviderRequest(@RequestParam("providerId") Long providerId) {
        usersService.deleteUsersById(providerId);
        return "redirect:/providers/all";
    }

    @GetMapping("/availability")
    public String showProviderAvailability(Model model) {
        Optional<Users> currentUsers = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails) return ((CustomUserDetails) u).getUser();
                    else return null;
                });
        model.addAttribute("plan", workingPlanService.getWorkingPlanByProviderId(currentUsers.get().getId()));
        model.addAttribute("breakModel", new TimePeroid());
        return "users/showOrUpdateProviderAvailability";
    }

    @PostMapping("/availability")
    public String processProviderWorkingPlanUpdate(@ModelAttribute("plan") WorkingPlan plan) {
        workingPlanService.updateWorkingPlan(plan);
        return "redirect:/providers/availability";
    }

    @PostMapping("/availability/breakes/add")
    public String processProviderAddBreak(@ModelAttribute("breakModel") TimePeroid breakToAdd, @RequestParam("planId") Long planId, @RequestParam("dayOfWeek") String dayOfWeek) {
        workingPlanService.addBreakToWorkingPlan(breakToAdd, planId, dayOfWeek);
        return "redirect:/providers/availability";
    }

    @PostMapping("/availability/breakes/delete")
    public String processProviderDeleteBreak(@ModelAttribute("breakModel") TimePeroid breakToDelete, @RequestParam("planId") Long planId, @RequestParam("dayOfWeek") String dayOfWeek) {
        workingPlanService.deleteBreakFromWorkingPlan(breakToDelete, planId, dayOfWeek);
        return "redirect:/providers/availability";
    }

    @PostMapping("/update/password")
    public String processProviderPasswordUpate(@Valid @ModelAttribute("passwordChange") ChangePasswordForm passwordChange, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.passwordChange", bindingResult);
            redirectAttributes.addFlashAttribute("passwordChange", passwordChange);
            return "redirect:/providers/" + passwordChange.getId();
        }
        usersService.updateUsersPassword(passwordChange);
        return "redirect:/providers/" + passwordChange.getId();
    }


}
