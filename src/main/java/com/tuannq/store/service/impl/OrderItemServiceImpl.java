package com.tuannq.store.service.impl;

import com.tuannq.store.entity.OrderItem;
import com.tuannq.store.exception.ArgumentException;
import com.tuannq.store.model.dto.OrderItemDTO;
import com.tuannq.store.repository.OrderItemRepository;
import com.tuannq.store.repository.ProductRepository;
import com.tuannq.store.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public OrderItem saveProductToCart(Long userId, Long productId, Integer quantity) {
        var productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty())
            throw new ArgumentException("productId", "Khong ton tai productId = " + productId);
        var item = new OrderItem();
        item.setUserId(userId);
        item.setProduct(productOpt.get());
        item.setQuantity(quantity);
        item.setOrder(null);

        var orderItem = orderItemRepository.findCartByAndUserIdAndProductId(userId, productId)
                .map(i -> {
                    i.setQuantity(quantity);
                    return i;
                }).orElse(item);

        return orderItemRepository.save(orderItem);
    }

    @Transactional
    @Override
    public Long deleteByItemId(Long userId, Long itemId) {
        orderItemRepository.deleteInCartByUserId(userId, itemId);
        return itemId;
    }

    @Override
    public List<OrderItem> getCartByUserId(Long userId) {
        return orderItemRepository.getCartByUserId(userId);
    }

    @Override
    public List<OrderItemDTO> findByIdsInCart(Long userId, List<Long> ids) {
        return orderItemRepository.findByIdsInCart(userId, ids)
                .stream()
                .map(OrderItemDTO::new)
                .collect(Collectors.toList());
    }
}
