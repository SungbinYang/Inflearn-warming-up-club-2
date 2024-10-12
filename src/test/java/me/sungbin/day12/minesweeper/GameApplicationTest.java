package me.sungbin.day12.minesweeper;

import me.sungbin.day12.minesweeper.minesweeper.Minesweeper;
import me.sungbin.day12.minesweeper.minesweeper.config.GameConfig;
import me.sungbin.day12.minesweeper.minesweeper.gamelevel.Beginner;
import me.sungbin.day12.minesweeper.minesweeper.io.ConsoleInputHandler;
import me.sungbin.day12.minesweeper.minesweeper.io.ConsoleOutputHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GameApplicationTest {

    @Test
    @DisplayName("Minesweeper 객체가 올바르게 생성되어야 한다")
    void testCreateMinesweeperReturnsValidObject() {
        // given
        GameConfig gameConfig = new GameConfig(
                new Beginner(),
                new ConsoleInputHandler(),
                new ConsoleOutputHandler()
        );

        // when
        Minesweeper minesweeper = new Minesweeper(gameConfig);

        // then
        assertThat(minesweeper).isNotNull();
    }

    @Test
    @DisplayName("GameConfig가 Beginner 레벨로 초기화되어야 한다")
    void testGameConfigIsInitializedWithBeginnerLevel() {
        // given
        GameConfig config = new GameConfig(
                new Beginner(),
                new ConsoleInputHandler(),
                new ConsoleOutputHandler()
        );

        // when
        Minesweeper minesweeper = new Minesweeper(config);

        // then
        assertThat(config.getGameLevel()).isInstanceOf(Beginner.class);
    }

    @Test
    @DisplayName("GameConfig 생성 시 null 의존성으로 예외가 발생해야 한다")
    void testGameConfigCreationWithNullDependenciesThrowsException() {
        // given
        GameConfig invalidConfig;

        // when
        invalidConfig = new GameConfig(null, null, null);

        // then
        assertThatThrownBy(() -> new Minesweeper(invalidConfig))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("null");
    }
}