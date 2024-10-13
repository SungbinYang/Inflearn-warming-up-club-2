package me.sungbin.day12.minesweeper.minesweeper.board.cell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmptyCellTest {

    @Test
    @DisplayName("빈 셀은 지뢰가 없는 상태여야 한다")
    void shouldReturnFalseWhenCheckingForLandMine() {
        // Given: 빈 셀이 초기화됨
        EmptyCell emptyCell = new EmptyCell();

        // When: 지뢰 여부를 확인할 때
        boolean result = emptyCell.isLandMine();

        // Then: 지뢰가 없는 상태여야 함
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("빈 셀은 지뢰 개수를 표시할 수 없는 상태여야 한다")
    void shouldReturnFalseWhenCheckingForLandMineCount() {
        // Given: 빈 셀이 초기화됨
        EmptyCell emptyCell = new EmptyCell();

        // When: 지뢰 개수 표시 가능 여부를 확인할 때
        boolean result = emptyCell.hasLandMineCount();

        // Then: 지뢰 개수를 표시할 수 없는 상태여야 함
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("빈 셀이 열리면 스냅샷은 'EMPTY' 상태로 표시되어야 한다")
    void shouldReturnEmptySnapshotWhenOpened() {
        // Given: 빈 셀이 초기화되고 열림
        EmptyCell emptyCell = new EmptyCell();
        emptyCell.open();

        // When: 스냅샷을 요청할 때
        CellSnapshot snapshot = emptyCell.getSnapshot();

        // Then: 스냅샷은 'EMPTY' 상태여야 함
        assertThat(snapshot.getStatus()).isEqualTo(CellSnapshotStatus.EMPTY);
    }

    @Test
    @DisplayName("빈 셀에 깃발을 표시하면 스냅샷은 'FLAG' 상태로 표시되어야 한다")
    void shouldReturnFlagSnapshotWhenFlagged() {
        // Given: 빈 셀이 초기화되고 깃발이 표시됨
        EmptyCell emptyCell = new EmptyCell();
        emptyCell.flag();

        // When: 스냅샷을 요청할 때
        CellSnapshot snapshot = emptyCell.getSnapshot();

        // Then: 스냅샷은 'FLAG' 상태여야 함
        assertThat(snapshot.getStatus()).isEqualTo(CellSnapshotStatus.FLAG);
    }

    @Test
    @DisplayName("빈 셀이 열리지 않고 확인되지 않은 상태일 때 스냅샷은 'UNCHECKED' 상태여야 한다")
    void shouldReturnUncheckedSnapshotWhenNotOpenedOrFlagged() {
        // Given: 빈 셀이 초기화됨
        EmptyCell emptyCell = new EmptyCell();

        // When: 스냅샷을 요청할 때
        CellSnapshot snapshot = emptyCell.getSnapshot();

        // Then: 스냅샷은 'UNCHECKED' 상태여야 함
        assertThat(snapshot.getStatus()).isEqualTo(CellSnapshotStatus.UNCHECKED);
    }

    @Test
    @DisplayName("빈 셀이 열리면 열린 상태로 간주되어야 한다")
    void shouldReturnTrueWhenCellIsOpened() {
        // Given: 빈 셀이 초기화되고 열림
        EmptyCell emptyCell = new EmptyCell();
        emptyCell.open();

        // When: 열린 상태를 확인할 때
        boolean result = emptyCell.isOpened();

        // Then: 열린 상태여야 함
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("빈 셀이 열리지 않으면 닫힌 상태로 간주되어야 한다")
    void shouldReturnFalseWhenCellIsNotOpened() {
        // Given: 빈 셀이 초기화됨
        EmptyCell emptyCell = new EmptyCell();

        // When: 열린 상태를 확인할 때
        boolean result = emptyCell.isOpened();

        // Then: 닫힌 상태여야 함
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("빈 셀이 열리면 체크된 상태로 간주되어야 한다")
    void shouldReturnTrueWhenCellIsChecked() {
        // Given: 빈 셀이 초기화되고 열림
        EmptyCell emptyCell = new EmptyCell();
        emptyCell.open();

        // When: 체크 상태를 확인할 때
        boolean result = emptyCell.isChecked();

        // Then: 체크된 상태여야 함
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("빈 셀이 열리지 않으면 체크되지 않은 상태로 간주되어야 한다")
    void shouldReturnFalseWhenCellIsNotChecked() {
        // Given: 빈 셀이 초기화됨
        EmptyCell emptyCell = new EmptyCell();

        // When: 체크 상태를 확인할 때
        boolean result = emptyCell.isChecked();

        // Then: 체크되지 않은 상태여야 함
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("빈 셀에 깃발을 표시한 후에도 셀을 열 수 있어야 한다")
    void shouldAllowOpeningEvenAfterFlagged() {
        // Given: 빈 셀이 초기화되고 깃발이 표시됨
        EmptyCell emptyCell = new EmptyCell();
        emptyCell.flag();

        // When: 깃발이 표시된 후 셀을 열 때
        emptyCell.open();

        // Then: 열린 상태여야 함
        assertThat(emptyCell.isOpened()).isTrue();
    }
}
