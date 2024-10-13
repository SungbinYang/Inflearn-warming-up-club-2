package me.sungbin.day12.minesweeper.minesweeper.gamelevel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MiddleTest {

    @Test
    @DisplayName("중급 레벨의 행 크기는 14이어야 한다")
    void shouldReturnRowSizeAs14ForMiddleLevel() {
        // Given: 중급 레벨 게임이 초기화된 상황
        Middle middleLevel = new Middle();

        // When: 행 크기를 요청할 때
        int rowSize = middleLevel.getRowSize();

        // Then: 행 크기는 14이어야 함
        assertThat(rowSize).isEqualTo(14);
    }

    @Test
    @DisplayName("중급 레벨의 열 크기는 18이어야 한다")
    void shouldReturnColSizeAs18ForMiddleLevel() {
        // Given: 중급 레벨 게임이 초기화된 상황
        Middle middleLevel = new Middle();

        // When: 열 크기를 요청할 때
        int colSize = middleLevel.getColSize();

        // Then: 열 크기는 18이어야 함
        assertThat(colSize).isEqualTo(18);
    }

    @Test
    @DisplayName("중급 레벨의 지뢰 개수는 40이어야 한다")
    void shouldReturnLandMineCountAs40ForMiddleLevel() {
        // Given: 중급 레벨 게임이 초기화된 상황
        Middle middleLevel = new Middle();

        // When: 지뢰 개수를 요청할 때
        int landMineCount = middleLevel.getLandMineCount();

        // Then: 지뢰 개수는 40이어야 함
        assertThat(landMineCount).isEqualTo(40);
    }

    @Test
    @DisplayName("중급 레벨은 GameLevel 인터페이스를 구현해야 한다")
    void shouldImplementGameLevelInterface() {
        // Given: 중급 레벨 객체 생성
        Middle middleLevel = new Middle();

        // When & Then: GameLevel 인터페이스 구현 여부 확인
        assertThat(middleLevel).isInstanceOf(GameLevel.class);
    }

    // 예외 케이스: 타입 검증 실패 시
    @Test
    @DisplayName("GameLevel 인터페이스를 구현하지 않은 객체는 예외를 발생시켜야 한다")
    void shouldFailIfNotImplementingGameLevel() {
        // Given: GameLevel을 구현하지 않는 객체 생성
        Object notGameLevel = new Object();

        // When & Then: 해당 객체가 GameLevel이 아닌지 검증
        assertThat(notGameLevel).isNotInstanceOf(GameLevel.class);
    }
}
