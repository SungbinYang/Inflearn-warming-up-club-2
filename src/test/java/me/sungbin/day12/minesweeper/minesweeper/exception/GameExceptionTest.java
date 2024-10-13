package me.sungbin.day12.minesweeper.minesweeper.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class GameExceptionTest {

    @Test
    @DisplayName("예외가 발생하면 해당 메시지가 포함되어야 한다")
    void shouldContainMessageWhenExceptionIsThrown() {
        // Given: 예외 메시지 정의
        String expectedMessage = "게임 중 오류가 발생했습니다.";

        // When & Then: GameException 발생 시 메시지가 포함되어야 함
        assertThatThrownBy(() -> {
            throw new GameException(expectedMessage);
        })
                .isInstanceOf(GameException.class)
                .hasMessageContaining(expectedMessage);
    }

    @Test
    @DisplayName("예외가 발생하면 정확한 메시지를 반환해야 한다")
    void shouldReturnExactMessage() {
        // Given: 예외 인스턴스 생성
        String message = "예상치 못한 에러가 발생했습니다.";
        GameException exception = new GameException(message);

        // When: 예외 메시지 조회
        String actualMessage = exception.getMessage();

        // Then: 정확한 메시지가 반환되어야 함
        assertThat(actualMessage).isEqualTo(message);
    }

    @Test
    @DisplayName("예외는 RuntimeException의 하위 타입이어야 한다")
    void shouldBeInstanceOfRuntimeException() {
        // Given & When & Then: GameException은 RuntimeException의 하위 클래스여야 함
        assertThat(new GameException("")).isInstanceOf(RuntimeException.class);
    }
}