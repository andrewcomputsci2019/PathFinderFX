package com.andrewcomputsci.pathfinderfx.Model;

import com.andrewcomputsci.pathfinderfx.view.CellRectangle;

public class Message {
    private final CellType newType;
    private final CellRectangle cellToBeChanged;

    public Message(CellRectangle cell, CellType type){
        cellToBeChanged = cell;
        newType = type;
    }

    public CellType getNewType() {
        return newType;
    }

    public CellRectangle getCellToBeChanged() {
        return cellToBeChanged;
    }
}
