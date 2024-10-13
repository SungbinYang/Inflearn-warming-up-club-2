package me.sungbin.day12.minesweeper.minesweeper.board.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RelativePositionTest {

    @Test
    @DisplayName("유효한 행과 열의 변화값으로 상대 위치를 생성할 수 있다")
    void shouldCreateRelativePositionWithValidDeltas() {
        // Given: 유효한 행과 열의 변화값 (-1, 1)

        // When: 해당 변화값으로 상대 위치를 생성할 때
        RelativePosition position = RelativePosition.of(-1, 1);

        // Then: 생성된 객체의 변화값이 동일해야 함
        assertThat(position.getDeltaRow()).isEqualTo(-1);
        assertThat(position.getDeltaCol()).isEqualTo(1);
    }

    @Test
    @DisplayName("동일한 변화값을 가진 두 상대 위치 객체는 동일한 객체로 간주된다")
    void shouldReturnTrueWhenTwoRelativePositionsHaveSameDeltas() {
        // Given: 동일한 변화값을 가진 두 객체 (-1, 1)
        RelativePosition position1 = RelativePosition.of(-1, 1);
        RelativePosition position2 = RelativePosition.of(-1, 1);

        // When & Then: 두 객체는 동일한 객체로 간주되어야 함
        assertThat(position1).isEqualTo(position2);
        assertThat(position1.hashCode()).isEqualTo(position2.hashCode());
    }

    @Test
    @DisplayName("주변 8방향의 상대 위치를 제공할 수 있다")
    void shouldProvideAllSurroundedPositions() {
        // Given: 주변 8방향 상대 위치 리스트

        // When: 주변 상대 위치를 제공할 때
        List<RelativePosition> surroundedPositions = RelativePosition.SURROUNDED_POSITIONS;

        // Then: 총 8개의 상대 위치가 반환되어야 함
        assertThat(surroundedPositions.size()).isEqualTo(8);
    }

    @Test
    @DisplayName("동일한 객체를 비교할 때 true를 반환한다")
    void shouldReturnTrueWhenComparingSameInstance() {
        // Given: 동일한 객체 생성
        RelativePosition position = RelativePosition.of(0, 1);

        // When & Then: 같은 객체를 비교할 때 true를 반환해야 함
        assertThat(position.equals(position)).isTrue();
    }

    // 예외 케이스

    @Test
    @DisplayName("null과 비교할 경우 false를 반환한다")
    void shouldReturnFalseWhenComparedWithNull() {
        // Given: 상대 위치 객체 생성
        RelativePosition position = RelativePosition.of(0, 1);

        // When & Then: null과 비교할 때 false를 반환해야 함
        assertThat(position.equals(null)).isFalse();
    }

    @Test
    @DisplayName("다른 타입의 객체와 비교할 경우 false를 반환한다")
    void shouldReturnFalseWhenComparedWithDifferentType() {
        // Given: 상대 위치 객체와 다른 타입의 객체 생성
        RelativePosition position = RelativePosition.of(0, 1);
        String differentObject = "Not a RelativePosition";

        // When & Then: 다른 타입의 객체와 비교할 때 false를 반환해야 함
        assertThat(position.equals(differentObject)).isFalse();
    }

    @Test
    @DisplayName("행과 열의 변화값이 다르면 두 객체는 동일하지 않다")
    void shouldReturnFalseWhenDeltasAreDifferent() {
        // Given: 서로 다른 변화값을 가진 두 객체
        RelativePosition position1 = RelativePosition.of(1, 0);
        RelativePosition position2 = RelativePosition.of(0, 1);

        // When & Then: 두 객체는 동일하지 않아야 함
        assertThat(position1).isNotEqualTo(position2);
    }

    @Test
    @DisplayName("행의 변화값이 동일하지 않으면 두 객체는 동일하지 않다")
    void shouldReturnFalseWhenRowDeltaIsDifferent() {
        // Given: 행의 변화값이 다른 두 객체
        RelativePosition position1 = RelativePosition.of(1, 0);
        RelativePosition position2 = RelativePosition.of(2, 0);

        // When & Then: 두 객체는 동일하지 않아야 함
        assertThat(position1).isNotEqualTo(position2);
    }

    @Test
    @DisplayName("열의 변화값이 동일하지 않으면 두 객체는 동일하지 않다")
    void shouldReturnFalseWhenColDeltaIsDifferent() {
        // Given: 열의 변화값이 다른 두 객체
        RelativePosition position1 = RelativePosition.of(0, 1);
        RelativePosition position2 = RelativePosition.of(0, 2);

        // When & Then: 두 객체는 동일하지 않아야 함
        assertThat(position1).isNotEqualTo(position2);
    }
}
