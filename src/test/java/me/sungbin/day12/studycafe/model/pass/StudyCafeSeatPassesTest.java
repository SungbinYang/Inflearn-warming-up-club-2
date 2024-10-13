package me.sungbin.day12.studycafe.model.pass;

import me.sungbin.day12.studycafe.model.pass.locker.StudyCafeLockerPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StudyCafeSeatPassesTest {

    @Test
    @DisplayName("고객이 주 단위 이용권을 요청할 경우 해당 이용권 목록이 제공된다")
    void shouldProvideWeeklyPassWhenRequested() {
        // Given: 다양한 이용권 목록 생성
        StudyCafeSeatPass hourlyPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 5, 10000, 0.1);
        StudyCafeSeatPass weeklyPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 7, 20000, 0.15);
        StudyCafeSeatPass fixedPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.2);
        StudyCafeSeatPasses seatPasses = StudyCafeSeatPasses.of(List.of(hourlyPass, weeklyPass, fixedPass));

        // When: 주 단위 이용권 타입으로 이용권 검색
        List<StudyCafeSeatPass> result = seatPasses.findPassBy(StudyCafePassType.WEEKLY);

        // Then: 주 단위 이용권만 반환되어야 함
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPassType()).isEqualTo(StudyCafePassType.WEEKLY);
    }

    @Test
    @DisplayName("고객이 존재하지 않는 유형의 이용권을 요청할 경우 빈 목록이 반환된다")
    void shouldReturnEmptyListWhenPassTypeNotAvailable() {
        // Given: 시간 단위 이용권만 포함된 목록 생성
        StudyCafeSeatPass hourlyPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 5, 10000, 0.1);
        StudyCafeSeatPasses seatPasses = StudyCafeSeatPasses.of(List.of(hourlyPass));

        // When: 주 단위 이용권 타입으로 이용권 검색
        List<StudyCafeSeatPass> result = seatPasses.findPassBy(StudyCafePassType.WEEKLY);

        // Then: 빈 목록이어야 함
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("이용권 목록이 비어 있을 때 고객이 요청해도 빈 목록이 반환된다")
    void shouldReturnEmptyListWhenPassesAreEmpty() {
        // Given: 빈 이용권 목록 생성
        StudyCafeSeatPasses seatPasses = StudyCafeSeatPasses.of(List.of());

        // When: 어떤 이용권 타입으로도 검색 시도
        List<StudyCafeSeatPass> result = seatPasses.findPassBy(StudyCafePassType.FIXED);

        // Then: 빈 목록이어야 함
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("고정석 좌석 이용권에 맞는 락커 이용권이 있을 경우, 사용자가 락커를 함께 이용할 수 있다")
    void shouldReturnTrueWhenSameTypeAndDuration() {
        // Given: 동일한 타입과 기간을 가진 락커 패스와 좌석 패스
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.2);
        StudyCafeLockerPass lockerPass = mock(StudyCafeLockerPass.class);

        when(lockerPass.isSamePassType(StudyCafePassType.FIXED)).thenReturn(true);
        when(lockerPass.isSameDuration(30)).thenReturn(true);

        // When & Then: 동일한 타입과 기간일 때 true 반환
        assertThat(seatPass.isSameDurationType(lockerPass)).isTrue();
    }

    @Test
    @DisplayName("고정석 좌석 이용권과 다른 유형 또는 기간의 락커 이용권이 있을 경우, 락커 이용이 불가능하다")
    void shouldReturnFalseWhenDifferentTypeOrDuration() {
        // Given: 다른 타입과 기간의 락커 패스와 좌석 패스
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.2);
        StudyCafeLockerPass lockerPass = mock(StudyCafeLockerPass.class);

        when(lockerPass.isSamePassType(StudyCafePassType.FIXED)).thenReturn(false);

        // When & Then: 타입이 다르면 false 반환
        assertThat(seatPass.isSameDurationType(lockerPass)).isFalse();
    }
}
