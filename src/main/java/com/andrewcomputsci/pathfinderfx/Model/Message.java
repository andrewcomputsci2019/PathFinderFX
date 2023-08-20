package com.andrewcomputsci.pathfinderfx.Model;

import com.andrewcomputsci.pathfinderfx.view.CellRectangle;

public class Message {
    private final CellState newType;
    private final CellRectangle cellToBeChanged;

    public Message(CellRectangle cell, CellState type){
        cellToBeChanged = cell;
        newType = type;
    }

    public CellState getNewType() {
        return newType;
    }

    public CellRectangle getCellToBeChanged() {
        return cellToBeChanged;
    }
}
