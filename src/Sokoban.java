import javafx.beans.binding.MapExpression;

import java.awt.*;
import java.util.*;

public class Sokoban {
    static HashMap<String, Board> visited = new HashMap<>();

    static int cols;

    Sokoban(){
        cols = 7;
    }

    Board movePlayer(Board b, int dx, int dy) {
        int x = b.getPlayerx();
        int y = b.getPlayery();


        int newPos = x+dx+(y+dy)*cols;
        int playerPos = x+y*cols;
        char replacement;
        char leftBehind;
        char[] boardArray = b.getBoard().toCharArray();

        char c = b.getBoard().charAt(newPos);

        if(c == '#'){
            return null;
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

        if(checkDeadlock(b,x+(2*dx), (y+(2*dy))*cols)){
            return null;
        }

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

    boolean checkDeadlock(Board b, int xBox, int yBox){
        int [][] positions = {{-1,-1},{1,-1},{1,1},{-1,1}};


        for (int[] pos : positions) {
            int p1 = xBox+pos[0] + yBox*cols;
            int p2 = xBox + (yBox+pos[1])*cols;
            if(p1 < b.getBoard().length() && p2<b.getBoard().length() && p1>0 && p2>0){
                char c1 = b.getBoard().charAt(p1);
                char c2 = b.getBoard().charAt(p2);
//            System.out.printf("%c %c\n",c1,c2);
                if(c1 == '#'  && c2 == '#'){
                    return true;
                }
            }

        }

        return false;
    }

    Board move(Board b, int dx, int dy){
        int x = b.getPlayerx();
        int y = b.getPlayery();
        int newPos = x+dx + (y+dy)*cols;

        char c = b.getBoard().charAt(newPos);
        if(c == '$' || c == '%'){
            return pushBox(b,dx,dy);
        }
        else{
            return movePlayer(b,dx,dy);
        }
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

    void printSolution(Board b){
        char[] board = b.getSolution().toCharArray();
        char[] aux = b.getBoard().toCharArray();
        int j = 0;
        int t = 0;
        while(t < board.length) {
            System.out.printf("\npaso N%d\n",(t/56));

            while (j < aux.length) {
                for (int i = 0; i < cols; i++) {
                    System.out.print(board[t]);
                    j++;
                    t++;
                }
                System.out.println();
            }
            j=0;
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

        String level3 = " ###########     " +
                        "##         ##    " +
                        "#  $     $  #    " +
                        "# $# #.# #$ #    " +
                        "#    #*#    #####" +
                        "#  ###.###  #   #" +
                        "#  .*.@.*.      #" +
                        "#  ###.###  #   #" +
                        "#    #*#    #####" +
                        "# $# #.# #$ #    " +
                        "#  $     $  #    " +
                        "##         ##    " +
                        " ###########     ";

        String level1 = "#######" +
                        "#     #" +
                        "#     #" +
                        "#. #  #" +
                        "#. $$ #" +
                        "#.$$  #" +
                        "#.#  @#" +
                        "#######";

        String level4 = "     #####   " +
                        "     #   #   " +
                        "     #   #   " +
                        "#### #   #   " +
                        "#  ####$#####" +
                        "#           #" +
                        "# .## # ##. #" +
                        "#           #" +
                        "#####$####  #" +
                        "   #   # ####" +
                        "   # @ #     " +
                        "   #   #     " +
                        "   #####     ";


        Sokoban s = new Sokoban();

        Board b = new Board(level1,level1,5,6);

        String dead =   "#######" +
                        "#     #" +
                        "#     #" +
                        "#. #  #" +
                        "#. $$ #" +
                        "#.$   #" +
                        "#.#$ @#" +
                        "#######";

//        Board test = new Board(dead,dead,5,6);
//        System.out.println(s.checkDeadlock(test,3,6));


//        s.solveByDFS(b);
        s.printSolution((s.solveByDFS(b)));
//        s.solveByBFS(b);
//        System.out.println(s.solveByBFS(b).getSolution());
    }

     Board solveByDFS(Board b){
        Board resultBoard = null;
        Board nextStep = null;

        int [][] directions = {{1,0},{0,1},{-1,0},{0,-1}};

        if(isSolution(b)){
//            System.out.println("SOLUCION!!!!\n");
//            b.printBoard(cols);
            return b;
        }
        else if(visited.containsKey(b.getBoard())){
            return null;
        }

        else{
            visited.put(b.getBoard(), b);

            for (int[] direction : directions) {
                if ((resultBoard = move(b, direction[0], direction[1])) != null) {
                    resultBoard.printBoard(cols);
                    nextStep = solveByDFS(resultBoard);
                }
            }

//            if((resultBoard = move(b,1,0)) != null){
//                resultBoard.printBoard(cols);
//                nextStep = solveByDFS(resultBoard);
//            }
//
//            if((resultBoard = move(b,0,1)) != null){
//                resultBoard.printBoard(cols);
//                nextStep = solveByDFS(resultBoard);
//            }
//
//            if((resultBoard = move(b,-1,0)) != null){
//                resultBoard.printBoard(cols);
//                nextStep = solveByDFS(resultBoard);
//            }
//
//            if((resultBoard = move(b,0,-1)) != null){
//                resultBoard.printBoard(cols);
//                nextStep = solveByDFS(resultBoard);
//            }

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

    Board solveByBFS(Board b){
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
            }

            if (isSolution(currentBoard)) {
                System.out.printf("SOLUCION en I = %d !!!!\n",i);
                currentBoard.printBoard(cols);
                boardQueue.clear();
                return currentBoard;
            }
            i++;
            visited.put(currentBoard.getBoard(), currentBoard);
//            currentBoard.printBoard(cols);

            for (int[] direction : directions) {
                if ((resultBoard = move(currentBoard, direction[0], direction[1])) != null) {
                    boardQueue.add(resultBoard);
                }
            }

//            if((resultBoard = move(currentBoard,1,0)) != null){
//                boardQueue.add(resultBoard);
//            }
//
//            if((resultBoard = move(currentBoard,0,1)) != null){
//                boardQueue.add(resultBoard);
//            }
//
//            if((resultBoard = move(currentBoard,-1,0)) != null){
//                boardQueue.add(resultBoard);
//            }
//
//            if((resultBoard = move(currentBoard,0,-1)) != null){
//                boardQueue.add(resultBoard);
//            }
        }

        return null;
    }
}
