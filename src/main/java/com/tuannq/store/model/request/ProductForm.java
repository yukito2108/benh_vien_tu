package com.tuannq.store.model.request;

import com.tuannq.store.model.annotation.Positive;
import com.tuannq.store.model.annotation.Slug;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductForm {
    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @com.tuannq.store.model.annotation.Long
    @ApiModelProperty(example = "1")
    private String brandId;

    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @com.tuannq.store.model.annotation.Long
    @ApiModelProperty(example = "1")
    private String categoryId;

    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @Size(min = 1, max = 255, message = "size-1-255")
    @ApiModelProperty(
            example = "Android Tivi Sony 8K 85 inch KD-85Z8H",
            notes = "not-null",
            required = true
    )
    private String name;

    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @Slug
    @Size(min = 1, max = 511, message = "size-1-511")
    @ApiModelProperty(
            example = "android-tivi-sony-8k-85-inch-kd-85z8h",
            notes = "not-null",
            required = true
    )
    private String slug;

    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @Size(min = 1, max = 511, message = "size-1-511")
    @ApiModelProperty(example = "/media/static/6d6415d4-76c6-4a58-aac6-3bfb8a9c1706.jpg")
    private String coverImage;

    @ApiModelProperty(
            example = "[" +
                    "Màn hình rộng 85 inch chất lượng 8K cho hình ảnh tuyệt đẹp, " +
                    "Công nghệ Full Array LED của Sony điều chỉnh ánh sáng rực rỡ, " +
                    "Tái tạo màu chân thực hơn với công nghệ hiển thị TRILUMINOS" +
                    "]"
    )
    private List<String> salientFeatures = new ArrayList<>();

    @ApiModelProperty(example = "[/media/static/6d6415d4-76c6-4a58-aac6-3bfb8a9c1706.jpg, /media/static/8048fd96-a86e-44f8-8da1-4635a62e3e28.jpg]")
    private List<String> productImages = new ArrayList<>();

    private String description;

    @com.tuannq.store.model.annotation.Long
    @com.tuannq.store.model.annotation.PositiveOrZero
    @ApiModelProperty(example = "209850000")
    private String priceFirst;

    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @com.tuannq.store.model.annotation.Long
    @com.tuannq.store.model.annotation.PositiveOrZero
    @ApiModelProperty(example = "199850000")
    private String priceFinal;

    @NotNull(message = "not-null")
    @NotEmpty(message = "not-null")
    @com.tuannq.store.model.annotation.PositiveOrZero
    @com.tuannq.store.model.annotation.Integer
    @ApiModelProperty(example = "10")
    private String quantity;

    @com.tuannq.store.model.annotation.Positive
    @com.tuannq.store.model.annotation.Integer
    @ApiModelProperty(example = "1")
    private String warrantyPeriod;

    @ApiModelProperty(example = "true")
    private Boolean isAvailable = false;

    @com.tuannq.store.model.annotation.Positive
    @com.tuannq.store.model.annotation.Integer
    @ApiModelProperty(example = "1")
    private String preOrder;

    @com.tuannq.store.model.annotation.Positive
    @com.tuannq.store.model.annotation.Integer
    @ApiModelProperty(example = "1")
    private String dimensionWeight;

    @com.tuannq.store.model.annotation.Positive
    @com.tuannq.store.model.annotation.Integer
    @ApiModelProperty(example = "1")
    private String dimensionHeight;

    @com.tuannq.store.model.annotation.Positive
    @com.tuannq.store.model.annotation.Integer
    @ApiModelProperty(example = "1")
    private String dimensionWidth;

    @com.tuannq.store.model.annotation.Positive
    @com.tuannq.store.model.annotation.Integer
    @ApiModelProperty(example = "1")
    private String dimensionLength;

    @Size(max = 255, message = "size-0-255")
    private String sku;

    private List<@Valid Specific> specifications;

    @ApiModelProperty(example = "false")
    private Boolean isDeleted = false;
    @Size(max = 255, message = "size-0-255")
    private String metaTitle;
    @Size(max = 255, message = "size-0-255")
    private String metaKeyword;
    @Size(max = 2047, message = "size-0-2047")
    private String metaDescription;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Specific {
        @Positive
        private String id;
        @Size(max = 255, message = "size-0-255")
        private String value;
    }
}
