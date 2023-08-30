package com.andrewcomputsci.pathfinderfx.Generators;

import com.andrewcomputsci.pathfinderfx.Model.CellState;
import com.andrewcomputsci.pathfinderfx.Model.CellType;
import com.andrewcomputsci.pathfinderfx.Model.Message;
import com.andrewcomputsci.pathfinderfx.Utils.MazeUtils;
import com.andrewcomputsci.pathfinderfx.view.CellRectangle;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BinaryTree implements MazeGenerator{
    CellRectangle[] grid;
    private int width;
    private int height;
    private Random random;
    private ConcurrentLinkedQueue<Message> messages;

    @Override
    public void generateMaze(CellRectangle[] grid, int width, int height, ConcurrentLinkedQueue<Message> messageQueue) {
        messages = messageQueue;
        random = new Random();
        this.grid = grid;
        this.width = width;
        this.height = height;
        System.out.println("[DEBUG] -- started binary tree maze generation");
        for(int y = 0; y < height; y++){
            if(y%2 !=0)continue;
            for(int x=0; x < width; x++){
                if(x%2!=0)continue;
                messageQueue.add(new Message(grid[y*width+x], CellState.Current));
                 createMaze(x, y);
                messageQueue.add(new Message(grid[y*width+x], CellType.Traversable));
            }
        }
        System.out.println("[DEBUG] -- ended binary tree maze generation");
    }

    private void createMaze(int x, int y){
        //calls getAdjacentCells randomly chooses between the values and creates the maze
        ArrayList<int[]> list = new ArrayList<>(getAdjacentCells(x, y));
        if(list.isEmpty()){
            return;
        }
        int[] randomNeighbor = list.get(random.nextInt(0, list.size()));
        joinCells(randomNeighbor[0],randomNeighbor[1],x,y);
    }

    private Collection<int[]> getAdjacentCells(int x, int y){
        //return cells that are even around the cell
        ArrayList<int[]> cellRectangles = new ArrayList<>();
            if(x-2 > -1){
                cellRectangles.add(new int[]{x-2,y});
            }
            if(y-2 > -1){
                cellRectangles.add(new int[]{x,y-2});
            }
            return cellRectangles;
    }

    private void joinCells(int x1, int y1, int x2, int y2){
        //connect two cells by setting cell between them as traversable
        int x = x2>x1?x2-1:x2;
        int y = y2>y1?y2-1:y2;
        messages.add(new Message(grid[y*width+x],CellType.Traversable));
    }
}
