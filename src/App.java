import implementations.*;
import interfaces.Board;
import interfaces.Heuristic;
import interfaces.SearchMethod;
import interfaces.Sokoban;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class App {

    static void printSolution(Board b){
        if(b == null){
            return;
        }

        StringBuilder sb = new StringBuilder();

        char[] board = b.getSolution().toCharArray();
        char[] aux = b.getBoard().toCharArray();
        int j = 0;
        int t = 0;
        while(t < board.length) {
            sb.append(String.format("\n------- PASO N° %d -------\n",(t/(b.getBoardSizex()*b.getBoardSizey()))));
            //System.out.printf("\n------- PASO N° %d -------\n",(t/(b.getBoardSizex()*b.getBoardSizey())));

            while (j < aux.length) {
                for (int i = 0; i < b.getBoardSizex(); i++) {
                    if(board[t] == 'x'){
                        sb.append(' ');
                        //System.out.print(' ');
                    }
                    else{
                        sb.append(board[t]);
                        //System.out.print(board[t]);

                    }
                    j++;
                    t++;
                }
                sb.append('\n');
                //System.out.println();
            }
            j=0;
        }

        try {
            FileWriter myWriter = new FileWriter("solution.txt");
            myWriter.write(sb.toString());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }




    public static void main(String[] arg){

        int searchMethod = 0;
        int heuristic = 0;
        int depth = 0;
        int increment = 0;
        boolean deadlockCheck = false;

        Properties prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream("config5.properties");
            prop.load(fis);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            searchMethod = Integer.parseInt(prop.getProperty("searchMethod"));
            heuristic = Integer.parseInt(prop.getProperty("heuristic"));
            depth = Integer.parseInt(prop.getProperty("depth"));
            deadlockCheck = Integer.parseInt(prop.getProperty("deadlockCheck")) == 1;
            increment = Integer.parseInt(prop.getProperty("iddfsInc"));

        } catch (NumberFormatException e){
            System.out.println("Missing parameters in config file!");
            System.exit(-1);
        }

        Heuristic h = getHeuristic(heuristic);
        Sokoban s = new SokobanImpl(h,depth,deadlockCheck, increment);
        SearchMethod sm = getSearchMethod(searchMethod,s);


        Board b = new BoardImpl("","",0,0,0,0);

        try{
            String level = prop.getProperty("board");
            int playerx = Integer.parseInt(prop.getProperty("playerx"));
            int playery = Integer.parseInt(prop.getProperty("playery"));
            int boardx = Integer.parseInt(prop.getProperty("boardx"));
            int boardy = Integer.parseInt(prop.getProperty("boardy"));

            b = new BoardImpl(level,level,boardx,boardy,playerx,playery);

        }catch (NumberFormatException e){
            System.out.println("Missing parameters in config file!");
            System.exit(-1);
        }


        b.setHeuristic(h.getHeuristic(b));
        b.setCost(0);
        long t0 = System.currentTimeMillis();
        Board sol = null;

        try{
            sol = sm.findPath(b,s);
        } catch(OutOfMemoryError e){
            System.out.println("System ran out of memory.");
        }

        long tf = System.currentTimeMillis();
        if(sol != null){
            printSolution(sol);
        }
        System.out.printf("Total runtime: %dms\n" ,tf-t0);
        System.out.printf("%s\n",sm);



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

    private static SearchMethod getSearchMethod(int searchMethod, Sokoban s) {
        switch (searchMethod){
            case 1:
                return new DFS(s);
            case 2:
                return new BFS(s);
            case 3:
                return new IDDFS(s);
            case 4:
                return new GlobalGreedy(s);
            case 5:
                return new AStar(s);
            case 6:
                return new IDAStar(s);
            default:
                return new BFS(s);
        }

    }


}
