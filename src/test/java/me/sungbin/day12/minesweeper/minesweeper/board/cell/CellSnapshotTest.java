package me.sungbin.day12.minesweeper.minesweeper.board.cell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CellSnapshotTest {

    @Test
    @DisplayName("확인되지 않은 셀의 스냅샷은 'UNCHECKED' 상태와 지뢰가 없는 상태를 반영해야 한다")
    void shouldCreateUncheckedSnapshot() {
        // Given: 확인되지 않은 셀의 스냅샷 생성 준비

        // When: 확인되지 않은 셀 스냅샷 생성
        CellSnapshot snapshot = CellSnapshot.ofUnchecked();

        // Then: 'UNCHECKED' 상태와 지뢰 개수 0이 설정되어야 함
        assertThat(snapshot.getStatus()).isEqualTo(CellSnapshotStatus.UNCHECKED);
        assertThat(snapshot.getNearbyLandMineCount()).isZero();
    }

    @Test
    @DisplayName("빈 셀의 스냅샷은 'EMPTY' 상태와 지뢰가 없는 상태를 반영해야 한다")
    void shouldCreateEmptySnapshot() {
        // Given: 빈 셀의 스냅샷 생성 준비

        // When: 빈 셀 스냅샷 생성
        CellSnapshot snapshot = CellSnapshot.ofEmpty();

        // Then: 'EMPTY' 상태와 지뢰 개수 0이 설정되어야 함
        assertThat(snapshot.getStatus()).isEqualTo(CellSnapshotStatus.EMPTY);
        assertThat(snapshot.getNearbyLandMineCount()).isZero();
    }

    @Test
    @DisplayName("깃발이 표시된 셀의 스냅샷은 'FLAG' 상태로 생성되어야 한다")
    void shouldCreateFlaggedSnapshot() {
        // Given: 깃발이 표시된 셀의 스냅샷 생성 준비

        // When: 깃발 셀 스냅샷 생성
        CellSnapshot snapshot = CellSnapshot.ofFlag();

        // Then: 'FLAG' 상태와 지뢰 개수 0이 설정되어야 함
        assertThat(snapshot.getStatus()).isEqualTo(CellSnapshotStatus.FLAG);
        assertThat(snapshot.getNearbyLandMineCount()).isZero();
    }

    @Test
    @DisplayName("지뢰 셀의 스냅샷은 'LAND_MINE' 상태로 생성되어야 한다")
    void shouldCreateLandMineSnapshot() {
        // Given: 지뢰 셀의 스냅샷 생성 준비

        // When: 지뢰 셀 스냅샷 생성
        CellSnapshot snapshot = CellSnapshot.ofLandMine();

        // Then: 'LAND_MINE' 상태와 지뢰 개수 0이 설정되어야 함
        assertThat(snapshot.getStatus()).isEqualTo(CellSnapshotStatus.LAND_MINE);
        assertThat(snapshot.getNearbyLandMineCount()).isZero();
    }

    @Test
    @DisplayName("숫자 셀의 스냅샷은 'NUMBER' 상태와 주변 지뢰 개수를 반영해야 한다")
    void shouldCreateNumberSnapshotWithNearbyMineCount() {
        // Given: 주변 지뢰 개수가 3인 숫자 셀의 스냅샷 생성 준비

        // When: 숫자 셀 스냅샷 생성
        CellSnapshot snapshot = CellSnapshot.ofNumber(3);

        // Then: 'NUMBER' 상태와 지뢰 개수 3이 설정되어야 함
        assertThat(snapshot.getStatus()).isEqualTo(CellSnapshotStatus.NUMBER);
        assertThat(snapshot.getNearbyLandMineCount()).isEqualTo(3);
    }

    // isSameStatus() 테스트

    @Test
    @DisplayName("셀의 스냅샷 상태가 동일하면 일치하는 것으로 간주해야 한다")
    void shouldMaintainEmptyStatusWhenChecked() {
        // Given: 'EMPTY' 상태의 스냅샷

        // When: 상태를 'EMPTY'로 확인
        boolean result = CellSnapshot.ofEmpty().isSameStatus(CellSnapshotStatus.EMPTY);

        // Then: 상태가 일치하는 것으로 간주
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("셀의 스냅샷 상태가 다르면 일치하지 않는 것으로 간주해야 한다")
    void shouldNotMatchLandMineStatusForEmptyCell() {
        // Given: 'EMPTY' 상태의 스냅샷

        // When: 상태를 'LAND_MINE'으로 확인
        boolean result = CellSnapshot.ofEmpty().isSameStatus(CellSnapshotStatus.LAND_MINE);

        // Then: 상태가 일치하지 않는 것으로 간주
        assertThat(result).isFalse();
    }

    // equals() 테스트

    @Test
    @DisplayName("동일한 스냅샷 객체는 자기 자신과 비교할 때 같은 객체로 간주되어야 한다")
    void shouldBeIdenticalWhenComparingSameInstance() {
        // Given: 동일한 스냅샷 객체
        CellSnapshot snapshot = CellSnapshot.ofFlag();

        // When & Then: 같은 객체로 간주
        assertThat(snapshot).isEqualTo(snapshot);
    }

    @Test
    @DisplayName("동일한 상태와 값을 가진 두 스냅샷은 같은 객체로 간주되어야 한다")
    void shouldTreatSameSnapshotsAsIdentical() {
        // Given: 동일한 'FLAG' 상태의 두 스냅샷
        CellSnapshot snapshot1 = CellSnapshot.ofFlag();
        CellSnapshot snapshot2 = CellSnapshot.ofFlag();

        // When & Then: 같은 객체로 간주
        assertThat(snapshot1).isEqualTo(snapshot2);
        assertThat(snapshot1.hashCode()).isEqualTo(snapshot2.hashCode());
    }

    @Test
    @DisplayName("서로 다른 상태의 두 스냅샷은 같은 객체로 간주되지 않아야 한다")
    void shouldNotTreatDifferentSnapshotsAsIdentical() {
        // Given: 'EMPTY'와 'FLAG' 상태의 스냅샷
        CellSnapshot snapshot1 = CellSnapshot.ofEmpty();
        CellSnapshot snapshot2 = CellSnapshot.ofFlag();

        // When & Then: 다른 객체로 간주
        assertThat(snapshot1).isNotEqualTo(snapshot2);
    }

    @Test
    @DisplayName("스냅샷을 null과 비교할 경우 다른 객체로 간주되어야 한다")
    void shouldNotTreatSnapshotAsIdenticalWhenComparedWithNull() {
        // Given: 'EMPTY' 상태의 스냅샷
        CellSnapshot snapshot = CellSnapshot.ofEmpty();

        // When & Then: 다른 객체로 간주
        assertThat(snapshot).isNotEqualTo(null);
    }

    @Test
    @DisplayName("다른 타입의 객체와 비교할 경우 다른 객체로 간주되어야 한다")
    void shouldNotTreatSnapshotAsIdenticalWhenComparedWithDifferentType() {
        // Given: 'LAND_MINE' 상태의 스냅샷과 다른 타입의 객체
        CellSnapshot snapshot = CellSnapshot.ofLandMine();
        String differentObject = "Not a CellSnapshot";

        // When & Then: 다른 객체로 간주
        assertThat(snapshot).isNotEqualTo(differentObject);
    }

    @Test
    @DisplayName("동일한 지뢰 개수와 동일한 상태를 가진 두 스냅샷은 동일한 객체로 간주되어야 한다")
    void shouldBeEqualWhenAllFieldsAreSame() {
        // Given: 동일한 상태와 지뢰 개수를 가진 두 스냅샷
        CellSnapshot snapshot1 = CellSnapshot.ofNumber(2);
        CellSnapshot snapshot2 = CellSnapshot.ofNumber(2);

        // When & Then: 두 스냅샷은 동일한 객체로 간주
        assertThat(snapshot1).isEqualTo(snapshot2);
    }

    @Test
    @DisplayName("다른 지뢰 개수를 가진 두 스냅샷은 동일한 객체로 간주되지 않아야 한다")
    void shouldNotBeEqualWhenMineCountsAreDifferent() {
        // Given: 다른 지뢰 개수를 가진 두 스냅샷
        CellSnapshot snapshot1 = CellSnapshot.ofNumber(1);
        CellSnapshot snapshot2 = CellSnapshot.ofNumber(2);

        // When & Then: 두 스냅샷은 동일하지 않아야 함
        assertThat(snapshot1).isNotEqualTo(snapshot2);
    }

    @Test
    @DisplayName("다른 상태를 가진 두 스냅샷은 동일한 객체로 간주되지 않아야 한다")
    void shouldNotBeEqualWhenStatusesAreDifferent() {
        // Given: 서로 다른 상태의 두 스냅샷 ('NUMBER'와 'FLAG')
        CellSnapshot snapshot1 = CellSnapshot.ofNumber(2);
        CellSnapshot snapshot2 = CellSnapshot.ofFlag();

        // When & Then: 두 스냅샷은 동일하지 않아야 함
        assertThat(snapshot1).isNotEqualTo(snapshot2);
    }

    @Test
    @DisplayName("스냅샷을 자기 자신과 비교할 경우 동일한 객체로 간주되어야 한다")
    void shouldBeEqualWhenComparingSameInstance() {
        // Given: 동일한 스냅샷 객체
        CellSnapshot snapshot = CellSnapshot.ofFlag();

        // When & Then: 동일한 객체끼리 비교 시 동일해야 함
        assertThat(snapshot).isEqualTo(snapshot);
    }

    @Test
    @DisplayName("null과의 비교에서는 동일한 객체로 간주되지 않아야 한다")
    void shouldNotBeEqualWhenComparedWithNull() {
        // Given: 'EMPTY' 상태의 스냅샷
        CellSnapshot snapshot = CellSnapshot.ofEmpty();

        // When & Then: null과 비교 시 동일하지 않아야 함
        assertThat(snapshot).isNotEqualTo(null);
    }

    @Test
    @DisplayName("다른 타입의 객체와 비교할 경우 동일한 객체로 간주되지 않아야 한다")
    void shouldNotBeEqualWhenComparedWithDifferentType() {
        // Given: 'LAND_MINE' 상태의 스냅샷과 다른 타입의 객체
        CellSnapshot snapshot = CellSnapshot.ofLandMine();
        String differentObject = "Not a CellSnapshot";

        // When & Then: 다른 타입과 비교 시 동일하지 않아야 함
        assertThat(snapshot).isNotEqualTo(differentObject);
    }
}
