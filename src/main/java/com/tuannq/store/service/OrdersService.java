package com.tuannq.store.service;

import com.tuannq.store.entity.Promotion;
import com.tuannq.store.model.Option2;
import com.tuannq.store.model.Option3;
import com.tuannq.store.model.dto.OrdersDTO;
import com.tuannq.store.model.request.*;
import com.tuannq.store.model.response.PageResponse;
import com.tuannq.store.model.type.StatusOrder;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public interface OrdersService {
    List<OrdersDTO> findByCustomerId(Long customerId);

    List<OrdersDTO> findByCustomerIdAndStatus(Long customerId, StatusOrder status);

    PageResponse<OrdersDTO> getOrderListByUserId(Long userId, OrdersSearchFormByCustomer form);

    PageResponse<OrdersDTO> searchFromAdmin(OrdersSearchFormByAdmin form);

    OrdersDTO findById(Long orderId) throws NotFoundException;

    OrdersDTO findById(Long orderId, Long userId) throws NotFoundException;

    Option2<OrdersDTO, String> addByCustomerId(Long userId, OrdersAddForm form) throws NotFoundException, MessagingException, UnsupportedEncodingException;

    OrdersDTO updateStatus(Long orderId, StatusOrder type) throws NotFoundException;

    OrdersDTO updateByCode(String code, OrdersUpdateForm form) throws NotFoundException;

    OrdersDTO findByCode(String orderCode) throws NotFoundException;

    Option3<Promotion, Long, Long> useCoupon(UseCouponForm form);
}
