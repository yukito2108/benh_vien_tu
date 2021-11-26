package com.tuannq.store.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tuannq.store.entity.core.BaseEntity;
import com.tuannq.store.model.request.OrdersAddForm;
import com.tuannq.store.model.type.StatusOrder;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Orders extends BaseEntity {
    @Column(nullable = false)
    private String code;
    private String transactionCodeMomo;
    private Long shippingFee = 0L;
    private Long totalDiscount = 0L;
    private Long totalAmount;
    @Column(nullable = false)
    private String paymentMethod;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false, columnDefinition = "BOOLEAN default true")
    private Boolean isBuyAtStore = true;
    private String receiverName;
    private String receiverPhone;
    @Column(length = 511)
    private String receiverAddress;
    @Column(length = 511)
    private String note;
    private Boolean alreadyPaid = false;

    @Type(type = "json")
    @Column(name = "promotion", columnDefinition = "json")
    private Promotion.UsedPromotion promotion;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Users customer;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    private Set<OrderItem> orderItems = new HashSet<>();

    public Orders(
            OrdersAddForm form,
            Users customer,
            Long totalAmount
    ) {
        this.code = String.valueOf(System.currentTimeMillis());
        if (!form.getIsBuyAtStore()) {
            this.receiverName = form.getReceiverName();
            this.receiverPhone = form.getReceiverPhone();
        }
        this.paymentMethod = form.getPaymentMethod();
        this.status = StatusOrder.NEW.getType();
        this.isBuyAtStore = form.getIsBuyAtStore();
        this.note = form.getNote();
        this.customer = customer;
        this.totalAmount = totalAmount;
    }

}
