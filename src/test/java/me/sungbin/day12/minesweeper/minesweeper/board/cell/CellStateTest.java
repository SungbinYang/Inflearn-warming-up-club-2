package me.sungbin.day12.minesweeper.minesweeper.board.cell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CellStateTest {

    @Test
    @DisplayName("게임 시작 시 모든 셀은 열리지 않은 상태와 깃발이 없는 상태여야 한다")
    void shouldInitializeWithDefaultState() {
        // Given
        CellState cellState = CellState.initialize();

        // When & Then
        assertThat(cellState.isOpened()).isFalse();
        assertThat(cellState.isFlagged()).isFalse();
    }

    @Test
    @DisplayName("게임 중 셀에 깃발을 표시하면 해당 셀은 깃발 상태가 되어야 한다")
    void shouldSetFlaggedStateWhenFlagged() {
        // Given
        CellState cellState = CellState.initialize();

        // When
        cellState.flag();

        // Then
        assertThat(cellState.isFlagged()).isTrue();
    }

    @Test
    @DisplayName("한 번 열린 셀은 열린 상태가 유지되어야 한다")
    void shouldSetOpenedStateWhenOpened() {
        // Given
        CellState cellState = CellState.initialize();

        // When
        cellState.open();

        // Then
        assertThat(cellState.isOpened()).isTrue();
    }

    @Test
    @DisplayName("깃발이 표시된 셀도 열리면 열린 상태로 변경되어야 한다")
    void shouldAllowOpeningEvenIfFlagged() {
        // Given
        CellState cellState = CellState.initialize();
        cellState.flag();

        // When
        cellState.open();

        // Then
        assertThat(cellState.isOpened()).isTrue();
        assertThat(cellState.isFlagged()).isTrue();
    }

    @Test
    @DisplayName("한 번 깃발이 표시된 셀에 다시 깃발을 표시해도 상태는 변하지 않아야 한다")
    void shouldNotChangeFlaggedStateWhenFlaggedAgain() {
        // Given
        CellState cellState = CellState.initialize();
        cellState.flag();

        // When
        cellState.flag();

        // Then
        assertThat(cellState.isFlagged()).isTrue();
    }

    @Test
    @DisplayName("이미 열린 셀을 다시 열려고 해도 상태는 변하지 않아야 한다")
    void shouldNotChangeOpenedStateWhenOpenedAgain() {
        // Given
        CellState cellState = CellState.initialize();
        cellState.open();

        // When
        cellState.open();

        // Then
        assertThat(cellState.isOpened()).isTrue();
    }
}