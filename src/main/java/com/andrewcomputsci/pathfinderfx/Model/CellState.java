package com.andrewcomputsci.pathfinderfx.Model;

/**
 * Represents the different states a cell can be in
 */
public enum CellState {
    /**
     * Represents that a cell has not been visited yet
     */
    Unvisited, //no-color
    /**
     * Represents that a cell has been visited
     */
    Visited, //#fcdb03 maybe
    /**
     * Represents that a cell is currently being viewed
     */
    Current, //red
    /**
     * Represents that a cell has been added to search
     */
    Expanded, //yellow
    /**
     * Represents that a cell is a part of final path
     */
    Path //magenta
}
