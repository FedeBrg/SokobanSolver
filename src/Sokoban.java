import java.util.Arrays;

public class Sokoban {

    int cols;

    Sokoban(){

    }

    Board movePlayer(Board b, int dx, int dy){
        int x = b.getPlayerx();
        int y = b.getPlayery();


        int newPos = x+dx+(y+dy)*cols;
        int playerPos = x+y*cols;
        char replacement;
        char leftBehind;

        char c = b.getBoard().charAt(newPos);
        if(c == '#' || c == '$'){
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


        char[] boardArray = b.getBoard().toCharArray();
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
        if(c == '%'){
            leftBehind = '.';
        }
        else{
            leftBehind = ' ';
        }

        char[] boardArray = b.getBoard().toCharArray();
        boardArray[x+y*cols] = leftBehind;
        boardArray[newPos] = replacement;


        return movePlayer(new Board(new String(boardArray),b.getSolution(),x,y),dx,dy);
    }

    boolean isSolution(String board){
        return false;
    }


}
