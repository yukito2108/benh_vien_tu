package com.tuannq.store.controller.appoiment;

import com.tuannq.store.entity.Appointment;
import com.tuannq.store.entity.Users;
import com.tuannq.store.security.CustomUserDetails;
import com.tuannq.store.security.CustomUsersDetails;
import com.tuannq.store.service.AppointmentService;
import com.tuannq.store.service.ExchangeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;
    private final AppointmentService appointmentService;

    public ExchangeController(ExchangeService exchangeService, AppointmentService appointmentService) {
        this.exchangeService = exchangeService;
        this.appointmentService = appointmentService;
    }

    @GetMapping("/{oldAppointmentId}")
    public String showEligibleAppointmentsToExchange(@PathVariable("oldAppointmentId") Long oldAppointmentId, Model model) {
        List<Appointment> eligibleAppointments = exchangeService.getEligibleAppointmentsForExchange(oldAppointmentId);
        model.addAttribute("appointmentId", oldAppointmentId);
        model.addAttribute("numberOfEligibleAppointments", eligibleAppointments.size());
        model.addAttribute("eligibleAppointments", eligibleAppointments);
        return "exchange/listProposals";
    }

    @GetMapping("/{oldAppointmentId}/{newAppointmentId}")
    public String showExchangeSummaryScreen(@PathVariable("oldAppointmentId") Long oldAppointmentId, @PathVariable("newAppointmentId") Long newAppointmentId, Model model) {
        Optional<Users> currentUsers = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails) return ((CustomUserDetails) u).getUser();
                    else return null;
                });
        if (exchangeService.checkIfExchangeIsPossible(oldAppointmentId, newAppointmentId, currentUsers.get().getId())) {
            model.addAttribute("oldAppointment", appointmentService.getAppointmentByIdWithAuthorization(oldAppointmentId));
            model.addAttribute("newAppointment", appointmentService.getAppointmentById(newAppointmentId));
        } else {
            return "redirect:/appointments/all";
        }

        return "exchange/exchangeSummary";
    }

    @PostMapping()
    public String processExchangeRequest(@RequestParam("oldAppointmentId") Long oldAppointmentId, @RequestParam("newAppointmentId") Long newAppointmentId, Model model) {
        Optional<Users> currentUsers = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails) return ((CustomUserDetails) u).getUser();
                    else return null;
                });
        boolean result = exchangeService.requestExchange(oldAppointmentId, newAppointmentId, currentUsers.get().getId());
        if (result) {
            model.addAttribute("message", "Exchange request sucsessfully sent!");
        } else {
            model.addAttribute("message", "Error! Exchange not sent!");
        }
        return "exchange/requestConfirmation";
    }

    @PostMapping("/accept")
    public String processExchangeAcceptation(@RequestParam("exchangeId") Long exchangeId, Model model) {
        Optional<Users> currentUsers = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails) return ((CustomUserDetails) u).getUser();
                    else return null;
                });
        exchangeService.acceptExchange(exchangeId, currentUsers.get().getId());
        return "redirect:/appointments/all";
    }

    @PostMapping("/reject")
    public String processExchangeRejection(@RequestParam("exchangeId") Long exchangeId, Model model) {
        Optional<Users> currentUsers = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(u -> {
                    if (u instanceof CustomUserDetails) return ((CustomUserDetails) u).getUser();
                    else return null;
                });
        exchangeService.rejectExchange(exchangeId, currentUsers.get().getId());
        return "redirect:/appointments/all";
    }
}
