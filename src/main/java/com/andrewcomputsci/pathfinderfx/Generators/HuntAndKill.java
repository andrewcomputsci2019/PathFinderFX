package com.andrewcomputsci.pathfinderfx.Generators;

import com.andrewcomputsci.pathfinderfx.Model.CellState;
import com.andrewcomputsci.pathfinderfx.Model.CellType;
import com.andrewcomputsci.pathfinderfx.Model.Message;
import com.andrewcomputsci.pathfinderfx.view.CellRectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class HuntAndKill implements MazeGenerator{
    private boolean[] visited;
    private int width;
    private int height;
    private ConcurrentLinkedQueue<Message> messages;
    private final Random random = new Random();
    private CellRectangle[] grid;

    @Override
    public void generateMaze(CellRectangle[] grid, int width, int height, ConcurrentLinkedQueue<Message> messageQueue) {
        this.width = width;
        this.height = height;
        this.grid = grid;
        messages = messageQueue;
        visited = new boolean[grid.length];
        Arrays.fill(visited,false);
        walk(random.nextInt(0,width), random.nextInt(0,height));
        while (true){
            boolean found = false;
            for(int y = 0; y < height; y++){
                if(y%2!=0)continue;
                if(found)break;
                for(int x = 0; x < width; x++){
                    if(x%2!=0)continue;
                    if(!visited[y*width+x] && hasVisitedAdjacentCell(x,y)){
                        found = true;
                        List<int[]> visitedCells = visitedCells(x,y);
                        int[] loc = visitedCells.get(random.nextInt(0, visitedCells.size()));
                        joinCells(x,y,loc[0],loc[1]);
                        walk(x,y);
                        break;
                    }
                }
            }
            if(!found){
                break;
            }
        }
    }

    private void walk(int x, int y){
        List<int[]> cells;
        visited[y*width+x] = true;
        while (!(cells = unvisitedAdjacentCells(x,y)).isEmpty()){
            visited[y*width+x] = true;
            messages.add(new Message(grid[y*width+x], CellState.Current));
            int[] next = cells.get(random.nextInt(0, cells.size()));
            joinCells(x,y,next[0],next[1]);
            messages.add(new Message(grid[y*width+x],CellType.Traversable));
            x = next[0];
            y = next[1];
        }
    }

    private boolean hasVisitedAdjacentCell(int x, int y){

       return (((x-2)>-1)&&visited[y*width+(x-2)]) || ((x+2 < width) && visited[y*width+x+2]) || ((y-2 > -1) && visited[(y-2)*width+x]) || ((y+2 < height) && visited[(y+2)*width+x]);
    }

    private List<int[]> unvisitedAdjacentCells(int x, int y){
        ArrayList<int[]> list = new ArrayList<>();
        if(x-2 > -1 && ! visited[y*width+x-2]){
            list.add(new int[]{x-2,y});
        }
        if(x+2 < width && ! visited[y*width+x+2]){
            list.add(new int[]{x+2,y});
        }
        if(y-2 > -1 && ! visited[(y-2)*width+x]){
            list.add(new int[]{x,y-2});
        }
        if(y+2 < width && ! visited[(y+2)*width+x]){
            list.add(new int[]{x,y+2});
        }
        return list;
    }
    private List<int[]> visitedCells(int x, int y){
        ArrayList<int[]> list = new ArrayList<>();
        if(x-2 > -1 &&  visited[y*width+x-2]){
            list.add(new int[]{x-2,y});
        }
        if(x+2 < width && visited[y*width+x+2]){
            list.add(new int[]{x+2,y});
        }
        if(y-2 > -1 &&  visited[(y-2)*width+x]){
            list.add(new int[]{x,y-2});
        }
        if(y+2 < width &&  visited[(y+2)*width+x]){
            list.add(new int[]{x,y+2});
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
        visited[y*width+x] = true;
        messages.add(new Message(grid[y*width+x], CellType.Traversable));
    }
}
