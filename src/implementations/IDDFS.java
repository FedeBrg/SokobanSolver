package implementations;

import interfaces.Board;
import interfaces.SearchMethod;
import interfaces.Sokoban;

import java.util.HashMap;

public class IDDFS implements SearchMethod {

    private HashMap<String, Integer> specialVisited = new HashMap<>();

    @Override
    public Board findPath(Board b, Sokoban s){
        int [][] directions = {{1,0},{0,1},{-1,0},{0,-1}};
        int i = 0;
        Board toReturn = null;

        while(toReturn == null){
            specialVisited.clear();
            toReturn = findPathWrapper(b, s, directions, s.getDepth()+(i*s.getIncrement()));
            i++;
        }

        return toReturn;
    }

    private Board findPathWrapper(Board b, Sokoban s, int[][] directions, int depth){
        Board resultBoard = null;
        Board nextStep = null;

        if(Utilities.isSolution(b)){
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
                    nextStep = findPathWrapper(resultBoard, s, directions, depth - 1);
                    if(nextStep != null){
                        return nextStep;
                    }
                }
            }
        }

        return nextStep;
    }
    @Override
    public String toString() {
        return "IDDFS";
    }
}
