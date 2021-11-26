package com.tuannq.store.model.request;

import com.tuannq.store.model.annotation.FieldMatch;
import com.tuannq.store.model.annotation.Password;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldMatch(first = "confirmPassword", second = "newPassword", message = "password.not-match")
public class ChangePasswordForm {
    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @ApiModelProperty(
            example = "User@123",
            notes = "not-null",
            required = true
    )
    private String oldPassword;

    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @Password
    @ApiModelProperty(
            example = "User@123",
            notes = "not-null",
            required = true
    )
    private String newPassword;

    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @Password
    @ApiModelProperty(
            example = "User@123",
            notes = "not-null",
            required = true
    )
    private String confirmPassword;
}
