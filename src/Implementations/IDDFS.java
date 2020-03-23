package Implementations;

import Interfaces.Board;
import Interfaces.SearchMethod;
import Interfaces.Sokoban;

import java.util.HashMap;

public class IDDFS implements SearchMethod {

    private HashMap<String, Integer> specialVisited = new HashMap<>();

    @Override
    public Board findPath(Board b, Sokoban s){
        Board resultBoard = null;
        Board nextStep = null;
        int [][] directions = {{1,0},{0,1},{-1,0},{0,-1}};

        if(Utilities.isSolution(b)){
            return b;
        }

        else if(specialVisited.containsKey(b.getBoard()) && specialVisited.get(b.getBoard()) >= s.getDepth()){
            return null;
        }

        else if(s.getDepth() == 0){
            return null;
        }

        else{
            specialVisited.put(b.getBoard(), s.getDepth());

            for (int[] direction : directions) {
                if ((resultBoard = s.move(b, direction[0], direction[1])) != null) {
                    s.setDepth(s.getDepth()-1);
                    nextStep = findPath(resultBoard,s);
                }
            }
        }

        return nextStep;
    }

}
