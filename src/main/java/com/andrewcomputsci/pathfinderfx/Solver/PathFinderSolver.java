package com.andrewcomputsci.pathfinderfx.Solver;

import com.andrewcomputsci.pathfinderfx.Model.Statistics;
import com.andrewcomputsci.pathfinderfx.view.CellRectangle;

public interface PathFinderSolver {
    public Statistics solve(CellRectangle[] grid, int width, int height);


}
