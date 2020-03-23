package interfaces;

public interface Board extends Comparable<Board> {

    String getBoard();
    String getSolution();
    int getPlayerx();
    int getPlayery();
    int getBoardSizex();
    int getBoardSizey();
    int getHeuristic();
    void setHeuristic(int heuristic);
    int getCost();
    void setCost(int cost);

}
