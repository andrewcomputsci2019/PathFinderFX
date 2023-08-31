package com.andrewcomputsci.pathfinderfx.Generators;

import com.andrewcomputsci.pathfinderfx.Model.CellState;
import com.andrewcomputsci.pathfinderfx.Model.CellType;
import com.andrewcomputsci.pathfinderfx.Model.Message;
import com.andrewcomputsci.pathfinderfx.view.CellRectangle;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PrimsRandom implements MazeGenerator {
    private CellRectangle[] grid;
    private boolean[] visited;
    private boolean[] frontierCell;
    private int width;
    private int height;
    private ConcurrentLinkedQueue<Message> messages;

    @Override
    public void generateMaze(CellRectangle[] grid, int width, int height, ConcurrentLinkedQueue<Message> messageQueue) {
        this.messages = messageQueue;
        this.grid = grid;
        this.width = width;
        this.height = height;
        visited = new boolean[grid.length];
        frontierCell = new boolean[grid.length];

        Arrays.fill(frontierCell, false);
        Arrays.fill(visited, false);

        Random random = new Random();
        int startX = random.nextInt(0, width);
        int startY = random.nextInt(0, height);
        ArrayList<int[]> frontierCells = new ArrayList<>(getFrontierCells(startX, startY));
        frontierCell[startY * width + startX] = true;
        visited[startY * width + startX] = true;
        messages.add(new Message(grid[startY * width + startX], CellType.Traversable));
        List<int[]> cells;
        System.out.println("[DEBUG] -- Started Prims algorithm");
        while (frontierCells.size() != 0) {
            int[] randCell = frontierCells.remove(random.nextInt(0, frontierCells.size()));
            cells = getVisitedAdjacentCells(randCell[0], randCell[1]);
            int[] randAdjacentCell = cells.get(random.nextInt(0, cells.size()));
            joinCell(randCell[0], randCell[1], randAdjacentCell[0], randAdjacentCell[1]);
            visited[randCell[1] * width + randCell[0]] = true;
            messages.add(new Message(grid[randCell[1] * width + randCell[0]], CellType.Traversable));
            frontierCells.addAll(getFrontierCells(randCell[0], randCell[1]));
        }
        System.out.println("[DEBUG] -- Ended Prims algorithm");
    }

    private void joinCell(int x1, int y1, int x2, int y2) {
        int x = x2;
        int y = y2;
        if (x2 > x1) {
            x -= 1;
        } else if (x1 > x2) {
            x += 1;
        }
        if (y2 > y1) {
            y -= 1;
        } else if (y1 > y2) {
            y += 1;
        }
        messages.add(new Message(grid[y * width + x], CellType.Traversable));
    }

    private List<int[]> getFrontierCells(int x, int y) {
        ArrayList<int[]> cellList = new ArrayList<>();
        if (x - 2 > -1 && !frontierCell[y * width + (x - 2)]) {
            cellList.add(new int[]{x - 2, y});
            messages.add(new Message(grid[y * width + x - 2], CellState.Expanded));
            frontierCell[y * width + (x - 2)] = true;
        }
        if (x + 2 < width && !frontierCell[y * width + (x + 2)]) {
            cellList.add(new int[]{x + 2, y});
            frontierCell[y * width + (x + 2)] = true;
            messages.add(new Message(grid[y * width + x + 2], CellState.Expanded));
        }
        if (y - 2 > -1 && !frontierCell[(y - 2) * width + x]) {
            cellList.add(new int[]{x, y - 2});
            frontierCell[(y - 2) * width + x] = true;
            messages.add(new Message(grid[(y - 2) * width + x], CellState.Expanded));
        }
        if (y + 2 < height && !frontierCell[(y + 2) * width + x]) {
            cellList.add(new int[]{x, y + 2});
            frontierCell[(y + 2) * width + x] = true;
            messages.add(new Message(grid[(y + 2) * width + x], CellState.Expanded));
        }
        return cellList;
    }

    private List<int[]> getVisitedAdjacentCells(int x, int y) {
        ArrayList<int[]> cellList = new ArrayList<>();
        if (x - 2 > -1 && visited[y * width + (x - 2)]) {
            cellList.add(new int[]{x - 2, y});
        }
        if (x + 2 < width && visited[y * width + x + 2]) {
            cellList.add(new int[]{x + 2, y});
        }
        if (y - 2 > -1 && visited[(y - 2) * width + x]) {
            cellList.add(new int[]{x, y - 2});
        }
        if (y + 2 < height && visited[(y + 2) * width + x]) {
            cellList.add(new int[]{x, y + 2});
        }
        return cellList;
    }
}
