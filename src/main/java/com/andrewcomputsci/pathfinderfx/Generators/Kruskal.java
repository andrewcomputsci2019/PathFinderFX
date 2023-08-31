package com.andrewcomputsci.pathfinderfx.Generators;

import com.andrewcomputsci.pathfinderfx.Model.CellType;
import com.andrewcomputsci.pathfinderfx.Model.Message;
import com.andrewcomputsci.pathfinderfx.view.CellRectangle;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Kruskal implements MazeGenerator {
    private int width;
    private int height;
    private CellRectangle[] grid;
    private ConcurrentLinkedQueue<Message> messages;

    @Override
    @SuppressWarnings("unchecked")
    public void generateMaze(CellRectangle[] grid, int width, int height, ConcurrentLinkedQueue<Message> messageQueue) {
        this.grid = grid;
        this.height = height;
        this.width = width;
        this.messages = messageQueue;
        Random random = new Random();
        Set<Integer>[] setTable = new Set[grid.length];
        //each cell gets added to a set and we can track
        ArrayList<int[]> edgeList = new ArrayList<>();
        System.out.println("[DEBUG] -- started Kruskal algorithm");
        for (int y = 0; y < height; y++) {
            if (y % 2 != 0) continue;
            for (int x = 0; x < width; x++) {
                HashSet<Integer> set = new HashSet<>();
                set.add(y * width + x);
                setTable[y * width + x] = set;
                if (x % 2 != 0) continue;
                edgeList.addAll(getEdgesFromCell(x, y));
            }
        }
        while (edgeList.size() != 0) {
            int[] randomEdge = edgeList.remove(random.nextInt(0, edgeList.size()));
            if (setTable[(randomEdge[0] + randomEdge[1] * width)].contains(randomEdge[2] + randomEdge[3] * width)) {
                continue;
            }
            ;
            Set<Integer> sharedSet1 = setTable[(randomEdge[0] + randomEdge[1] * width)];
            Set<Integer> sharedSet2 = setTable[(randomEdge[2] + randomEdge[3] * width)];
            sharedSet1.add(joinCells(randomEdge[0], randomEdge[1], randomEdge[2], randomEdge[3]));
            sharedSet1.addAll(sharedSet2);
            sharedSet2.forEach(item -> setTable[item] = sharedSet1);
            messages.add(new Message(grid[randomEdge[1] * width + randomEdge[0]], CellType.Traversable));
            messages.add(new Message(grid[randomEdge[3] * width + randomEdge[2]], CellType.Traversable));
        }
        System.out.println("[DEBUG] -- ended Kruskal algorithm");
    }

    private Collection<int[]> getEdgesFromCell(int x, int y) {
        ArrayList<int[]> edgeList = new ArrayList<>();
        if (x + 2 < width) {
            edgeList.add(new int[]{x, y, x + 2, y});
        }
        if (y + 2 < height) {
            edgeList.add(new int[]{x, y, x, y + 2});
        }
        return edgeList;
    }

    private int joinCells(int x1, int y1, int x2, int y2) {
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
        return y * width + x;
    }
}
