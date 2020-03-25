package implementations;

import interfaces.Board;
import interfaces.Heuristic;

public class Hamming implements Heuristic {

    @Override
    public int getHeuristic(Board board) {
        int boxOutOfPlaceCount = 0;
        char[] boardArray = board.getBoard().toCharArray();

        for(char c : boardArray){
            if (c == '$') {
                boxOutOfPlaceCount++;
            }
        }

        return boxOutOfPlaceCount;
    }

    @Override
    public String toString() {
        return "Hamming";
    }
}
