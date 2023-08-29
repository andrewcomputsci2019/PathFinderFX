package com.andrewcomputsci.pathfinderfx.Generators;

import com.andrewcomputsci.pathfinderfx.Model.CellState;
import com.andrewcomputsci.pathfinderfx.Model.CellType;
import com.andrewcomputsci.pathfinderfx.Model.Message;
import com.andrewcomputsci.pathfinderfx.view.CellRectangle;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DFSRandom implements MazeGenerator{
    ConcurrentLinkedQueue<Message> messages;
    boolean[] visited;
    int width;
    int height;
    CellRectangle[] grid;

    @Override
    public void generateMaze(CellRectangle[] grid, int width, int height, ConcurrentLinkedQueue<Message> messageQueue) {

        System.out.println("[DEBUG] -- started DFS maze gen");
        this.grid = grid;
        this.width = width;
        this.height = height;
        this.messages = messageQueue;
        ArrayList<int[]> cellStack = new ArrayList<>();
        cellStack.add(new int[]{0,0});
        visited = new boolean[grid.length];
        Arrays.fill(visited,false);
        while (!cellStack.isEmpty()){
            int[] cellLoc = cellStack.get(0); //take off the top
            messages.add(new Message(grid[cellLoc[1]*width+cellLoc[0]], CellState.Current));
            List<int[]> neighbors = getCellNeighbors(cellLoc[0],cellLoc[1]);
            if(neighbors.isEmpty()){
                cellStack.remove(0);
                messages.add(new Message(grid[cellLoc[1]*width+cellLoc[0]],CellType.Traversable));
                continue;
            }
            Collections.shuffle(neighbors);
            neighbors.forEach(item -> joinCells(cellLoc[0],cellLoc[1],item[0],item[1]));
            neighbors.forEach(item -> cellStack.add(0,item));
            messages.add(new Message(grid[cellLoc[1]*width+cellLoc[0]],CellState.Visited));
        }
        System.out.println("[DEBUG] -- end DFS maze Gen");
    }


    private List<int[]> getCellNeighbors(int x, int y){
        //returns valid neighbors of north, east, south, west, and sets them as visited
        ArrayList<int[]> list = new ArrayList<>();
        if(x-2 > -1 && !visited[y*width+(x-2)] ){
            list.add(new int[]{x-2,y});
            visited[y*width+(x-2)] = true;
        }
        if(x+2 < width && !visited[y*width+(x+2)]){
            list.add(new int[]{x+2,y});
            visited[y*width+(x+2)] = true;
        }
        if(y-2 > -1 && !visited[(y-2)*width+x]){
            list.add(new int[]{x,y-2});
            visited[(y-2)*width+x] = true;
        }
        if(y+2 < height && !visited[(y+2)*width+x]){
            list.add(new int[]{x,y+2});
            visited[(y+2)*width+x] = true;
        }
        return list;
    }

    private void joinCells(int x1, int y1, int x2, int y2){
        int x = x2;
        int y = y2;
        if(x2 > x1){
            x-=1;
        }else if(x1 > x2){
            x+=1;
        }
        if(y2 > y1){
            y-=1;
        }else if(y1 > y2){
            y+=1;
        }
        messages.add(new Message(grid[y*width+x],CellType.Traversable));
    }
}
