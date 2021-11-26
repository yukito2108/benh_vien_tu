package com.tuannq.store.model.type;

public enum TransactionType {
    FORGOT_PASSWORD("FORGOT_PASSWORD");
    private final String name;

    TransactionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
