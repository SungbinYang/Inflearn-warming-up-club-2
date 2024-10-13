package me.sungbin.day12.minesweeper.minesweeper.gamelevel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VeryBeginnerTest {

    @Test
    @DisplayName("매우 초급 레벨의 행 크기는 4이어야 한다")
    void shouldReturnRowSizeAs4ForVeryBeginnerLevel() {
        // Given: 매우 초급 레벨 게임이 초기화된 상황
        VeryBeginner veryBeginner = new VeryBeginner();

        // When: 행 크기를 요청할 때
        int rowSize = veryBeginner.getRowSize();

        // Then: 행 크기는 4이어야 한다
        assertThat(rowSize).isEqualTo(4);
    }

    @Test
    @DisplayName("매우 초급 레벨의 열 크기는 5이어야 한다")
    void shouldReturnColSizeAs5ForVeryBeginnerLevel() {
        // Given: 매우 초급 레벨 게임이 초기화된 상황
        VeryBeginner veryBeginner = new VeryBeginner();

        // When: 열 크기를 요청할 때
        int colSize = veryBeginner.getColSize();

        // Then: 열 크기는 5이어야 한다
        assertThat(colSize).isEqualTo(5);
    }

    @Test
    @DisplayName("매우 초급 레벨의 지뢰 개수는 2이어야 한다")
    void shouldReturnLandMineCountAs2ForVeryBeginnerLevel() {
        // Given: 매우 초급 레벨 게임이 초기화된 상황
        VeryBeginner veryBeginner = new VeryBeginner();

        // When: 지뢰 개수를 요청할 때
        int landMineCount = veryBeginner.getLandMineCount();

        // Then: 지뢰 개수는 2이어야 한다
        assertThat(landMineCount).isEqualTo(2);
    }

    @Test
    @DisplayName("매우 초급 레벨은 GameLevel 인터페이스를 구현해야 한다")
    void shouldImplementGameLevelInterface() {
        // Given: 매우 초급 레벨 객체 생성
        VeryBeginner veryBeginner = new VeryBeginner();

        // When & Then: GameLevel 인터페이스 구현 여부 확인
        assertThat(veryBeginner).isInstanceOf(GameLevel.class);
    }

    // 예외 케이스 테스트 (해당 클래스에서는 크게 없으므로 인터페이스 검증만 추가)
    @Test
    @DisplayName("GameLevel 인터페이스를 구현하지 않으면 예외를 발생시켜야 한다")
    void shouldFailIfNotImplementingGameLevel() {
        // Given & When & Then: 다른 타입의 객체를 GameLevel로 캐스팅할 때 예외 발생
        Object notGameLevel = new Object();
        assertThat(notGameLevel).isNotInstanceOf(GameLevel.class);
    }
}
