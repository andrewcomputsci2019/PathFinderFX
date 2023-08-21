package com.andrewcomputsci.pathfinderfx.Model;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Represents the different states a cell can be in
 */
public enum CellState {
    /**
     * Represents that a cell has not been visited yet
     */
    Unvisited(null), //no-color
    /**
     * Represents that a cell has been visited
     */
    Visited(Color.valueOf("#fcdb03")), //#fcdb03 maybe
    /**
     * Represents that a cell is currently being viewed
     */
    Current(Color.RED), //red
    /**
     * Represents that a cell has been added to search
     */
    Expanded(Color.YELLOW), //yellow
    /**
     * Represents that a cell is a part of final path
     */
    Path(Color.MAGENTA); //magenta

    private final Paint color;

    CellState(Paint color){
        this.color = color;
    }

    public Paint getColor() {
        return color;
    }
}
