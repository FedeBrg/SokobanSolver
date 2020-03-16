import java.util.Arrays;

public class Sokoban {

    int cols;

    Sokoban(){
        cols = 15;
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

    boolean isSolution(String board){
        return false;
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
        Board newB = s.pushBox(b,1,0);
        if(newB != null) {
            s.printBoard(newB);
        }
    }


}
