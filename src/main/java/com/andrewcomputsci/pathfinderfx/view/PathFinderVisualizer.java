package com.andrewcomputsci.pathfinderfx.view;

import com.andrewcomputsci.pathfinderfx.Model.CellState;
import com.andrewcomputsci.pathfinderfx.Utils.Validators;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class PathFinderVisualizer {
    //static defaults
    private static final int DEFAULT_WIDTH = 50;
    private static final int DEFAULT_HEIGHT = 50;
    //fields
    private int width;
    private int height;
    //View Components
    private GridPane grid;
    private CellRectangle[] cellGrid;

    public PathFinderVisualizer() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public PathFinderVisualizer(int width, int height) {
        if (Validators.isNegative(width)) {
            width = DEFAULT_WIDTH;
        }
        if (Validators.isNegative(height)) {
            height = DEFAULT_HEIGHT;
        }
        setWidth(width);
        setHeight(height);
        grid = new GridPane();
        //grid.setStyle("-fx-border-color: red;");
        initGrid();
        drawGrid();
    }

    //getters
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public CellRectangle[] getCellGrid() {
        return cellGrid;
    }

    public static int getDefaultWidth() {
        return DEFAULT_WIDTH;
    }

    public static int getDefaultHeight() {
        return DEFAULT_HEIGHT;
    }

    public GridPane getGridNode() {
        return grid;
    }

    //setters

    /**
     * Setter of width field
     *
     * @param width positive integer value
     */
    private void setWidth(int width) {
        this.width = width;
    }

    /**
     * Setter of Height field
     *
     * @param height positive integer value
     */
    private void setHeight(int height) {
        this.height = height;
    }

    private void emptyGrid() {
        grid.getChildren().clear();
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();
    }

    /**
     * @param width  the new width of the grid
     * @param height the new height of the grid
     * @throws IllegalArgumentException if negative value given for either arguments
     */
    public void changeGridDimension(int width, int height) throws IllegalArgumentException {
        //check if values are negative
        if (Validators.isNegative(width) || Validators.isNegative(height)) {
            System.err.println("Expected non-negative number but received: " + "( " + width + ", " + height + " )");
            throw new IllegalArgumentException("Expected non-negative number but received: " + "( " + width + ", " + height + " )");
        }
        //set new values
        setHeight(height);
        setWidth(width);
        emptyGrid();
        initGrid();
        drawGrid();
    }

    /**
     * Method that allocates the Cell Grid and fills it with {@link CellRectangle}
     */
    private void initGrid() {
        cellGrid = new CellRectangle[width * height];
        for (int i = 0; i < (width * height); i++) {
            CellRectangle rectangle = new CellRectangle();
            cellGrid[i] = rectangle;
            cellGrid[i].setFill(Color.WHITE);
            cellGrid[i].setStyle("-fx-stroke-width: .5; -fx-stroke:black;");
        }
    }

    /**
     * Method that setups the grid ui, allowing for resizing of the grid to fit that of the parent container
     */
    private void drawGrid() {
        //set grid size constraints
        for (int c = 0; c < width; c++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100.0 / width);
            cc.setHgrow(Priority.ALWAYS);
            grid.getColumnConstraints().add(cc);
        }
        //set grid size constraints
        for (int r = 0; r < height; r++) {
            RowConstraints rc = new RowConstraints();
            rc.setPercentHeight(100.0 / height);
            rc.setVgrow(Priority.ALWAYS);
            grid.getRowConstraints().add(rc);
        }
        //add cells to grid
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int index = i * width + j;
                Pane pane = new Pane();
                pane.getChildren().add(cellGrid[index]);
                cellGrid[index].widthProperty().bind(pane.widthProperty());
                cellGrid[index].heightProperty().bind(pane.heightProperty());
                //alternative way of setting automatic resizing
/*            pane.widthProperty().addListener((observable, oldValue, newValue) -> {
                CellRectange rect = (CellRectange) pane.getChildren().get(0);
                rect.setWidth(newValue.doubleValue());
            });
            pane.heightProperty().addListener((observable, oldValue, newValue) -> {
                CellRectange rect = (CellRectange) pane.getChildren().get(0);
                rect.setHeight(newValue.doubleValue());
            });*/
                grid.add(pane, j, i);
                grid.getChildren().get(grid.getChildren().size() - 1).setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: .5 .5 0 0;");
            }
        }
    }


}
