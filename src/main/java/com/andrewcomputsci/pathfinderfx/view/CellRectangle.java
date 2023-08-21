package com.andrewcomputsci.pathfinderfx.view;

import com.andrewcomputsci.pathfinderfx.Model.Cell;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


public class CellRectangle extends Rectangle {
    private Cell innerCell;

    public CellRectangle() {
        super();
        innerCell = new Cell();
    }

    public CellRectangle(double width, double height) {
        super(width, height);
        innerCell = new Cell();
    }

    public CellRectangle(double width, double height, Paint fill) {
        super(width, height, fill);
        innerCell = new Cell();
    }

    public CellRectangle(double x, double y, double width, double height) {
        super(x, y, width, height);

    }

    public Cell getInnerCell() {
        return innerCell;
    }

}
