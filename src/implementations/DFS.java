package implementations;

import interfaces.Board;
import interfaces.SearchMethod;
import interfaces.Sokoban;

import java.util.HashMap;

public class DFS implements SearchMethod {
    static HashMap<String, Board> visited = new HashMap<>();

    @Override
    public Board findPath(Board b, Sokoban s) {
        return findPathDFS(b,s,0);
    }

    public Board findPathDFS(Board b, Sokoban s,int frontier) {
        Board resultBoard = null;
        Board nextStep = null;

        int [][] directions = {{1,0},{0,1},{-1,0},{0,-1}};

        if(Utilities.isSolution(b)){
            System.out.print("Solution found!\n");
            System.out.printf("Solution cost: %d\n",(b.getSolution().length()/b.getBoardSizex()/b.getBoardSizey())-1);
            System.out.printf("Expanded nodes: %d\n",visited.size()+1);
            System.out.printf("Nodes in frontier: %d\n",frontier);
            return b;
        }
        else if(visited.containsKey(b.getBoard())){
            return null;
        }

        else{
            visited.put(b.getBoard(), b);
            for (int[] direction : directions) {
                if ((resultBoard = s.move(b, direction[0], direction[1])) != null) {
                    frontier++;
                    nextStep = findPathDFS(resultBoard,s,frontier);
                    if (nextStep != null){
                        return nextStep;
                    }
                }
            }

            return null;
        }
    }

    @Override
    public String toString() {
        return "DFS";
    }
}
