package com.andrewcomputsci.pathfinderfx.view;

import com.andrewcomputsci.pathfinderfx.Model.Cell;
import com.andrewcomputsci.pathfinderfx.Model.CellType;
import javafx.beans.value.ObservableObjectValue;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


public class CellRectange extends Rectangle {
    private Cell innerCell;

    public CellRectange() {
        super();
        innerCell = new Cell();
        innerCell.stateProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Old Value: " + oldValue + " New Value: " + newValue);
        });
    }

    public CellRectange(double width, double height) {
        super(width, height);
        innerCell = new Cell();
        innerCell.stateProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Old Value: " + oldValue + " New Value: " + newValue);
        });
    }

    public CellRectange(double width, double height, Paint fill) {
        super(width, height, fill);

    }

    public CellRectange(double x, double y, double width, double height) {
        super(x, y, width, height);

    }

    public Cell getInnerCell() {
        return innerCell;
    }

}
