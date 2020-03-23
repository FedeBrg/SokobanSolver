package Implementations;

import Interfaces.Board;
import Interfaces.Heuristic;

public class Trivial implements Heuristic {
    @Override
    public int getHeuristic(Board board) {
        if(isSolution(board)){
            return 0;
        }

        return 1;
    }

    private boolean isSolution(Board board){
        if(board == null){
            return false;
        }
        return !board.getBoard().contains(".") && !board.getBoard().contains("O");
    }

}
