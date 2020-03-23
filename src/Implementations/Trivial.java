package Implementations;

import Interfaces.Board;
import Interfaces.Heuristic;

public class Trivial implements Heuristic {
    @Override
    public int getHeuristic(Board board) {
        if(Utilities.isSolution(board)){
            return 0;
        }

        return 1;
    }



}
