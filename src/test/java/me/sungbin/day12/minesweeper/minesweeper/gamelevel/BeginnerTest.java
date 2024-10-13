package me.sungbin.day12.minesweeper.minesweeper.gamelevel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BeginnerTest {

    @Test
    @DisplayName("초급 레벨의 행 크기는 8이어야 한다")
    void shouldReturnRowSizeAs8ForBeginnerLevel() {
        // Given: 초급 레벨 게임이 초기화된 상황
        Beginner beginner = new Beginner();

        // When: 행 크기를 요청할 때
        int rowSize = beginner.getRowSize();

        // Then: 행 크기는 8이어야 한다
        assertThat(rowSize).isEqualTo(8);
    }

    @Test
    @DisplayName("초급 레벨의 열 크기는 10이어야 한다")
    void shouldReturnColSizeAs10ForBeginnerLevel() {
        // Given: 초급 레벨 게임이 초기화된 상황
        Beginner beginner = new Beginner();

        // When: 열 크기를 요청할 때
        int colSize = beginner.getColSize();

        // Then: 열 크기는 10이어야 한다
        assertThat(colSize).isEqualTo(10);
    }

    @Test
    @DisplayName("초급 레벨의 지뢰 개수는 10이어야 한다")
    void shouldReturnLandMineCountAs10ForBeginnerLevel() {
        // Given: 초급 레벨 게임이 초기화된 상황
        Beginner beginner = new Beginner();

        // When: 지뢰 개수를 요청할 때
        int landMineCount = beginner.getLandMineCount();

        // Then: 지뢰 개수는 10이어야 한다
        assertThat(landMineCount).isEqualTo(10);
    }

    @Test
    @DisplayName("초급 레벨은 GameLevel 인터페이스를 구현해야 한다")
    void shouldImplementGameLevelInterface() {
        // Given: 초급 레벨 객체 생성
        Beginner beginner = new Beginner();

        // When & Then: GameLevel 인터페이스 구현 여부 확인
        assertThat(beginner).isInstanceOf(GameLevel.class);
    }
}
