package com.andrewcomputsci.pathfinderfx.Model;

/**
 * Represents the different states a cell can be in
 */
public enum CellState {
    /**
     * Represents that a cell has not been visited yet
     */
    Unvisited,
    /**
     * Represents that a cell has been visited
     */
    Visited,
    /**
     * Represents that a cell is currently being viewed
     */
    Current,
    /**
     * Represents that a cell has been added to search
     */
    Added,
    /**
     * Represents that a cell is a part of final path
     */
    Path
}
