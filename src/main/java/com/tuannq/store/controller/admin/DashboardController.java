package com.tuannq.store.controller.admin;

import com.tuannq.store.entity.Role;
import com.tuannq.store.model.type.StatusOrder;

import com.tuannq.store.repository.OrdersRepository;
import com.tuannq.store.repository.ProductRepository;
import com.tuannq.store.repository.RoleRepository;
import com.tuannq.store.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;

@Controller
public class DashboardController {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private RoleRepository roleRepository;


    @GetMapping("/admin")
    public String getDashboardPage(Model model) {
        HashSet<Role> roles = new HashSet();
        roles.add(roleRepository.findByName("ROLE_CUSTOMER"));
        model.addAttribute("totalProduct", productRepository.findAll().size());
        model.addAttribute("totalCustomer", usersRepository.findAll().stream().filter(u -> u.getRoles().contains(roles)).count());
        model.addAttribute("totalOrdersNew", ordersRepository.findAll().stream().filter(o -> o.getStatus().equals(StatusOrder.NEW.getType())).count());
        model.addAttribute("totalOrdersCancel", ordersRepository.findAll().stream().filter(o -> o.getStatus().equals(StatusOrder.CANCEL.getType())).count());
        return "admin/dashboard";
    }
}
