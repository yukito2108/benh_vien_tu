package com.tuannq.store.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UseCouponForm {
    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @Size(min = 1)
    private Set<Long> orderItemId;

    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @JsonProperty("couponCode")
    private String couponCode;
}
