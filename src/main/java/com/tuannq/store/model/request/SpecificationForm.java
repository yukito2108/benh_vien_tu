package com.tuannq.store.model.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecificationForm {
    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @Size(min = 1, max = 255, message = "size-1-255")
    private String name;

    @Size(max = 2047, message = "size-0-2047")
    private String description;
}
