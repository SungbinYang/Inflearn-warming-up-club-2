package me.sungbin.day12.minesweeper.minesweeper.board.position;

import me.sungbin.day12.minesweeper.minesweeper.board.cell.Cell;
import me.sungbin.day12.minesweeper.minesweeper.board.cell.EmptyCell;
import me.sungbin.day12.minesweeper.minesweeper.board.cell.NumberCell;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CellPositionsTest {

    @Test
    @DisplayName("주어진 위치 리스트로 CellPositions 객체를 생성할 수 있다")
    void shouldCreateCellPositionsWithGivenList() {
        // Given: 셀 위치 리스트
        List<CellPosition> positions = List.of(
                CellPosition.of(0, 0),
                CellPosition.of(1, 1)
        );

        // When: CellPositions 객체를 생성할 때
        CellPositions cellPositions = CellPositions.of(positions);

        // Then: 생성된 객체는 해당 위치를 포함해야 함
        assertThat(cellPositions.getPositions()).isEqualTo(positions);
    }

    @Test
    @DisplayName("주어진 보드로부터 모든 셀 위치를 추출할 수 있다")
    void shouldExtractAllPositionsFromBoard() {
        // Given: 2x2 셀 보드
        Cell[][] board = {
                {new EmptyCell(), new NumberCell(1)},
                {new EmptyCell(), new NumberCell(2)}
        };

        // When: 보드로부터 셀 위치를 생성할 때
        CellPositions cellPositions = CellPositions.from(board);

        // Then: 4개의 위치가 포함되어야 함
        assertThat(cellPositions.getPositions().size()).isEqualTo(4);
    }

    @Test
    @DisplayName("랜덤으로 주어진 개수만큼 셀 위치를 추출할 수 있다")
    void shouldExtractRandomPositions() {
        // Given: 5개의 셀 위치
        List<CellPosition> positions = List.of(
                CellPosition.of(0, 0),
                CellPosition.of(0, 1),
                CellPosition.of(1, 0),
                CellPosition.of(1, 1),
                CellPosition.of(2, 2)
        );
        CellPositions cellPositions = CellPositions.of(positions);

        // When: 3개의 위치를 랜덤으로 추출할 때
        List<CellPosition> randomPositions = cellPositions.extractRandomPositions(3);

        // Then: 3개의 위치가 반환되어야 함
        assertThat(randomPositions.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("주어진 리스트와 겹치지 않는 위치를 추출할 수 있다")
    void shouldSubtractPositions() {
        // Given: 전체 셀 위치와 빼야 할 위치
        List<CellPosition> allPositions = List.of(
                CellPosition.of(0, 0),
                CellPosition.of(0, 1),
                CellPosition.of(1, 0)
        );
        List<CellPosition> toSubtract = List.of(
                CellPosition.of(0, 1)
        );

        CellPositions cellPositions = CellPositions.of(allPositions);

        // When: 빼야 할 위치를 제외할 때
        List<CellPosition> result = cellPositions.subtract(toSubtract);

        // Then: 남아 있는 위치는 2개여야 함
        assertThat(result).containsExactly(
                CellPosition.of(0, 0),
                CellPosition.of(1, 0)
        );
    }

    @Test
    @DisplayName("셀 위치를 빼려는 리스트가 비어 있어도 전체 셀 위치는 변하지 않는다")
    void shouldNotChangeWhenSubtractingEmptyList() {
        // Given: 전체 셀 위치
        List<CellPosition> allPositions = List.of(
                CellPosition.of(0, 0),
                CellPosition.of(0, 1)
        );
        CellPositions cellPositions = CellPositions.of(allPositions);

        // When: 빈 리스트를 제외할 때
        List<CellPosition> result = cellPositions.subtract(List.of());

        // Then: 원래의 위치가 그대로 유지되어야 함
        assertThat(result).isEqualTo(allPositions);
    }

    @Test
    @DisplayName("보드가 비어 있을 때 셀 위치를 생성하면 빈 리스트가 반환된다")
    void shouldReturnEmptyListWhenBoardIsEmpty() {
        // Given: 비어 있는 보드
        Cell[][] board = new Cell[0][0];

        // When: 비어 있는 보드로부터 위치를 생성할 때
        CellPositions cellPositions = CellPositions.from(board);

        // Then: 위치 리스트는 비어 있어야 함
        assertThat(cellPositions.getPositions()).isEmpty();
    }
}
