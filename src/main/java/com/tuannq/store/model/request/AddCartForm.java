package com.tuannq.store.model.request;

import com.tuannq.store.model.annotation.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddCartForm {
    @NotBlank
    @NotNull
    @Positive
    private String productId;
    @NotBlank
    @NotNull
    @Positive
    private String quantity;
}
