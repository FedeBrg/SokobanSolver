import implementations.*;
import interfaces.Board;
import interfaces.Heuristic;
import interfaces.SearchMethod;
import interfaces.Sokoban;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class App {

    static void printSolution(Board b){
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
                for (int i = 0; i < b.getBoardSizex(); i++) {
                    if(board[t] == 'x'){
                        System.out.print(' ');
                    }
                    else{
                        System.out.print(board[t]);

                    }
                    j++;
                    t++;
                }
                System.out.println();
            }
            j=0;
        }

    }


    public static void main(String[] arg){

        int searchMethod;
        int heuristic;
        int depth;
        boolean deadlockCheck;

        Properties prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream("config.properties");
            prop.load(fis);

        } catch (IOException e) {
            e.printStackTrace();
        }

        searchMethod = Integer.parseInt(prop.getProperty("searchMethod"));
        heuristic = Integer.parseInt(prop.getProperty("heuristic"));
        depth = Integer.parseInt(prop.getProperty("depth"));
        deadlockCheck = Integer.parseInt(prop.getProperty("deadlockCheck")) == 1;

        SearchMethod sm = getSearchMethod(searchMethod);
        Heuristic h = getHeuristic(heuristic);

        Sokoban s = new SokobanImpl(h,depth,deadlockCheck);


        String level = prop.getProperty("board");
        int playerx = Integer.parseInt(prop.getProperty("playerx"));
        int playery = Integer.parseInt(prop.getProperty("playery"));
        int boardx = Integer.parseInt(prop.getProperty("boardx"));
        int boardy = Integer.parseInt(prop.getProperty("boardy"));

        Board b = new BoardImpl(level,level,boardx,boardy,playerx,playery);

        if(sm != null && h!=null){
            long t0 = System.currentTimeMillis();
            Board sol = sm.findPath(b,s);
            long tf = System.currentTimeMillis();
            if(sol != null){
                printSolution(sol);
            }
            System.out.printf("Total runtime: %dms" ,tf-t0);
        }
        else{
            System.out.println("Error in configuration file");
        }


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






    }

    private static Heuristic getHeuristic(int heuristic) {
        switch (heuristic){
            case 0:
                return new Trivial();
            case 1:
                return new Hamming();
            case 2:
                return new Manhattan();
            case 3:
                return new ImprovedManhattan();
            default:
                return null;
        }
    }

    private static SearchMethod getSearchMethod(int searchMethod) {
        switch (searchMethod){
            case 1:
                return new DFS();
            case 2:
                return new BFS();
            case 3:
                return new IDDFS();
            case 4:
                return new GlobalGreedy();
            case 5:
                return new AStar();
            case 6:
                return new IDAStar();
            default:
                return null;
        }

    }


}
