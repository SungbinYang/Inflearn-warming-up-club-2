package me.sungbin.day12.minesweeper.minesweeper.board.cell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NumberCellTest {

    @Test
    @DisplayName("숫자 셀은 지뢰가 없는 상태여야 한다")
    void shouldReturnFalseWhenCheckingForLandMine() {
        // Given: 주변 지뢰 개수가 3인 숫자 셀
        NumberCell numberCell = new NumberCell(3);

        // When: 지뢰 여부를 확인할 때
        boolean result = numberCell.isLandMine();

        // Then: 지뢰가 없는 상태여야 함
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("숫자 셀은 지뢰 개수를 표시할 수 있는 상태여야 한다")
    void shouldReturnTrueWhenCheckingForLandMineCount() {
        // Given: 숫자 셀 초기화
        NumberCell numberCell = new NumberCell(2);

        // When: 지뢰 개수 표시 가능 여부를 확인할 때
        boolean result = numberCell.hasLandMineCount();

        // Then: 지뢰 개수를 표시할 수 있는 상태여야 함
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("숫자 셀에 깃발이 표시되면 스냅샷은 'FLAG' 상태로 표시되어야 한다")
    void shouldReturnFlagSnapshotWhenFlagged() {
        // Given: 숫자 셀 초기화
        NumberCell numberCell = new NumberCell(1);

        // When: 깃발을 표시한 후 스냅샷을 요청할 때
        numberCell.flag();
        CellSnapshot snapshot = numberCell.getSnapshot();

        // Then: 스냅샷은 'FLAG' 상태여야 함
        assertThat(snapshot.getStatus()).isEqualTo(CellSnapshotStatus.FLAG);
    }

    @Test
    @DisplayName("숫자 셀이 열리면 스냅샷은 지뢰 개수를 반영한 'NUMBER' 상태여야 한다")
    void shouldReturnNumberSnapshotWhenOpened() {
        // Given: 숫자 셀 초기화
        NumberCell numberCell = new NumberCell(4);

        // When: 셀을 열고 스냅샷을 요청할 때
        numberCell.open();
        CellSnapshot snapshot = numberCell.getSnapshot();

        // Then: 스냅샷은 지뢰 개수 4를 포함한 'NUMBER' 상태여야 함
        assertThat(snapshot.getStatus()).isEqualTo(CellSnapshotStatus.NUMBER);
        assertThat(snapshot.getNearbyLandMineCount()).isEqualTo(4);
    }

    @Test
    @DisplayName("숫자 셀이 열리지 않고 깃발도 표시되지 않은 경우 스냅샷은 'UNCHECKED' 상태여야 한다")
    void shouldReturnUncheckedSnapshotWhenNotOpenedOrFlagged() {
        // Given: 초기화된 숫자 셀
        NumberCell numberCell = new NumberCell(2);

        // When: 스냅샷을 요청할 때
        CellSnapshot snapshot = numberCell.getSnapshot();

        // Then: 스냅샷은 'UNCHECKED' 상태여야 함
        assertThat(snapshot.getStatus()).isEqualTo(CellSnapshotStatus.UNCHECKED);
    }

    @Test
    @DisplayName("숫자 셀이 열리면 열린 상태로 간주되어야 한다")
    void shouldReturnTrueWhenCellIsOpened() {
        // Given: 숫자 셀이 초기화됨
        NumberCell numberCell = new NumberCell(3);

        // When: 셀을 열고 열린 상태를 확인할 때
        numberCell.open();
        boolean result = numberCell.isOpened();

        // Then: 열린 상태여야 함
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("숫자 셀이 열리지 않은 경우 닫힌 상태로 간주되어야 한다")
    void shouldReturnFalseWhenCellIsNotOpened() {
        // Given: 숫자 셀이 초기화됨
        NumberCell numberCell = new NumberCell(2);

        // When: 열린 상태를 확인할 때
        boolean result = numberCell.isOpened();

        // Then: 닫힌 상태여야 함
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("숫자 셀이 열리면 체크된 상태로 간주되어야 한다")
    void shouldReturnTrueWhenCellIsChecked() {
        // Given: 숫자 셀이 초기화되고 열림
        NumberCell numberCell = new NumberCell(2);
        numberCell.open();

        // When: 체크 상태를 확인할 때
        boolean result = numberCell.isChecked();

        // Then: 체크된 상태여야 함
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("숫자 셀이 열리지 않으면 체크되지 않은 상태로 간주되어야 한다")
    void shouldReturnFalseWhenCellIsNotChecked() {
        // Given: 숫자 셀이 초기화됨
        NumberCell numberCell = new NumberCell(2);

        // When: 체크 상태를 확인할 때
        boolean result = numberCell.isChecked();

        // Then: 체크되지 않은 상태여야 함
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("깃발이 표시된 숫자 셀도 열 수 있어야 한다")
    void shouldAllowOpeningEvenAfterFlagged() {
        // Given: 숫자 셀에 깃발이 표시됨
        NumberCell numberCell = new NumberCell(2);
        numberCell.flag();

        // When: 깃발이 표시된 후 셀을 열 때
        numberCell.open();

        // Then: 열린 상태여야 함
        assertThat(numberCell.isOpened()).isTrue();
    }
}
