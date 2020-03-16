public class Sokoban {

    int cols;

    Sokoban(){

    }

    String movePlayer(int x, int y, int dx, int dy, String board){
        int newPos = x+dx+(y+dy)*cols;
        if(board.charAt(newPos) == '#' || board.charAt(newPos) == '@'){
            return null;
        }

        return null;
    }

    String pushBox(int x, int y, int dx, int dy, String board){
        return null;
    }

    boolean isSolution(String board){
        return false;
    }


}
