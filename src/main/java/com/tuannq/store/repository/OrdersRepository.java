package com.tuannq.store.repository;

import com.tuannq.store.entity.Orders;
import com.tuannq.store.model.dto.OrdersDTO;
import com.tuannq.store.model.request.OrdersSearchFormByAdmin;
import com.tuannq.store.model.request.OrdersSearchFormByCustomer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    @Query("select x1 from Orders x1 where x1.customer.id = :userId " +
            "   and (:#{#form.status} is null or x1.status = :#{#form.status}) " +
            "   and (:#{#form.note} is null or lower(x1.note) like lower(concat('%',:#{#form.note},'%'))) " +
            "   and (:#{#form.isBuyAtStore} is null or :#{#form.isBuyAtStore} = false or x1.isBuyAtStore = :#{#form.isBuyAtStore}) ")
    Page<Orders> findByCustomerId(
            @Param("userId") Long userId,
            @Param("form") OrdersSearchFormByCustomer form,
            Pageable pageable
    );

    @Query("select x1 from Orders x1 " +
            " where (:#{#form.status} is null or x1.status = :#{#form.status}) " +
            "   and (:#{#form.customerName} is null or lower(x1.customer.firstName) like lower(concat('%',:#{#form.customerName},'%'))) " +
            "   and (:#{#form.note} is null or lower(x1.note) like lower(concat('%',:#{#form.note},'%'))) " +
            "   and (:#{#form.receiverName} is null or lower(x1.receiverName) like lower(concat('%',:#{#form.receiverName},'%'))) " +
            "   and (:#{#form.receiverPhone} is null or lower(x1.receiverPhone) like lower(concat('%',:#{#form.receiverPhone},'%'))) " +
            "   and (:#{#form.receiverAddress} is null or lower(x1.receiverAddress) like lower(concat('%',:#{#form.receiverAddress},'%'))) " +
            "   and (:#{#form.paymentMethod} is null or x1.paymentMethod = :#{#form.paymentMethod}) " +
            "   and (:#{#form.isBuyAtStore} is null or :#{#form.isBuyAtStore} = false or x1.isBuyAtStore = :#{#form.isBuyAtStore}) ")
    Page<Orders> searchFromAdmin(
            @Param("form") OrdersSearchFormByAdmin form,
            Pageable pageable
    );

    @Query("select x1 from Orders x1 " +
            " where x1.customer.id = :#{#userId} and x1.id = :orderId ")
    Optional<Orders> findByOrderIdAndUserId(
            @Param("orderId") Long orderId,
            @Param("userId") Long userId
    );

    @Query("select x1 from Orders x1 " +
            " where x1.code = :#{#code} ")
    Optional<Orders> findByCode(@Param("code") String code);

    @Query("select x1 from Orders x1 " +
            " where x1.transactionCodeMomo = :#{#code} ")
    Optional<Orders> findByCodeOrderIdMomo(@Param("code") String code);


    @Query("select x1 from Orders x1 " +
            " where x1.customer.id = :#{#customerId} " +
            "   and x1.orderItems.size > 0 " +
            " order by x1.createdAt desc ")
    List<Orders> findByCustomerId(@Param("customerId") Long customerId);

    @Query("select x1 from Orders x1 " +
            " where x1.customer.id = :#{#customerId} " +
            "   and x1.orderItems.size > 0 " +
            "   and x1.status = :#{#status} " +
            " order by x1.createdAt desc ")
    List<Orders> findByCustomerIdAndStatus(@Param("customerId") Long customerId, @Param("status") String status);
}
