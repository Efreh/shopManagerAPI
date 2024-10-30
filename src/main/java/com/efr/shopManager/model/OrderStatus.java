package com.efr.shopManager.model;

public enum OrderStatus {
    NEW("Новый заказ"), // Новый заказ, который еще не обработан
    PROCESSING("Обработка"), // Заказ находится в процессе обработки
    SHIPPED("Отправлен"), // Заказ отправлен покупателю
    DELIVERED("Доставлен"), // Заказ доставлен покупателю
    CANCELED("Отменен"); // Заказ отменен

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
