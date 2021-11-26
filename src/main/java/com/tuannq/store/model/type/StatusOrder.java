package com.tuannq.store.model.type;

import org.apache.commons.lang3.StringUtils;

public enum StatusOrder {
    NEW("new", "Đơn hàng mới"),
    IN_PROGRESS("inprogress", "Đang giao hàng"),
    COMPLETE("complete", "Giao hàng thành công"),
    PENDING("pending", "Đơn hàng chờ xử lý"),
    CANCEL("cancel", "Đơn hàng bị hủy");


    private final String type;
    private final String description;

    StatusOrder(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public static StatusOrder getStatus(String type) {
        if (StringUtils.isBlank(type)) {
            return null;
        }

        for (var item : StatusOrder.values()) {
            if (type.equalsIgnoreCase(item.type)) {
                return item;
            }
        }
        return null;
    }

    public static Boolean isExist(String type) {
        if (StringUtils.isBlank(type)) {
            return false;
        }

        for (var item : StatusOrder.values()) {
            if (type.equalsIgnoreCase(item.type)) {
                return true;
            }
        }
        return false;
    }

    public static String getStatusByCustomer(String status) {
        var statusOrder = StatusOrder.getStatus(status);
        if (statusOrder == null)
            return null;
        if (statusOrder == NEW)
            return PENDING.getDescription();
        return statusOrder.getDescription();
    }
}
