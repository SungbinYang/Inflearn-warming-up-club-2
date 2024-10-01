package me.sungbin.day4;

import java.util.Collections;
import java.util.List;

public class Order {

    private final List<Item> items;
    private final String customer;

    public Order(List<Item> items, String customer) {
        this.items = (items != null) ? items : Collections.emptyList();
        this.customer = customer;
    }

    public int calculateTotalPrice() {
        return items.stream().mapToInt(Item::getPrice).sum();
    }

    public boolean hasNoCustomerInfo() {
        return customer == null || customer.isEmpty();
    }

    public boolean hasNotItems() {
        return this.items.isEmpty();
    }

    public boolean isInvalidPrice() {
        return calculateTotalPrice() <= 0;
    }
}
