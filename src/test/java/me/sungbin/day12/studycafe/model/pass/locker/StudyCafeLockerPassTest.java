package me.sungbin.day12.studycafe.model.pass.locker;

import me.sungbin.day12.studycafe.model.pass.StudyCafePassType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafeLockerPassTest {

    @Test
    @DisplayName("락커 이용권을 유형, 기간, 가격을 설정하여 정상적으로 생성할 수 있다")
    void shouldCreateLockerPassWithValidAttributes() {
        // Given: 고정석 락커 이용권의 속성
        StudyCafePassType passType = StudyCafePassType.FIXED;
        int duration = 30;
        int price = 50000;

        // When: 이용권 생성
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(passType, duration, price);

        // Then: 생성된 이용권의 속성 확인
        assertThat(lockerPass.getPassType()).isEqualTo(passType);
        assertThat(lockerPass.getDuration()).isEqualTo(duration);
        assertThat(lockerPass.getPrice()).isEqualTo(price);
    }

    @Test
    @DisplayName("고정석 락커 이용권을 사용할 때 동일한 유형인지 확인할 수 있어야 한다")
    void shouldReturnTrueWhenPassTypeIsSame() {
        // Given: 두 개의 동일한 유형의 이용권
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 50000);

        // When & Then: 이용권 유형 비교 시 동일한 유형이면 true 반환
        assertThat(lockerPass.isSamePassType(StudyCafePassType.FIXED)).isTrue();
    }

    @Test
    @DisplayName("주 단위 락커 이용권을 시간 단위 이용권과 비교할 때, 서로 다른 유형임을 식별할 수 있어야 한다")
    void shouldReturnFalseWhenPassTypeIsDifferent() {
        // Given: 주 단위 이용권과 시간 단위 이용권
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.WEEKLY, 7, 20000);

        // When & Then: 다른 유형과 비교 시 false 반환
        assertThat(lockerPass.isSamePassType(StudyCafePassType.HOURLY)).isFalse();
    }

    @Test
    @DisplayName("락커 이용권의 사용 기간이 동일할 때, 일관된 기간인지 확인할 수 있어야 한다")
    void shouldReturnTrueWhenDurationIsSame() {
        // Given: 동일한 기간의 이용권
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 50000);

        // When & Then: 동일한 기간과 비교 시 true 반환
        assertThat(lockerPass.isSameDuration(30)).isTrue();
    }

    @Test
    @DisplayName("락커 이용권의 사용 기간이 다를 때, 서로 다른 기간임을 식별할 수 있어야 한다")
    void shouldReturnFalseWhenDurationIsDifferent() {
        // Given: 다른 기간의 이용권
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 50000);

        // When & Then: 다른 기간과 비교 시 false 반환
        assertThat(lockerPass.isSameDuration(7)).isFalse();
    }

    @Test
    @DisplayName("락커 이용권의 유형을 올바르게 반환해야 한다")
    void shouldReturnCorrectPassType() {
        // Given: 고정석 유형의 이용권
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 50000);

        // When & Then: 이용권 유형 반환 검증
        assertThat(lockerPass.getPassType()).isEqualTo(StudyCafePassType.FIXED);
    }

    @Test
    @DisplayName("락커 이용권의 기간을 올바르게 반환해야 한다")
    void shouldReturnCorrectDuration() {
        // Given: 30일 기간의 이용권
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 50000);

        // When & Then: 이용권 기간 반환 검증
        assertThat(lockerPass.getDuration()).isEqualTo(30);
    }

    @Test
    @DisplayName("락커 이용권의 가격을 올바르게 반환해야 한다")
    void shouldReturnCorrectPrice() {
        // Given: 50000원 가격의 이용권
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 50000);

        // When & Then: 이용권 가격 반환 검증
        assertThat(lockerPass.getPrice()).isEqualTo(50000);
    }

    @Test
    @DisplayName("잘못된 데이터로 이용권을 생성하면 예외가 발생하지 않고 정상 생성된다")
    void shouldCreateLockerPassEvenWithInvalidAttributes() {
        // Given: 잘못된 데이터로 구성된 이용권 (음수 기간 및 가격)
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, -5, -10000);

        // When & Then: 생성된 이용권의 속성 확인
        assertThat(lockerPass.getDuration()).isEqualTo(-5);
        assertThat(lockerPass.getPrice()).isEqualTo(-10000);
    }
}
