package me.sungbin.day12.minesweeper.minesweeper.config;

import me.sungbin.day12.minesweeper.minesweeper.gamelevel.GameLevel;
import me.sungbin.day12.minesweeper.minesweeper.io.InputHandler;
import me.sungbin.day12.minesweeper.minesweeper.io.OutputHandler;

public class GameConfig {

    private final GameLevel gameLevel;
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;

    public GameConfig(GameLevel gameLevel, InputHandler inputHandler, OutputHandler outputHandler) {
        this.gameLevel = gameLevel;
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    public GameLevel getGameLevel() {
        return gameLevel;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public OutputHandler getOutputHandler() {
        return outputHandler;
    }

}
