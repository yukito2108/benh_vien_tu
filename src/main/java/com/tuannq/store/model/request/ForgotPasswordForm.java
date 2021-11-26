package com.tuannq.store.model.request;

import com.tuannq.store.model.annotation.Email;
import com.tuannq.store.model.annotation.FieldMatch;
import com.tuannq.store.model.annotation.Password;
import com.tuannq.store.model.annotation.Phone;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordForm {
    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @Email
    @ApiModelProperty(
            example = "user@tuannq.com",
            notes = "not-null",
            required = true
    )
    private String email;

}
