package me.sungbin.day4;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class Order {

    private final List<Item> items;
    private final String customerInfo;

    private final Logger log = Logger.getLogger(this.getClass().getName());

    public Order(List<Item> items, String customerInfo) {
        this.items = (items != null) ? items : Collections.emptyList();
        this.customerInfo = (customerInfo != null) ? customerInfo : "";
    }

    public boolean validateOrder() {
        boolean isValid = true;

        try {
            if (hasNotItems()) {
                throw new OrderException("주문 항목이 없습니다.");
            }

            if (isInvalidPrice()) {
                throw new OrderException("올바르지 않은 총 가격입니다.");
            }

            if (hasNotCustomerInfo()) {
                throw new OrderException("사용자 정보가 없습니다.");
            }
        } catch (OrderException e) {
            log.info(e.getMessage());
            isValid = false;
        } catch (Exception e) {
            log.info("프로그램에 문제가 생겼습니다.");
            isValid = false;
        }

        return isValid;
    }

    private int calculateTotalPrice() {
        return this.items.stream().mapToInt(Item::getPrice).sum();
    }

    private boolean hasNotCustomerInfo() {
        return this.customerInfo.isEmpty();
    }

    private boolean hasNotItems() {
        return this.items.isEmpty();
    }

    private boolean isInvalidPrice() {
        return calculateTotalPrice() <= 0;
    }
}
