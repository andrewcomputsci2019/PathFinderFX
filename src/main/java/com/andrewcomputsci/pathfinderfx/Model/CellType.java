package com.andrewcomputsci.pathfinderfx.Model;




/**
 * Represents the different cell type that can exist
 * {@link #Wall}
 * {@link #Traversable}
 * {@link #Source}
 * {@link #Target}
 */
public enum CellType {
    /**
     * Represents a wall, preventing travel across
     */
    Wall, //black
    /**
     * Represents that a cell can be traveled across
     */
    Traversable, //white
    /**
     * Represents the starting node in the grid
     */
    Source, //green
    /**
     * Represents the end goal of the path
     */
    Target; //blue/teal


}
