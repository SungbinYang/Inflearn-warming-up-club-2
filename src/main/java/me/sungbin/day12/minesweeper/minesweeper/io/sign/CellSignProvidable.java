package me.sungbin.day12.minesweeper.minesweeper.io.sign;

import me.sungbin.day12.minesweeper.minesweeper.board.cell.CellSnapshot;

public interface CellSignProvidable {

    boolean supports(CellSnapshot cellSnapshot);

    String provide(CellSnapshot cellSnapshot);

}
