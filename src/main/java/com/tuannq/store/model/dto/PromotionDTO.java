package com.tuannq.store.model.dto;

import com.tuannq.store.entity.Promotion;
import com.tuannq.store.entity.core.BaseEntity;
import com.tuannq.store.util.ConverterUtils;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromotionDTO extends AbstractDTO {
    private String name;
    private String couponCode;
    private Integer discountType;
    private Long discountValue;
    private Long maximumDiscountValue;
    private Integer quantity;
    private String startDate;
    private String endDate;
    private Boolean isPublic = false;
    Set<Long> products = new HashSet<>();

    public PromotionDTO(Promotion promotion) {
        super(
                promotion.getId(),
                promotion.getCreatedBy(),
                promotion.getCreatedAt(),
                promotion.getModifiedBy(),
                promotion.getModifiedAt(),
                promotion.getIsDeleted()
        );
        this.name = promotion.getName();
        this.couponCode = promotion.getCouponCode();
        this.discountType = promotion.getDiscountType();
        this.discountValue = promotion.getDiscountValue();
        this.maximumDiscountValue = promotion.getMaximumDiscountValue();
        this.quantity = promotion.getQuantity();
        this.startDate = ConverterUtils.formatDateToDatetimeString(promotion.getStartDate());
        this.endDate = ConverterUtils.formatDateToDatetimeString(promotion.getEndDate());
        this.isPublic = promotion.getIsPublic();
        this.products = promotion.getProducts().stream().map(BaseEntity::getId).collect(Collectors.toSet());
    }
}
