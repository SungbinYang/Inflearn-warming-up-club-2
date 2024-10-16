package me.sungbin.day12.minesweeper.minesweeper.io.sign;

import me.sungbin.day12.minesweeper.minesweeper.board.cell.CellSnapshot;
import me.sungbin.day12.minesweeper.minesweeper.board.cell.CellSnapshotStatus;

public class FlagCellSignProvider implements CellSignProvidable {

    private static final String FLAG_SIGN = "⚑";

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return cellSnapshot.isSameStatus(CellSnapshotStatus.FLAG);
    }

    @Override
    public String provide(CellSnapshot cellSnapshot) {
        return FLAG_SIGN;
    }

}
