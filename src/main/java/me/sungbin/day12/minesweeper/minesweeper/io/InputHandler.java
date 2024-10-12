package me.sungbin.day12.minesweeper.minesweeper.io;

import me.sungbin.day12.minesweeper.minesweeper.board.position.CellPosition;
import me.sungbin.day12.minesweeper.minesweeper.user.UserAction;

public interface InputHandler {

    UserAction getUserActionFromUser();

    CellPosition getCellPositionFromUser();

}
