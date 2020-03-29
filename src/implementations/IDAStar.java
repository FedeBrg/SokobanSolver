package implementations;

import interfaces.Board;
import interfaces.SearchMethod;
import interfaces.Sokoban;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class IDAStar implements SearchMethod {

    private Queue<Board> boardQueue = new PriorityQueue<>();
    private HashMap<String, Integer> specialVisited = new HashMap<>();


    @Override
    public Board findPath(Board b, Sokoban s) {
        int [][] directions = {{1,0},{0,1},{-1,0},{0,-1}};
        int limit = 0;
        Board currentBoard;
        Board resultBoard;
        boardQueue.add(b);

        while(true){
            currentBoard = boardQueue.poll();
            if (currentBoard == null) {
                return null;
            }

            limit = currentBoard.getCost() + currentBoard.getHeuristic();
            resultBoard = specialIDDFS(currentBoard, limit, directions, s);

            if(resultBoard != null){
                return resultBoard;
            }
        }
    }

    private Board specialIDDFS(Board b, int limit, int[][] directions, Sokoban s){
        Board resultBoard;
        Board nextStep = null;
        int totalCost = b.getCost() + b.getHeuristic();

        if(Utilities.isSolution(b)){
            return b;
        }

        else if(totalCost > limit){
            return null;
        }

        else{
            specialVisited.put(b.getBoard(), limit);

            for (int[] direction : directions) {
                if ((resultBoard = s.move(b, direction[0], direction[1])) != null) {
                    resultBoard.setCost(b.getCost()+1);
                    resultBoard.setHeuristic(s.getHeuristic(resultBoard));

                    if(!specialVisited.containsKey(resultBoard.getBoard())){
                        boardQueue.add(resultBoard);
                    }

                    nextStep = specialIDDFS(resultBoard, limit,directions, s);
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
        return "IDA*";
    }
}
