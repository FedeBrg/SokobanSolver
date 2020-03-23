package Implementations;

import Interfaces.Board;
import Interfaces.SearchMethod;
import Interfaces.Sokoban;

import java.util.HashMap;

public class DFS implements SearchMethod {
    static HashMap<String, Board> visited = new HashMap<>();

    @Override
    public Board findPath(Board b, Sokoban s) {
        Board resultBoard = null;
        Board nextStep = null;

        int [][] directions = {{1,0},{0,1},{-1,0},{0,-1}};

        if(Utilities.isSolution(b)){
            System.out.println("SOLUCION!!!!\n");
            return b;
        }
        else if(visited.containsKey(b.getBoard())){
            return null;
        }

        else{
            visited.put(b.getBoard(), b);

            for (int[] direction : directions) {
                if ((resultBoard = s.move(b, direction[0], direction[1])) != null) {
                    nextStep = findPath(resultBoard,s);
                }
            }

            return nextStep;
        }
    }
}
