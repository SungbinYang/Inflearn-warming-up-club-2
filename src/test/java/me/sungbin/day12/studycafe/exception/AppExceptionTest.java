package me.sungbin.day12.studycafe.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AppExceptionTest {

    @Test
    @DisplayName("애플리케이션에서 문제가 발생했을 때 AppException을 발생시켜야 한다")
    void shouldThrowAppExceptionWhenApplicationErrorOccurs() {
        // Given: 예외 메시지를 설정
        String errorMessage = "예상치 못한 오류가 발생했습니다.";

        // When & Then: AppException 발생 검증
        assertThatThrownBy(() -> {
            throw new AppException(errorMessage);
        }).isInstanceOf(AppException.class)
                .hasMessageContaining(errorMessage);
    }

    @Test
    @DisplayName("예외 메시지가 null일 때도 AppException이 발생해야 한다")
    void shouldThrowAppExceptionWhenMessageIsNull() {
        // Given: null 메시지를 설정
        String nullMessage = null;

        // When & Then: 메시지가 null이어도 예외 발생 검증
        assertThatThrownBy(() -> {
            throw new AppException(nullMessage);
        }).isInstanceOf(AppException.class)
                .hasMessage(null);
    }

    @Test
    @DisplayName("빈 문자열 메시지로도 AppException이 발생해야 한다")
    void shouldThrowAppExceptionWhenMessageIsEmpty() {
        // Given: 빈 문자열 메시지를 설정
        String emptyMessage = "";

        // When & Then: 빈 메시지로 예외 발생 검증
        assertThatThrownBy(() -> {
            throw new AppException(emptyMessage);
        }).isInstanceOf(AppException.class)
                .hasMessage(emptyMessage);
    }
}
