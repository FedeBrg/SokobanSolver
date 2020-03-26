package implementations;

import interfaces.Board;

public class BoardImpl implements Board {

    private String board;
    private String solution;

    private int boardSizex;
    private int boardSizey;

    private int playerx;
    private int playery;

    private int heuristic;
    private int cost;


    public BoardImpl (String board, String solution, int boardSizex, int boardSizey, int playerx, int playery) {
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


    public String getSolution() {
        return solution;
    }


    public int getPlayerx() {
        return playerx;
    }


    public int getPlayery() {
        return playery;
    }


    public int getBoardSizex() {
        return boardSizex;
    }


    public int getBoardSizey() {
        return boardSizey;
    }


    public int getHeuristic() {
        return heuristic;
    }


    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }


    public int getCost() {
        return cost;
    }


    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public int compareTo(Board o) {

        int dif = heuristic+cost - o.getHeuristic()-o.getCost();
        if(dif == 0){
            return heuristic - o.getHeuristic();
        }
        return dif;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Board)){
            return false;
        }

        Board other = (Board) obj;
        return board.equals(other.getBoard());
    }

    public void printBoard(){
        System.out.print(board.charAt(0));
        for(int i = 1; i < board.length(); i++){
            if(i%boardSizex == 0){
                System.out.print("\n");
            }

            System.out.print(board.charAt(i));
        }

        System.out.print("\n");
    }
}
