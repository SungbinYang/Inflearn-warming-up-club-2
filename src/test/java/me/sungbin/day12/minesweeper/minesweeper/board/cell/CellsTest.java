package me.sungbin.day12.minesweeper.minesweeper.board.cell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CellsTest {

    @Test
    @DisplayName("주어진 셀들로 구성된 게임 보드가 성공적으로 생성되어야 한다")
    void shouldInitializeCellsFromList() {
        // Given
        List<Cell> cellsList = List.of(new EmptyCell(), new NumberCell(2));

        // When
        Cells cells = Cells.of(cellsList);

        // Then
        assertThat(cells).isNotNull();
    }

    @Test
    @DisplayName("2차원 배열의 셀들로부터 게임 보드를 구성할 수 있어야 한다")
    void shouldInitializeCellsFrom2DArray() {
        // Given
        Cell[][] cellArray = {
                {new EmptyCell(), new NumberCell(1)},
                {new LandMineCell(), new EmptyCell()}
        };

        // When
        Cells cells = Cells.from(cellArray);

        // Then
        assertThat(cells).isNotNull();
    }

    @Test
    @DisplayName("잘못된 게임 보드 데이터를 사용하려고 하면 예외가 발생해야 한다")
    void shouldThrowExceptionWhenArrayIsEmptyOrInvalid() {
        // Given
        Cell[][] invalidArray = null;

        // When & Then
        assertThatThrownBy(() -> Cells.from(invalidArray))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("모든 셀이 열리면 게임은 종료 가능한 상태가 되어야 한다")
    void shouldMarkAllCheckedWhenAllCellsAreOpened() {
        // Given
        List<Cell> cellsList = List.of(new EmptyCell(), new NumberCell(2));
        Cells cells = Cells.of(cellsList);

        // When
        cellsList.forEach(Cell::open);

        // Then
        assertThat(cells.isAllChecked()).isTrue();
    }

    @Test
    @DisplayName("지뢰가 포함된 보드는 모든 셀이 열린 상태로 간주되지 않아야 한다")
    void shouldNotMarkAllCheckedWhenMineCellsArePresent() {
        // Given
        List<Cell> mixedCells = List.of(new EmptyCell(), new LandMineCell());
        Cells cells = Cells.of(mixedCells);

        // When
        mixedCells.forEach(cell -> {
            if (!cell.isLandMine()) cell.open();
        });

        // Then
        assertThat(cells.isAllChecked()).isFalse();
    }

    @Test
    @DisplayName("비어 있는 리스트로 구성된 보드는 모든 셀이 열린 것으로 간주되어야 한다")
    void shouldTreatEmptyBoardAsAllChecked() {
        // Given
        List<Cell> emptyList = List.of();

        // When
        Cells cells = Cells.of(emptyList);

        // Then
        assertThat(cells.isAllChecked()).isTrue();
    }
}
