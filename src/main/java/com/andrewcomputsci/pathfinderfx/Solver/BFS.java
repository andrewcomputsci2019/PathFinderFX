package com.andrewcomputsci.pathfinderfx.Solver;

import com.andrewcomputsci.pathfinderfx.Model.Message;
import com.andrewcomputsci.pathfinderfx.Model.Statistics;
import com.andrewcomputsci.pathfinderfx.view.CellRectangle;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BFS implements PathFinderSolver{

    @Override
    public Statistics solve(CellRectangle[] grid, int width, int height, ConcurrentLinkedQueue<Message> queue) {
        throw new UnsupportedOperationException("Not implemented yet");
    }


    public List<List<Integer>> solve(int[] grid, int width, int height) {
        for(int i = 0; i < height; i++){
            System.out.print("[");
            for(int j = 0; j < width; j++){
                System.out.printf("%d ",grid[i*width+j]);
            }
            System.out.println("]");
        }
        int startWidth = 0;
        int startHeight = 0;
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(grid[i*width+j] == 1){
                    startWidth = j;
                    startHeight = i;
                }
            }
        }
        Queue<List<Integer>> queue = new ArrayDeque<>();
        queue.add(Arrays.asList(startHeight,startWidth));
        //if queue becomes empty before path found, not paths exist
        HashMap<List<Integer>,List<Integer>> chain = new HashMap<>();
        chain.put(queue.peek(),null);
        while (!queue.isEmpty()){
            //add bfs code here
            List<Integer> currList = queue.poll();
            int windex = currList.get(1);
            int hindex = currList.get(0);
            if(hindex-1 >= 0){
                if(grid[(hindex-1)*width+windex] != 3 && !chain.containsKey(Arrays.asList(hindex-1,windex))){ //check if cell is wall and check if cell been visited before
                    if(grid[(hindex-1)*width+windex] == 2){
                        return createFinalPath(chain, currList,Arrays.asList(hindex-1,windex));
                    }
                    List<Integer> tlist = Arrays.asList(hindex-1,windex);
                    queue.add(tlist);
                    chain.put(tlist,currList);
                }
            }
            if(windex-1 >= 0){
                if(grid[(hindex)*width+windex-1] != 3 && !chain.containsKey(Arrays.asList(hindex,windex-1))){ //check if cell is wall and check if cell been visited before
                    if(grid[(hindex)*width+windex-1] == 2){
                        return createFinalPath(chain,currList,Arrays.asList(hindex,windex-1));
                    }
                    List<Integer> tlist = Arrays.asList(hindex,windex-1);
                    queue.add(tlist);
                    chain.put(tlist,currList);
                }
            }
            if(hindex+1 < height){
                if(grid[(hindex+1)*width+windex] != 3 && !chain.containsKey(Arrays.asList(hindex+1,windex))){
                    if(grid[(hindex+1)*width+windex] == 2){
                        return createFinalPath(chain,currList,Arrays.asList(hindex+1,windex));
                    }
                    List<Integer> tlist = Arrays.asList(hindex+1,windex);
                    queue.add(tlist);
                    chain.put(tlist,currList);
                }
            }
            if(windex+1 < width){
                if(grid[(hindex)*width+windex+1] != 3 && !chain.containsKey(Arrays.asList(hindex,windex+1))){ //check if cell is wall and check if cell been visited before
                    if(grid[(hindex)*width+windex+1] == 2){
                        return createFinalPath(chain,currList,Arrays.asList(hindex,windex+1));
                    }
                    List<Integer> tlist = Arrays.asList(hindex,windex+1);
                    queue.add(tlist);
                    chain.put(tlist,currList);
                }
            }
        }
        return null;
    }
    private List<List<Integer>> createFinalPath(HashMap<List<Integer>,List<Integer>> map, List<Integer> prev, List<Integer> target){
        ArrayList<List<Integer>> path = new ArrayList<>();
        path.add(target);
        path.add(0,prev);
        List<Integer> keys = prev;
        while (map.containsKey(keys) && map.get(keys)!=null){
            path.add(0,map.get(keys));
            keys = path.get(0);
        }
        return path;
    }
}
