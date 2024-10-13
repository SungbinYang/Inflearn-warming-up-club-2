package me.sungbin.day12.minesweeper.minesweeper.config;

import me.sungbin.day12.minesweeper.minesweeper.gamelevel.GameLevel;
import me.sungbin.day12.minesweeper.minesweeper.io.InputHandler;
import me.sungbin.day12.minesweeper.minesweeper.io.OutputHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class GameConfigTest {

    @Test
    @DisplayName("게임 설정이 유효한 구성 요소로 초기화되면 올바르게 반환되어야 한다")
    void shouldReturnCorrectComponentsWhenInitializedWithValidConfig() {
        // Given: 유효한 게임 설정 구성 요소 (mock 사용)
        GameLevel gameLevel = mock(GameLevel.class);
        InputHandler inputHandler = mock(InputHandler.class);
        OutputHandler outputHandler = mock(OutputHandler.class);

        // When: GameConfig가 생성될 때
        GameConfig gameConfig = new GameConfig(gameLevel, inputHandler, outputHandler);

        // Then: 각 구성 요소가 올바르게 반환되어야 함
        assertThat(gameConfig.getGameLevel()).isEqualTo(gameLevel);
        assertThat(gameConfig.getInputHandler()).isEqualTo(inputHandler);
        assertThat(gameConfig.getOutputHandler()).isEqualTo(outputHandler);
    }

    @Test
    @DisplayName("게임 레벨을 올바르게 반환해야 한다")
    void shouldReturnCorrectGameLevel() {
        // Given: 유효한 GameLevel (mock 사용)
        GameLevel gameLevel = mock(GameLevel.class);
        GameConfig gameConfig = new GameConfig(gameLevel, mock(InputHandler.class), mock(OutputHandler.class));

        // When: GameLevel 반환 요청
        GameLevel returnedGameLevel = gameConfig.getGameLevel();

        // Then: 올바른 GameLevel이 반환되어야 함
        assertThat(returnedGameLevel).isEqualTo(gameLevel);
    }

    @Test
    @DisplayName("입력 핸들러를 올바르게 반환해야 한다")
    void shouldReturnCorrectInputHandler() {
        // Given: 유효한 InputHandler (mock 사용)
        InputHandler inputHandler = mock(InputHandler.class);
        GameConfig gameConfig = new GameConfig(mock(GameLevel.class), inputHandler, mock(OutputHandler.class));

        // When: InputHandler 반환 요청
        InputHandler returnedInputHandler = gameConfig.getInputHandler();

        // Then: 올바른 InputHandler가 반환되어야 함
        assertThat(returnedInputHandler).isEqualTo(inputHandler);
    }

    @Test
    @DisplayName("출력 핸들러를 올바르게 반환해야 한다")
    void shouldReturnCorrectOutputHandler() {
        // Given: 유효한 OutputHandler (mock 사용)
        OutputHandler outputHandler = mock(OutputHandler.class);
        GameConfig gameConfig = new GameConfig(mock(GameLevel.class), mock(InputHandler.class), outputHandler);

        // When: OutputHandler 반환 요청
        OutputHandler returnedOutputHandler = gameConfig.getOutputHandler();

        // Then: 올바른 OutputHandler가 반환되어야 함
        assertThat(returnedOutputHandler).isEqualTo(outputHandler);
    }
}
