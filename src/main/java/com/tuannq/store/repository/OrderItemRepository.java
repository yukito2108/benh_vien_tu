package com.tuannq.store.repository;

import com.tuannq.store.entity.OrderItem;
import com.tuannq.store.model.dto.OrdersDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("select x1 from OrderItem x1 " +
            " where x1.userId = :userId and x1.order is null")
    List<OrderItem> findCartByAndUserId(@Param("userId") Long userId);

    @Query("select x1 from OrderItem x1 " +
            " where x1.userId = :userId " +
            "   and x1.order is null" +
            "   and x1.product.id = :productId")
    Optional<OrderItem> findCartByAndUserIdAndProductId(
            @Param("userId") Long userId,
            @Param("productId") Long productId
    );

    @Transactional
    @Modifying
    @Query("delete from OrderItem x1 " +
            " where x1.id = :itemId " +
            "   and x1.userId = :userId " +
            "   and x1.order is null ")
    void deleteInCartByUserId(
            @Param("userId") Long userId,
            @Param("itemId") Long itemId
    );

    @Query("select x1 from OrderItem x1 " +
            " where x1.userId = :userId " +
            "   and x1.order is null ")
    List<OrderItem> getCartByUserId(@Param("userId") Long userId);

    @Query("select x1 from OrderItem x1 " +
            " where x1.userId = :userId " +
            "   and x1.order is null " +
            "   and x1.id in :#{#ids} ")
    List<OrderItem> findByIdsInCart(@Param("userId") Long userId, @Param("ids") List<Long> ids);

    @Query("select x1 from OrderItem x1 " +
            " where x1.id in :#{#ids} and x1.order is null ")
    List<OrderItem> findByIdsAndOrderIsNull(@Param("ids") Set<Long> ids);

    @Query("select x1 from OrderItem x1 " +
            " where x1.order.id = :#{#orderId} ")
    Set<OrderItem> findByOrderId(Long orderId);
}
