package com.tuannq.store.controller.admin;

import com.tuannq.store.model.dto.OrdersDTO;
import com.tuannq.store.model.dto.ProductDTO;
import com.tuannq.store.model.request.OrdersUpdateForm;
import com.tuannq.store.model.response.SuccessResponse;
import com.tuannq.store.repository.OrdersRepository;
import com.tuannq.store.repository.ProductRepository;
import com.tuannq.store.service.OrderItemService;
import com.tuannq.store.service.OrdersService;
import com.tuannq.store.service.ProductService;
import com.tuannq.store.util.AuthUtils;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ManageOrderController {
    @Autowired
    private AuthUtils authUtils;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrdersService ordersService;

    @GetMapping("/admin/orders")
    public String getOrderManagePage(
            Model model,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String id,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String phone,
            @RequestParam(defaultValue = "") String status,
            @RequestParam(defaultValue = "") String product
    ) {
        // Get list product to select
        List<ProductDTO> products = productRepository.findAll().stream().map(ProductDTO::new).collect(Collectors.toList());
        model.addAttribute("products", products);

        // Get list order
        Page<OrdersDTO> result = ordersRepository.findAll(PageRequest.of(0, 1000)).map(OrdersDTO::new);
        model.addAttribute("orders", result.getContent());
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("currentPage", result.getPageable().getPageNumber() - 1);

        return "admin/order/list";
    }


    @GetMapping("/api/admin/orders/{code}")
    public ResponseEntity<SuccessResponse<OrdersDTO>> findById(
            @PathVariable String code
    ) throws NotFoundException {
        var data = ordersService.findByCode(code);
        return ResponseEntity.ok(new SuccessResponse<>(data));
    }

    @PutMapping("/api/admin/orders/{code}")
    public ResponseEntity<SuccessResponse<OrdersDTO>> updateByCode(
            @PathVariable String code,
            @Validated @RequestBody OrdersUpdateForm form
    ) throws NotFoundException {
        var data = ordersService.updateByCode(code, form);
        return ResponseEntity.ok(new SuccessResponse<>(data));
    }


}
