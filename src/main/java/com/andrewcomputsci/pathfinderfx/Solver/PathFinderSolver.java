package com.andrewcomputsci.pathfinderfx.Solver;

import com.andrewcomputsci.pathfinderfx.view.CellRectangle;

import java.util.List;

public interface PathFinderSolver {
    public List<CellRectangle> solve(CellRectangle[] grid, int width, int height);


}
