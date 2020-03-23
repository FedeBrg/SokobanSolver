package Implementations;

import Interfaces.Board;
import Interfaces.Heuristic;
import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ImprovedManhattan implements Heuristic {
    @Override
    public int getHeuristic(Board board) {
        Pair<List<Point>,List<Point>> p = HeuristicUtilities.getBoxesAndBins(board);
        List<Point> boxes = p.getKey();
        List<Point> bins = p.getValue();

        HashMap<Pair<Integer,Integer>,Integer> map = new HashMap<>();

        for(int i = 0;i<boxes.size();i++){
            for(int j = 0;j<bins.size();j++){
                Point box = boxes.get(i);
                Point bin = bins.get(j);
                map.put(new Pair<>(i,j),Math.abs(box.x-bin.x) + Math.abs(box.y-bin.y));
            }
        }

        ArrayList<Integer> l = new ArrayList<>();
        for (int i = 0;i<boxes.size();i++){
            l.add(i);
        }

        ArrayList<ArrayList<Integer>> perm = permutations(l);

        List<Integer> results = new ArrayList<>();
        for (int i = 0;i<perm.size();i++){
            results.add(0);
        }

        for (int i = 0;i<perm.size();i++){
            for (int j = 0;j<boxes.size();j++){
                int r = results.get(i);
                r+= map.get(new Pair<>(i,j));
                results.set(i,r);
            }
        }

        if(results.isEmpty()){
            return 0;
        }

        return Collections.min(results);
    }

    //SOURCE https://www.java-forums.org/new-java/67881-loops-recursion-get-every-permutation-arraylist-elements-order-matters.html

    private  ArrayList<ArrayList<Integer>> permutations(ArrayList<Integer> list)
    {
        return permutations(null, list, null);
    }

    private ArrayList<ArrayList<Integer>> permutations(ArrayList<Integer> prefix, ArrayList<Integer> suffix, ArrayList<ArrayList<Integer>> output)
    {
        if(prefix == null)
            prefix = new ArrayList<Integer>();
        if(output == null)
            output = new ArrayList<ArrayList<Integer>>();

        if(suffix.size() == 1)
        {
            ArrayList<Integer> newElement = new ArrayList<Integer>(prefix);
            newElement.addAll(suffix);
            output.add(newElement);
            return output;
        }

        for(int i = 0; i < suffix.size(); i++)
        {
            ArrayList<Integer> newPrefix = new ArrayList<Integer>(prefix);
            newPrefix.add(suffix.get(i));
            ArrayList<Integer> newSuffix = new ArrayList<Integer>(suffix);
            newSuffix.remove(i);
            permutations(newPrefix,newSuffix,output);
        }

        return output;
    }
}
