package interfaces;

public interface Sokoban {

    Board move(Board b, int dx, int dy);
    int getHeuristic(Board b);
    boolean isOptimized();
    int getDepth();
    int getIncrement();
    void setDepth(int depth);
    int getInitialDepth();
    Heuristic getHeuristic();

}
