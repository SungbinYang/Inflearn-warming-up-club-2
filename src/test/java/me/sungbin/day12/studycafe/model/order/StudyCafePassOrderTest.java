package me.sungbin.day12.studycafe.model.order;

import me.sungbin.day12.studycafe.model.pass.StudyCafePassType;
import me.sungbin.day12.studycafe.model.pass.StudyCafeSeatPass;
import me.sungbin.day12.studycafe.model.pass.locker.StudyCafeLockerPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafePassOrderTest {

    @Test
    @DisplayName("좌석과 락커 이용권이 포함된 주문의 총 가격을 정확하게 계산해야 한다")
    void shouldCorrectlyCalculateTotalPriceWithLocker() {
        // Given: 좌석 이용권과 락커 이용권 생성
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.1);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 10000);
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, lockerPass);

        // When: 총 가격 계산
        int totalPrice = order.getTotalPrice();

        // Then: 할인 적용 후 정확한 총 가격이어야 함 (50000 + 10000 - 5000)
        assertThat(totalPrice).isEqualTo(55000);
    }

    @Test
    @DisplayName("락커 이용권이 포함되지 않은 주문의 총 가격을 정확하게 계산해야 한다")
    void shouldCorrectlyCalculateTotalPriceWithoutLocker() {
        // Given: 좌석 이용권만 포함된 주문 생성
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 5, 10000, 0.1);
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, null);

        // When: 총 가격 계산
        int totalPrice = order.getTotalPrice();

        // Then: 할인 적용 후 정확한 총 가격이어야 함 (10000 - 1000)
        assertThat(totalPrice).isEqualTo(9000);
    }

    @Test
    @DisplayName("할인된 좌석 이용권 가격을 정확하게 계산해야 한다")
    void shouldCorrectlyCalculateDiscountPrice() {
        // Given: 할인율이 적용된 좌석 이용권 생성
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 7, 20000, 0.15);
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, null);

        // When: 할인 가격 계산
        int discountPrice = order.getDiscountPrice();

        // Then: 정확한 할인 가격이어야 함 (20000 * 0.15)
        assertThat(discountPrice).isEqualTo(3000);
    }

    @Test
    @DisplayName("락커 이용권이 포함된 경우 Optional로 반환되어야 한다")
    void shouldReturnLockerPassAsOptionalWhenIncluded() {
        // Given: 락커 이용권 포함된 주문 생성
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 10000);
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.1);
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, lockerPass);

        // When: 락커 이용권 가져오기
        Optional<StudyCafeLockerPass> result = order.getLockerPass();

        // Then: Optional로 락커 이용권이 포함되어 있어야 함
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(lockerPass);
    }

    @Test
    @DisplayName("락커 이용권이 포함되지 않은 경우 Optional.empty()를 반환해야 한다")
    void shouldReturnEmptyOptionalWhenLockerPassNotIncluded() {
        // Given: 락커 이용권 없이 주문 생성
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 5, 10000, 0.1);
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, null);

        // When: 락커 이용권 가져오기
        Optional<StudyCafeLockerPass> result = order.getLockerPass();

        // Then: Optional.empty()이어야 함
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("주문에서 좌석 이용권이 올바르게 반환되어야 한다")
    void shouldReturnSeatPassCorrectly() {
        // Given: 좌석 이용권 포함된 주문 생성
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.1);
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, null);

        // When: 좌석 이용권 가져오기
        StudyCafeSeatPass result = order.getSeatPass();

        // Then: 좌석 이용권이 정확히 반환되어야 함
        assertThat(result).isEqualTo(seatPass);
    }
}
