package me.sungbin.day12.minesweeper.minesweeper.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GameStatusTest {

    @Test
    @DisplayName("게임이 시작되면 상태는 '진행중'이어야 한다")
    void shouldReturnInProgressStatus() {
        // Given

        // When
        GameStatus status = GameStatus.IN_PROGRESS;

        // Then
        assertThat(status).isEqualTo(GameStatus.IN_PROGRESS);
        assertThat(status.name()).isEqualTo("IN_PROGRESS");
    }

    @Test
    @DisplayName("게임이 종료되면 상태는 '승리' 또는 '패배'로 변경될 수 있다")
    void shouldReturnWinOrLoseStatus() {
        // Given

        // When
        GameStatus winStatus = GameStatus.WIN;

        // Then
        assertThat(winStatus).isEqualTo(GameStatus.WIN);

        // When
        GameStatus loseStatus = GameStatus.LOSE;

        // Then
        assertThat(loseStatus).isEqualTo(GameStatus.LOSE);
    }

    @Test
    @DisplayName("모든 게임 상태는 해당 열거형 값과 설명을 포함해야 한다")
    void shouldContainAllGameStatusesWithDescriptions() {
        // Given & When
        GameStatus[] statuses = GameStatus.values();

        // Then
        assertThat(statuses).containsExactly(
                GameStatus.IN_PROGRESS, GameStatus.WIN, GameStatus.LOSE
        );
        assertThat(statuses[0].toString()).contains("IN_PROGRESS");
        assertThat(statuses[1].toString()).contains("WIN");
        assertThat(statuses[2].toString()).contains("LOSE");
    }

    @Test
    @DisplayName("존재하지 않는 상태를 조회하면 예외가 발생해야 한다")
    void shouldThrowExceptionWhenAccessingInvalidStatus() {
        // Given & When & Then
        assertThatThrownBy(() -> GameStatus.valueOf("INVALID"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No enum constant");
    }
}