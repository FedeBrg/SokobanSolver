package Implementations;

import Interfaces.Board;
import Interfaces.Heuristic;
import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Manhattan implements Heuristic {
    @Override
    public int getHeuristic(Board board) {
        Pair<List<Point>,List<Point>> p = HeuristicUtilities.getBoxesAndBins(board);
        List<Point> boxes = p.getKey();
        List<Point> bins = p.getValue();

        List<Integer> heuristicList = new ArrayList<>();
        int toReturn = 0;

        for(Point box : boxes){
            for(Point bin : bins){
                heuristicList.add(Math.abs(box.x-bin.x) + Math.abs(box.y-bin.y));
            }

            toReturn += Collections.min(heuristicList);
            heuristicList.clear();
        }

        return toReturn;
    }
}
