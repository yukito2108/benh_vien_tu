package com.tuannq.store.model.type;

import org.apache.commons.lang3.StringUtils;

public enum PaymentMethod {
    MOMO("momo", "Thanh toán qua cổng Momo"),
    COD("cod", "Giao hàng thu tiền hộ");

    private final String method;
    private final String description;

    PaymentMethod(String type, String description) {
        this.method = type;
        this.description = description;
    }

    public String getMethod() {
        return method;
    }

    public String getDescription() {
        return description;
    }

    public static Boolean isExist(String type) {
        if (StringUtils.isBlank(type)) {
            return false;
        }

        for (var item : PaymentMethod.values()) {
            if (type.equalsIgnoreCase(item.method)) {
                return true;
            }
        }
        return false;
    }
}
