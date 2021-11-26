//package com.tuannq.store.controller.appoiment;
//
//import com.tuannq.store.entity.user.customer.Customer;
//import com.tuannq.store.model.ChangePasswordForm;
//import com.tuannq.store.model.UsersForm;
//import com.tuannq.store.security.CustomUsersDetails;
//import com.tuannq.store.service.AppointmentService;
//import com.tuannq.store.service.UsersService;
//import com.tuannq.store.validation.groups.CreateCorporateCustomer;
//import com.tuannq.store.validation.groups.CreateUsers;
//import com.tuannq.store.validation.groups.UpdateCorporateCustomer;
//import com.tuannq.store.validation.groups.UpdateUsers;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import javax.validation.Valid;
//
//@Controller
//@RequestMapping("/customers")
//public class CustomerController {
//
//    private final UsersService usersService;
//    private final AppointmentService appointmentService;
//
//    public CustomerController(UsersService usersService, AppointmentService appointmentService) {
//        this.usersService = usersService;
//        this.appointmentService = appointmentService;
//    }
//
//    @GetMapping("/all")
//    public String showAllCustomers(Model model) {
//        model.addAttribute("customers", usersService.getAllCustomers());
//        return "users/listCustomers";
//    }
//
//    @GetMapping("/{id}")
//    public String showCustomerDetails(@PathVariable int id, Model model) {
//        Customer customer = usersService.getCustomerById(id);
//        if (customer.hasRole("ROLE_CUSTOMER_CORPORATE")) {
//            if (!model.containsAttribute("users")) {
//                model.addAttribute("users", new UsersForm(usersService.getCorporateCustomerById(id)));
//            }
//            model.addAttribute("account_type", "customer_corporate");
//            model.addAttribute("formActionProfile", "/customers/corporate/update/profile");
//        } else if (customer.hasRole("ROLE_CUSTOMER_RETAIL")) {
//            if (!model.containsAttribute("users")) {
//                model.addAttribute("users", new UsersForm(usersService.getRetailCustomerById(id)));
//            }
//            model.addAttribute("account_type", "customer_retail");
//            model.addAttribute("formActionProfile", "/customers/retail/update/profile");
//        }
//        if (!model.containsAttribute("passwordChange")) {
//            model.addAttribute("passwordChange", new ChangePasswordForm(id));
//        }
//        model.addAttribute("formActionPassword", "/customers/update/password");
//        model.addAttribute("numberOfScheduledAppointments", appointmentService.getNumberOfScheduledAppointmentsForUsers(id));
//        model.addAttribute("numberOfCanceledAppointments", appointmentService.getNumberOfCanceledAppointmentsForUsers(id));
//        return "users/updateUsersForm";
//    }
//
//    @PostMapping("/corporate/update/profile")
//    public String processCorporateCustomerProfileUpdate(@Validated({UpdateUsers.class, UpdateCorporateCustomer.class}) @ModelAttribute("users") UsersForm users, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.users", bindingResult);
//            redirectAttributes.addFlashAttribute("users", users);
//            return "redirect:/customers/" + users.getId();
//        }
//        usersService.updateCorporateCustomerProfile(users);
//        return "redirect:/customers/" + users.getId();
//    }
//
//    @PostMapping("/retail/update/profile")
//    public String processRetailCustomerProfileUpdate(@Validated({UpdateUsers.class}) @ModelAttribute("users") UsersForm users, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.users", bindingResult);
//            redirectAttributes.addFlashAttribute("users", users);
//            return "redirect:/customers/" + users.getId();
//        }
//        usersService.updateRetailCustomerProfile(users);
//        return "redirect:/customers/" + users.getId();
//    }
//
//
//    @GetMapping("/new/{customer_type}")
//    public String showCustomerRegistrationForm(@PathVariable("customer_type") String customerType, Model model, @AuthenticationPrincipal CustomUsersDetails currentUsers) {
//        if (currentUsers != null) {
//            return "redirect:/";
//        }
//        if (customerType.equals("corporate")) {
//            model.addAttribute("account_type", "customer_corporate");
//            model.addAttribute("registerAction", "/customers/new/corporate");
//        } else if (customerType.equals("retail")) {
//            model.addAttribute("account_type", "customer_retail");
//            model.addAttribute("registerAction", "/customers/new/retail");
//        } else {
//            throw new RuntimeException();
//        }
//        model.addAttribute("users", new UsersForm());
//        return "users/createUsersForm";
//    }
//
//
//    @PostMapping("/new/retail")
//    public String processReatilCustomerRegistration(@Validated({CreateUsers.class}) @ModelAttribute("users") UsersForm usersForm, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            populateModel(model, usersForm, "customer_retail", "/customers/new/retail", null);
//            return "users/createUsersForm";
//        }
//        usersService.saveNewRetailCustomer(usersForm);
//        model.addAttribute("createdUserName", usersForm.getUserName());
//        return "users/login";
//    }
//
//    @PostMapping("/new/corporate")
//    public String processCorporateCustomerRegistration(@Validated({CreateUsers.class, CreateCorporateCustomer.class}) @ModelAttribute("users") UsersForm usersForm, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            populateModel(model, usersForm, "customer_corporate", "/customers/new/corporate", null);
//            return "users/createUsersForm";
//        }
//        usersService.saveNewCorporateCustomer(usersForm);
//        model.addAttribute("createdUserName", usersForm.getUserName());
//        return "users/login";
//    }
//
//
//    @PostMapping("/update/password")
//    public String processCustomerPasswordUpate(@Valid @ModelAttribute("passwordChange") ChangePasswordForm passwordChange, BindingResult bindingResult, @AuthenticationPrincipal CustomUsersDetails currentUsers, RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.passwordChange", bindingResult);
//            redirectAttributes.addFlashAttribute("passwordChange", passwordChange);
//            return "redirect:/customers/" + currentUsers.getId();
//        }
//        usersService.updateUsersPassword(passwordChange);
//        return "redirect:/customers/" + currentUsers.getId();
//    }
//
//    @PostMapping("/delete")
//    public String processDeleteCustomerRequest(@RequestParam("customerId") Long customerId) {
//        usersService.deleteUsersById(customerId);
//        return "redirect:/customers/all";
//    }
//
//    public Model populateModel(Model model, UsersForm users, String account_type, String action, String error) {
//        model.addAttribute("users", users);
//        model.addAttribute("account_type", account_type);
//        model.addAttribute("registerAction", action);
//        model.addAttribute("registrationError", error);
//        return model;
//    }
//
//}
