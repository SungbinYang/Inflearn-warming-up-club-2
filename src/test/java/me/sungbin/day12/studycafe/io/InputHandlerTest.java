package me.sungbin.day12.studycafe.io;

import me.sungbin.day12.studycafe.exception.AppException;
import me.sungbin.day12.studycafe.model.pass.StudyCafePassType;
import me.sungbin.day12.studycafe.model.pass.StudyCafeSeatPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InputHandlerTest {

    @Test
    @DisplayName("시간 단위 이용권을 선택하면 시간 단위로 좌석 예약이 시작된다")
    void shouldReturnHourlyPassTypeWhenUserSelectsHourly() {
        // given: 사용자 입력으로 시간 단위 이용권 선택
        InputHandler inputHandler = createInputHandlerWithInput("1\n");

        // when: 이용권 유형 선택 메서드 호출
        StudyCafePassType result = inputHandler.getPassTypeSelectingUserAction();

        // then: 시간 단위 이용권이 반환됨
        assertThat(result).isEqualTo(StudyCafePassType.HOURLY);
    }

    @Test
    @DisplayName("주 단위 이용권을 선택하면 주 단위로 좌석 예약이 설정된다")
    void shouldReturnWeeklyPassTypeWhenUserSelectsWeekly() {
        // given: 사용자 입력으로 주 단위 이용권 선택
        InputHandler inputHandler = createInputHandlerWithInput("2\n");

        // when: 이용권 유형 선택 메서드 호출
        StudyCafePassType result = inputHandler.getPassTypeSelectingUserAction();

        // then: 주 단위 이용권이 반환됨
        assertThat(result).isEqualTo(StudyCafePassType.WEEKLY);
    }

    @Test
    @DisplayName("고정석 이용권을 선택하면 고정석 좌석이 예약된다")
    void shouldReturnFixedPassTypeWhenUserSelectsFixed() {
        // given: 사용자 입력으로 고정석 이용권 선택
        InputHandler inputHandler = createInputHandlerWithInput("3\n");

        // when: 이용권 유형 선택 메서드 호출
        StudyCafePassType result = inputHandler.getPassTypeSelectingUserAction();

        // then: 고정석 이용권이 반환됨
        assertThat(result).isEqualTo(StudyCafePassType.FIXED);
    }

    @Test
    @DisplayName("잘못된 이용권 유형을 선택하면 오류 메시지와 함께 예외가 발생한다")
    void shouldThrowExceptionForInvalidPassTypeInput() {
        // given: 잘못된 사용자 입력
        InputHandler inputHandler = createInputHandlerWithInput("4\n");

        // when & then: 예외가 발생하는지 검증
        assertThatThrownBy(inputHandler::getPassTypeSelectingUserAction)
                .isInstanceOf(AppException.class)
                .hasMessageContaining("잘못된 입력입니다.");
    }

    @Test
    @DisplayName("사용자가 선택한 이용권에 맞는 좌석 예약이 진행된다")
    void shouldReturnSelectedPassBasedOnUserInput() {
        // given: 사용자 입력과 이용 가능한 이용권 설정
        InputHandler inputHandler = createInputHandlerWithInput("2\n");
        StudyCafeSeatPass firstPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 5, 10000, 0.1);
        StudyCafeSeatPass secondPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 7, 20000, 0.15);

        // when: 이용권 선택 메서드 호출
        StudyCafeSeatPass result = inputHandler.getSelectPass(List.of(firstPass, secondPass));

        // then: 주 단위 이용권이 반환됨
        assertThat(result).isEqualTo(secondPass);
    }

    @Test
    @DisplayName("존재하지 않는 이용권을 선택하면 예외가 발생한다")
    void shouldThrowExceptionForInvalidPassSelection() {
        // given: 사용자 입력과 제한된 이용권 목록 설정
        InputHandler inputHandler = createInputHandlerWithInput("3\n");
        StudyCafeSeatPass firstPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 5, 10000, 0.1);

        // when & then: 예외가 발생하는지 검증
        assertThatThrownBy(() -> inputHandler.getSelectPass(List.of(firstPass)))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    @DisplayName("사용자가 락커를 선택하면 락커 사용이 활성화된다")
    void shouldReturnTrueWhenUserSelectsLocker() {
        // given: 사용자 입력으로 락커 사용 선택
        InputHandler inputHandler = createInputHandlerWithInput("1\n");

        // when: 락커 선택 메서드 호출
        boolean result = inputHandler.getLockerSelection();

        // then: 락커 사용이 활성화됨
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("사용자가 락커를 선택하지 않으면 락커 사용이 비활성화된다")
    void shouldReturnFalseWhenUserDeclinesLocker() {
        // given: 사용자 입력으로 락커 사용 거부
        InputHandler inputHandler = createInputHandlerWithInput("2\n");

        // when: 락커 선택 메서드 호출
        boolean result = inputHandler.getLockerSelection();

        // then: 락커 사용이 비활성화됨
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("잘못된 락커 선택 입력 시 예외가 발생한다")
    void shouldThrowExceptionForInvalidLockerInput() {
        // given: 잘못된 사용자 입력
        InputHandler inputHandler = createInputHandlerWithInput("3\n");

        // when & then: 예외가 발생하는지 검증
        assertThatThrownBy(inputHandler::getLockerSelection)
                .isInstanceOf(AppException.class)
                .hasMessageContaining("잘못된 입력입니다.");
    }

    // 사용자 입력 설정을 위한 헬퍼 메서드
    private InputHandler createInputHandlerWithInput(String input) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        return new InputHandler(new Scanner(inputStream));
    }
}

