package implementations;

import interfaces.Board;
import interfaces.SearchMethod;
import interfaces.Sokoban;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;

public class IDAStar implements SearchMethod {

    private Queue<Board> boardQueue = new PriorityQueue<>();
    private HashMap<String, Integer> specialVisited = new HashMap<>();


    @Override
    public Board findPath(Board b, Sokoban s) {
        int [][] directions = {{1,0},{0,1},{-1,0},{0,-1}};
        int limit = 0;
        int prevLimit = 0;
        Board currentBoard;
        Board resultBoard;
        boardQueue.add(b);

        while(true){
            prevLimit = limit;
            do {
                currentBoard = boardQueue.poll();
                if (currentBoard == null) {
                    return null;
                }

                limit = currentBoard.getCost() + currentBoard.getHeuristic();
            } while (limit <= prevLimit && !specialVisited.containsKey(currentBoard.getBoard()));

            resultBoard = specialIDDFS(currentBoard, limit, directions, s);

            if(resultBoard != null){
                return resultBoard;
            }
        }
    }

    private Board specialIDDFS(Board b, int depth, int[][] directions, Sokoban s){
        Board resultBoard;
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
                    resultBoard.setCost(b.getCost()+1);
                    resultBoard.setHeuristic(s.getHeuristic(resultBoard));

                    if(!specialVisited.containsKey(resultBoard.getBoard())){
                        boardQueue.add(resultBoard);
                    }

                    nextStep = specialIDDFS(resultBoard, depth-1,directions, s);
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
