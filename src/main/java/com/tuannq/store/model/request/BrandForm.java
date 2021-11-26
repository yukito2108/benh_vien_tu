package com.tuannq.store.model.request;

import com.tuannq.store.model.annotation.Slug;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BrandForm {
    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @Size(min = 1, max = 255, message = "size-1-255")
    private String name;

    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @Size(min = 1, max = 255, message = "size-1-255")
    @Slug
    private String slug;

    private String description;

    @Size(max = 1023, message = "size-0-1023")
    private String thumbnail;

    @ApiModelProperty(
            example = "false",
            required = false
    )
    private Boolean isDeleted = false;
    @Size(max = 255, message = "size-0-255")
    private String metaTitle;
    @Size(max = 255, message = "size-0-255")
    private String metaKeyword;
    @Size(max = 2047, message = "size-0-2047")
    private String metaDescription;
}
