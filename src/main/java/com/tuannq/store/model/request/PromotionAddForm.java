package com.tuannq.store.model.request;


import com.tuannq.store.exception.ArgumentException;
import com.tuannq.store.model.annotation.Date;
import com.tuannq.store.model.annotation.DiscountType;
import com.tuannq.store.model.annotation.StartBeforeEndDate;
import com.tuannq.store.util.ConverterUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@StartBeforeEndDate(startDate = "startDate", endDate = "endDate", message = "Thời gian bắt đầu phải trước thời gian kết thúc")
public class PromotionAddForm {
    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @Pattern(regexp = "^[0-9A-Z-]+$", message = "Mã code không đúng định dạng")
    private String code;

    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @Size(min = 1, max = 255, message = "size-1-255")
    private String name;

    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @DiscountType
    private String discountType;

    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @com.tuannq.store.model.annotation.Positive
    private String discountValue;

    @com.tuannq.store.model.annotation.Positive
    private String maxValue;

    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @com.tuannq.store.model.annotation.Positive
    private String quantity;

    private List<String> productIds = new ArrayList<>();

    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @Date
    private String startDate;

    @NotNull(message = "not-null")
    @NotEmpty
    @Date
    private String endDate;

    private Boolean isPublic = false;

    @ApiModelProperty(hidden = true)
    public static void validate(PromotionAddForm form){
        if (com.tuannq.store.model.type.DiscountType.getType(form.getDiscountType()) == com.tuannq.store.model.type.DiscountType.DISCOUNT_PERCENT){
            var discountValue = ConverterUtils.convertStringToLong(form.getDiscountValue());
            if (discountValue == null || discountValue < 1 || discountValue > 100)
                throw new ArgumentException("discountValue", "Mức giảm giá từ 1% - 100%");
            if (ConverterUtils.convertStringToLong(form.getMaxValue()) == null)
                throw new ArgumentException("maxValue", "not-null");
        }
    }
}
