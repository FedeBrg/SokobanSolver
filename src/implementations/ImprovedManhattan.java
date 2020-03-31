package implementations;

import interfaces.Board;
import interfaces.Heuristic;
//import javafx.util.Pair;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ImprovedManhattan implements Heuristic {
    @Override
    public int getHeuristic(Board board) {
        Map.Entry<List<Point>,List<Point>> p = Utilities.getBoxesAndBins(board);
        List<Point> boxes = p.getKey();
        List<Point> bins = p.getValue();

        HashMap<Map.Entry<Integer,Integer>,Integer> map = new HashMap<>();

        for(int i = 0;i<boxes.size();i++){
            for(int j = 0;j<bins.size();j++){
                Point box = boxes.get(i);
                Point bin = bins.get(j);
                map.put(new AbstractMap.SimpleEntry<>(i,j),Math.abs(box.x-bin.x) + Math.abs(box.y-bin.y));
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
                ArrayList<Integer> arr = perm.get(i);
                int t = arr.get(j);

                AbstractMap.SimpleEntry<Integer,Integer> entry = new AbstractMap.SimpleEntry<>(j,t);

                if(map.get(entry) != null){
                    r+= map.get(entry);
                }
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

    @Override
    public String toString() {
        return "Improved Manhattan";
    }
}
