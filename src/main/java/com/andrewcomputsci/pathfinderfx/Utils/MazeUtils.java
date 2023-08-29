package com.andrewcomputsci.pathfinderfx.Utils;

import com.andrewcomputsci.pathfinderfx.Model.CellType;
import com.andrewcomputsci.pathfinderfx.view.CellRectangle;

import java.util.Arrays;

public class MazeUtils {


    public static void initMaze(CellRectangle[] grid){
        for(CellRectangle itm: grid){
            itm.getInnerCell().typeProperty().set(CellType.Wall);
        }
    }
}
