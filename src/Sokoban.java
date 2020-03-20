import java.awt.*;
import java.util.*;
import java.util.List;

public class Sokoban {
    static HashMap<String, Board> visited = new HashMap<>();

    static int cols;

    Sokoban(){
        cols = 17;
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

        return new Board(new String(boardArray), b.getSolution().concat(new String((boardArray))), b.getBoardSizex(),b.getBoardSizey(), x + dx, y + dy);

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
        if(c == '%'){
            leftBehind = '.';
        }
        else{
            leftBehind = ' ';
        }

        char[] boardArray = b.getBoard().toCharArray();
        boardArray[boxPos] = leftBehind;
        boardArray[newPos] = replacement;

        Board rtb = movePlayer(new Board(new String(boardArray),b.getSolution(), b.getBoardSizex(), b.getBoardSizey(),x,y),dx,dy);

        if(replacement != '%' && (checkEdgeDeadlock(b,x+(2*dx), (y+(2*dy))) || checkMapDeadlock(b)) && !isSolution(rtb)){
            return null;
        }

        return rtb;
    }

    boolean checkEdgeDeadlock(Board b, int xBox, int yBox){
        int [][] positions = {{-1,-1},{1,-1},{1,1},{-1,1}};


        for (int[] pos : positions) {
            int p1 = xBox+pos[0] + yBox*cols;
            int p2 = xBox + (yBox+pos[1])*cols;
            if(p1 < b.getBoard().length() && p2<b.getBoard().length() && p1>0 && p2>0){
                char c1 = b.getBoard().charAt(p1);
                char c2 = b.getBoard().charAt(p2);
                if(c1 == '#'  && c2 == '#'){
                    return true;
                }
            }

        }

        return false;
    }


    boolean checkPossibleDeadlock(Board b, int xBox, int yBox){
        int [][] positions = {{-1,-1},{1,-1},{1,1},{-1,1}};


        for (int[] pos : positions) {
            int p1 = xBox+pos[0] + yBox*b.getBoardSizex();
            int p2 = xBox + (yBox+pos[1])*b.getBoardSizex();
            if(p1 < b.getBoard().length() && p2<b.getBoard().length() && p1>0 && p2>0){
                char c1 = b.getBoard().charAt(p1);
                char c2 = b.getBoard().charAt(p2);
                if((c1 == '#' || c1 == '$' || c1 == '%')  && (c2 == '#' || c2 == '$' || c2 == '%')){
                    return true;
                }
            }

        }

        return false;
    }
    boolean checkMapDeadlock(Board b){
        int boxes = 0;
        int deadlocked = 0;

        char[] board = b.getBoard().toCharArray();
        int j = 0;
        while(j<board.length){
            if(board[j] == '$' || board[j] == '%'){
                boxes++;
                int x = j % b.getBoardSizex();
                int y = j / b.getBoardSizex();

                if(checkPossibleDeadlock(b,x,y)){
                    deadlocked++;
                }
                else{
                    return false;
                }
            }
            j++;

        }

        return deadlocked == boxes;
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
            System.out.printf("\npaso N%d\n",(t/(b.getBoardSizex()*b.getBoardSizey())));

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
                        "#    # #    #####" +
                        "#  ###.###  #   #" +
                        "#  . .@. .      #" +
                        "#  ###.###  #   #" +
                        "#    # #    #####" +
                        "# $# #.# #$ #    " +
                        "#  $     $  #    " +
                        "##         ##    " +
                        " ###########     ";

        String level1 = "#######" +
                        "#     #" +
                        "#     #" +
                        "#$    #" +
                        "#     #" +
                        "#     #" +
                        "#.   @#" +
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

        Board b = new Board(level3,level3,17,13,6,6);
//        System.out.println(s.checkMapDeadlock(b));

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

       // s.solveByDFS(b);
//        s.solveByBFS(b);
//        s.solveByBFS(b);
//        System.out.println(s.solveByBFS(b).getSolution());
    }

     Board solveByDFS(Board b){
        Board resultBoard = null;
        Board nextStep = null;

        int [][] directions = {{1,0},{0,1},{-1,0},{0,-1}};

        if(isSolution(b)){
            System.out.println("SOLUCION!!!!\n");
            printSolution(b);
            //b.printBoard(cols);
            return b;
        }
        else if(visited.containsKey(b.getBoard())){
            return null;
        }

        else{
            visited.put(b.getBoard(), b);

            for (int[] direction : directions) {
                if ((resultBoard = move(b, direction[0], direction[1])) != null) {
                    //resultBoard.printBoard(cols);
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
                //visited.remove(b.getBoard());
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
                //currentBoard.printBoard(cols);
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


    Board solveByGlobalGreedy(Board b){
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

            if (isSolution(currentBoard)) {
                System.out.printf("SOLUCION en I = %d !!!!\n",i);
                //currentBoard.printBoard(cols);
                boardQueue.clear();
                return currentBoard;
            }

            i++;
            visited.put(currentBoard.getBoard(), currentBoard);
//            currentBoard.printBoard(cols);

            for (int[] direction : directions) {
                if ((resultBoard = move(currentBoard, direction[0], direction[1])) != null) {
                    resultBoard.setHeuristic(manhattanHeuristic(resultBoard));
                    boardQueue.add(resultBoard);
                }
            }

        }

        return null;
    }

    Board solveByAStar(Board b){
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

            if (isSolution(currentBoard)) {
                System.out.printf("SOLUCION en I = %d !!!!\n",i);
                //currentBoard.printBoard(cols);
                boardQueue.clear();
                return currentBoard;
            }

            i++;
            visited.put(currentBoard.getBoard(), currentBoard);
//            currentBoard.printBoard(cols);

            for (int[] direction : directions) {
                if ((resultBoard = move(currentBoard, direction[0], direction[1])) != null) {
                    resultBoard.setHeuristic(manhattanHeuristic(resultBoard)+1);
                    boardQueue.add(resultBoard);
                }
            }

        }

        return null;
    }

    public int manhattanHeuristic(Board b) {
        List<Point> boxes = new ArrayList<>();
        List<Point> bins = new ArrayList<>();
        Point p;

        char[] boardArray = b.getBoard().toCharArray();
        for(int i = 0; i < boardArray.length; i++){
            if(boardArray[i] == '.' || boardArray[i] == 'O') {
                p = new Point(i % b.getBoardSizex(), i / b.getBoardSizex());
                bins.add(p);
            } else if(boardArray[i] == '$'){
                p = new Point(i % b.getBoardSizex(), i / b.getBoardSizex());
                boxes.add(p);
            }
        }

        //for(Point point : boxes){
        //    System.out.printf("BOX: X = %d, Y = %d\n", point.x, point.y);
        //}

        //for(Point point : bins){
        //    System.out.printf("BIN: X = %d, Y = %d\n", point.x, point.y);
        //}

        List<Integer> heuristicList = new ArrayList<>();
        int toReturn = 0;

        for(Point box : boxes){
            for(Point bin : bins){
                heuristicList.add(Math.abs(box.x-bin.x) + Math.abs(box.y-bin.y));
            }

            toReturn += Collections.max(heuristicList);
            heuristicList.clear();
        }

        return toReturn;
    }
}
