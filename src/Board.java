public class Board {
    private String board;
    private String solution;

    private int boardSizex;
    private int boardSizey;

    private int playerx;
    private int playery;

    public Board(String board, String solution, int boardSizex, int boardSizey, int playerx, int playery) {
        this.board = board;
        this.solution = solution;
        this.boardSizex = boardSizex;
        this.boardSizey = boardSizey;
        this.playerx = playerx;
        this.playery = playery;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public int getPlayerx() {
        return playerx;
    }

    public void setPlayerx(int playerx) {
        this.playerx = playerx;
    }

    public int getPlayery() {
        return playery;
    }

    public void setPlayery(int playery) {
        this.playery = playery;
    }

    public void printBoard(int cols){
        for(int i = 0; i < board.length(); i++){
            if(i % cols == 0){
                System.out.print("\n");
            }
            System.out.printf("%c",board.charAt(i));
        }


        System.out.println("\n");
        System.out.println("\n");
    }

    public int getBoardSizex() {
        return boardSizex;
    }

    public void setBoardSizex(int boardSizex) {
        this.boardSizex = boardSizex;
    }

    public int getBoardSizey() {
        return boardSizey;
    }

    public void setBoardSizey(int boardSizey) {
        this.boardSizey = boardSizey;
    }
}
