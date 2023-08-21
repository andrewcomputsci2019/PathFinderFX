package com.andrewcomputsci.pathfinderfx.Utils;

import com.andrewcomputsci.pathfinderfx.Model.Algorithm;
import com.andrewcomputsci.pathfinderfx.Solver.*;

public class AlgorithmFactory {


    public static PathFinderSolver getPathFinder(Algorithm algorithm){
        switch (algorithm){
            case BFS -> {
                return new BFS();
            }
            case DFS -> {
                return new DFS();
            }
            case BFSGreedy -> {
                return new BFSGreedy();
            }
            case AStar -> {
                return new AStar();
            }
            default -> {
                return null;
            }
        }
    }
}
