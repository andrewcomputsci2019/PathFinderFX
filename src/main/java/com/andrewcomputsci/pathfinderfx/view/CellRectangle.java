package com.andrewcomputsci.pathfinderfx.view;

import com.andrewcomputsci.pathfinderfx.Model.Cell;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


public class CellRectangle extends Rectangle {
    private Cell innerCell;

    public CellRectangle() {
        super();
        innerCell = new Cell();
        innerCell.stateProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Old Value: " + oldValue + " New Value: " + newValue);
        });
    }

    public CellRectangle(double width, double height) {
        super(width, height);
        innerCell = new Cell();
        innerCell.stateProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Old Value: " + oldValue + " New Value: " + newValue);
        });
    }

    public CellRectangle(double width, double height, Paint fill) {
        super(width, height, fill);

    }

    public CellRectangle(double x, double y, double width, double height) {
        super(x, y, width, height);

    }

    public Cell getInnerCell() {
        return innerCell;
    }

}
