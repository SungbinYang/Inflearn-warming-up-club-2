package me.sungbin.day12.minesweeper.minesweeper.io;

import me.sungbin.day12.minesweeper.minesweeper.board.GameBoard;
import me.sungbin.day12.minesweeper.minesweeper.exception.GameException;

public interface OutputHandler {

    void showGameStartComments();

    void showBoard(GameBoard board);

    void showGameWinningComment();

    void showGameLosingComment();

    void showCommentForSelectingCell();

    void showCommentForUserAction();

    void showExceptionMessage(GameException e);

    void showSimpleMessage(String message);

}
