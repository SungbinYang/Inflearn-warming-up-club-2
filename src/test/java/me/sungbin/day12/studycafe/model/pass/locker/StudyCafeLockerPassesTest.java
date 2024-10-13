package me.sungbin.day12.studycafe.model.pass.locker;

import me.sungbin.day12.studycafe.model.pass.StudyCafePassType;
import me.sungbin.day12.studycafe.model.pass.StudyCafeSeatPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafeLockerPassesTest {

    @Test
    @DisplayName("락커 이용권과 동일한 기간과 유형의 좌석 이용권이 있을 때 해당 락커 이용권을 반환한다")
    void shouldReturnLockerPassWhenSameTypeAndDurationExist() {
        // Given: 동일한 유형과 기간의 락커 및 좌석 이용권 생성
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 50000);
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.2);
        StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.of(List.of(lockerPass));

        // When: 좌석 이용권과 동일한 유형과 기간의 락커 이용권 검색
        Optional<StudyCafeLockerPass> result = lockerPasses.findLockerPassBy(seatPass);

        // Then: 락커 이용권이 존재해야 함
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(lockerPass);
    }

    @Test
    @DisplayName("동일한 기간과 유형이 없을 때 빈 값을 반환해야 한다")
    void shouldReturnEmptyWhenNoMatchingLockerPassExists() {
        // Given: 다른 유형의 락커 이용권과 좌석 이용권 생성
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.HOURLY, 5, 10000);
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.2);
        StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.of(List.of(lockerPass));

        // When: 좌석 이용권과 일치하는 락커 이용권 검색
        Optional<StudyCafeLockerPass> result = lockerPasses.findLockerPassBy(seatPass);

        // Then: 빈 Optional이어야 함
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("락커 이용권 목록이 비어 있을 때 빈 값을 반환해야 한다")
    void shouldReturnEmptyWhenLockerPassesAreEmpty() {
        // Given: 빈 락커 이용권 목록 생성
        StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.of(List.of());
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.2);

        // When: 좌석 이용권과 일치하는 락커 이용권 검색
        Optional<StudyCafeLockerPass> result = lockerPasses.findLockerPassBy(seatPass);

        // Then: 빈 Optional이어야 함
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("락커 이용권이 여러 개일 때 첫 번째 일치하는 이용권을 반환해야 한다")
    void shouldReturnFirstMatchingLockerPassWhenMultipleMatchesExist() {
        // Given: 동일한 유형과 기간을 가진 여러 락커 이용권 생성
        StudyCafeLockerPass lockerPass1 = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 50000);
        StudyCafeLockerPass lockerPass2 = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 60000);
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.2);
        StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.of(List.of(lockerPass1, lockerPass2));

        // When: 좌석 이용권과 일치하는 락커 이용권 검색
        Optional<StudyCafeLockerPass> result = lockerPasses.findLockerPassBy(seatPass);

        // Then: 첫 번째 락커 이용권이 반환되어야 함
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(lockerPass1);
    }

    @Test
    @DisplayName("빈 목록에서 검색 시도 시 빈 값을 반환해야 한다")
    void shouldReturnEmptyWhenSearchAttemptedOnEmptyList() {
        // Given: 빈 락커 이용권 목록 생성
        StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.of(List.of());

        // When: 좌석 이용권과 일치하는 락커 이용권 검색
        Optional<StudyCafeLockerPass> result = lockerPasses.findLockerPassBy(
                StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 7, 20000, 0.15));

        // Then: 빈 Optional이어야 함
        assertThat(result).isEmpty();
    }
}
