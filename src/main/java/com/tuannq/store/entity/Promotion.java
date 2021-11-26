package com.tuannq.store.entity;

import com.tuannq.store.entity.core.BaseEntity;
import com.tuannq.store.model.type.DiscountType;
import com.tuannq.store.model.request.PromotionAddForm;
import com.tuannq.store.util.ConverterUtils;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity()
public class Promotion extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String couponCode;

    @Column(nullable = false)
    private Integer discountType;

    @Column(nullable = false)
    private Long discountValue;

    private Long maximumDiscountValue;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Instant startDate;

    @Column(nullable = false)
    private Instant endDate;

    @Column(nullable = false)
    private Boolean isPublic = false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_promotion",
            joinColumns = @JoinColumn(name = "promotion_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    Set<Product> products = new HashSet<>();

    public Promotion(PromotionAddForm form) {
        this.name = form.getName();
        this.couponCode = form.getCode();
        this.discountType = Integer.parseInt(form.getDiscountType());
        this.discountValue = ConverterUtils.convertStringToLong(form.getDiscountValue());
        if (DiscountType.getType(form.getDiscountType()) == DiscountType.DISCOUNT_PERCENT)
            this.maximumDiscountValue = ConverterUtils.convertStringToLong(form.getMaxValue());
        this.quantity = ConverterUtils.convertStringToNumber(form.getQuantity());
        this.startDate = ConverterUtils.formatStringToInstant(form.getStartDate());
        this.endDate = ConverterUtils.formatStringToInstant(form.getEndDate());
        this.isPublic = form.getIsPublic();
    }

    public void setData(PromotionAddForm form) {
        this.name = form.getName();
        this.couponCode = form.getCode();
        this.discountType = Integer.parseInt(form.getDiscountType());
        this.discountValue = ConverterUtils.convertStringToLong(form.getDiscountValue());
        if (DiscountType.getType(form.getDiscountType()) == DiscountType.DISCOUNT_PERCENT)
            this.maximumDiscountValue = ConverterUtils.convertStringToLong(form.getMaxValue());
        this.quantity = ConverterUtils.convertStringToNumber(form.getQuantity());
        this.startDate = ConverterUtils.formatStringToInstant(form.getStartDate());
        this.endDate = ConverterUtils.formatStringToInstant(form.getEndDate());
        this.isPublic = form.getIsPublic();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UsedPromotion {
        private String couponCode;
        private Integer discountType;
        private Long discountValue;
        private Long maximumDiscountValue;

        public UsedPromotion(Promotion promotion) {
            this.couponCode = promotion.getCouponCode();
            this.discountType = promotion.getDiscountType();
            this.discountValue = promotion.getDiscountValue();
            this.maximumDiscountValue = promotion.getMaximumDiscountValue();
        }
    }
}
