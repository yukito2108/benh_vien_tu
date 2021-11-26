package com.tuannq.store.model.request;


import com.tuannq.store.model.annotation.Date;
import com.tuannq.store.model.annotation.DiscountType;
import com.tuannq.store.util.ConverterUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PromotionAdminSearchForm extends SearchPage {
    @Pattern(regexp = "^[0-9A-Z-]+$", message = "Mã code không đúng định dạng")
    private String code;

    @Size(min = 1, max = 255, message = "size-1-255")
    private String name;

    @DiscountType
    private String discountType;

    @Date
    private String startDate;

    @Date
    private String endDate;

    private Boolean isPublic;

    @ApiModelProperty(hidden = true)
    public Instant getStartDate() {
        return ConverterUtils.formatStringToInstant(this.startDate);
    }

    @ApiModelProperty(hidden = true)
    public Instant getEndDate() {
        return ConverterUtils.formatStringToInstant(this.endDate);
    }

    @ApiModelProperty(hidden = true)
    public Integer getDiscountType() {
        return ConverterUtils.convertStringToNumber(this.discountType);
    }
}
