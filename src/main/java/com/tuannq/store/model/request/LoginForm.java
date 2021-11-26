package com.tuannq.store.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginForm {
  @NotNull(message = "not-null")
  @NotEmpty(message = "not-null")
  @ApiModelProperty(
      example = "admin@tuannq.com",
      notes = "not-null",
      required = true
  )
  private String email;

  @NotNull(message = "not-null")
  @NotEmpty(message = "not-null")
  @ApiModelProperty(
      example = "123456",
      notes = "not-null",
      required = true
  )
  private String password;
}
