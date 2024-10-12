package me.sungbin.day12.minesweeper.minesweeper.io.sign;

import me.sungbin.day12.minesweeper.minesweeper.board.cell.CellSnapshot;
import me.sungbin.day12.minesweeper.minesweeper.board.cell.CellSnapshotStatus;

public class NumberCellSignProvider implements CellSignProvidable {

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return cellSnapshot.isSameStatus(CellSnapshotStatus.NUMBER);
    }

    @Override
    public String provide(CellSnapshot cellSnapshot) {
        return String.valueOf(cellSnapshot.getNearbyLandMineCount());
    }

}
