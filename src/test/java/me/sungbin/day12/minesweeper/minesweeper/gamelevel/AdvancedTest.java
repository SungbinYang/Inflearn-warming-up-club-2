package me.sungbin.day12.minesweeper.minesweeper.gamelevel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AdvancedTest {

    @Test
    @DisplayName("고급 레벨의 행 크기는 20이어야 한다")
    void shouldReturnRowSizeAs20ForAdvancedLevel() {
        // Given: 고급 레벨 게임이 초기화된 상황
        Advanced advancedLevel = new Advanced();

        // When: 행 크기를 요청할 때
        int rowSize = advancedLevel.getRowSize();

        // Then: 행 크기는 20이어야 함
        assertThat(rowSize).isEqualTo(20);
    }

    @Test
    @DisplayName("고급 레벨의 열 크기는 24이어야 한다")
    void shouldReturnColSizeAs24ForAdvancedLevel() {
        // Given: 고급 레벨 게임이 초기화된 상황
        Advanced advancedLevel = new Advanced();

        // When: 열 크기를 요청할 때
        int colSize = advancedLevel.getColSize();

        // Then: 열 크기는 24이어야 함
        assertThat(colSize).isEqualTo(24);
    }

    @Test
    @DisplayName("고급 레벨의 지뢰 개수는 99이어야 한다")
    void shouldReturnLandMineCountAs99ForAdvancedLevel() {
        // Given: 고급 레벨 게임이 초기화된 상황
        Advanced advancedLevel = new Advanced();

        // When: 지뢰 개수를 요청할 때
        int landMineCount = advancedLevel.getLandMineCount();

        // Then: 지뢰 개수는 99이어야 함
        assertThat(landMineCount).isEqualTo(99);
    }

    @Test
    @DisplayName("고급 레벨은 GameLevel 인터페이스를 구현해야 한다")
    void shouldImplementGameLevelInterface() {
        // Given: 고급 레벨 객체 생성
        Advanced advancedLevel = new Advanced();

        // When & Then: GameLevel 인터페이스 구현 여부 확인
        assertThat(advancedLevel).isInstanceOf(GameLevel.class);
    }

    // 예외 케이스: GameLevel 인터페이스를 구현하지 않은 객체에 대한 타입 검증
    @Test
    @DisplayName("GameLevel을 구현하지 않은 객체는 타입 검증에 실패해야 한다")
    void shouldFailIfNotImplementingGameLevel() {
        // Given: GameLevel을 구현하지 않는 객체 생성
        Object notGameLevel = new Object();

        // When & Then: 해당 객체가 GameLevel이 아닌지 검증
        assertThat(notGameLevel).isNotInstanceOf(GameLevel.class);
    }
}
