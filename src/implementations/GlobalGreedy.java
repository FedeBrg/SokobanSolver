package implementations;

import interfaces.Board;
import interfaces.SearchMethod;
import interfaces.Sokoban;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;

public class GlobalGreedy implements SearchMethod {

    static HashMap<String, Board> visited = new HashMap<>();
    private Sokoban sokoban;

    public GlobalGreedy(Sokoban sokoban) {
        this.sokoban = sokoban;
    }

    @Override
    public Board findPath(Board b, Sokoban s) {
        int i = 0;
        Board resultBoard;
        Board currentBoard;
        int [][] directions = {{1,0},{0,1},{-1,0},{0,-1}};


        Queue<Board> boardQueue = new PriorityQueue<>();
        boardQueue.add(b);

        while(!boardQueue.isEmpty()){
            currentBoard = boardQueue.poll();

            while(visited.containsKey(currentBoard.getBoard())){
                currentBoard = boardQueue.poll();
            }

            if (Utilities.isSolution(currentBoard)) {
                System.out.printf("Solution found!\n");
                System.out.printf("Solution cost: %d\n",(currentBoard.getSolution().length()/currentBoard.getBoardSizex()/currentBoard.getBoardSizey())-1);
                System.out.printf("Expanded nodes: %d\n",i);
                System.out.printf("Nodes in frontier: %d\n",boardQueue.size());
                boardQueue.clear();
                return currentBoard;
            }

            i++;
            visited.put(currentBoard.getBoard(), currentBoard);
//            currentBoard.printBoard(cols);

            for (int[] direction : directions) {
                if ((resultBoard = s.move(currentBoard, direction[0], direction[1])) != null) {
                    resultBoard.setHeuristic(s.getHeuristic(resultBoard));
                    boardQueue.add(resultBoard);
                }
            }

        }

        System.out.println("Solution not found!\n");
        System.out.printf("Expanded nodes: %d\n",i);
        System.out.printf("Nodes in frontier: %d\n",boardQueue.size());

        return null;
    }

    @Override
    public String toString() {
        return String.format("Search method used: Global Greedy\nHeuristic used: %s",sokoban.getHeuristic());
    }
}
