package me.sungbin.day12.minesweeper;

import me.sungbin.day12.minesweeper.minesweeper.Minesweeper;
import me.sungbin.day12.minesweeper.minesweeper.config.GameConfig;
import me.sungbin.day12.minesweeper.minesweeper.gamelevel.Beginner;
import me.sungbin.day12.minesweeper.minesweeper.io.ConsoleInputHandler;
import me.sungbin.day12.minesweeper.minesweeper.io.ConsoleOutputHandler;

public class GameApplication {

    public static void main(String[] args) {
        GameConfig gameConfig = new GameConfig(
            new Beginner(),
            new ConsoleInputHandler(),
            new ConsoleOutputHandler()
        );

        Minesweeper minesweeper = new Minesweeper(gameConfig);
        minesweeper.initialize();
        minesweeper.run();
    }

}
