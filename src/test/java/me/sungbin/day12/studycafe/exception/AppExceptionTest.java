package me.sungbin.day12.studycafe.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AppExceptionTest {

    @Test
    @DisplayName("예기치 못한 시스템 오류가 발생하면 알림이 전달되어야 한다")
    void shouldThrowAppExceptionWhenApplicationErrorOccurs() {
        // Given
        String errorMessage = "예상치 못한 오류가 발생했습니다.";

        // When & Then
        assertThatThrownBy(() -> {
            throw new AppException(errorMessage);
        }).isInstanceOf(AppException.class)
                .hasMessageContaining(errorMessage);
    }

    @Test
    @DisplayName("오류 메시지가 제공되지 않아도 시스템 오류가 처리되어야 한다")
    void shouldThrowAppExceptionWhenMessageIsNull() {
        // Given
        String nullMessage = null;

        // When & Then
        assertThatThrownBy(() -> {
            throw new AppException(nullMessage);
        }).isInstanceOf(AppException.class)
                .hasMessage(null);
    }

    @Test
    @DisplayName("빈 메시지로도 시스템 오류가 전달되어야 한다")
    void shouldThrowAppExceptionWhenMessageIsEmpty() {
        // Given
        String emptyMessage = "";

        // When & Then
        assertThatThrownBy(() -> {
            throw new AppException(emptyMessage);
        }).isInstanceOf(AppException.class)
                .hasMessage(emptyMessage);
    }
}
