package me.sungbin.day12.minesweeper.minesweeper.io.sign;

import me.sungbin.day12.minesweeper.minesweeper.board.cell.CellSnapshot;
import me.sungbin.day12.minesweeper.minesweeper.board.cell.CellSnapshotStatus;

public class EmptyCellSignProvider implements CellSignProvidable {

    private static final String EMPTY_SIGN = "■";

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return cellSnapshot.isSameStatus(CellSnapshotStatus.EMPTY);
    }

    @Override
    public String provide(CellSnapshot cellSnapshot) {
        return EMPTY_SIGN;
    }

}
