package com.tuannq.store.model.dto;

import com.tuannq.store.entity.OrderItem;
import com.tuannq.store.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO extends AbstractDTO {
    private Long userId;
    private Long price;
    private Integer quantity;
    private Long orderId;
    private ProductDTO product;
    private Long amount;

    public OrderItemDTO(OrderItem item) {
        super(
                item.getId(),
                item.getCreatedBy(),
                item.getCreatedAt(),
                item.getModifiedBy(),
                item.getModifiedAt(),
                item.getIsDeleted()
        );
        this.userId = item.getUserId();
        this.price = item.getPrice();
        this.quantity = item.getQuantity();
        this.orderId = Optional.ofNullable(item.getOrder()).map(Orders::getId).orElse(null);
        this.product = new ProductDTO(item.getProduct());
        this.amount = item.getQuantity() * item.getProduct().getPriceFinal();
    }
}
