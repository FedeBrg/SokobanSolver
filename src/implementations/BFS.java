package implementations;

import interfaces.Board;
import interfaces.SearchMethod;
import interfaces.Sokoban;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

public class BFS implements SearchMethod {
    static HashMap<String, Board> visited = new HashMap<>();

    @Override
    public Board findPath(Board b, Sokoban s) {
        int i = 0;
        Board resultBoard;
        Board currentBoard;
        int [][] directions = {{1,0},{0,1},{-1,0},{0,-1}};

        Deque<Board> boardQueue = new ArrayDeque<>();
        boardQueue.add(b);

        while(!boardQueue.isEmpty()){
            currentBoard = boardQueue.poll();


            while(visited.containsKey(currentBoard.getBoard())){
                currentBoard = boardQueue.poll();
                if(currentBoard == null){
                    System.out.println("There is no solution :(\n");
                    return null;
                }
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

            for (int[] direction : directions) {
                if ((resultBoard = s.move(currentBoard, direction[0], direction[1])) != null) {
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
        return "BFS";
    }
}
