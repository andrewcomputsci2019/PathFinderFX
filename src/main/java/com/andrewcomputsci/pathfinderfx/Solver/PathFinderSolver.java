package com.andrewcomputsci.pathfinderfx.Solver;

import com.andrewcomputsci.pathfinderfx.view.CellRectange;

import java.util.List;

public interface PathFinderSolver {
    public List<CellRectange> solve(CellRectange[] grid, int width, int height);


}
