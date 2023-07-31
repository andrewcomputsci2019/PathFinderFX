package com.andrewcomputsci.pathfinderfx.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Cell {
    private static final int DEFAULT_WEIGHT = 0;
    private final SimpleIntegerProperty weight;
    private SimpleObjectProperty<CellState> state;
    private SimpleObjectProperty<CellType> type;

    public Cell() {
        this(DEFAULT_WEIGHT, CellType.Traversable, CellState.Unvisited);
    }

    private Cell(int weight, CellType cellType, CellState state) {
        this.weight = new SimpleIntegerProperty(weight);
        setType(cellType);
        setState(state);
    }

    public Cell(int weight, CellType type) {
        this(weight, type, CellState.Unvisited);
    }

    public Cell(int weight) {
        this(weight, CellType.Traversable, CellState.Unvisited);
    }

    public void setState(CellState state) {
        this.state = new SimpleObjectProperty<>(state);
    }

    public void setType(CellType type) {
        this.type = new SimpleObjectProperty<>(type);
    }

    public int getWeight() {
        return weight.get();
    }

    public CellState getCellState() {
        return state.getValue();
    }

    public void setCellState(CellState state) {
        this.state.set(state);
    }

    public void setCellType(CellType type) {
        this.type.set(type);
    }

    public SimpleIntegerProperty weightProperty() {
        return weight;
    }

    public CellState getState() {
        return state.get();
    }

    public SimpleObjectProperty<CellState> stateProperty() {
        return state;
    }

    public CellType getType() {
        return type.get();
    }

    public SimpleObjectProperty<CellType> typeProperty() {
        return type;
    }
}
