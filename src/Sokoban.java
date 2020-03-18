import javafx.beans.binding.MapExpression;

import java.util.*;

public class Sokoban {
    static HashMap<String, Board> visited = new HashMap<>();

    static int cols;

    Sokoban(){
        cols = 15;
    }

    static Board movePlayer(Board b, int dx, int dy) {
        int x = b.getPlayerx();
        int y = b.getPlayery();

        int newPos = x+dx+(y+dy)*cols;
        int playerPos = x+y*cols;
        char replacement;
        char leftBehind;
        char[] boardArray = b.getBoard().toCharArray();
        boolean boxIsBeingMoved = false;

        char c = b.getBoard().charAt(newPos);
        char aux;
        if(c == '#'){
            return null;
        }
        else if(c == '$'){
            boxIsBeingMoved = true;
            if(dx == 1){
                aux = b.getBoard().charAt(newPos+1);
                if(aux == '$' || aux == '#'){
                    return null;
                }
                else{
                    boardArray = b.getBoard().toCharArray();
                    boardArray[newPos+1] = '$';
                    replacement = '@';
                }
            }

            else if(dx == -1){
                aux = b.getBoard().charAt(newPos-1);
                if(aux == '$' || aux == '#'){
                    return null;
                }
                else{
                    boardArray = b.getBoard().toCharArray();
                    boardArray[newPos-1] = '$';
                    replacement = '@';
                }
            }

            else if(dy == 1){
                aux = b.getBoard().charAt(newPos+cols);
                if(aux == '$' || aux == '#'){
                    return null;
                }
                else{
                    boardArray = b.getBoard().toCharArray();
                    boardArray[newPos+cols] = '$';
                    replacement = '@';
                }
            }

            else{
                aux = b.getBoard().charAt(newPos-cols);
                if(aux == '$' || aux == '#'){
                    return null;
                }
                else{
                    boardArray = b.getBoard().toCharArray();
                    boardArray[newPos-cols] = '$';
                    replacement = '@';
                }
            }
        }
        else if(c == '.'){
            replacement = 'O';
        }
        else{
            replacement = '@';
        }

        c = b.getBoard().charAt(playerPos);
        if(c == 'O'){
            leftBehind = '.';
        }
        else{
            leftBehind = ' ';
        }

        if(!boxIsBeingMoved){
            boardArray = b.getBoard().toCharArray();
        }


        boardArray[playerPos] = leftBehind;
        boardArray[newPos] = replacement;

        return new Board(new String(boardArray),b.getSolution().concat(new String((boardArray))),x+dx,y+dy);
    }

    Board pushBox(Board b, int dx, int dy){
        int x = b.getPlayerx();
        int y = b.getPlayery();
        int newPos = x+(2*dx) + (y+(2*dy))*cols;
        int boxPos = x+dx+(y+dy)*cols;

        char replacement;
        char leftBehind;

        char c = b.getBoard().charAt(newPos);
        if(c == '#' || c == '$' || c == '%'){
            return null;
        }
        else if(c == '.'){
            replacement = '%';
        }
        else{
            replacement = '$';
        }

        c = b.getBoard().charAt(boxPos);
        System.out.println("El c es"+c);
        if(c == '%'){
            leftBehind = '.';
        }
        else{
            leftBehind = ' ';
        }

        char[] boardArray = b.getBoard().toCharArray();
        boardArray[boxPos] = leftBehind;
        boardArray[newPos] = replacement;

        return movePlayer(new Board(new String(boardArray),b.getSolution(),x,y),dx,dy);
    }

    void printBoard(Board b){
        char[] board = b.getBoard().toCharArray();
        int j = 0;
        while(j<board.length){
            for(int i = 0;i<cols;i++){
                System.out.print(board[j]);
                j++;

            }
            System.out.println();
        }
    }

    static boolean isSolution(Board board){
        return !board.getBoard().contains(".") && !board.getBoard().contains("O");
    }

    public static void main(String[] arg){

        String level2 = "      ###      " +
                        "      #.#      " +
                        "  #####.#####  " +
                        " ##         ## " +
                        "##  # # # #  ##" +
                        "#  ##     ##  #" +
                        "# ##  # #  ## #" +
                        "#     $@$     #" +
                        "####  ###  ####" +
                        "   #### ####   ";

        Sokoban s = new Sokoban();
        Board b = new Board(level2,"",7,7);
        s.printBoard(b);
        System.out.println();
        //Board newB = s.pushBox(b,1,0);
        //if(newB != null) {
        //    s.printBoard(newB);
        //}


        //solveByDFS(b);
        solveByBFS(b);
    }

    static Board solveByDFS(Board b){
        Board resultBoard = null;
        Board nextStep = null;

        if(isSolution(b)){
            System.out.println("SOLUCION!!!!\n");
            b.printBoard(cols);
            return b;
        }
        else if(visited.containsKey(b.getBoard())){
            return null;
        }

        else{
            visited.put(b.getBoard(), b);

            if((resultBoard = movePlayer(b,1,0)) != null){
                resultBoard.printBoard(cols);
                nextStep = solveByDFS(resultBoard);
            }

            if((resultBoard = movePlayer(b,0,1)) != null){
                resultBoard.printBoard(cols);
                nextStep = solveByDFS(resultBoard);
            }

            if((resultBoard = movePlayer(b,-1,0)) != null){
                resultBoard.printBoard(cols);
                nextStep = solveByDFS(resultBoard);
            }

            if((resultBoard = movePlayer(b,0,-1)) != null){
                resultBoard.printBoard(cols);
                nextStep = solveByDFS(resultBoard);
            }

            if(nextStep == null){
                StringBuilder str;
                str = new StringBuilder(b.getSolution());
                //str.deleteCharAt(str.length() - 1);
                b.setSolution(str.toString());
                visited.remove(b.getBoard());
            }

            return nextStep;
        }
    }

    static Board solveByBFS(Board b){
        int i = 0;
        Board resultBoard;
        Board currentBoard;
        Deque<Board> boardQueue = new ArrayDeque<>();
        boardQueue.add(b);

        while(!boardQueue.isEmpty()){
            currentBoard = boardQueue.poll();
            if (isSolution(currentBoard)) {
                System.out.printf("SOLUCION en I = %d !!!!\n",i);
                currentBoard.printBoard(cols);
                return currentBoard;
            }

            while(visited.containsKey(currentBoard.getBoard())){
                currentBoard = boardQueue.poll();
            }
            i++;
            visited.put(currentBoard.getBoard(), currentBoard);
            currentBoard.printBoard(cols);

            if((resultBoard = movePlayer(currentBoard,1,0)) != null){
                boardQueue.add(resultBoard);
            }

            if((resultBoard = movePlayer(currentBoard,0,1)) != null){
                boardQueue.add(resultBoard);
            }

            if((resultBoard = movePlayer(currentBoard,-1,0)) != null){
                boardQueue.add(resultBoard);
            }

            if((resultBoard = movePlayer(currentBoard,0,-1)) != null){
                boardQueue.add(resultBoard);
            }
        }

        return null;
    }
}
