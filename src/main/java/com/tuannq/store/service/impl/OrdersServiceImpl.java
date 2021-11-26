package com.tuannq.store.service.impl;

import com.tuannq.store.entity.Orders;
import com.tuannq.store.entity.Promotion;
import com.tuannq.store.exception.ArgumentException;
import com.tuannq.store.model.Option2;
import com.tuannq.store.model.Option3;
import com.tuannq.store.model.dto.OrdersDTO;
import com.tuannq.store.model.request.*;
import com.tuannq.store.model.response.PageResponse;
import com.tuannq.store.model.type.PaymentMethod;
import com.tuannq.store.model.type.StatusOrder;
import com.tuannq.store.repository.OrderItemRepository;
import com.tuannq.store.repository.OrdersRepository;
import com.tuannq.store.repository.PromotionRepository;
import com.tuannq.store.repository.UsersRepository;
import com.tuannq.store.service.LocationService;
import com.tuannq.store.service.MailService;
import com.tuannq.store.service.OrdersService;
import com.tuannq.store.service.PaymentService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tuannq.store.config.DefaultVariable.DISCOUNT_AMOUNT;
import static com.tuannq.store.config.DefaultVariable.LIMIT;

@Component
public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepository;
    private final OrderItemRepository orderItemRepository;
    private final UsersRepository usersRepository;
    private final MessageSource messageSource;
    private final PaymentService paymentService;
    private final LocationService locationService;
    private final MailService mailService;
    private final PromotionRepository promotionRepository;

    @Autowired
    public OrdersServiceImpl(OrdersRepository ordersRepository, OrderItemRepository orderItemRepository, UsersRepository usersRepository, MessageSource messageSource, PaymentService paymentService, LocationService locationService, MailService mailService, PromotionRepository promotionRepository) {
        this.ordersRepository = ordersRepository;
        this.orderItemRepository = orderItemRepository;
        this.usersRepository = usersRepository;
        this.messageSource = messageSource;
        this.paymentService = paymentService;
        this.locationService = locationService;
        this.mailService = mailService;
        this.promotionRepository = promotionRepository;
    }

    @Override
    public List<OrdersDTO> findByCustomerId(Long customerId) {
        return ordersRepository.findByCustomerId(customerId)
                .stream().map(OrdersDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrdersDTO> findByCustomerIdAndStatus(Long customerId, StatusOrder status) {
        return ordersRepository.findByCustomerIdAndStatus(customerId, status.getType())
                .stream().map(OrdersDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public PageResponse<OrdersDTO> getOrderListByUserId(
            Long userId,
            OrdersSearchFormByCustomer form
    ) {
        int offset = Optional.of(form.getPage()).map(p -> {
            if (p > 0) return p - 1;
            else return 0;
        }).orElse(0);
        Pageable pageable = PageRequest.of(offset, LIMIT, Sort.by("id").descending());
        var data = ordersRepository.findByCustomerId(userId, form, pageable)
                .map(OrdersDTO::new);
        return new PageResponse<>(data);
    }

    @Override
    public PageResponse<OrdersDTO> searchFromAdmin(OrdersSearchFormByAdmin form) {
        int offset = Optional.of(form.getPage()).map(p -> {
            if (p > 0) return p - 1;
            else return 0;
        }).orElse(0);
        Pageable pageable = PageRequest.of(offset, LIMIT, Sort.by("id").descending());
        var data = ordersRepository.searchFromAdmin(form, pageable)
                .map(OrdersDTO::new);
        return new PageResponse<>(data);
    }

    @Override
    public OrdersDTO findById(Long orderId) throws NotFoundException {
        var orders = ordersRepository.findById(orderId);
        if (orders.isEmpty())
            throw new NotFoundException("Không tìm thấy đơn hàng #" + orderId);
        return new OrdersDTO(orders.get());
    }

    @Override
    public OrdersDTO findById(Long orderId, Long userId) throws NotFoundException {
        var orders = ordersRepository.findByOrderIdAndUserId(orderId, userId);
        if (orders.isEmpty())
            throw new NotFoundException("Không tìm thấy đơn hàng #" + orders);
        return new OrdersDTO(orders.get());
    }

    @Override
    public Option2<OrdersDTO, String> addByCustomerId(Long userId, OrdersAddForm form) throws NotFoundException, MessagingException, UnsupportedEncodingException {
        var userOpt = usersRepository.findById(userId);
        if (userOpt.isEmpty())
            throw new NotFoundException(messageSource.getMessage("not-found.user.id", null, LocaleContextHolder.getLocale()).concat(String.valueOf(userId)));

        var orderItem = orderItemRepository.findByIdsAndOrderIsNull(form.getOrderItemId())
                .stream().peek(item -> item.setPrice(item.getProduct().getPriceFinal()))
                .collect(Collectors.toList());

        var totalAmount = orderItem.stream()
                .map(item -> item.getQuantity() * item.getPrice())
                .reduce(Long::sum)
                .orElse(null);

        var order = new Orders(form, userOpt.get(), totalAmount);
        // kiểm tra form
        if (!form.getIsBuyAtStore()) {
            if (Objects.equals(form.getReceiverName(), "") || form.getReceiverName() == null)
                throw new ArgumentException("receiverName", "not-null");
            if (Objects.equals(form.getReceiverPhone(), "") || form.getReceiverPhone() == null)
                throw new ArgumentException("receiverPhone", "not-null");
            if (Objects.equals(form.getCityId(), "") || form.getCityId() == null)
                throw new ArgumentException("cityId", "not-null");
            if (Objects.equals(form.getDistrictId(), "") || form.getDistrictId() == null)
                throw new ArgumentException("districtId", "not-null");
            if (Objects.equals(form.getWardId(), "") || form.getWardId() == null)
                throw new ArgumentException("wardId", "not-null");
            if (Objects.equals(form.getStreet(), "") || form.getStreet() == null)
                throw new ArgumentException("street", "not-null");
            var address = locationService.getAddress(form.getWardId(), form.getStreet());
            order.setReceiverAddress(address);
        }

        if (form.getCouponCodeList().size() > 0) {
            if(!form.getCouponCodeList().get(0).isEmpty()) {
                var promotion = promotionRepository.checkCouponCode(form.getCouponCodeList().get(0), form.getOrderItemId());
                if (promotion == null)
                    throw new ArgumentException("couponCode", "not-found.coupon-code");
                var discount = discount(totalAmount, promotion);
                order.setPromotion(new Promotion.UsedPromotion(promotion));
                order.setTotalAmount(discount._2);
                order.setTotalDiscount(discount._1);
                if (order.getTotalAmount() == 0)
                    order.setAlreadyPaid(true);
            }
        }

        var orderNew = ordersRepository.save(order);

        var itemNew = orderItem.stream()
                .peek(item -> item.setOrder(orderNew))
                .collect(Collectors.toList());

        orderItemRepository.saveAll(itemNew);
        var data = ordersRepository.findById(orderNew.getId()).orElse(orderNew);

        if (data.getPaymentMethod().equalsIgnoreCase(PaymentMethod.MOMO.getMethod()) && !order.getAlreadyPaid()) {
            String redirectLink = paymentService.displayPaymentScreen(order.getId());
            return new Option2<>(new OrdersDTO(data), redirectLink);
        }
        mailService.sendBillToCustomer(data.getCode());
        return new Option2<>(new OrdersDTO(data), null);
    }

    @Override
    public OrdersDTO updateStatus(Long orderId, @NotNull StatusOrder type) throws NotFoundException {
        var orderOpt = ordersRepository.findById(orderId);
        if (orderOpt.isEmpty())
            throw new NotFoundException("Không tìm thấy đơn hàng #" + orderOpt);
        var order = orderOpt.map(o -> {
            o.setStatus(type.getType());
            return o;
        }).get();

        var ordersNew = ordersRepository.save(order);
        return new OrdersDTO(ordersNew);
    }

    @Override
    public OrdersDTO updateByCode(String code, OrdersUpdateForm form) throws NotFoundException {
        var orderOpt = ordersRepository.findByCode(code);
        if (orderOpt.isEmpty())
            throw new NotFoundException("Không tìm thấy đơn hàng #" + orderOpt);
        var order = orderOpt.map(o -> {
            var status = StatusOrder.getStatus(form.getStatus());
            if (status != null)
                o.setStatus(status.getType());
            o.setAlreadyPaid(form.getAlreadyPaid());
            return o;
        }).get();

        var ordersNew = ordersRepository.save(order);
        return new OrdersDTO(ordersNew);
    }

    @Override
    public OrdersDTO findByCode(String orderCode) throws NotFoundException {
        var orders = ordersRepository.findByCode(orderCode);
        if (orders.isEmpty())
            throw new NotFoundException("Không tìm thấy đơn hàng #" + orderCode);
        return new OrdersDTO(orders.get());
    }

    @Override
    public Option3<Promotion, Long, Long> useCoupon(UseCouponForm form) {
        var promotion = promotionRepository.checkCouponCode(form.getCouponCode(), form.getOrderItemId());
        if (promotion == null)
            throw new ArgumentException("couponCode", "not-found.coupon-code");

        var orderItem = orderItemRepository.findByIdsAndOrderIsNull(form.getOrderItemId())
                .stream().peek(item -> item.setPrice(item.getProduct().getPriceFinal()))
                .collect(Collectors.toList());

        var totalAmount = orderItem.stream()
                .map(item -> item.getQuantity() * item.getPrice())
                .reduce(Long::sum)
                .orElse(null);

        var discount = discount(totalAmount, promotion);
        return new Option3<>(promotion, discount._1, discount._2);
    }

    private Option2<Long, Long> discount(Long totalAmount, Promotion promotion) {
        if (promotion.getDiscountType() == DISCOUNT_AMOUNT) {
            if (totalAmount < promotion.getDiscountValue())
                return new Option2<>(totalAmount, 0L);
            return new Option2<>(promotion.getDiscountValue(), totalAmount - promotion.getDiscountValue());
        }
        Long discount = totalAmount * promotion.getDiscountValue() / 100;
        if (discount > promotion.getMaximumDiscountValue())
            return new Option2<>(promotion.getMaximumDiscountValue(), totalAmount - promotion.getMaximumDiscountValue());
        if (discount > totalAmount)
            return new Option2<>(totalAmount, 0L);
        return new Option2<>(discount, totalAmount - discount);
    }


}
