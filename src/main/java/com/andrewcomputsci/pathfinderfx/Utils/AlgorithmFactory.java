package com.andrewcomputsci.pathfinderfx.Utils;

import com.andrewcomputsci.pathfinderfx.Generators.BinaryTree;
import com.andrewcomputsci.pathfinderfx.Generators.DFSRandom;
import com.andrewcomputsci.pathfinderfx.Generators.MazeGenerator;
import com.andrewcomputsci.pathfinderfx.Model.Algorithm;
import com.andrewcomputsci.pathfinderfx.Model.Heuristic;
import com.andrewcomputsci.pathfinderfx.Model.MazeType;
import com.andrewcomputsci.pathfinderfx.Solver.*;

import java.util.Optional;

public class AlgorithmFactory {


    public static PathFinderSolver getPathFinder(Algorithm algorithm, Heuristic heuristic, boolean exactMode){
        switch (algorithm){
            case BFS -> {
                return new BFS();
            }
            case DFS -> {
                return new DFS(exactMode);
            }
            case BFSGreedy -> {
                return new BFSGreedy(Heuristics.getFunctor(heuristic));
            }
            case AStar -> {
                return new AStar(Heuristics.getFunctor(heuristic));
            }
            case Dijkstra -> {return new Dijkstra();}
            case WaveFront -> {
                return new WaveFront();
            }
            default -> {
                return null;
            }
        }
    }

    public static MazeGenerator getMazeGenerator(MazeType mazeType){
        switch (mazeType){
            case BinaryTree -> {
                return new BinaryTree();
            }
            case DFSRandom -> {
                return new DFSRandom();
            }
            default -> {
                return null;
            }
        }
    }
}
