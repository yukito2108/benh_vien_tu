package com.tuannq.store.controller.anonymous;

import com.mservice.allinone.processor.allinone.QueryStatusTransaction;
import com.tuannq.store.entity.Orders;
import com.tuannq.store.model.dto.OrderItemDTO;
import com.tuannq.store.model.dto.OrdersDTO;
import com.tuannq.store.model.request.IpnMomo;
import com.tuannq.store.model.request.OrdersAddForm;
import com.tuannq.store.model.request.UseCouponForm;
import com.tuannq.store.model.response.SuccessResponse;
import com.tuannq.store.model.type.PaymentMethod;
import com.tuannq.store.model.type.StatusOrder;
import com.tuannq.store.repository.OrdersRepository;
import com.tuannq.store.service.*;
import com.tuannq.store.util.AuthUtils;
import com.tuannq.store.util.ConverterUtils;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.tuannq.store.config.DefaultVariable.ENVIRONMENT_MOMO;

@Slf4j
@Controller
public class OrdersController {
    private final OrdersService ordersService;
    private final OrderItemService orderItemService;
    private final ConverterUtils converterUtils;
    private final AuthUtils authUtils;
    private final PaymentService paymentService;
    private final OrdersRepository ordersRepository;
    private final LocationService locationService;
    private final MailService mailService;

    @Autowired
    public OrdersController(OrdersService ordersService, OrderItemService orderItemService, ConverterUtils converterUtils, AuthUtils authUtils, PaymentService paymentService, OrdersRepository ordersRepository, LocationService locationService, MailService mailService) {
        this.ordersService = ordersService;
        this.orderItemService = orderItemService;
        this.converterUtils = converterUtils;
        this.authUtils = authUtils;
        this.paymentService = paymentService;
        this.ordersRepository = ordersRepository;
        this.locationService = locationService;
        this.mailService = mailService;
    }


    @GetMapping("/checkout/{itemIds}")
    public String checkout(
            @PathVariable List<String> itemIds,
            HttpServletResponse httpResponse,
            Model model
    ) throws IOException {
        var ids = ConverterUtils.convertStringToLong(itemIds);
        var userOpt = authUtils.getUser();
        if (userOpt.isEmpty() || ids == null) {
            httpResponse.sendRedirect("/cart");
            return null;
        }
        var items = orderItemService.findByIdsInCart(userOpt.get().getId(), ids);
        if (items.size() == 0) {
            httpResponse.sendRedirect("/cart");
            return null;
        }

        var totalAmount = items.stream().map(OrderItemDTO::getAmount).reduce(Long::sum).orElse(0L);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("items", items);
        model.addAttribute("province", locationService.findProvince());
        return "account/cart/checkout";
    }

    @GetMapping("/orders")
    public String orders(
            Model model
    ) throws IOException {
        var userOpt = authUtils.getUser();
        if (userOpt.isEmpty()) {
            return "error/401";
        }
        var user = userOpt.get();
        model.addAttribute("converterUtils", converterUtils);
        model.addAttribute("order_all", ordersService.findByCustomerId(user.getId()));
        model.addAttribute("order_new", ordersService.findByCustomerIdAndStatus(user.getId(), StatusOrder.NEW));
        model.addAttribute("order_complete", ordersService.findByCustomerIdAndStatus(user.getId(), StatusOrder.COMPLETE));
        model.addAttribute("order_cancel", ordersService.findByCustomerIdAndStatus(user.getId(), StatusOrder.CANCEL));
        model.addAttribute("order_in_progress", ordersService.findByCustomerIdAndStatus(user.getId(), StatusOrder.IN_PROGRESS));

        return "account/orders/orders";
    }

    @PostMapping("/api/order")
    public ResponseEntity<?> createOrder(
            @Validated @RequestBody OrdersAddForm form
    ) throws NotFoundException, MessagingException, UnsupportedEncodingException {
        var userOpt = authUtils.getUser();
        if (userOpt.isEmpty()) return null;
        var data = ordersService.addByCustomerId(userOpt.get().getId(), form);
        return ResponseEntity.ok(new SuccessResponse<>(data));
    }

    @GetMapping("order/payment/response/{orderCode}/{requestId}")
    public String paymentResponse(
            @PathVariable String orderCode,
            @PathVariable String requestId,
            HttpServletResponse httpResponse
    ) throws Exception {
        //Transaction Query - Kiểm tra trạng thái giao dịch
        var queryStatusTransactionResponse = QueryStatusTransaction.process(ENVIRONMENT_MOMO, orderCode, requestId);
        if (queryStatusTransactionResponse.getErrorCode() == 0) {
            var orders = ordersRepository.findByCodeOrderIdMomo(orderCode).map(o -> {
                o.setAlreadyPaid(true);
                return o;
            }).map(ordersRepository::save);
            if (orders.isPresent())
                mailService.sendBillToCustomer(orders.get().getCode());
            httpResponse.sendRedirect(orders.map(Orders::getCode).map("/thank-you/"::concat).orElse("/cart"));
            return null;
        }
        return "error/404";
    }

    @GetMapping("/thank-you/{orderCode}")
    public String thankYou(
            @PathVariable String orderCode,
            Model model,
            HttpServletResponse httpResponse
    ) throws IOException {
        var orderOpt = ordersRepository.findByCode(orderCode);
        if (orderOpt.isEmpty()) {
            httpResponse.sendRedirect("/cart");
            return null;
        }
        var order = new OrdersDTO(orderOpt.get());

        if (order.getPaymentMethod().equals(PaymentMethod.MOMO.getMethod())) {
            if (!order.getAlreadyPaid()) {
                String redirectLink = paymentService.displayPaymentScreen(order.getId());
                httpResponse.sendRedirect(redirectLink);
                return null;
            }
        }
        model.addAttribute("converterUtils", converterUtils);
        model.addAttribute("order", order);
        return "account/cart/thank-you";
    }


    @PostMapping(value = "order/payment/response", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public void IPNPayment(IpnMomo response) {
        paymentService.IPNProcess(response);
    }

    @PostMapping("api/order/use-coupon")
    public ResponseEntity<SuccessResponse<?>> useCoupon(
            @Validated @RequestBody UseCouponForm form
    ) {
        var data = ordersService.useCoupon(form);

        return ResponseEntity.ok(new SuccessResponse<>(data));
    }
}
