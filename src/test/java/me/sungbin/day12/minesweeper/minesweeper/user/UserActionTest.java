package me.sungbin.day12.minesweeper.minesweeper.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserActionTest {

    @Test
    @DisplayName("사용자가 셀을 열기 동작을 선택하면 OPEN 동작이 반환된다")
    void shouldReturnOpenActionWhenUserChoosesToOpenCell() {
        // Given: 사용자가 셀 열기 동작을 선택한 상황
        UserAction action = UserAction.OPEN;

        // When: 열거형 값으로 동작을 확인할 때
        String actionName = action.name();

        // Then: 동작은 'OPEN'이어야 한다
        assertThat(actionName).isEqualTo("OPEN");
    }

    @Test
    @DisplayName("사용자가 깃발 꽂기 동작을 선택하면 FLAG 동작이 반환된다")
    void shouldReturnFlagActionWhenUserChoosesToPlaceFlag() {
        // Given: 사용자가 깃발 꽂기 동작을 선택한 상황
        UserAction action = UserAction.FLAG;

        // When: 열거형 값으로 동작을 확인할 때
        String actionName = action.name();

        // Then: 동작은 'FLAG'이어야 한다
        assertThat(actionName).isEqualTo("FLAG");
    }

    @Test
    @DisplayName("알 수 없는 동작을 선택한 경우 UNKNOWN 동작이 반환된다")
    void shouldReturnUnknownActionWhenUserChoosesUnknownAction() {
        // Given: 사용자가 알 수 없는 동작을 선택한 상황
        UserAction action = UserAction.UNKNOWN;

        // When: 열거형 값으로 동작을 확인할 때
        String actionName = action.name();

        // Then: 동작은 'UNKNOWN'이어야 한다
        assertThat(actionName).isEqualTo("UNKNOWN");
    }

    @Test
    @DisplayName("모든 동작에는 올바른 설명이 포함되어야 한다")
    void shouldHaveCorrectDescriptionsForAllActions() {
        // Given: 열거형에 정의된 모든 동작
        UserAction openAction = UserAction.OPEN;
        UserAction flagAction = UserAction.FLAG;
        UserAction unknownAction = UserAction.UNKNOWN;

        // When & Then: 각 동작에 대해 설명을 확인
        assertThat(openAction.name()).isEqualTo("OPEN");
        assertThat(flagAction.name()).isEqualTo("FLAG");
        assertThat(unknownAction.name()).isEqualTo("UNKNOWN");
    }

    @Test
    @DisplayName("열거형 값이 null이면 NullPointerException이 발생해야 한다")
    void shouldThrowExceptionWhenEnumValueIsNull() {
        // Given: 열거형 값이 null인 상황

        // When & Then: NullPointerException이 발생해야 한다
        assertThatThrownBy(() -> UserAction.valueOf(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("존재하지 않는 열거형 값을 요청하면 IllegalArgumentException이 발생한다")
    void shouldThrowExceptionWhenEnumValueDoesNotExist() {
        // Given: 열거형에 정의되지 않은 값 'INVALID'

        // When & Then: IllegalArgumentException이 발생해야 한다
        assertThatThrownBy(() -> UserAction.valueOf("INVALID"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No enum constant me.sungbin.day12.minesweeper.minesweeper.user.UserAction.INVALID");
    }
}
