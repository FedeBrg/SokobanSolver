package Implementations;

import Interfaces.Board;
import Interfaces.SearchMethod;
import Interfaces.Sokoban;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;

public class IDAStar implements SearchMethod {

    private Queue<Board> boardQueue = new PriorityQueue<>();
    private HashMap<String, Integer> specialVisited = new HashMap<>();


    @Override
    public Board findPath(Board b, Sokoban s) {
        int limit;
        Board currentBoard;
        Board resultBoard;
        boardQueue.add(b);

        while(true){
            currentBoard = boardQueue.poll();
            if(currentBoard == null){
                return null;
            }

            limit = currentBoard.getCost() + currentBoard.getHeuristic();
            //System.out.printf("QUEUE = %d, COST = %d, HEURISTIC = %d\n", boardQueue.size(), currentBoard.getCost(), currentBoard.getHeuristic());
            resultBoard = specialIDDFS(currentBoard, limit, s);

            if(resultBoard != null){
                return resultBoard;
            }
        }
    }

    private Board specialIDDFS(Board b, int depth, Sokoban s){
        Board resultBoard;
        Board nextStep = null;
        int [][] directions = {{1,0},{0,1},{-1,0},{0,-1}};

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
            if(!boardQueue.contains(b)){
                boardQueue.add(b);
            }

            for (int[] direction : directions) {
                if ((resultBoard = s.move(b, direction[0], direction[1])) != null) {
                    resultBoard.setCost(b.getCost()+1);
                    resultBoard.setHeuristic(s.getHeuristic(resultBoard));
                    nextStep = specialIDDFS(resultBoard, depth-1,s);
                    if(Utilities.isSolution(nextStep)){
                        return nextStep;
                    }
                }
            }
        }

        return nextStep;
    }
}
