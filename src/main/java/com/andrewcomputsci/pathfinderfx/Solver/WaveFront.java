package com.andrewcomputsci.pathfinderfx.Solver;

import com.andrewcomputsci.pathfinderfx.Model.*;
import com.andrewcomputsci.pathfinderfx.view.CellRectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WaveFront implements PathFinderSolver {
    @Override
    public Statistics solve(CellRectangle[] grid, int width, int height, ConcurrentLinkedQueue<Message> queue) {
        int targetX = 0;
        int targetY = 0;
        int startX = 0;
        int startY = 0;
        int passes = 0;
        int[] weightList = new int[grid.length];
        Arrays.fill(weightList, 0);
        ArrayList<int[]> cellList = new ArrayList<>(height);

        for (int i = 0; i < grid.length; i++) {
            if (grid[i].getInnerCell().typeProperty().get().equals(CellType.Target)) {
                targetY = i / width;
                targetX = i % width;
                weightList[i] = 1;
                if (targetY - 1 > -1 && !grid[(targetY - 1) * width + targetX].getInnerCell().typeProperty().get().equals(CellType.Wall)) {
                    queue.add(new Message(grid[(targetY - 1) * width + targetX], CellState.Expanded));
                    weightList[(targetY - 1) * width + targetX] = 2;
                    cellList.add(new int[]{targetX, targetY - 1});
                }
                if (targetX + 1 < width && !grid[(targetY) * width + (targetX + 1)].getInnerCell().typeProperty().get().equals(CellType.Wall)) {
                    queue.add(new Message(grid[targetY * width + targetX + 1], CellState.Expanded));
                    weightList[targetY * width + targetX + 1] = 2;
                    cellList.add(new int[]{targetX + 1, targetY});
                }
                if (targetY + 1 < height && !grid[(targetY + 1) * width + targetX].getInnerCell().typeProperty().get().equals(CellType.Wall)) {
                    weightList[(targetY + 1) * width + targetX] = 2;
                    cellList.add(new int[]{targetX, targetY + 1});
                }
                if (targetX - 1 > -1 && !grid[(targetY) * width + (targetX - 1)].getInnerCell().typeProperty().get().equals(CellType.Wall)) {
                    queue.add(new Message(grid[targetY * width + targetX - 1], CellState.Expanded));
                    weightList[targetY * width + targetX - 1] = 2;
                    cellList.add(new int[]{targetX - 1, targetY});
                }
            }
            if (grid[i].getInnerCell().typeProperty().get().equals(CellType.Source)) {
                startY = i / width;
                startX = i % width;
            }
        }
        long delta = System.nanoTime();
        while (!cellList.isEmpty()) {
            passes++;
            int[] tmp = cellList.remove(0);
            int y = tmp[1];
            int x = tmp[0];
            queue.add(new Message(grid[y * width + x], CellState.Current));
            //try to visit all nodes around and set weight
            if (y - 1 > -1 && weightList[(y - 1) * width + x] == 0 && !grid[(y - 1) * width + x].getInnerCell().typeProperty().get().equals(CellType.Wall)) {
                weightList[(y - 1) * width + x] = weightList[y * width + x] + 1;
                cellList.add(new int[]{x, y - 1});
                queue.add(new Message(grid[(y - 1) * width + x], CellState.Expanded));
            }
            if (x + 1 < width && weightList[y * width + x + 1] == 0 && !grid[y * width + x + 1].getInnerCell().typeProperty().get().equals(CellType.Wall)) {
                weightList[y * width + x + 1] = weightList[y * width + x] + 1;
                cellList.add(new int[]{x + 1, y});
                queue.add(new Message(grid[y * width + x + 1], CellState.Expanded));
            }
            if (y + 1 < height && weightList[(y + 1) * width + x] == 0 && !grid[(y + 1) * width + x].getInnerCell().typeProperty().get().equals(CellType.Wall)) {
                weightList[(y + 1) * width + x] = weightList[y * width + x] + 1;
                cellList.add(new int[]{x, y + 1});
                queue.add(new Message(grid[(y + 1) * width + x], CellState.Expanded));
            }
            if (x - 1 > -1 && weightList[y * width + x - 1] == 0 && !grid[y * width + x - 1].getInnerCell().typeProperty().get().equals(CellType.Wall)) {
                weightList[y * width + x - 1] = weightList[y * width + x] + 1;
                cellList.add(new int[]{x - 1, y});
                queue.add(new Message(grid[y * width + x - 1], CellState.Expanded));
            }
            queue.add(new Message(grid[y * width + x], CellState.Visited));
        }
        List<CellRectangle> path = new ArrayList<>();
        int[] curr = new int[]{startX, startY};
        while (curr != null) {
            int x = curr[0];
            int y = curr[1];
            int[] minNode = null;
            int minVal = Integer.MAX_VALUE;
            if (y - 1 > -1 && weightList[(y - 1) * width + x] != 0 && !grid[(y - 1) * width + x].getInnerCell().typeProperty().get().equals(CellType.Wall)) {
                if (grid[(y - 1) * width + x].getInnerCell().typeProperty().get().equals(CellType.Target)) {
                    break;
                }
                minVal = Math.min(minVal, weightList[(y - 1) * width + x]);
                minNode = minVal == weightList[(y - 1) * width + x] ? new int[]{x, y - 1} : minNode;
            }
            if (x + 1 < width && weightList[y * width + x + 1] != 0 && !grid[y * width + x + 1].getInnerCell().typeProperty().get().equals(CellType.Wall)) {
                if (grid[(y) * width + x + 1].getInnerCell().typeProperty().get().equals(CellType.Target)) {
                    break;
                }
                minVal = Math.min(minVal, weightList[y * width + x + 1]);
                minNode = minVal == weightList[y * width + x + 1] ? new int[]{x + 1, y} : minNode;
            }
            if (y + 1 < height && weightList[(y + 1) * width + x] != 0 && !grid[(y + 1) * width + x].getInnerCell().typeProperty().get().equals(CellType.Wall)) {
                if (grid[(y + 1) * width + x].getInnerCell().typeProperty().get().equals(CellType.Target)) {
                    break;
                }
                minVal = Math.min(minVal, weightList[(y + 1) * width + x]);
                minNode = minVal == weightList[(y + 1) * width + x] ? new int[]{x, y + 1} : minNode;
            }
            if (x - 1 > -1 && weightList[y * width + x - 1] != 0 && !grid[y * width + x - 1].getInnerCell().typeProperty().get().equals(CellType.Wall)) {
                if (grid[(y) * width + x - 1].getInnerCell().typeProperty().get().equals(CellType.Target)) {
                    break;
                }
                minVal = Math.min(minVal, weightList[y * width + x - 1]);
                minNode = minVal == weightList[y * width + x - 1] ? new int[]{x - 1, y} : minNode;
            }
            curr = minNode;
            if (curr != null) {
                path.add(grid[curr[1] * width + curr[0]]);
            }
            //search around curr find min node, set as new curr
        }
        return new Statistics(path, passes, System.nanoTime() - delta, path.size());
    }
}
