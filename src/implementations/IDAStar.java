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
        Board currentBoard;
        Board resultBoard;
        boardQueue.add(b);
        currentBoard = boardQueue.peek();
        limit = currentBoard.getCost() + currentBoard.getHeuristic();

        /* Mientras tengamos chance de encontrar la solucion */
        while(!boardQueue.isEmpty()){

            /* Hasta que encontremos un estado con f mayor... */
            while(currentBoard.getHeuristic() + currentBoard.getCost() <= limit){
                //System.out.printf("LIMIT = %d\n", limit);

                /* Buscamos la solución */
                resultBoard = findPathIDAStar(currentBoard, limit, directions, s);

                /* La solucion es esta */
                if(resultBoard != null){
                    return resultBoard;
                }

                currentBoard = boardQueue.poll();

                /* No mas estados = No solucion */
                if(currentBoard == null){
                    return null;
                }
            }

            /* El nuevo limite esta en el nodo que rompio el while de arriba */
            limit = currentBoard.getCost() + currentBoard.getHeuristic();
        }

        /* Si llegamos hasta aca algo anda mal */
        return null;
    }

    private Board findPathIDAStar(Board b, int limit, int[][] directions, Sokoban s){
        Board resultBoard;
        Board nextStep = null;
        int totalCost = b.getCost() + b.getHeuristic();

        /* Si es solución llevamos devolvemos el nodo, q tiene todo el camino */
        if(Utilities.isSolution(b)){
            return b;
        }

        /* Si es mayor del limite vuelvo para atras pero con null porque todavia no es solucion
        *  Si no esta en la cola, lo agrego para que sea el nuevo limite
        */
        else if(totalCost > limit){
            if(!specialVisited.containsKey(b.getBoard())){
                boardQueue.add(b);
            }
            return null;
        }

        else{
            /* Lo pongo como visitado sino expande todos los nodos indiscriminadamente y para eso
             * necesito el heap del tamaño de una casa
             */
            specialVisited.put(b.getBoard(), totalCost);

            /* Vemos que camino es valido... */
            for (int[] direction : directions) {
                if ((resultBoard = s.move(b, direction[0], direction[1])) != null) {
                    resultBoard.setCost(b.getCost()+1);
                    resultBoard.setHeuristic(s.getHeuristic(resultBoard));

                    /* Desde el nuevo nodo, nos tiramos a buscar el resto del camino */
                    nextStep = findPathIDAStar(resultBoard, limit,directions, s);
                    if(nextStep != null){
                        /* Encontre la solucion, la sigo llevando */
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
