package com.tuannq.store.controller.appoiment;

import com.tuannq.store.entity.Appointment;
import com.tuannq.store.entity.ChatMessage;
import com.tuannq.store.entity.Image;
import com.tuannq.store.entity.Users;
import com.tuannq.store.security.CustomUserDetails;
import com.tuannq.store.security.CustomUsersDetails;
import com.tuannq.store.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    private static final String REJECTION_CONFIRMATION_VIEW = "appointments/rejectionConfirmation";

    private final WorkService workService;
    private final UsersService usersService;
    private final AppointmentService appointmentService;
    private final ExchangeService exchangeService;
    private final ImageService imageService;

    public AppointmentController(WorkService workService, UsersService usersService, AppointmentService appointmentService,
                                 ExchangeService exchangeService, ImageService imageService) {
        this.workService = workService;
        this.usersService = usersService;
        this.appointmentService = appointmentService;
        this.exchangeService = exchangeService;
        this.imageService = imageService;
    }

    @GetMapping("/all")
    public String showAllAppointments(Model model ) {
        String appointmentsModelName = "appointments";
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
        System.out.println("id="+currentUsers.get().getId());
        if (currentUsers.get().hasRole("ROLE_CUSTOMER")) {
            System.out.println("id_customer="+Math.toIntExact(currentUsers.get().getId()));
            model.addAttribute(appointmentsModelName, appointmentService.getAppointmentByCustomerId(currentUsers.get().getId()));
            System.out.println("ROLE_CUSTOMER");
        } else if (currentUsers.get().hasRole("ROLE_PROVIDER")) {
            System.out.println("id_provider="+Math.toIntExact(currentUsers.get().getId()));
            model.addAttribute(appointmentsModelName, appointmentService.getAppointmentByProviderId(currentUsers.get().getId()));
            System.out.println("ROLE_PROVIDER");
        } else if (currentUsers.get().hasRole("ROLE_ADMIN")) {
            System.out.println("id_admin="+currentUsers.get().getId());
            model.addAttribute(appointmentsModelName, appointmentService.getAllAppointments());
            System.out.println("ROLE_ADMIN");
        }
        if(currentUsers.get().hasRole("ROLE_CUSTOMER")){
            return "medikal/appointment";
        }
        return "appointments/listAppointments";
    }

    @GetMapping("/{id}")
    public String showAppointmentDetail(@PathVariable("id") Long appointmentId, Model model) {
        Optional<Users> currentUsers = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails) return ((CustomUserDetails) u).getUser();
                    else return null;
                });
        Appointment appointment = appointmentService.getAppointmentByIdWithAuthorization(appointmentId);
        model.addAttribute("appointment", appointment);
        model.addAttribute("chatMessage", new ChatMessage());
        boolean allowedToRequestRejection = appointmentService.isCustomerAllowedToRejectAppointment(currentUsers.get().getId(), appointmentId);
        boolean allowedToAcceptRejection = appointmentService.isProviderAllowedToAcceptRejection(currentUsers.get().getId(), appointmentId);
        boolean allowedToExchange = exchangeService.checkIfEligibleForExchange(currentUsers.get().getId(), appointmentId);
        model.addAttribute("allowedToRequestRejection", allowedToRequestRejection);
        model.addAttribute("allowedToAcceptRejection", allowedToAcceptRejection);
        model.addAttribute("allowedToExchange", allowedToExchange);
        if (allowedToRequestRejection) {
            model.addAttribute("remainingTime", formatDuration(Duration.between(LocalDateTime.now(), appointment.getEnd().plusDays(1))));
        }
        String cancelNotAllowedReason = appointmentService.getCancelNotAllowedReason(currentUsers.get().getId(), appointmentId);
        model.addAttribute("allowedToCancel", cancelNotAllowedReason == null);
        model.addAttribute("cancelNotAllowedReason", cancelNotAllowedReason);
        if(currentUsers.get().hasRole("ROLE_CUSTOMER")){
            return "medikal/appointment_detail";
        }
        return "appointments/appointmentDetail";
    }


    @PostMapping("/reject")
    public String processAppointmentRejectionRequest(@RequestParam("appointmentId") Long appointmentId, Model model) {
        Optional<Users> currentUsers = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails) return ((CustomUserDetails) u).getUser();
                    else return null;
                });
        boolean result = appointmentService.requestAppointmentRejection(appointmentId, currentUsers.get().getId());
        model.addAttribute(result);
        model.addAttribute("type", "request");
        return REJECTION_CONFIRMATION_VIEW;
    }

    @GetMapping("/reject")
    public String processAppointmentRejectionRequest(@RequestParam("token") String token, Model model) {
        boolean result = appointmentService.requestAppointmentRejection(token);
        model.addAttribute(result);
        model.addAttribute("type", "request");
        return REJECTION_CONFIRMATION_VIEW;
    }

    @PostMapping("/acceptRejection")
    public String acceptAppointmentRejectionRequest(@RequestParam("appointmentId") Long appointmentId, Model model) {
        Optional<Users> currentUsers = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails) return ((CustomUserDetails) u).getUser();
                    else return null;
                });
        boolean result = appointmentService.acceptRejection(appointmentId, currentUsers.get().getId());
        model.addAttribute(result);
        model.addAttribute("type", "accept");
        return REJECTION_CONFIRMATION_VIEW;
    }

    @GetMapping("/acceptRejection")
    public String acceptAppointmentRejectionRequest(@RequestParam("token") String token, Model model) {
        boolean result = appointmentService.acceptRejection(token);
        model.addAttribute(result);
        model.addAttribute("type", "accept");
        return REJECTION_CONFIRMATION_VIEW;
    }

    @PostMapping("/messages/new")
    public String addNewChatMessage(@ModelAttribute("chatMessage") ChatMessage chatMessage, @RequestParam("appointmentId") Long appointmentId) {
        Optional<Users> currentUsers = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails) return ((CustomUserDetails) u).getUser();
                    else return null;
                });
        Long authorId = currentUsers.get().getId();
        appointmentService.addMessageToAppointmentChat(appointmentId, authorId, chatMessage);
        return "redirect:/appointments/" + appointmentId;
    }

    @GetMapping("/new")
    public String selectProvider(Model model) {
        var images = imageService.getAll().stream().map(Image::getLink).collect(Collectors.toList());
        model.addAttribute("images", images);
        Optional<Users> currentUsers = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails) return ((CustomUserDetails) u).getUser();
                    else return null;
                });
        if (currentUsers.get().hasRole("ROLE_CUSTOMER_RETAIL")) {
            model.addAttribute("providers", usersService.getProvidersWithRetailWorks());
        } else if (currentUsers.get().hasRole("ROLE_CUSTOMER_CORPORATE")) {
            model.addAttribute("providers", usersService.getProvidersWithCorporateWorks());
        }
        if(currentUsers.get().hasRole("ROLE_CUSTOMER")){
            return "medikal/appointment_add_doctor";
        }
        return "appointments/selectProvider";
    }

    @GetMapping("/new/{providerId}")
    public String selectService(@PathVariable("providerId") Long providerId, Model model) {
        Optional<Users> currentUsers = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails) return ((CustomUserDetails) u).getUser();
                    else return null;
                });
        if (currentUsers.get().hasRole("ROLE_CUSTOMER_RETAIL")) {
            model.addAttribute("works", workService.getWorksForRetailCustomerByProviderId(providerId));
        } else if (currentUsers.get().hasRole("ROLE_CUSTOMER_CORPORATE")) {
            model.addAttribute("works", workService.getWorksForCorporateCustomerByProviderId(providerId));
        }
        model.addAttribute(providerId);
        if(currentUsers.get().hasRole("ROLE_CUSTOMER")){
            return "medikal/appointment_add_work";
        }
        return "appointments/selectService";
    }

    @GetMapping("/new/{providerId}/{workId}")
    public String selectDate(@PathVariable("workId") Long workId, @PathVariable("providerId") Long providerId, Model model) {
        Optional<Users> currentUsers = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails) return ((CustomUserDetails) u).getUser();
                    else return null;
                });
        if (workService.isWorkForCustomer(workId, currentUsers.get().getId())) {
            model.addAttribute(providerId);
            model.addAttribute("workId", workId);
            if(currentUsers.get().hasRole("ROLE_CUSTOMER")){
                return "medikal/appointment_add_time";
            }
            return "appointments/selectDate";
        } else {
            return "redirect:/appointments/new";
        }

    }

    @GetMapping("/new/{providerId}/{workId}/{dateTime}")
    public String showNewAppointmentSummary(@PathVariable("workId") Long workId, @PathVariable("providerId") Long providerId, @PathVariable("dateTime") String start, Model model) {
        Optional<Users> currentUsers = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails) return ((CustomUserDetails) u).getUser();
                    else return null;
                });
        if (appointmentService.isAvailable(workId, providerId, currentUsers.get().getId(), LocalDateTime.parse(start))) {
            model.addAttribute("work", workService.getWorkById(workId));
            model.addAttribute("provider", usersService.getProviderById(providerId).getFirstName() + " " + usersService.getProviderById(providerId).getLastName());
            model.addAttribute(providerId);
            model.addAttribute("start", LocalDateTime.parse(start));
            model.addAttribute("end", LocalDateTime.parse(start).plusMinutes(workService.getWorkById(workId).getDuration()));
            if(currentUsers.get().hasRole("ROLE_CUSTOMER")){
                return "medikal/appointment_add_overview";
            }
            return "appointments/newAppointmentSummary";
        } else {
            return "redirect:/appointments/new";
        }
    }

    @PostMapping("/new")
    public String bookAppointment(@RequestParam("workId") Long workId, @RequestParam("providerId") Long providerId, @RequestParam("start") String start) {
        Optional<Users> currentUsers = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails) return ((CustomUserDetails) u).getUser();
                    else return null;
                });
        appointmentService.createNewAppointment(workId, providerId, currentUsers.get().getId(), LocalDateTime.parse(start));
        return "redirect:/appointments/all";
    }

    @PostMapping("/cancel")
    public String cancelAppointment(@RequestParam("appointmentId") Long appointmentId) {
        Optional<Users> currentUsers = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails) return ((CustomUserDetails) u).getUser();
                    else return null;
                });
        appointmentService.cancelUsersAppointmentById(appointmentId, currentUsers.get().getId());
        return "redirect:/appointments/all";
    }


    public static String formatDuration(Duration duration) {
        long s = duration.getSeconds();
        return String.format("%dh%02dm", s / 3600, (s % 3600) / 60);
    }

}
