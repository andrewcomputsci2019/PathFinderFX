package com.andrewcomputsci.pathfinderfx.Model;

import com.andrewcomputsci.pathfinderfx.view.CellRectangle;

import java.util.List;

public record Statistics(List<CellRectangle> path, int passes, long deltaTime, double pathCost) {

}
