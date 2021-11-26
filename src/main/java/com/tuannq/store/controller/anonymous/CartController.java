package com.tuannq.store.controller.anonymous;

import com.tuannq.store.model.dto.OrderItemDTO;
import com.tuannq.store.model.request.AddCartForm;
import com.tuannq.store.model.response.SuccessResponse;
import com.tuannq.store.service.OrderItemService;
import com.tuannq.store.util.AuthUtils;
import com.tuannq.store.util.ConverterUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class CartController {
    private final OrderItemService orderItemService;
    private final AuthUtils authUtils;
    private final ConverterUtils converterUtils;

    @Autowired
    public CartController(OrderItemService orderItemService, AuthUtils authUtils, ConverterUtils converterUtils) {
        this.orderItemService = orderItemService;
        this.authUtils = authUtils;
        this.converterUtils = converterUtils;
    }

    @GetMapping("/cart")
    public String getCart(
            Model model,
            HttpServletResponse httpResponse
    ) throws IOException {
        var userOpt = authUtils.getUser();
        if (userOpt.isEmpty()) {
            httpResponse.sendRedirect("/login?redirectURI=/cart");
            return null;
        }
        var items = orderItemService.getCartByUserId(userOpt.get().getId())
                .stream()
                .map(OrderItemDTO::new)
                .collect(Collectors.toList());
        model.addAttribute("cart", items);
        return "account/cart/cart";
    }

    @PostMapping("/api/cart")
    public ResponseEntity<SuccessResponse<OrderItemDTO>> addProductToCart(
            @Validated @RequestBody AddCartForm form
    ) {
        var userOpt = authUtils.getUser();
        if (userOpt.isEmpty()) return null;
        var item = orderItemService.saveProductToCart(
                userOpt.get().getId(),
                ConverterUtils.convertStringToLong(form.getProductId()),
                ConverterUtils.convertStringToInt(form.getQuantity())
        );
        return ResponseEntity.ok(new SuccessResponse<>("Cập nhật giỏ hàng thành công!", new OrderItemDTO(item)));
    }

    @DeleteMapping("/api/cart/{itemId}")
    public ResponseEntity<SuccessResponse<Long>> removeProductInCart(
            @PathVariable Long itemId) {
        var userOpt = authUtils.getUser();
        if (userOpt.isEmpty()) return null;
        var rs = orderItemService.deleteByItemId(userOpt.get().getId(), itemId);
        return ResponseEntity.ok(new SuccessResponse<>("Xóa thành công!", rs));
    }

    @GetMapping("/api/cart}")
    public ResponseEntity<SuccessResponse<List<OrderItemDTO>>> getCart() {
        var userOpt = authUtils.getUser();
        if (userOpt.isEmpty()) return null;
        var items = orderItemService.getCartByUserId(userOpt.get().getId())
                .stream()
                .map(OrderItemDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new SuccessResponse<>(items));
    }
}
