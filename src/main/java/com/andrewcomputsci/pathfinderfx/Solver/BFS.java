package com.andrewcomputsci.pathfinderfx.Solver;

import com.andrewcomputsci.pathfinderfx.Model.CellState;
import com.andrewcomputsci.pathfinderfx.Model.CellType;
import com.andrewcomputsci.pathfinderfx.Model.Message;
import com.andrewcomputsci.pathfinderfx.Model.Statistics;
import com.andrewcomputsci.pathfinderfx.view.CellRectangle;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BFS implements PathFinderSolver {

    @Override
    public Statistics solve(CellRectangle[] grid, int width, int height, ConcurrentLinkedQueue<Message> queue) {
        boolean[] visited = new boolean[grid.length];
        int[] predecessorTable = new int[grid.length];
        Arrays.fill(visited, false);
        Queue<int[]> searchQueue = new ArrayDeque<>(50);
        int passes = 0;
        long deltaTime;
        for (int i = 0; i < grid.length; i++) {
            if (grid[i].getInnerCell().getType().equals(CellType.Source)) {
                predecessorTable[i] = -1;
                searchQueue.add(new int[]{i - (i / width) * width, i / width});
                break;
            }
        }
        deltaTime = System.nanoTime();
        while (!searchQueue.isEmpty()) {
            int[] array = searchQueue.poll();
            int x = array[0];
            int y = array[1];
            if (visited[y * width + x]) continue;
            queue.add(new Message(grid[y * width + x], CellState.Current));
            if ((y - 1) > -1 && !visited[(y - 1) * width + x] && !grid[(y - 1) * width + x].getInnerCell().getType().equals(CellType.Wall)) {
                if (grid[(y - 1) * width + x].getInnerCell().getType().equals(CellType.Target)) {
                    deltaTime = System.nanoTime() - deltaTime;
                    return getStatistics(grid, width, predecessorTable, passes, deltaTime, x, y);
                }
                queue.add(new Message(grid[(y - 1) * width + x], CellState.Expanded));
                predecessorTable[(y - 1) * width + x] = y * width + x;
                searchQueue.add(new int[]{x, y - 1});
            }
            if ((x + 1) < width && !visited[y * width + (x + 1)] && !grid[y * width + (x + 1)].getInnerCell().getType().equals(CellType.Wall)) {
                if (grid[y * width + (x + 1)].getInnerCell().getType().equals(CellType.Target)) {
                    deltaTime = System.nanoTime() - deltaTime;
                    return getStatistics(grid, width, predecessorTable, passes, deltaTime, x, y);
                }
                queue.add(new Message(grid[y * width + (x + 1)], CellState.Expanded));
                searchQueue.add(new int[]{x + 1, y});
                predecessorTable[y * width + (x + 1)] = y * width + x;
            }
            if (((y + 1) < height) && !visited[(y + 1) * width + x] && !grid[(y + 1) * width + x].getInnerCell().getType().equals(CellType.Wall)) {
                if (grid[(y + 1) * width + x].getInnerCell().getType().equals(CellType.Target)) {
                    deltaTime = System.nanoTime() - deltaTime;
                    return getStatistics(grid, width, predecessorTable, passes, deltaTime, x, y);
                }
                queue.add(new Message(grid[(y + 1) * width + x], CellState.Expanded));
                searchQueue.add(new int[]{x, y + 1});
                predecessorTable[(y + 1) * width + x] = y * width + x;
            }
            if ((x - 1) > -1 && !visited[y * width + (x - 1)] && !grid[y * width + (x - 1)].getInnerCell().getType().equals(CellType.Wall)) {
                if (grid[y * width + (x - 1)].getInnerCell().getType().equals(CellType.Target)) {
                    deltaTime = System.nanoTime() - deltaTime;
                    return getStatistics(grid, width, predecessorTable, passes, deltaTime, x, y);
                }
                queue.add(new Message(grid[y * width + (x - 1)], CellState.Expanded));
                searchQueue.add(new int[]{x - 1, y});
                predecessorTable[y * width + (x - 1)] = y * width + x;
            }
            visited[y * width + x] = true;
            queue.add(new Message(grid[y * width + x], CellState.Visited));
            passes++;
        }
        return new Statistics(null, passes, System.nanoTime() - deltaTime, 0);
    }

    protected static Statistics getStatistics(CellRectangle[] grid, int width, int[] predecessorTable, int passes, long deltaTime, int x, int y) {
        long sysTime = System.nanoTime();
        System.out.println("[DEBUG] -- Creating Statistic set");
        List<CellRectangle> path = new ArrayList<>(25);
        path.add(0, grid[y * width + x]);
        int index = y * width + x;
        while (predecessorTable[index] != -1) {
            index = predecessorTable[index];
            path.add(0, grid[index]);
        }
        System.out.println("[DEBUG] -- Finished Creating SET: " + (System.nanoTime() - sysTime) + "ms");
        return new Statistics(path, passes, deltaTime, path.size()+1);
    }


    public List<List<Integer>> solve(int[] grid, int width, int height) {
        for (int i = 0; i < height; i++) {
            System.out.print("[");
            for (int j = 0; j < width; j++) {
                System.out.printf("%d ", grid[i * width + j]);
            }
            System.out.println("]");
        }
        int startWidth = 0;
        int startHeight = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i * width + j] == 1) {
                    startWidth = j;
                    startHeight = i;
                }
            }
        }
        Queue<List<Integer>> queue = new ArrayDeque<>();
        queue.add(Arrays.asList(startHeight, startWidth));
        //if queue becomes empty before path found, not paths exist
        HashMap<List<Integer>, List<Integer>> chain = new HashMap<>();
        chain.put(queue.peek(), null);
        while (!queue.isEmpty()) {
            //add bfs code here
            List<Integer> currList = queue.poll();
            int windex = currList.get(1);
            int hindex = currList.get(0);
            if (hindex - 1 >= 0) {
                if (grid[(hindex - 1) * width + windex] != 3 && !chain.containsKey(Arrays.asList(hindex - 1, windex))) { //check if cell is wall and check if cell been visited before
                    if (grid[(hindex - 1) * width + windex] == 2) {
                        return createFinalPath(chain, currList, Arrays.asList(hindex - 1, windex));
                    }
                    List<Integer> tlist = Arrays.asList(hindex - 1, windex);
                    queue.add(tlist);
                    chain.put(tlist, currList);
                }
            }
            if (windex - 1 >= 0) {
                if (grid[(hindex) * width + windex - 1] != 3 && !chain.containsKey(Arrays.asList(hindex, windex - 1))) { //check if cell is wall and check if cell been visited before
                    if (grid[(hindex) * width + windex - 1] == 2) {
                        return createFinalPath(chain, currList, Arrays.asList(hindex, windex - 1));
                    }
                    List<Integer> tlist = Arrays.asList(hindex, windex - 1);
                    queue.add(tlist);
                    chain.put(tlist, currList);
                }
            }
            if (hindex + 1 < height) {
                if (grid[(hindex + 1) * width + windex] != 3 && !chain.containsKey(Arrays.asList(hindex + 1, windex))) {
                    if (grid[(hindex + 1) * width + windex] == 2) {
                        return createFinalPath(chain, currList, Arrays.asList(hindex + 1, windex));
                    }
                    List<Integer> tlist = Arrays.asList(hindex + 1, windex);
                    queue.add(tlist);
                    chain.put(tlist, currList);
                }
            }
            if (windex + 1 < width) {
                if (grid[(hindex) * width + windex + 1] != 3 && !chain.containsKey(Arrays.asList(hindex, windex + 1))) { //check if cell is wall and check if cell been visited before
                    if (grid[(hindex) * width + windex + 1] == 2) {
                        return createFinalPath(chain, currList, Arrays.asList(hindex, windex + 1));
                    }
                    List<Integer> tlist = Arrays.asList(hindex, windex + 1);
                    queue.add(tlist);
                    chain.put(tlist, currList);
                }
            }
        }
        return null;
    }

    private List<List<Integer>> createFinalPath(HashMap<List<Integer>, List<Integer>> map, List<Integer> prev, List<Integer> target) {
        ArrayList<List<Integer>> path = new ArrayList<>();
        path.add(target);
        path.add(0, prev);
        List<Integer> keys = prev;
        while (map.containsKey(keys) && map.get(keys) != null) {
            path.add(0, map.get(keys));
            keys = path.get(0);
        }
        return path;
    }
}
