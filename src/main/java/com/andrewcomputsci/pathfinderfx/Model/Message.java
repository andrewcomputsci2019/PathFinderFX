package com.andrewcomputsci.pathfinderfx.Model;

import com.andrewcomputsci.pathfinderfx.view.CellRectangle;

public class Message {
    private final CellState newType;
    private final CellRectangle cellToBeChanged;
    private final CellType cellType;

    public Message(CellRectangle cell, CellState type) {
        cellToBeChanged = cell;
        newType = type;
        cellType = null;
    }

    public Message(CellRectangle cell, CellType type) {
        cellToBeChanged = cell;
        cellType = type;
        newType = null;
    }

    public CellState getNewType() {
        return newType;
    }

    public CellRectangle getCellToBeChanged() {
        return cellToBeChanged;
    }

    public CellType getCellType() {
        return cellType;
    }
}
