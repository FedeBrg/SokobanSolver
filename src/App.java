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
            System.out.printf("\n------- PASO N° %d -------\n",(t/(b.getBoardSizex()*b.getBoardSizey())));

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

        b.setHeuristic(h.getHeuristic(b));
        b.setCost(0);
        long t0 = System.currentTimeMillis();
        Board sol = null;

        try{
            sol = sm.findPath(b,s);
        } catch(OutOfMemoryError e){
            System.out.println("System run out of memory.");
            e.printStackTrace();
        }

        long tf = System.currentTimeMillis();
        if(sol != null){
            printSolution(sol);
        }
        System.out.printf("Total runtime: %dms\n" ,tf-t0);
        System.out.printf("Search method used: %s\n",sm);
        System.out.printf("Heuristic used: %s\n",h);




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
            case 1:
                return new Trivial();
            case 2:
                return new Hamming();
            case 3:
                return new Manhattan();
            case 4:
                return new ImprovedManhattan();
            default:
                return new Trivial();
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
                return new BFS();
        }

    }


}
