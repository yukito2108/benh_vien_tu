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

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldMatch(first = "confirmPassword", second = "password", message = "password.not-match")
public class UserFormCustomer {
    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @Size(min = 1, max = 255, message = "size-1-255")
    @ApiModelProperty(
            example = "Nguyễn Anh Tùng",
            notes = "not-null",
            required = true
    )
    private String firstName;

    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @Password
    @ApiModelProperty(
            example = "User@123",
            notes = "not-null",
            required = true
    )
    private String password;

    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @Password
    @ApiModelProperty(
            example = "User@123",
            notes = "not-null",
            required = true
    )

    private String confirmPassword;

    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @Email
    @ApiModelProperty(
            example = "user@tuannq.com",
            notes = "not-null",
            required = true
    )
    private String email;

    @Phone
    @ApiModelProperty(
            example = "0919234567",
            notes = "not-null",
            required = true
    )
    private String phone;

    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @Pattern(regexp = "[012]", message = "gender.invalid")
    @ApiModelProperty(
            example = "0",
            notes = "not-null",
            required = true
    )
    private String gender;

    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @Size(min = 5, max = 511, message = "size-5-511")
    @ApiModelProperty(
            example = "Thiệu Ngọc, Thiệu Hóa, Thanh Hóa.",
            notes = "not-null",
            required = true
    )
    private String address;


}
