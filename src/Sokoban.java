import javafx.util.Pair;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.LongStream;

public class Sokoban {
    static HashMap<String, Board> visited = new HashMap<>();
    private HashMap<String, Integer> specialVisited = new HashMap<>();
    private Queue<Board> boardQueue = new PriorityQueue<>();
    static int cols;

    Sokoban(){
        cols = 13;
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
        if(b == null){
            return;
        }

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
        if(board == null){
            return false;
        }
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

        Board b = new Board(level2,level2,15,10,7,7);
        Board b2 = new Board(level4, level4, 13, 13, 5,10);


        String dead =   "#######" +
                        "#     #" +
                        "#     #" +
                        "#. #  #" +
                        "#. $$ #" +
                        "#.$   #" +
                        "#.#$ @#" +
                        "#######";

        //s.printSolution(s.solveByBFS(b));
        b2.setCost(1);
        b2.setHeuristic(s.easyHeuristic(b2));
        s.printSolution(s.solveByAStar(b2));
//        s.printSolution(s.solveByBFS(b2));

    }

     Board solveByDFS(Board b){
        Board resultBoard = null;
        Board nextStep = null;

        int [][] directions = {{1,0},{0,1},{-1,0},{0,-1}};

        if(isSolution(b)){
            System.out.println("SOLUCION!!!!\n");
            printSolution(b);
            return b;
        }
        else if(visited.containsKey(b.getBoard())){
            return null;
        }

        else{
            visited.put(b.getBoard(), b);

            for (int[] direction : directions) {
                if ((resultBoard = move(b, direction[0], direction[1])) != null) {
                    nextStep = solveByDFS(resultBoard);
                }
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
                if(currentBoard == null){
                    System.out.println("There is no solution :(\n");
                    return null;
                }
            }

            if (isSolution(currentBoard)) {
                System.out.printf("SOLUCION en I = %d !!!!\n",i);
                boardQueue.clear();
                return currentBoard;
            }

            i++;
            visited.put(currentBoard.getBoard(), currentBoard);

            for (int[] direction : directions) {
                if ((resultBoard = move(currentBoard, direction[0], direction[1])) != null) {
                    boardQueue.add(resultBoard);
                }
            }
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
                    resultBoard.setHeuristic(easyHeuristic(resultBoard));
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
                    resultBoard.setHeuristic(improvedManhattanHeuristic(resultBoard));
                    resultBoard.setCost(currentBoard.getCost()+1);
//                    System.out.println(currentBoard.getCost());
                    boardQueue.add(resultBoard);
                }
            }

        }

        return null;
    }

    public Board solveByIDAStar(Board b){
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
            resultBoard = specialIDDFS(currentBoard, limit);

            if(resultBoard != null){
                return resultBoard;
            }
        }
    }


    public Board solveByIDDFS(Board b, int depth){
        Board resultBoard = null;
        Board nextStep = null;
        int [][] directions = {{1,0},{0,1},{-1,0},{0,-1}};

        if(isSolution(b)){
            printSolution(b);
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
                if ((resultBoard = move(b, direction[0], direction[1])) != null) {
                    nextStep = solveByIDDFS(resultBoard, depth-1);
                }
            }
        }

        return nextStep;
    }

    private Board specialIDDFS(Board b, int depth){
        Board resultBoard;
        Board nextStep = null;
        int [][] directions = {{1,0},{0,1},{-1,0},{0,-1}};

        if(isSolution(b)){
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
                if ((resultBoard = move(b, direction[0], direction[1])) != null) {
                    resultBoard.setCost(b.getCost()+1);
                    resultBoard.setHeuristic(manhattanHeuristic(resultBoard));
                    nextStep = specialIDDFS(resultBoard, depth-1);
                    if(isSolution(nextStep)){
                        return nextStep;
                    }
                }
            }
        }

        return nextStep;
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

        List<Integer> heuristicList = new ArrayList<>();
        int toReturn = 0;

        for(Point box : boxes){
            for(Point bin : bins){
                heuristicList.add(Math.abs(box.x-bin.x) + Math.abs(box.y-bin.y));
            }

            toReturn += Collections.min(heuristicList);
            heuristicList.clear();
        }

        return toReturn;
    }

    public int easyHeuristic(Board b){
        if(isSolution(b)){
            return 0;
        }

        return 1;
    }

    public int hammingHeuristic(Board b){
        int boxOutOfPlaceCount = 0;
        char[] boardArray = b.getBoard().toCharArray();

        for(char c : boardArray){
            if (c == '$') {
                boxOutOfPlaceCount++;
            }
        }

        return boxOutOfPlaceCount;
    }

    public int improvedManhattanHeuristic(Board b){
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


        HashMap<Pair<Integer,Integer>,Integer> map = new HashMap<>();

        for(int i = 0;i<boxes.size();i++){
            for(int j = 0;j<bins.size();j++){
                Point box = boxes.get(i);
                Point bin = bins.get(j);
                map.put(new Pair<>(i,j),Math.abs(box.x-bin.x) + Math.abs(box.y-bin.y));
            }
        }

        ArrayList<Integer> l = new ArrayList<>();
        for (int i = 0;i<boxes.size();i++){
            l.add(i);
        }

        ArrayList<ArrayList<Integer>> perm = permutations(l);

        List<Integer> results = new ArrayList<>();
        for (int i = 0;i<perm.size();i++){
            results.add(0);
        }

        for (int i = 0;i<perm.size();i++){
            for (int j = 0;j<boxes.size();j++){
                int r = results.get(i);
                r+= map.get(new Pair<>(i,j));
                results.set(i,r);
            }
        }

        if(results.isEmpty()){
            return 0;
        }

        return Collections.min(results);
    }


    //SOURCE https://www.java-forums.org/new-java/67881-loops-recursion-get-every-permutation-arraylist-elements-order-matters.html

    public  ArrayList<ArrayList<Integer>> permutations(ArrayList<Integer> list)
    {
        return permutations(null, list, null);
    }

    public ArrayList<ArrayList<Integer>> permutations(ArrayList<Integer> prefix, ArrayList<Integer> suffix, ArrayList<ArrayList<Integer>> output)
    {
        if(prefix == null)
            prefix = new ArrayList<Integer>();
        if(output == null)
            output = new ArrayList<ArrayList<Integer>>();

        if(suffix.size() == 1)
        {
            ArrayList<Integer> newElement = new ArrayList<Integer>(prefix);
            newElement.addAll(suffix);
            output.add(newElement);
            return output;
        }

        for(int i = 0; i < suffix.size(); i++)
        {
            ArrayList<Integer> newPrefix = new ArrayList<Integer>(prefix);
            newPrefix.add(suffix.get(i));
            ArrayList<Integer> newSuffix = new ArrayList<Integer>(suffix);
            newSuffix.remove(i);
            permutations(newPrefix,newSuffix,output);
        }

        return output;
    }
}
