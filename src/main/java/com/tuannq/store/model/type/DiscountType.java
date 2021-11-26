package com.tuannq.store.model.type;

import org.apache.commons.lang3.StringUtils;

public enum DiscountType {
    DISCOUNT_PERCENT(1),
    DISCOUNT_AMOUNT(2);
    private final int id;

    private DiscountType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public static Boolean isExist(String type) {
        if (StringUtils.isBlank(type)) {
            return false;
        }

        for (var item : DiscountType.values()) {
            if (type.equalsIgnoreCase(item.getId() + "")) {
                return true;
            }
        }
        return false;
    }

    public static DiscountType getType(String type) {
        if (StringUtils.isBlank(type)) {
            return null;
        }

        for (var item : DiscountType.values()) {
            if (type.equalsIgnoreCase(item.getId() + "")) {
                return item;
            }
        }
        return null;
    }
}
