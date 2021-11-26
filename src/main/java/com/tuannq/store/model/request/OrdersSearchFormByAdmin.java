package com.tuannq.store.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersSearchFormByAdmin extends OrdersSearchFormByCustomer {
    private String customerName;
    private String paymentMethod;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
}
