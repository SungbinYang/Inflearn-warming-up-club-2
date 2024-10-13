package me.sungbin.day12.minesweeper.minesweeper.board.cell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LandMineCellTest {

    @Test
    @DisplayName("지뢰 셀은 항상 지뢰가 있는 상태여야 한다")
    void shouldReturnTrueWhenCheckingForLandMine() {
        // Given: 지뢰 셀이 초기화됨
        LandMineCell landMineCell = new LandMineCell();

        // When: 지뢰 여부를 확인할 때
        boolean result = landMineCell.isLandMine();

        // Then: 지뢰가 있는 상태여야 함
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("지뢰 셀은 지뢰 개수를 표시할 수 없는 상태여야 한다")
    void shouldReturnFalseWhenCheckingForLandMineCount() {
        // Given: 지뢰 셀이 초기화됨
        LandMineCell landMineCell = new LandMineCell();

        // When: 지뢰 개수 표시 가능 여부를 확인할 때
        boolean result = landMineCell.hasLandMineCount();

        // Then: 지뢰 개수를 표시할 수 없는 상태여야 함
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("지뢰 셀에 깃발이 표시되면 스냅샷은 'FLAG' 상태여야 한다")
    void shouldReturnFlagSnapshotWhenFlagged() {
        // Given: 지뢰 셀이 초기화됨
        LandMineCell landMineCell = new LandMineCell();

        // When: 깃발을 표시한 후 스냅샷을 요청할 때
        landMineCell.flag();
        CellSnapshot snapshot = landMineCell.getSnapshot();

        // Then: 스냅샷은 'FLAG' 상태여야 함
        assertThat(snapshot.getStatus()).isEqualTo(CellSnapshotStatus.FLAG);
    }

    @Test
    @DisplayName("지뢰 셀이 열리면 스냅샷은 'LAND_MINE' 상태여야 한다")
    void shouldReturnLandMineSnapshotWhenOpened() {
        // Given: 지뢰 셀이 초기화됨
        LandMineCell landMineCell = new LandMineCell();

        // When: 셀을 열고 스냅샷을 요청할 때
        landMineCell.open();
        CellSnapshot snapshot = landMineCell.getSnapshot();

        // Then: 스냅샷은 'LAND_MINE' 상태여야 함
        assertThat(snapshot.getStatus()).isEqualTo(CellSnapshotStatus.LAND_MINE);
    }

    @Test
    @DisplayName("지뢰 셀이 열리지 않고 깃발도 표시되지 않은 경우 스냅샷은 'UNCHECKED' 상태여야 한다")
    void shouldReturnUncheckedSnapshotWhenNotOpenedOrFlagged() {
        // Given: 초기화된 지뢰 셀
        LandMineCell landMineCell = new LandMineCell();

        // When: 스냅샷을 요청할 때
        CellSnapshot snapshot = landMineCell.getSnapshot();

        // Then: 스냅샷은 'UNCHECKED' 상태여야 함
        assertThat(snapshot.getStatus()).isEqualTo(CellSnapshotStatus.UNCHECKED);
    }

    @Test
    @DisplayName("지뢰 셀에 깃발이 표시되면 체크된 상태로 간주되어야 한다")
    void shouldReturnTrueWhenCellIsCheckedAfterFlagged() {
        // Given: 지뢰 셀에 깃발이 표시됨
        LandMineCell landMineCell = new LandMineCell();
        landMineCell.flag();

        // When: 체크 상태를 확인할 때
        boolean result = landMineCell.isChecked();

        // Then: 체크된 상태여야 함
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("지뢰 셀이 열리지 않으면 체크되지 않은 상태로 간주되어야 한다")
    void shouldReturnFalseWhenCellIsNotChecked() {
        // Given: 지뢰 셀이 초기화됨
        LandMineCell landMineCell = new LandMineCell();

        // When: 체크 상태를 확인할 때
        boolean result = landMineCell.isChecked();

        // Then: 체크되지 않은 상태여야 함
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("지뢰 셀이 열리면 열린 상태로 간주되어야 한다")
    void shouldReturnTrueWhenCellIsOpened() {
        // Given: 지뢰 셀이 초기화됨
        LandMineCell landMineCell = new LandMineCell();

        // When: 셀을 열고 열린 상태를 확인할 때
        landMineCell.open();
        boolean result = landMineCell.isOpened();

        // Then: 열린 상태여야 함
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("지뢰 셀이 열리지 않은 경우 닫힌 상태로 간주되어야 한다")
    void shouldReturnFalseWhenCellIsNotOpened() {
        // Given: 지뢰 셀이 초기화됨
        LandMineCell landMineCell = new LandMineCell();

        // When: 열린 상태를 확인할 때
        boolean result = landMineCell.isOpened();

        // Then: 닫힌 상태여야 함
        assertThat(result).isFalse();
    }

    // 예외 케이스

    @Test
    @DisplayName("깃발이 표시된 지뢰 셀도 열 수 있어야 한다")
    void shouldAllowOpeningEvenAfterFlagged() {
        // Given: 지뢰 셀에 깃발이 표시됨
        LandMineCell landMineCell = new LandMineCell();
        landMineCell.flag();

        // When: 깃발이 표시된 후 셀을 열 때
        landMineCell.open();

        // Then: 열린 상태여야 함
        assertThat(landMineCell.isOpened()).isTrue();
    }

    @Test
    @DisplayName("스냅샷 요청 시 지뢰 셀의 현재 상태를 정확히 반영해야 한다")
    void shouldReturnCorrectSnapshotBasedOnCurrentState() {
        // Given: 지뢰 셀에 깃발이 표시된 후 열림
        LandMineCell landMineCell = new LandMineCell();
        landMineCell.flag();
        landMineCell.open();

        // When: 스냅샷을 요청할 때
        CellSnapshot snapshot = landMineCell.getSnapshot();

        // Then: 스냅샷은 'LAND_MINE' 상태여야 함
        assertThat(snapshot.getStatus()).isEqualTo(CellSnapshotStatus.LAND_MINE);
    }
}
