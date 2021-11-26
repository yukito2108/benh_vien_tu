package com.tuannq.store.model.dto;

import com.tuannq.store.entity.OrderItem;
import com.tuannq.store.entity.Orders;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDTO extends AbstractDTO {
    private String code;
    private Long shippingFee;
    private Long totalDiscount;
    private Long totalAmount;
    private String paymentMethod;
    private Boolean alreadyPaid;
    private String status;
    private Boolean isBuyAtStore;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private String note;
    private UserDTO customer;
    private Set<OrderItemDTO> orderItems;

    public OrdersDTO(Orders orders){
        super(
                orders.getId(),
                orders.getCreatedBy(),
                orders.getCreatedAt(),
                orders.getModifiedBy(),
                orders.getModifiedAt(),
                orders.getIsDeleted()
        );
        this.code = orders.getCode();
        this.shippingFee = orders.getShippingFee();
        this.totalAmount = orders.getTotalAmount();
        this.totalDiscount = orders.getTotalDiscount();
        this.paymentMethod = orders.getPaymentMethod();
        this.alreadyPaid = orders.getAlreadyPaid();
        this.status = orders.getStatus();
        this.isBuyAtStore = orders.getIsBuyAtStore();
        this.receiverName = orders.getReceiverName();
        this.receiverPhone = orders.getReceiverPhone();
        this.receiverAddress = orders.getReceiverAddress();
        this.note = orders.getNote();
        this.customer = new UserDTO(orders.getCustomer());
        this.orderItems = orders.getOrderItems().stream()
                .map(OrderItemDTO::new)
                .collect(Collectors.toSet());
    }
}
