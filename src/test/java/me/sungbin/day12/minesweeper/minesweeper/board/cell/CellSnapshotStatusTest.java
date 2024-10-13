package me.sungbin.day12.minesweeper.minesweeper.board.cell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CellSnapshotStatusTest {

    @Test
    @DisplayName("게임 보드의 셀은 상황에 맞는 상태를 가져야 한다")
    void shouldReturnCorrectDescriptionForEachStatus() {
        // Given
        CellSnapshotStatus emptyStatus = CellSnapshotStatus.EMPTY;
        CellSnapshotStatus flagStatus = CellSnapshotStatus.FLAG;
        CellSnapshotStatus landMineStatus = CellSnapshotStatus.LAND_MINE;
        CellSnapshotStatus numberStatus = CellSnapshotStatus.NUMBER;
        CellSnapshotStatus uncheckedStatus = CellSnapshotStatus.UNCHECKED;

        // When & Then
        assertThat(emptyStatus.name()).isEqualTo("EMPTY");
        assertThat(emptyStatus.ordinal()).isEqualTo(0);
        assertThat(emptyStatus).extracting("description").isEqualTo("빈 셀");

        assertThat(flagStatus.name()).isEqualTo("FLAG");
        assertThat(flagStatus.ordinal()).isEqualTo(1);
        assertThat(flagStatus).extracting("description").isEqualTo("깃발");

        assertThat(landMineStatus.name()).isEqualTo("LAND_MINE");
        assertThat(landMineStatus.ordinal()).isEqualTo(2);
        assertThat(landMineStatus).extracting("description").isEqualTo("지뢰");

        assertThat(numberStatus.name()).isEqualTo("NUMBER");
        assertThat(numberStatus.ordinal()).isEqualTo(3);
        assertThat(numberStatus).extracting("description").isEqualTo("숫자");

        assertThat(uncheckedStatus.name()).isEqualTo("UNCHECKED");
        assertThat(uncheckedStatus.ordinal()).isEqualTo(4);
        assertThat(uncheckedStatus).extracting("description").isEqualTo("확인 전");
    }

    @Test
    @DisplayName("잘못된 상태를 요청하면 게임 규칙에 맞지 않는다는 오류를 발생시켜야 한다")
    void shouldThrowExceptionForInvalidStatusName() {
        // Given
        String invalidStatus = "UNKNOWN";

        // When & Then
        assertThatThrownBy(() -> CellSnapshotStatus.valueOf(invalidStatus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No enum constant")
                .hasMessageContaining(invalidStatus);
    }

    @Test
    @DisplayName("상태가 정의되지 않은 경우 셀의 상태를 요청하면 오류가 발생해야 한다")
    void shouldThrowExceptionForNullStatus() {
        // Given
        String nullStatus = null;

        // When & Then
        assertThatThrownBy(() -> CellSnapshotStatus.valueOf(nullStatus))
                .isInstanceOf(NullPointerException.class);
    }
}