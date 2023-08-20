package com.andrewcomputsci.pathfinderfx.Solver;

import com.andrewcomputsci.pathfinderfx.Model.CellState;
import com.andrewcomputsci.pathfinderfx.Model.CellType;
import com.andrewcomputsci.pathfinderfx.Model.Message;
import com.andrewcomputsci.pathfinderfx.Model.Statistics;
import com.andrewcomputsci.pathfinderfx.view.CellRectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DFS implements PathFinderSolver {
    @Override
    public Statistics solve(CellRectangle[] grid, int width, int height, ConcurrentLinkedQueue<Message> queue) {
        int passes = 0;

        boolean[] visited = new boolean[grid.length];
        int[] predecessorTable = new int[grid.length];
        Arrays.fill(visited, false);
        //row major traversal
        Stack<int[]> cellStack = new Stack<>();
        for (int i = 0; i < grid.length; i++) {
            if (grid[i].getInnerCell().getType().equals(CellType.Source)) {
                int y = i / width;
                int x = i - y * width;
                cellStack.add(new int[]{x, y});
                predecessorTable[y * width + x] = -1;
                visited[y * width + x] = true;
            }
        }
        long timeInit = System.nanoTime();
        while (!cellStack.empty()) {
            int x = cellStack.peek()[0];
            int y = cellStack.pop()[1];
            queue.add(new Message(grid[y * width + x], CellState.Current));
            if (x - 1 > -1 && !visited[y * width + (x - 1)] && !grid[y * width + (x - 1)].getInnerCell().getType().equals(CellType.Wall)) {
                if (grid[y * width + (x - 1)].getInnerCell().getType().equals(CellType.Target)) {
                    timeInit = System.nanoTime() - timeInit;
                    return getStatistics(grid, width, passes, predecessorTable, timeInit, x, y);
                }
                queue.add(new Message(grid[y * width + (x - 1)], CellState.Expanded));
                cellStack.add(new int[]{x - 1, y});
                visited[y*width+(x-1)] = true;
                predecessorTable[y * width + (x - 1)] = y * width + x;
            }
            if (y + 1 < height && !visited[(y + 1) * width + x] && !grid[(y + 1) * width + x].getInnerCell().getType().equals(CellType.Wall)) {
                if (grid[(y + 1) * width + x].getInnerCell().getType().equals(CellType.Target)) {
                    return getStatistics(grid, width, passes, predecessorTable, timeInit, x, y);
                }
                queue.add(new Message(grid[(y + 1) * width + x], CellState.Expanded));
                cellStack.add(new int[]{x, y + 1});
                predecessorTable[(y + 1) * width + x] = y * width + x;
            }
            if (x + 1 < width && !visited[y * width + (x + 1)] && !grid[y * width + (x + 1)].getInnerCell().getType().equals(CellType.Wall)) {
                if (grid[y * width + (x + 1)].getInnerCell().getType().equals(CellType.Target)) {
                    return getStatistics(grid, width, passes, predecessorTable, timeInit, x, y);
                }
                queue.add(new Message(grid[y * width + (x + 1)], CellState.Expanded));
                cellStack.add(new int[]{x + 1, y});
                visited[(y + 1) * width + x] = true;
                predecessorTable[y * width + (x + 1)] = y * width + x;
            }
            if (y - 1 > -1 && !visited[(y - 1) * width + x] && !grid[(y - 1) * width + x].getInnerCell().getType().equals(CellType.Wall)) {
                if (grid[(y - 1) * width + x].getInnerCell().getType().equals(CellType.Target)) {
                    return getStatistics(grid, width, passes, predecessorTable, timeInit, x, y);
                }
                queue.add(new Message(grid[(y - 1) * width + x], CellState.Expanded));
                cellStack.add(new int[]{x, y - 1});
                visited[(y - 1) * width + x] = true;
                predecessorTable[(y - 1) * width + x] = y * width + x;
            }

            queue.add(new Message(grid[y * width + x], CellState.Visited));
            passes++;
        }
        return new Statistics(null, passes, System.nanoTime() - timeInit, 0);
    }

    private Statistics getStatistics(CellRectangle[] grid, int width, int passes, int[] predecessorTable, long timeInit, int x, int y) {
        List<CellRectangle> path = new ArrayList<>();
        int index = y * width + x;
        path.add(0, grid[index]);
        while (predecessorTable[index] != -1) {
            index = predecessorTable[index];
            path.add(0, grid[index]);

        }
        return new Statistics(path, passes, timeInit, path.size());
    }
}
