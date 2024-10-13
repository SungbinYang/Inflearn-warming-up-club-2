package me.sungbin.day12.studycafe.model.pass;

import me.sungbin.day12.studycafe.model.pass.locker.StudyCafeLockerPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class StudyCafeSeatPassTest {

    @Test
    @DisplayName("고정석 이용권은 사물함을 이용할 수 없다")
    void shouldReturnTrueWhenLockerNotAllowedForSeatPass() {
        // Given: 고정석 이용권 생성
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 5, 10000, 0.1);

        // When: 사물함 이용 가능 여부 확인
        boolean cannotUseLocker = seatPass.cannotUseLocker();

        // Then: 사물함을 이용할 수 없어야 한다
        assertThat(cannotUseLocker).isTrue();
    }

    @Test
    @DisplayName("고정석 이용권은 사물함을 이용할 수 있어야 한다")
    void shouldReturnFalseWhenLockerAllowedForFixedPass() {
        // Given: 고정석 이용권 생성
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 30000, 0.15);

        // When: 사물함 이용 가능 여부 확인
        boolean cannotUseLocker = seatPass.cannotUseLocker();

        // Then: 사물함을 이용할 수 없어야 한다
        assertThat(cannotUseLocker).isFalse();
    }

    @Test
    @DisplayName("동일한 기간과 타입의 사물함 이용권과 비교할 때 true를 반환해야 한다")
    void shouldReturnTrueForSameDurationAndTypeWithLockerPass() {
        // Given: 동일한 타입과 기간의 이용권 및 사물함 이용권 생성
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 5, 10000, 0.1);
        StudyCafeLockerPass lockerPass = mock(StudyCafeLockerPass.class);

        when(lockerPass.isSamePassType(StudyCafePassType.HOURLY)).thenReturn(true);
        when(lockerPass.isSameDuration(5)).thenReturn(true);

        // When: 동일한 타입과 기간인지 확인
        boolean result = seatPass.isSameDurationType(lockerPass);

        // Then: true를 반환해야 함
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("기간 또는 타입이 다를 경우 false를 반환해야 한다")
    void shouldReturnFalseForDifferentDurationOrTypeWithLockerPass() {
        // Given: 다른 기간의 이용권 및 사물함 이용권 생성
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 7, 20000, 0.1);
        StudyCafeLockerPass lockerPass = mock(StudyCafeLockerPass.class);

        when(lockerPass.isSamePassType(StudyCafePassType.WEEKLY)).thenReturn(true);
        when(lockerPass.isSameDuration(5)).thenReturn(false);

        // When: 동일한 기간과 타입인지 확인
        boolean result = seatPass.isSameDurationType(lockerPass);

        // Then: false를 반환해야 함
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("이용권의 타입이 일치하는 경우 true를 반환해야 한다")
    void shouldReturnTrueWhenPassTypeMatches() {
        // Given: 주 단위 이용권 생성
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 7, 20000, 0.1);

        // When: 동일한 타입인지 확인
        boolean result = seatPass.isSamePassType(StudyCafePassType.WEEKLY);

        // Then: true를 반환해야 함
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("이용권의 타입이 일치하지 않는 경우 false를 반환해야 한다")
    void shouldReturnFalseWhenPassTypeDoesNotMatch() {
        // Given: 고정석 이용권 생성
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 30000, 0.15);

        // When: 타입이 다른 경우 확인
        boolean result = seatPass.isSamePassType(StudyCafePassType.HOURLY);

        // Then: false를 반환해야 함
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("할인된 가격을 정확하게 계산해야 한다")
    void shouldCalculateDiscountedPriceCorrectly() {
        // Given: 이용권 생성 (10% 할인)
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 5, 10000, 0.1);

        // When: 할인된 가격 계산
        int discountPrice = seatPass.getDiscountPrice();

        // Then: 할인된 가격은 9000원이어야 함
        assertThat(discountPrice).isEqualTo(1000);
    }

    @Test
    @DisplayName("0% 할인인 경우 원래 가격과 동일해야 한다")
    void shouldReturnSamePriceWhenNoDiscount() {
        // Given: 할인율이 없는 이용권 생성
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 5, 10000, 0.0);

        // When: 할인된 가격 계산
        int discountPrice = seatPass.getDiscountPrice();

        // Then: 할인된 가격은 원래 가격과 동일해야 함
        assertThat(discountPrice).isEqualTo(0);
    }

    @Test
    @DisplayName("이용권의 타입을 정확하게 반환해야 한다")
    void shouldReturnCorrectPassType() {
        // Given: 시간 단위 이용권 생성
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 5, 10000, 0.1);

        // When: 이용권 타입 가져오기
        StudyCafePassType passType = seatPass.getPassType();

        // Then: 시간 단위 이용권 타입이어야 한다
        assertThat(passType).isEqualTo(StudyCafePassType.HOURLY);
    }

    @Test
    @DisplayName("이용권의 기간을 정확하게 반환해야 한다")
    void shouldReturnCorrectDuration() {
        // Given: 주 단위 이용권 생성
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 7, 20000, 0.15);

        // When: 이용권 기간 가져오기
        int duration = seatPass.getDuration();

        // Then: 기간은 7일이어야 한다
        assertThat(duration).isEqualTo(7);
    }

    @Test
    @DisplayName("이용권의 가격을 정확하게 반환해야 한다")
    void shouldReturnCorrectPrice() {
        // Given: 고정석 이용권 생성
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.2);

        // When: 이용권 가격 가져오기
        int price = seatPass.getPrice();

        // Then: 가격은 50000원이어야 한다
        assertThat(price).isEqualTo(50000);
    }
}
