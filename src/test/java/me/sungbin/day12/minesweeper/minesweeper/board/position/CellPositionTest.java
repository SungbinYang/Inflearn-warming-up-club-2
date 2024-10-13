package me.sungbin.day12.minesweeper.minesweeper.board.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CellPositionTest {

    // 해피 케이스

    @Test
    @DisplayName("게임 보드에 유효한 셀 위치를 생성할 수 있다")
    void shouldCreateCellPositionWithValidCoordinates() {
        // Given: 유효한 좌표 (2, 3)
        int rowIndex = 2;
        int colIndex = 3;

        // When: 좌표로 셀 위치를 생성할 때
        CellPosition position = CellPosition.of(rowIndex, colIndex);

        // Then: 셀 위치가 정상적으로 생성되어야 함
        assertThat(position.getRowIndex()).isEqualTo(2);
        assertThat(position.getColIndex()).isEqualTo(3);
    }

    @Test
    @DisplayName("같은 위치에 있는 셀들은 동일한 좌표로 간주된다")
    void shouldBeEqualWhenTwoPositionsHaveSameCoordinates() {
        // Given: 동일한 좌표 (1, 1)
        CellPosition position1 = CellPosition.of(1, 1);
        CellPosition position2 = CellPosition.of(1, 1);

        // When & Then: 두 위치는 동일한 객체로 간주되어야 함
        assertThat(position1).isEqualTo(position2);
        assertThat(position1.hashCode()).isEqualTo(position2.hashCode());
    }

    @Test
    @DisplayName("셀의 행 인덱스가 목표 인덱스 이상이면 참으로 간주된다")
    void shouldReturnTrueWhenRowIndexIsMoreThanOrEqual() {
        // Given: 셀 위치 (2, 3)
        CellPosition position = CellPosition.of(2, 3);

        // When: 행 인덱스가 1 이상인지 확인할 때
        boolean result = position.isRowIndexMoreThanOrEqual(1);

        // Then: 참이어야 함
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("셀의 열 인덱스가 목표 인덱스 미만이면 참으로 간주된다")
    void shouldReturnTrueWhenColIndexIsLessThan() {
        // Given: 셀 위치 (2, 1)
        CellPosition position = CellPosition.of(2, 1);

        // When: 열 인덱스가 2 미만인지 확인할 때
        boolean result = position.isColIndexLessThan(2);

        // Then: 참이어야 함
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("셀의 상대 위치를 이용해 새로운 위치를 계산할 수 있다")
    void shouldCalculateNewPositionWithValidRelativePosition() {
        // Given: 셀 위치 (2, 3)와 상대 위치 (1, -1)
        CellPosition position = CellPosition.of(2, 3);
        RelativePosition relativePosition = RelativePosition.of(1, -1);

        // When: 상대 위치를 사용해 새로운 셀 위치를 계산할 때
        CellPosition newPosition = position.calculatePositionBy(relativePosition);

        // Then: 새로운 위치는 (3, 2)여야 함
        assertThat(newPosition.getRowIndex()).isEqualTo(3);
        assertThat(newPosition.getColIndex()).isEqualTo(2);
    }

    // 예외 케이스

    @Test
    @DisplayName("음수 좌표로 셀 위치를 생성하면 예외가 발생해야 한다")
    void shouldThrowExceptionWhenCreatingWithNegativeCoordinates() {
        // Given: 음수 좌표 (-1, 0)

        // When & Then: 좌표가 음수일 때 예외가 발생해야 함
        assertThatThrownBy(() -> CellPosition.of(-1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("올바르지 않은 좌표입니다.");
    }

    @Test
    @DisplayName("유효하지 않은 상대 위치를 계산할 때 예외가 발생해야 한다")
    void shouldThrowExceptionWhenCalculatingInvalidPosition() {
        // Given: 셀 위치 (0, 0)과 상대 위치 (-1, 0)
        CellPosition position = CellPosition.of(0, 0);
        RelativePosition relativePosition = RelativePosition.of(-1, 0);

        // When & Then: 유효하지 않은 상대 위치 계산 시 예외가 발생해야 함
        assertThatThrownBy(() -> position.calculatePositionBy(relativePosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("움직일 수 있는 좌표가 아닙니다.");
    }

    @Test
    @DisplayName("셀의 행 인덱스가 목표 인덱스 미만이면 유효한 인덱스이다")
    void shouldReturnTrueWhenRowIndexIsLessThan() {
        // Given: 셀 위치 (1, 2)
        CellPosition position = CellPosition.of(1, 2);

        // When: 행 인덱스가 2보다 작은지 확인할 때
        boolean result = position.isRowIndexLessThan(2);

        // Then: 참이어야 함
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("셀의 열 인덱스가 목표 인덱스 이상이면 유효한 인덱스이다")
    void shouldReturnTrueWhenColIndexIsMoreThanOrEqual() {
        // Given: 셀 위치 (3, 2)
        CellPosition position = CellPosition.of(3, 2);

        // When: 열 인덱스가 2 이상인지 확인할 때
        boolean result = position.isColIndexMoreThanOrEqual(2);

        // Then: 참이어야 함
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("음수 행 인덱스로 셀 위치를 생성하면 예외가 발생해야 한다")
    void shouldThrowExceptionWhenRowIndexIsNegative() {
        // Given: 음수의 행 인덱스 (-1)와 유효한 열 인덱스 (0)

        // When & Then: 행 인덱스가 음수일 때 예외가 발생해야 함
        assertThatThrownBy(() -> CellPosition.of(-1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("올바르지 않은 좌표입니다.");
    }

    @Test
    @DisplayName("음수 열 인덱스로 셀 위치를 생성하면 예외가 발생해야 한다")
    void shouldThrowExceptionWhenColIndexIsNegative() {
        // Given: 유효한 행 인덱스 (0)와 음수의 열 인덱스 (-1)

        // When & Then: 열 인덱스가 음수일 때 예외가 발생해야 함
        assertThatThrownBy(() -> CellPosition.of(0, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("올바르지 않은 좌표입니다.");
    }

    @Test
    @DisplayName("음수의 행과 열 인덱스를 가진 좌표는 예외를 발생시킨다")
    void shouldThrowExceptionWhenBothIndicesAreNegative() {
        // Given: 음수의 행 인덱스 (-1)와 음수의 열 인덱스 (-1)

        // When & Then: 두 인덱스가 모두 음수일 때 예외가 발생해야 함
        assertThatThrownBy(() -> CellPosition.of(-1, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("올바르지 않은 좌표입니다.");
    }

    @Test
    @DisplayName("셀 위치를 자기 자신과 비교할 때 같은 위치로 간주된다")
    void shouldReturnTrueWhenComparingSameInstance() {
        // Given: 동일한 객체 생성
        CellPosition position = CellPosition.of(2, 3);

        // When & Then: 같은 객체를 비교할 때 true를 반환해야 함
        assertThat(position.equals(position)).isTrue();
    }

    @Test
    @DisplayName("셀 위치를 null과 비교하면 같지 않은 위치로 간주된다")
    void shouldReturnFalseWhenComparedWithNull() {
        // Given: 셀 위치 생성
        CellPosition position = CellPosition.of(2, 3);

        // When & Then: null과 비교할 때 false를 반환해야 함
        assertThat(position.equals(null)).isFalse();
    }

    @Test
    @DisplayName("다른 타입의 객체와 비교하면 거짓으로 간주된다")
    void shouldReturnFalseWhenComparedWithDifferentType() {
        // Given: 셀 위치 생성
        CellPosition position = CellPosition.of(2, 3);
        String differentObject = "Not a CellPosition";

        // When & Then: 다른 타입의 객체와 비교할 때 false를 반환해야 함
        assertThat(position.equals(differentObject)).isFalse();
    }

    @Test
    @DisplayName("같은 좌표를 가진 셀 위치는 동일한 객체로 간주된다")
    void shouldReturnTrueWhenCoordinatesAreSame() {
        // Given: 같은 좌표를 가진 두 객체 생성
        CellPosition position1 = CellPosition.of(2, 3);
        CellPosition position2 = CellPosition.of(2, 3);

        // When & Then: 같은 좌표를 가진 두 객체는 true를 반환해야 함
        assertThat(position1.equals(position2)).isTrue();
    }

    @Test
    @DisplayName("다른 좌표를 가진 두 객체는 동일하지 않은 객체로 간주된다")
    void shouldReturnFalseWhenCoordinatesAreDifferent() {
        // Given: 다른 좌표를 가진 두 객체 생성
        CellPosition position1 = CellPosition.of(2, 3);
        CellPosition position2 = CellPosition.of(3, 4);

        // When & Then: 다른 좌표를 가진 두 객체는 false를 반환해야 함
        assertThat(position1.equals(position2)).isFalse();
    }

    @Test
    @DisplayName("행과 열 인덱스가 동일한 두 셀 위치는 동일한 객체로 간주해야 한다")
    void shouldReturnTrueWhenBothRowAndColAreSame() {
        // Given: 동일한 좌표 (2, 3)로 생성된 두 셀 위치
        CellPosition position1 = CellPosition.of(2, 3);
        CellPosition position2 = CellPosition.of(2, 3);

        // When & Then: 두 셀 위치는 동일한 객체로 간주되어야 함
        assertThat(position1.equals(position2)).isTrue();
    }

    @Test
    @DisplayName("행 인덱스가 다르면 두 셀 위치는 동일하지 않아야 한다")
    void shouldReturnFalseWhenRowIndexIsDifferent() {
        // Given: 다른 행 인덱스 (2 vs 3)를 가진 두 셀 위치
        CellPosition position1 = CellPosition.of(2, 3);
        CellPosition position2 = CellPosition.of(3, 3);

        // When & Then: 두 셀 위치는 동일하지 않아야 함
        assertThat(position1.equals(position2)).isFalse();
    }

    @Test
    @DisplayName("열 인덱스가 다르면 두 셀 위치는 동일하지 않아야 한다")
    void shouldReturnFalseWhenColIndexIsDifferent() {
        // Given: 다른 열 인덱스 (3 vs 4)를 가진 두 셀 위치
        CellPosition position1 = CellPosition.of(2, 3);
        CellPosition position2 = CellPosition.of(2, 4);

        // When & Then: 두 셀 위치는 동일하지 않아야 함
        assertThat(position1.equals(position2)).isFalse();
    }

    @Test
    @DisplayName("행과 열 인덱스가 모두 다르면 두 셀 위치는 동일하지 않아야 한다")
    void shouldReturnFalseWhenBothRowAndColAreDifferent() {
        // Given: 다른 행과 열 인덱스 (2, 3 vs 3, 4)를 가진 두 셀 위치
        CellPosition position1 = CellPosition.of(2, 3);
        CellPosition position2 = CellPosition.of(3, 4);

        // When & Then: 두 셀 위치는 동일하지 않아야 함
        assertThat(position1.equals(position2)).isFalse();
    }

    @Test
    @DisplayName("셀 위치의 행 인덱스가 지정된 기준과 같거나 크면 유효한 위치로 간주된다")
    void shouldReturnTrueWhenRowIndexIsMoreThanOrEqualToTarget() {
        // Given: 셀 위치 (2, 3)
        CellPosition position = CellPosition.of(2, 3);

        // When: 행 인덱스가 2 이상인지 확인할 때
        boolean result = position.isRowIndexMoreThanOrEqual(2);

        // Then: true여야 함
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("셀의 행 인덱스가 기준보다 작으면 유효하지 않은 위치로 간주된다")
    void shouldReturnFalseWhenRowIndexIsLessThanTarget() {
        // Given: 셀 위치 (1, 3)
        CellPosition position = CellPosition.of(1, 3);

        // When: 행 인덱스가 2 이상인지 확인할 때
        boolean result = position.isRowIndexMoreThanOrEqual(2);

        // Then: false여야 함
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("셀의 열 인덱스가 기준과 같거나 크면 유효한 위치로 간주된다")
    void shouldReturnTrueWhenColIndexIsMoreThanOrEqualToTarget() {
        // Given: 셀 위치 (2, 3)
        CellPosition position = CellPosition.of(2, 3);

        // When: 열 인덱스가 3 이상인지 확인할 때
        boolean result = position.isColIndexMoreThanOrEqual(3);

        // Then: true여야 함
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("셀의 열 인덱스가 기준보다 작으면 유효하지 않은 위치로 간주된다")
    void shouldReturnFalseWhenColIndexIsLessThanTarget() {
        // Given: 셀 위치 (2, 2)
        CellPosition position = CellPosition.of(2, 2);

        // When: 열 인덱스가 3 이상인지 확인할 때
        boolean result = position.isColIndexMoreThanOrEqual(3);

        // Then: false여야 함
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("상대 위치를 더한 결과가 유효할 경우 새로운 위치로 계산될 수 있다")
    void shouldReturnTrueWhenColIndexAfterCalculationIsNonNegative() {
        // Given: 셀 위치 (2, 2)와 상대 위치 (0, 1)
        CellPosition position = CellPosition.of(2, 2);
        RelativePosition relativePosition = RelativePosition.of(0, 1);

        // When: 상대 위치를 더한 결과가 0 이상인지 확인할 때
        boolean result = position.canCalculatePositionBy(relativePosition);

        // Then: true여야 함
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("상대 위치를 더한 결과가 음수일 경우 유효하지 않은 위치로 간주된다")
    void shouldReturnFalseWhenColIndexAfterCalculationIsNegative() {
        // Given: 셀 위치 (2, 2)와 상대 위치 (0, -3)
        CellPosition position = CellPosition.of(2, 2);
        RelativePosition relativePosition = RelativePosition.of(0, -3);

        // When: 상대 위치를 더한 결과가 음수인지 확인할 때
        boolean result = position.canCalculatePositionBy(relativePosition);

        // Then: false여야 함
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("상대 위치를 더한 결과, 행과 열 인덱스가 모두 유효하면 이동이 가능하다")
    void shouldReturnTrueWhenBothRowAndColAreNonNegative() {
        // Given: 셀 위치 (2, 2)와 상대 위치 (1, 1)
        CellPosition position = CellPosition.of(2, 2);
        RelativePosition relativePosition = RelativePosition.of(1, 1);

        // When: 상대 위치를 더한 결과가 모두 0 이상인지 확인할 때
        boolean result = position.canCalculatePositionBy(relativePosition);

        // Then: true여야 함
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("상대 위치를 더한 결과, 행 또는 열 인덱스가 음수면 이동이 불가능하다")
    void shouldReturnFalseWhenEitherRowOrColIsNegative() {
        // Given: 셀 위치 (1, 1)과 상대 위치 (-2, 0)
        CellPosition position = CellPosition.of(1, 1);
        RelativePosition relativePosition = RelativePosition.of(-2, 0);

        // When: 상대 위치를 더한 결과가 음수인지 확인할 때
        boolean result = position.canCalculatePositionBy(relativePosition);

        // Then: false여야 함
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("셀의 행 인덱스가 기준보다 작으면 유효한 위치로 간주된다")
    void shouldReturnTrueWhenRowIndexIsLessThanTarget() {
        // Given: 셀 위치 (1, 2)
        CellPosition position = CellPosition.of(1, 2);

        // When: 행 인덱스가 2보다 작은지 확인할 때
        boolean result = position.isRowIndexLessThan(2);

        // Then: true여야 함
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("셀의 행 인덱스가 기준보다 크거나 같으면 유효하지 않은 위치로 간주된다")
    void shouldReturnFalseWhenRowIndexIsNotLessThanTarget() {
        // Given: 셀 위치 (2, 2)
        CellPosition position = CellPosition.of(2, 2);

        // When: 행 인덱스가 2보다 작은지 확인할 때
        boolean result = position.isRowIndexLessThan(2);

        // Then: false여야 함
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("셀의 열 인덱스가 기준보다 작으면 유효한 위치로 간주된다")
    void shouldReturnTrueWhenColIndexIsLessThanTarget() {
        // Given: 셀 위치 (2, 1)
        CellPosition position = CellPosition.of(2, 1);

        // When: 열 인덱스가 2보다 작은지 확인할 때
        boolean result = position.isColIndexLessThan(2);

        // Then: true여야 함
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("셀의 열 인덱스가 기준과 같거나 크면 유효하지 않은 위치로 간주된다")
    void shouldReturnFalseWhenColIndexIsNotLessThanTarget() {
        // Given: 셀 위치 (2, 2)
        CellPosition position = CellPosition.of(2, 2);

        // When: 열 인덱스가 2보다 작은지 확인할 때
        boolean result = position.isColIndexLessThan(2);

        // Then: false여야 함
        assertThat(result).isFalse();
    }
}
