package me.sungbin.day4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService();
    }

    @Test
    @DisplayName("주문 유효성 테스트 - 실패(주문 항목이 null)")
    void validate_order_fail_caused_by_items_is_null() {
        // given
        Order nullItemOrder = new Order(null, "양성빈");

        // when
        boolean result = orderService.validateOrder(nullItemOrder);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("주문 유효성 테스트 - 실패(주문 항목이 없음)")
    void validate_order_fail_caused_by_no_items() {
        // given
        Order noItemOrder = new Order(Collections.emptyList(), "양성빈");

        // when
        boolean result = orderService.validateOrder(noItemOrder);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("주문 유효성 테스트 - 실패(유효하지 않은 총 가격)")
    void validate_order_fail_caused_by_invalid_items_total_price() {
        // given
        Order invalidPriceOrder = new Order(List.of(new Item("item1", -100)), "양성빈");

        // when
        boolean result = orderService.validateOrder(invalidPriceOrder);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("주문 유효성 테스트 - 실패(유효하지 않은 사용자 정보 - 빈 사용자 정보)")
    void validate_order_fail_caused_by_no_customer_info() {
        // given
        Order noCustomerInfoOrder = new Order(List.of(new Item("item1", 100)), "");

        // when
        boolean result = orderService.validateOrder(noCustomerInfoOrder);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("주문 유효성 테스트 - 실패(유효하지 않은 사용자 정보 - 사용자 정보가 null)")
    void validate_order_fail_caused_by_null_customer_info() {
        // given
        Order noCustomerInfoOrder = new Order(List.of(new Item("item1", 100)), null);

        // when
        boolean result = orderService.validateOrder(noCustomerInfoOrder);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("주문 유효성 테스트 - 성공")
    public void validate_order_success() {
        // given
        Order validOrder = new Order(Arrays.asList(new Item("item1", 100), new Item("item2", 200)), "양성빈");

        // when
        boolean result = orderService.validateOrder(validOrder);

        // then
        assertTrue(result);
    }

}