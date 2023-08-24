package com.andrewcomputsci.pathfinderfx.Solver;

import com.andrewcomputsci.pathfinderfx.Model.*;
import com.andrewcomputsci.pathfinderfx.view.CellRectangle;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Dijkstra implements PathFinderSolver{
    @Override
    public Statistics solve(CellRectangle[] grid, int width, int height, ConcurrentLinkedQueue<Message> queue) {
        int passes = 0;
        long deltaTime;
        boolean[] visited = new boolean[grid.length];
        double[] occurredCost = new double[grid.length];
        int[] predecessorTable = new int[grid.length];
        PriorityQueue<int[]> searchQueue = new PriorityQueue<>(Comparator.comparing(ints -> {
            return occurredCost[ints[0]+ints[1]*width];
        }));
        Arrays.fill(visited,false);

        for(int i = 0; i < grid.length; i++){
            if(grid[i].getInnerCell().typeProperty().get().equals(CellType.Source)){
                occurredCost[i] = grid[i].getInnerCell().weightProperty().get();
                int y = i/width;
                int x = i-y*width;
                searchQueue.add(new int[]{x,y,width-1,-1});
                break;
            }
        }
        deltaTime = System.nanoTime();
        while (!searchQueue.isEmpty()){
            int x = searchQueue.peek()[0];
            int y = searchQueue.peek()[1];
            int predX = searchQueue.peek()[2];
            int predY = searchQueue.poll()[3];
            if(visited[y*width+x])continue;
            visited[y*width+x] = true;
            predecessorTable[y*width+x] = predY*width+predX;
            queue.add(new Message(grid[y*width+x],CellState.Current));
            if((y-1) > -1 && ! visited[(y-1)*width+x] && !grid[(y-1)*width+x].getInnerCell().typeProperty().get().equals(CellType.Wall)){
                if(grid[(y-1)*width+x].getInnerCell().typeProperty().get().equals(CellType.Target)){
                    deltaTime = System.nanoTime() - deltaTime;
                    return getStatistics(grid,width,predecessorTable,passes,deltaTime,x,y,occurredCost[y*width+x]+grid[(y-1)*width+x].getInnerCell().weightProperty().get());
                }

                queue.add(new Message(grid[(y-1)*width+x], CellState.Expanded));
                occurredCost[(y-1)*width+x] = occurredCost[y*width+x]+grid[(y-1)*width+x].getInnerCell().weightProperty().get();
                searchQueue.add(new int[]{x,(y-1),x,y});
            }
            if((x+1) < width && !visited[y*width+(x+1)] && !grid[y*width+(x+1)].getInnerCell().typeProperty().get().equals(CellType.Wall)){
                if(grid[y*width+(x+1)].getInnerCell().typeProperty().get().equals(CellType.Target)){
                    deltaTime = System.nanoTime()-deltaTime;
                    return getStatistics(grid,width,predecessorTable,passes,deltaTime,x,y,occurredCost[y*width+x]+grid[y*width+(x+1)].getInnerCell().weightProperty().get());
                }

                queue.add(new Message(grid[y*width+(x+1)],CellState.Expanded));
                occurredCost[y*width+(x+1)] = occurredCost[y*width+x]+grid[y*width+(x+1)].getInnerCell().weightProperty().get();
                searchQueue.add(new int[]{x+1,y,x,y});
            }
            if((y+1) < height &&  !visited[(y+1)*width+x] && !grid[(y+1)*width+x].getInnerCell().typeProperty().get().equals(CellType.Wall)){
                if(grid[(y+1)*width+x].getInnerCell().typeProperty().get().equals(CellType.Target)){
                    deltaTime = System.nanoTime() - deltaTime;
                    return getStatistics(grid,width,predecessorTable,passes,deltaTime,x,y,occurredCost[y*width+x]+grid[(y+1)*width+x].getInnerCell().weightProperty().get());
                }
                queue.add(new Message(grid[(y+1)*width+x], CellState.Expanded));
                occurredCost[(y+1)*width+x] = occurredCost[y*width+x] + grid[(y+1)*width+x].getInnerCell().weightProperty().get();
                searchQueue.add(new int[]{x,y+1,x,y});
            }
            if((x-1) > -1 && !visited[y*width+(x-1)] && !grid[y*width+(x-1)].getInnerCell().typeProperty().get().equals(CellType.Wall)){
                if(grid[y*width+(x-1)].getInnerCell().typeProperty().get().equals(CellType.Target)){
                    deltaTime = System.nanoTime() - deltaTime;
                    return getStatistics(grid,width,predecessorTable,passes,deltaTime,x,y,occurredCost[y*width+x]+grid[y*width+(x-1)].getInnerCell().weightProperty().get());
                }
                queue.add(new Message(grid[y*width + (x-1)],CellState.Expanded));
                occurredCost[y*width+(x-1)] = occurredCost[y*width+x] + grid[y*width+(x-1)].getInnerCell().weightProperty().get();
                searchQueue.add(new int[]{(x-1),y,x,y});
            }
            queue.add(new Message(grid[y*width+x],CellState.Visited));
            passes++;
        }
        return new Statistics(null,passes,System.nanoTime()-deltaTime,0);
    }


    private static Statistics getStatistics(CellRectangle[] grid, int width, int[] predecessorTable, int passes, long deltaTime, int x, int y,double cost){
        System.out.println("[DEBUG] -- getting stat");
        List<CellRectangle> path = new ArrayList<>();
        int index = y*width+x;
        path.add(0,grid[index]);
        while (predecessorTable[index]!=-1){
            index = predecessorTable[index];
            path.add(0,grid[index]);
        }
        return new Statistics(path,passes,deltaTime,cost);
    }

}
