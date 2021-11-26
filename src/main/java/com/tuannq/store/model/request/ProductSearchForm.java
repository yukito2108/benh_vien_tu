package com.tuannq.store.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchForm {
    private List<String> brands = new ArrayList<>();
    private List<String> categories = new ArrayList<>();
    private String priceRange = "";
    private String keyword;
    private Integer page = 1;

    @ApiModelProperty(hidden = true)
    private String order = "priceFinal";
    private String direction = "";
}
