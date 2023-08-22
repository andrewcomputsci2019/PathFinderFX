package com.andrewcomputsci.pathfinderfx.Solver;

import com.andrewcomputsci.pathfinderfx.Model.CellState;
import com.andrewcomputsci.pathfinderfx.Model.CellType;
import com.andrewcomputsci.pathfinderfx.Model.Message;
import com.andrewcomputsci.pathfinderfx.Model.Statistics;
import com.andrewcomputsci.pathfinderfx.view.CellRectangle;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;

public class BFSGreedy implements PathFinderSolver{
    @Override
    public Statistics solve(CellRectangle[] grid, int width, int height, ConcurrentLinkedQueue<Message> queue) {
        //3 length array 0 = x 1 = y 2 = distance to target
        int targetX = 0;
        int targetY = 0;
        int passes = 0;
        long deltaTime;
        PriorityQueue<int[]> searchQueue = new PriorityQueue<>(Comparator.comparing(ints -> ints[2]));
        int[] predecessorTable = new int[grid.length];
        boolean[] visited = new boolean[grid.length];
        Arrays.fill(visited,false);
        int startX = 0;
        int startY = 0;
        for(int i = 0; i < grid.length; i++){
            if(grid[i].getInnerCell().typeProperty().get().equals(CellType.Source)){
                int y = i/width;
                int x = i%width;
                predecessorTable[i] = -1;
                visited[i] = true;
                startX = i%width;
                startY = i/width;
            }
            if(grid[i].getInnerCell().typeProperty().get().equals(CellType.Target)){
                int y = i/width;
                int x = i%width;
                targetX = x;
                targetY = y;
            }
        }
        searchQueue.add(new int[]{startX,startY,getDistance(startX,startY,targetX,targetY)});
        deltaTime = System.nanoTime();
        while (!searchQueue.isEmpty()){
            int x = searchQueue.peek()[0];
            int y = searchQueue.poll()[1];
            queue.add(new Message(grid[y*width+x], CellState.Current));
            if((y-1) > -1 && !visited[(y-1)*width+x] && !grid[(y-1)*width+x].getInnerCell().typeProperty().get().equals(CellType.Wall)){
                if (grid[(y - 1) * width + x].getInnerCell().getType().equals(CellType.Target)) {
                    deltaTime = System.nanoTime() - deltaTime;
                    return BFS.getStatistics(grid, width, predecessorTable, passes, deltaTime, x, y);
                }
                queue.add(new Message(grid[(y - 1) * width + x],CellState.Expanded));
                searchQueue.add(new int[]{x,y-1,getDistance(x,y-1,targetX,targetY)});
                visited[(y - 1) * width + x] = true;
                predecessorTable[(y - 1) * width + x] = y*width+x;
            }
            if((x+1) < width && !visited[y*width+(x+1)] && !grid[y*width + (x+1)].getInnerCell().typeProperty().get().equals(CellType.Wall)){
                if (grid[y * width + (x + 1)].getInnerCell().getType().equals(CellType.Target)) {
                    deltaTime = System.nanoTime() - deltaTime;
                    return BFS.getStatistics(grid, width, predecessorTable, passes, deltaTime, x, y);
                }
                queue.add(new Message(grid[y * width + (x + 1)], CellState.Expanded));
                searchQueue.add(new int[]{x+1,y,getDistance(x+1,y,targetX,targetY)});
                predecessorTable[y * width + (x + 1)] = y * width + x;
                visited[y * width + (x + 1)] = true;
            }
            if((y+1) < height && !visited[(y+1)*width+x] && !grid[(y+1)*width+x].getInnerCell().typeProperty().get().equals(CellType.Wall)){
                if (grid[(y + 1) * width + x].getInnerCell().getType().equals(CellType.Target)) {
                    deltaTime = System.nanoTime() - deltaTime;
                    return BFS.getStatistics(grid, width, predecessorTable, passes, deltaTime, x, y);
                }
                queue.add(new Message(grid[(y + 1) * width + x], CellState.Expanded));
                searchQueue.add(new int[]{x,y+1,getDistance(x,y+1,targetX,targetY)});
                predecessorTable[(y + 1) * width + x] = y * width + x;
                visited[(y + 1) * width + x] = true;
            }
            if((x-1) > -1 && !visited[y*width+(x-1)] && !grid[y*width+(x-1)].getInnerCell().typeProperty().get().equals(CellType.Wall)){
                if (grid[y * width + (x - 1)].getInnerCell().getType().equals(CellType.Target)) {
                    deltaTime = System.nanoTime() - deltaTime;
                    return BFS.getStatistics(grid, width, predecessorTable, passes, deltaTime, x, y);
                }
                queue.add(new Message(grid[y * width + (x - 1)], CellState.Expanded));
                searchQueue.add(new int[]{x-1,y,getDistance(x-1,y,targetX,targetY)});
                predecessorTable[y * width + (x - 1)] = y * width + x;
                visited[y * width + (x - 1)] = true;
            }
            queue.add(new Message(grid[y*width+x],CellState.Visited));
            passes++;
        }
        return new Statistics(null,passes,deltaTime,0);
    }


    public static int getDistance(int x1, int y1, int x2, int y2){
        return Math.abs(x1-x2) + Math.abs(y1-y2);
    }
}
