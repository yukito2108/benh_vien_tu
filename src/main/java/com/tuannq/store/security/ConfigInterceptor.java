package com.tuannq.store.security;

import com.tuannq.store.model.dto.OrderItemDTO;
import com.tuannq.store.service.OrderItemService;

import com.tuannq.store.util.AuthUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Configuration
public class ConfigInterceptor implements HandlerInterceptor {

    private final AuthUtils authUtils;
    private final OrderItemService orderItemService;

    public ConfigInterceptor(AuthUtils authUtils, OrderItemService orderItemService) {

        this.authUtils = authUtils;
        this.orderItemService = orderItemService;
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView
    ) throws Exception {
        if (modelAndView == null) return;




        var userOpt = authUtils.getUser();
        if (userOpt.isPresent()) {
            var items = orderItemService.getCartByUserId(userOpt.get().getId())
                    .stream()
                    .map(OrderItemDTO::new)
                    .collect(Collectors.toList());

            modelAndView.addObject("_cart", items);
            modelAndView.addObject("_user", userOpt.get());
        } else {
            modelAndView.addObject("_cart", new ArrayList<>());
            modelAndView.addObject("_totalAmount", 0);
        }
    }
}
