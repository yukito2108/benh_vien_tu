package com.tuannq.store.service;

import com.tuannq.store.entity.Orders;
import com.tuannq.store.model.Option2;
import com.tuannq.store.model.dto.OrderItemDTO;
import com.tuannq.store.model.dto.OrdersDTO;
import com.tuannq.store.repository.OrderItemRepository;
import com.tuannq.store.repository.OrdersRepository;
import com.tuannq.store.util.ConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MailService {
    private final JavaMailSender javaMailSender;
    private final ThymeleafService thymeleafService;
    private final OrdersRepository ordersRepository;
    private final OrderItemRepository orderItemRepository;
    @Value("${spring.mail.username}")
    private String senderEmail;
    @Value("${spring.application.name}")
    private String APPLICATION_NAME;

    @Autowired
    public MailService(JavaMailSender javaMailSender, ThymeleafService thymeleafService, OrdersRepository ordersRepository, OrderItemRepository orderItemRepository) {
        this.javaMailSender = javaMailSender;
        this.thymeleafService = thymeleafService;
        this.ordersRepository = ordersRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public void sendBillToCustomer(String orderCode) throws MessagingException, UnsupportedEncodingException {
        var orderOpt = ordersRepository.findByCode(orderCode);
        if (orderOpt.isEmpty())
            return;
        var order = new OrdersDTO(orderOpt.get());
        var itemOrders = orderItemRepository.findByOrderId(order.getId())
                .stream().map(OrderItemDTO::new)
                .collect(Collectors.toSet());
        order.setOrderItems(itemOrders);

        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        final MimeMessageHelper message =
                new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart
        message.setSubject("Thông báo đơn hàng #" + order.getCode() + " của quý khách đã được tiếp nhận");
        message.setFrom(senderEmail, APPLICATION_NAME);
        message.setTo(order.getCustomer().getEmail());

        // Create the HTML body using Thymeleaf
        final String htmlContent = thymeleafService.getContent(
                "account/orders/send-bill",
                List.of(
                        new Option2<>("order", order),
                        new Option2<>("converterUtils", new ConverterUtils())
                )
        );
        message.setText(htmlContent, true); // true = isHtml
        // Send mail
        javaMailSender.send(mimeMessage);
    }
}
