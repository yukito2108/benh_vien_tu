package com.tuannq.store.model.request;

import com.tuannq.store.model.annotation.Integer;
import com.tuannq.store.model.annotation.Phone;
import com.tuannq.store.model.annotation.Email;
import com.tuannq.store.entity.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.*;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserFormAdmin {
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
    @Email
    @ApiModelProperty(
            example = "user@tuannq.com",
            notes = "not-null",
            required = true
    )
    private String email;

    @Phone
    @ApiModelProperty(
            example = "0916016972",
            notes = "not-null",
            required = true
    )
    private String phone;

    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @ApiModelProperty(
            example = "[USER]",
            notes = "not-null",
            required = true
    )
    private Collection<Role> roles;

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

    @ApiModelProperty(
            example = "false",
            required = false
    )
    private Boolean isDeleted = false;
}
