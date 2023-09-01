package com.andrewcomputsci.pathfinderfx.Solver;

import com.andrewcomputsci.pathfinderfx.Model.*;
import com.andrewcomputsci.pathfinderfx.Utils.Heuristics;
import com.andrewcomputsci.pathfinderfx.view.CellRectangle;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BFSGreedy implements PathFinderSolver {
    Heuristics.Functor heuristic;

    public BFSGreedy(Heuristics.Functor functor) {
        this.heuristic = functor;
    }

    @Override
    public Statistics solve(CellRectangle[] grid, int width, int height, ConcurrentLinkedQueue<Message> queue) {
        //3 length array 0 = x 1 = y 2 = distance to target
        int[] target = new int[]{0, 0};
        int passes = 0;
        long deltaTime;
        PriorityQueue<int[]> searchQueue = new PriorityQueue<>(Comparator.comparing(ints -> heuristic.compute(ints[0], ints[1], target[0], target[1])));
        int[] predecessorTable = new int[grid.length];
        boolean[] visited = new boolean[grid.length];
        Arrays.fill(visited, false);
        int startX = 0;
        int startY = 0;
        for (int i = 0; i < grid.length; i++) {
            if (grid[i].getInnerCell().typeProperty().get().equals(CellType.Source)) {
                predecessorTable[i] = -1;
                startX = i % width;
                startY = i / width;
            }
            if (grid[i].getInnerCell().typeProperty().get().equals(CellType.Target)) {
                int y = i / width;
                int x = i % width;
                target[0] = x;
                target[1] = y;
            }
        }
        visited[startY*width+startX] = true;
        searchQueue.add(new int[]{startX, startY});
        deltaTime = System.nanoTime();
        while (!searchQueue.isEmpty()) {
            int x = searchQueue.peek()[0];
            int y = searchQueue.poll()[1];
            queue.add(new Message(grid[y * width + x], CellState.Current));
            if ((y - 1) > -1 && !visited[(y - 1) * width + x] && !grid[(y - 1) * width + x].getInnerCell().typeProperty().get().equals(CellType.Wall)) {
                if (grid[(y - 1) * width + x].getInnerCell().getType().equals(CellType.Target)) {
                    deltaTime = System.nanoTime() - deltaTime;
                    return BFS.getStatistics(grid, width, predecessorTable, passes, deltaTime, x, y);
                }
                queue.add(new Message(grid[(y - 1) * width + x], CellState.Expanded));
                visited[(y - 1) * width + x] = true;
                searchQueue.add(new int[]{x, y - 1});
                predecessorTable[(y - 1) * width + x] = y * width + x;
            }
            if ((x + 1) < width && !visited[y * width + (x + 1)] && !grid[y * width + (x + 1)].getInnerCell().typeProperty().get().equals(CellType.Wall)) {
                if (grid[y * width + (x + 1)].getInnerCell().getType().equals(CellType.Target)) {
                    deltaTime = System.nanoTime() - deltaTime;
                    return BFS.getStatistics(grid, width, predecessorTable, passes, deltaTime, x, y);
                }
                queue.add(new Message(grid[y * width + (x + 1)], CellState.Expanded));
                searchQueue.add(new int[]{x + 1, y});
                visited[y * width + (x + 1)] = true;
                predecessorTable[y * width + (x + 1)] = y * width + x;
            }
            if ((y + 1) < height && !visited[(y + 1) * width + x] && !grid[(y + 1) * width + x].getInnerCell().typeProperty().get().equals(CellType.Wall)) {
                if (grid[(y + 1) * width + x].getInnerCell().getType().equals(CellType.Target)) {
                    deltaTime = System.nanoTime() - deltaTime;
                    return BFS.getStatistics(grid, width, predecessorTable, passes, deltaTime, x, y);
                }
                queue.add(new Message(grid[(y + 1) * width + x], CellState.Expanded));
                searchQueue.add(new int[]{x, y + 1});
                visited[(y + 1) * width + x] = true;
                predecessorTable[(y + 1) * width + x] = y * width + x;
            }
            if ((x - 1) > -1 && !visited[y * width + (x - 1)] && !grid[y * width + (x - 1)].getInnerCell().typeProperty().get().equals(CellType.Wall)) {
                if (grid[y * width + (x - 1)].getInnerCell().getType().equals(CellType.Target)) {
                    deltaTime = System.nanoTime() - deltaTime;
                    return BFS.getStatistics(grid, width, predecessorTable, passes, deltaTime, x, y);
                }
                queue.add(new Message(grid[y * width + (x - 1)], CellState.Expanded));
                visited[y * width + (x - 1)] = true;
                searchQueue.add(new int[]{x - 1, y});
                predecessorTable[y * width + (x - 1)] = y * width + x;
            }
            queue.add(new Message(grid[y * width + x], CellState.Visited));
            passes++;
        }
        return new Statistics(null, passes, deltaTime, 0);
    }

}
