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
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersUpdateForm {
    private String status;

    private Boolean alreadyPaid;
}
