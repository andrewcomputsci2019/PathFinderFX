package com.andrewcomputsci.pathfinderfx.Utils;

import com.andrewcomputsci.pathfinderfx.Model.Heuristic;

public class Heuristics {

    @FunctionalInterface
    public interface Functor {
       double compute(int x1, int y1, int x2, int y2);
    }

    public static Functor Manhattan(){
        //sum sides of a right triangle
        return (x1, y1, x2, y2) -> Math.abs(x1-x2)+Math.abs(y1-y2);
    }
    public static Functor Euclidean(){
        //vector magnitude
        return ((x1, y1, x2, y2) -> Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2)));
    }

    public static Functor Chebyshev(){
        return (x1, y1, x2, y2) -> Math.max(Math.abs(x1-x2),Math.abs(y1-y2));
    }

    public static Functor getFunctor(Heuristic heuristic){
        switch (heuristic){
            case Manhattan -> {
                return Manhattan();
            }
            case Chebyshev -> {
                return Chebyshev();
            }
            case Euclidean -> {
                return Euclidean();
            }
            default -> {
                return null;
            }
        }
    }
}
