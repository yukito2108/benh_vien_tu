package com.tuannq.store.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersSearchFormByCustomer {
    private Boolean isBuyAtStore;
    private String note;
    private String status;
    private Integer page = 1;
}
