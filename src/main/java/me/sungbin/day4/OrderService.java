package me.sungbin.day4;

import java.util.logging.Logger;

public class OrderService {

    private final Logger log = Logger.getLogger(this.getClass().getName());

    public boolean validateOrder(Order order) {
        boolean isValid = true;

        try {
            if (order.hasNotItems()) {
                throw new OrderException("주문 항목이 없습니다.");
            }

            if (order.isInvalidPrice()) {
                throw new OrderException("올바르지 않은 총 가격입니다.");
            }

            if (order.hasNoCustomerInfo()) {
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
}
