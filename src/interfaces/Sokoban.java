package interfaces;

public interface Sokoban {

    Board move(Board b, int dx, int dy);
    int getHeuristic(Board b);
    int getDepth();
    int getIncrement();
    void setDepth(int depth);
    int getInitialDepth();
    Heuristic getHeuristic();

}
