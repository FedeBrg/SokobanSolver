package implementations;

import interfaces.Board;
import interfaces.SearchMethod;
import interfaces.Sokoban;

import java.util.HashMap;

public class IDDFS implements SearchMethod {

    private HashMap<String, Integer> specialVisited = new HashMap<>();
    private Sokoban sokoban;

    public IDDFS(Sokoban sokoban) {
        this.sokoban = sokoban;
    }

    @Override
    public Board findPath(Board b, Sokoban s){
        int [][] directions = {{1,0},{0,1},{-1,0},{0,-1}};
        int i = 0;
        Board toReturn = null;

        while(toReturn == null){
            if(!s.isOptimized()){
                specialVisited.clear();
            }
            toReturn = findPathWrapper(b, s, directions, s.getDepth()+(i*s.getIncrement()),0);
            i++;
        }

        return toReturn;
    }

    private Board findPathWrapper(Board b, Sokoban s, int[][] directions, int depth, int frontier){
        Board resultBoard = null;
        Board nextStep = null;

        if(Utilities.isSolution(b)){
            System.out.print("Solution found!\n");
            System.out.printf("Solution cost: %d\n",(b.getSolution().length()/b.getBoardSizex()/b.getBoardSizey())-1);
            System.out.printf("Expanded nodes: %d\n",specialVisited.size()+1);
            System.out.printf("Nodes in frontier: %d\n",frontier);
            System.out.printf("Algorithm optimized: %b\n", s.isOptimized());
            return b;
        }

        else if(specialVisited.containsKey(b.getBoard()) && specialVisited.get(b.getBoard()) >= depth){
            return null;
        }

        else if(depth == 0){
            return null;
        }

        else{
            specialVisited.put(b.getBoard(), depth);

            for (int[] direction : directions) {
                if ((resultBoard = s.move(b, direction[0], direction[1])) != null) {
                    nextStep = findPathWrapper(resultBoard, s, directions, depth - 1,frontier);
                    if(nextStep != null){
                        return nextStep;
                    }
                    else{
                        frontier++;
                    }
                }
            }
        }

        return nextStep;
    }
    @Override
    public String toString() {
        return String.format("Search method used: IDDFS\nInitial depth:%d\nIncrement: %d",sokoban.getInitialDepth(),sokoban.getIncrement());
    }
}
