package com.andrewcomputsci.pathfinderfx.Solver;

import com.andrewcomputsci.pathfinderfx.Model.CellState;
import com.andrewcomputsci.pathfinderfx.Model.CellType;
import com.andrewcomputsci.pathfinderfx.Model.Message;
import com.andrewcomputsci.pathfinderfx.Model.Statistics;
import com.andrewcomputsci.pathfinderfx.view.CellRectangle;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DFS implements PathFinderSolver {

    private List<CellRectangle> listAns;
    private int min;
    private int exactPasses;

    boolean exactMode;


    public DFS(boolean exact){
        exactMode = exact;
    }
    @Override
    public Statistics solve(CellRectangle[] grid, int width, int height, ConcurrentLinkedQueue<Message> queue) {
        if(exactMode){
            return exactSolve(grid, width, height, queue);
        }
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
            }
        }
        long timeInit = System.nanoTime();
        while (!cellStack.empty()) {
            int x = cellStack.peek()[0];
            int y = cellStack.pop()[1];
            if(visited[y*width+x]){
                continue;
            }
            queue.add(new Message(grid[y * width + x], CellState.Current));
            if (x - 1 > -1 && !visited[y * width + (x - 1)] && !grid[y * width + (x - 1)].getInnerCell().getType().equals(CellType.Wall)) {
                if (grid[y * width + (x - 1)].getInnerCell().getType().equals(CellType.Target)) {
                    return getStatistics(grid, width, passes, predecessorTable, timeInit, x, y);
                }
                queue.add(new Message(grid[y * width + (x - 1)], CellState.Expanded));
                cellStack.add(new int[]{x - 1, y});
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
                predecessorTable[y * width + (x + 1)] = y * width + x;
            }
            if (y - 1 > -1 && !visited[(y - 1) * width + x] && !grid[(y - 1) * width + x].getInnerCell().getType().equals(CellType.Wall)) {
                if (grid[(y - 1) * width + x].getInnerCell().getType().equals(CellType.Target)) {
                    return getStatistics(grid, width, passes, predecessorTable, timeInit, x, y);
                }
                queue.add(new Message(grid[(y - 1) * width + x], CellState.Expanded));
                cellStack.add(new int[]{x, y - 1});
                predecessorTable[(y - 1) * width + x] = y * width + x;
            }
            visited[y * width + x] = true;
            queue.add(new Message(grid[y * width + x], CellState.Visited));
            passes++;
        }
        return new Statistics(null, passes, System.nanoTime() - timeInit, 0);
    }

    public Statistics exactSolve(CellRectangle[] grid, int width, int height, ConcurrentLinkedQueue<Message> queue){
        //branch and bound technique, we can eliminate certain paths after finding at least one path, as we can look at the cost of the traveled path
        //take the min of the four potential
        listAns = null;
        min = -1;
        exactPasses = 0;
        boolean[] vistied = new boolean[grid.length];
        Arrays.fill(vistied,false);
        int startX = 0;
        int startY = 0;
        for(int i = 0; i < grid.length; i++){
            if(grid[i].getInnerCell().typeProperty().get().equals(CellType.Source)){
                startY = i/width;
                startX = i - startY*width;
                break;
            }
        }
        long deltaTime = System.nanoTime();
        dfs(grid,vistied,width,height,queue,new ArrayList<>(),startX,startY);
        deltaTime = System.nanoTime()-deltaTime;
        return new Statistics(listAns,exactPasses,deltaTime,listAns.size());
    }
    private void dfs(CellRectangle[] grid,boolean[] visited, int width, int height, ConcurrentLinkedQueue<Message> queue, List<CellRectangle> ans, int x, int y){
        if(min!=-1 && ans.size() >= min){
            return; //no point in searching as this answer will not a be a min
        }
        System.out.println("Running with: x: " + x + " y: " + y);
        ans.add(grid[y*width+x]);
        queue.add(new Message(grid[y*width+x],CellState.Current));
        visited[y*width+x] = true;
        if( (y-1) > -1 && !visited[(y-1)*width+x]&&!grid[(y-1)*width+x].getInnerCell().typeProperty().get().equals(CellType.Wall)){

            if(grid[(y-1)*width+x].getInnerCell().typeProperty().get().equals(CellType.Target)){
                cleanUp(grid, visited, width, queue, ans, x, y);
            }else {
                queue.add(new Message(grid[(y-1)*width+x],CellState.Expanded));
                dfs(grid, visited, width, height, queue, ans, x, y - 1);
            }
        }
        if((x+1) < width && !visited[y*width+(x+1)] && !grid[y*width+(x+1)].getInnerCell().typeProperty().get().equals(CellType.Wall)){
            if(grid[y*width+(x+1)].getInnerCell().typeProperty().get().equals(CellType.Target)){
                cleanUp(grid, visited, width, queue, ans, x, y);
                return;
            }else {
                queue.add(new Message(grid[y*width+(x+1)],CellState.Expanded));
                dfs(grid, visited, width, height, queue, ans, x+1, y);
            }
        }
        if((y+1)<height && !visited[(y+1)*width+x] && !grid[(y+1)*width+x].getInnerCell().typeProperty().get().equals(CellType.Wall)){
            if(grid[(y+1)*width+x].getInnerCell().typeProperty().get().equals(CellType.Target)){
                cleanUp(grid, visited, width, queue, ans, x, y);
                return;
            }else{
                queue.add(new Message(grid[(y+1)*width+x],CellState.Expanded));
                dfs(grid,visited,width,height,queue,ans,x,y+1);
            }
        }
        if((x-1) > -1 && !visited[y*width+(x-1)] && !grid[y*width+(x-1)].getInnerCell().typeProperty().get().equals(CellType.Wall)){
            if(grid[y*width+(x-1)].getInnerCell().typeProperty().get().equals(CellType.Target)){
                cleanUp(grid, visited, width, queue, ans, x, y);
                return;
            }else {
                queue.add(new Message(grid[y*width+(x-1)],CellState.Expanded));
                dfs(grid, visited, width, height, queue, ans, x-1, y);
            }
        }
        visited[y*width+x] = false;
        ans.remove(ans.size()-1);
        queue.add(new Message(grid[y*width+x],CellState.Unvisited));
        exactPasses++;
    }

    private void cleanUp(CellRectangle[] grid, boolean[] visited, int width, ConcurrentLinkedQueue<Message> queue, List<CellRectangle> ans, int x, int y) {
        min = min == -1 ? ans.size() : Math.min(min,ans.size());
        listAns = min==ans.size() ? new ArrayList<>(ans) : listAns;
        visited[(y*width+x)] = false;
        ans.remove(ans.size()-1);
        queue.add(new Message(grid[y*width+x], CellState.Unvisited));
        exactPasses++;
    }

    private Statistics getStatistics(CellRectangle[] grid, int width, int passes, int[] predecessorTable, long timeInit, int x, int y) {
        List<CellRectangle> path = new ArrayList<>();
        int index = y * width + x;
        path.add(0, grid[index]);
        while (predecessorTable[index] != -1) {
            index = predecessorTable[index];
            path.add(0, grid[index]);

        }
        return new Statistics(path, passes, System.nanoTime()-timeInit, path.size());
    }
}
