package me.sungbin.day12.minesweeper.minesweeper.board.cell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CellStateTest {

    @Test
    @DisplayName("셀의 초기 상태는 열리지 않고 깃발이 표시되지 않아야 한다")
    void shouldInitializeWithDefaultState() {
        // Given: 셀 상태가 초기화됨
        CellState cellState = CellState.initialize();

        // When & Then: 초기 상태를 확인했을 때
        assertThat(cellState.isOpened()).isFalse();
        assertThat(cellState.isFlagged()).isFalse();
    }

    @Test
    @DisplayName("셀에 깃발을 표시하면 깃발 상태로 변경되어야 한다")
    void shouldSetFlaggedStateWhenFlagged() {
        // Given
        CellState cellState = CellState.initialize();

        // When
        cellState.flag();

        // Then
        assertThat(cellState.isFlagged()).isTrue();
    }

    @Test
    @DisplayName("셀을 열면 열린 상태로 변경되어야 한다")
    void shouldSetOpenedStateWhenOpened() {
        // Given
        CellState cellState = CellState.initialize();

        // When
        cellState.open();

        // Then
        assertThat(cellState.isOpened()).isTrue();
    }

    @Test
    @DisplayName("깃발이 표시된 셀도 열 수 있어야 한다")
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
}