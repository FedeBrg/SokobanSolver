package implementations;

import interfaces.Board;
import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Utilities {

    public static Pair<List<Point>,List<Point>> getBoxesAndBins(Board board){
        List<Point> boxes = new ArrayList<>();
        List<Point> bins = new ArrayList<>();
        Point p;

        char[] boardArray = board.getBoard().toCharArray();
        for(int i = 0; i < boardArray.length; i++){
            if(boardArray[i] == '.' || boardArray[i] == 'O') {
                p = new Point(i % board.getBoardSizex(), i / board.getBoardSizex());
                bins.add(p);
            } else if(boardArray[i] == '$'){
                p = new Point(i % board.getBoardSizex(), i / board.getBoardSizex());
                boxes.add(p);
            }
        }

        return new Pair<>(boxes,bins);
    }

    static boolean isSolution(Board board){
        if(board == null){
            return false;
        }
        return !board.getBoard().contains(".") && !board.getBoard().contains("O");
    }


}