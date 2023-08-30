package com.andrewcomputsci.pathfinderfx.Utils;

import com.andrewcomputsci.pathfinderfx.Model.CellState;
import com.andrewcomputsci.pathfinderfx.Model.CellType;
import com.andrewcomputsci.pathfinderfx.view.CellRectangle;

public class GridUtils {


    public static void initMaze(CellRectangle[] grid){
        for(CellRectangle itm: grid){
            itm.getInnerCell().typeProperty().set(CellType.Wall);
        }
    }
    public static void initPathFind(CellRectangle[] grid){
        for(CellRectangle rect : grid){
            rect.getInnerCell().stateProperty().set(CellState.Unvisited);
        }
    }
}
