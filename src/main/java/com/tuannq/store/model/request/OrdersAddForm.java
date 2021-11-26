package com.tuannq.store.model.request;

import com.tuannq.store.model.annotation.Phone;
import com.tuannq.store.model.annotation.Positive;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersAddForm {
    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @Size(max = 255, message = "size-0-255")
    private String paymentMethod;

    @ApiModelProperty(
            example = "false"
    )
    private Boolean isBuyAtStore = false;

    @Size(max = 255, message = "size-1-255")
    private String receiverName;

    @Phone
    @ApiModelProperty(
            example = "0919808080"
    )
    @Size(max = 255, message = "size-0-255")
    private String receiverPhone;

    @Positive
    private String cityId;

    @Positive
    private String districtId;

    @Positive
    private String wardId;

    @Size(max = 255, message = "size-0-255")
    private String street;

    @Size(max = 511, message = "size-0-511")
    private String note;

    @NotNull(message = "{not-null}")
    @Size(min = 1)
    private Set<Long> orderItemId;

    private List<String> couponCodeList = new ArrayList<>();
}
