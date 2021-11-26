package com.tuannq.store.service;

import com.tuannq.store.entity.OrderItem;
import com.tuannq.store.model.dto.OrderItemDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderItemService {
    OrderItem saveProductToCart(Long userId, Long productId, Integer quantity);

    Long deleteByItemId(Long userId, Long itemId);

    List<OrderItem> getCartByUserId(Long userId);

    List<OrderItemDTO> findByIdsInCart(Long userId, List<Long> ids);
}
