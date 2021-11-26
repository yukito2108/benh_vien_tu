package com.tuannq.store.model.request;

import com.tuannq.store.model.annotation.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchFormByAdmin {
    private String code = "";
    private String name = "";
    @Positive
    private String category = "";
    @Positive
    private String brand = "";
    private Integer page = 1;
    private String order = "";
    private String direction = "";
}
