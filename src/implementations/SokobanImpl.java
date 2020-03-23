package implementations;

import interfaces.Board;
import interfaces.Heuristic;
import interfaces.Sokoban;

public class SokobanImpl implements Sokoban {

    private Heuristic heuristic;
    private int depth;

    public SokobanImpl(Heuristic heuristic, int depth) {
        this.heuristic = heuristic;
        this.depth = depth;
    }

    public int getDepth(){
        return depth;
    }

    public void setDepth(int depth){
        this.depth = depth;
    }

    public int getHeuristic(Board b){
        return heuristic.getHeuristic(b);
    }

    public Board move(Board b, int dx, int dy){
        int x = b.getPlayerx();
        int y = b.getPlayery();
        int newPos = x+dx + (y+dy)*b.getBoardSizex();

        char c = b.getBoard().charAt(newPos);
        if(c == '$' || c == '%'){
            return pushBox(b,dx,dy);
        }
        else{
            return movePlayer(b,dx,dy);
        }
    }

    private Board movePlayer(Board b, int dx, int dy) {
        int x = b.getPlayerx();
        int y = b.getPlayery();


        int newPos = x+dx+(y+dy)*b.getBoardSizex();
        int playerPos = x+y*b.getBoardSizex();
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

        return new BoardImpl(new String(boardArray), b.getSolution().concat(new String((boardArray))), b.getBoardSizex(),b.getBoardSizey(), x + dx, y + dy);
    }

    private Board pushBox(Board b, int dx, int dy){
        int x = b.getPlayerx();
        int y = b.getPlayery();
        int newPos = x+(2*dx) + (y+(2*dy))*b.getBoardSizex();
        int boxPos = x+dx+(y+dy)*b.getBoardSizex();

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

        Board rtb = movePlayer(new BoardImpl(new String(boardArray),b.getSolution(), b.getBoardSizex(), b.getBoardSizey(),x,y),dx,dy);

        if(replacement != '%' && (checkEdgeDeadlock(b,x+(2*dx), (y+(2*dy))) || checkMapDeadlock(b)) && !Utilities.isSolution(rtb)){
            return null;
        }

        return rtb;
    }

    boolean checkEdgeDeadlock(Board b, int xBox, int yBox){
        int [][] positions = {{-1,-1},{1,-1},{1,1},{-1,1}};


        for (int[] pos : positions) {
            int p1 = xBox+pos[0] + yBox*b.getBoardSizex();
            int p2 = xBox + (yBox+pos[1])*b.getBoardSizex();
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

}
