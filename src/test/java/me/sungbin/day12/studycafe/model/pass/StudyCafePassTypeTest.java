package me.sungbin.day12.studycafe.model.pass;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafePassTypeTest {

    @Test
    @DisplayName("시간 단위 이용권은 사물함 이용이 불가능해야 한다")
    void hourlyPassShouldNotBeLockerType() {
        // Given: 시간 단위 이용권
        StudyCafePassType passType = StudyCafePassType.HOURLY;

        // When: 사물함 이용 가능 여부 확인
        boolean isLockerType = passType.isLockerType();

        // Then: 시간 단위 이용권은 사물함 이용이 불가능해야 함
        assertThat(isLockerType).isFalse();
    }

    @Test
    @DisplayName("주 단위 이용권은 사물함 이용이 불가능해야 한다")
    void weeklyPassShouldNotBeLockerType() {
        // Given: 주 단위 이용권
        StudyCafePassType passType = StudyCafePassType.WEEKLY;

        // When: 사물함 이용 가능 여부 확인
        boolean isLockerType = passType.isLockerType();

        // Then: 주 단위 이용권은 사물함 이용이 불가능해야 함
        assertThat(isLockerType).isFalse();
    }

    @Test
    @DisplayName("1인 고정석 이용권은 사물함 이용이 가능해야 한다")
    void fixedPassShouldBeLockerType() {
        // Given: 1인 고정석 이용권
        StudyCafePassType passType = StudyCafePassType.FIXED;

        // When: 사물함 이용 가능 여부 확인
        boolean isLockerType = passType.isLockerType();

        // Then: 1인 고정석 이용권은 사물함 이용이 가능해야 함
        assertThat(isLockerType).isTrue();
    }

    @Test
    @DisplayName("1인 고정석 이용권은 사물함 이용 불가 타입이 아니어야 한다")
    void fixedPassShouldNotBeNonLockerType() {
        // Given: 1인 고정석 이용권
        StudyCafePassType passType = StudyCafePassType.FIXED;

        // When: 사물함 이용 불가 타입 여부 확인
        boolean isNotLockerType = passType.isNotLockerType();

        // Then: 1인 고정석 이용권은 사물함 이용 불가 타입이 아니어야 함
        assertThat(isNotLockerType).isFalse();
    }

    @Test
    @DisplayName("주 단위 이용권은 사물함 이용 불가 타입이어야 한다")
    void weeklyPassShouldBeNonLockerType() {
        // Given: 주 단위 이용권
        StudyCafePassType passType = StudyCafePassType.WEEKLY;

        // When: 사물함 이용 불가 타입 여부 확인
        boolean isNotLockerType = passType.isNotLockerType();

        // Then: 주 단위 이용권은 사물함 이용 불가 타입이어야 함
        assertThat(isNotLockerType).isTrue();
    }

    @Test
    @DisplayName("시간 단위 이용권은 사물함 이용 불가 타입이어야 한다")
    void hourlyPassShouldBeNonLockerType() {
        // Given: 시간 단위 이용권
        StudyCafePassType passType = StudyCafePassType.HOURLY;

        // When: 사물함 이용 불가 타입 여부 확인
        boolean isNotLockerType = passType.isNotLockerType();

        // Then: 시간 단위 이용권은 사물함 이용 불가 타입이어야 함
        assertThat(isNotLockerType).isTrue();
    }
}
