package com.andrewcomputsci.pathfinderfx.Model;


import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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
    Wall(Color.BLACK), //black
    /**
     * Represents that a cell can be traveled across
     */
    Traversable(Color.WHITE), //white
    /**
     * Represents the starting node in the grid
     */
    Source(Color.GREEN), //green
    /**
     * Represents the end goal of the path
     */
    Target(Color.TEAL); //blue/teal

    private final Paint color;

    CellType(Paint color) {
        this.color = color;
    }

    public Paint getColor() {
        return color;
    }

}
