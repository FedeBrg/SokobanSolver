import javafx.util.Pair;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.LongStream;

public class Sokoban {




    void printSolution(Board b){
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
                    System.out.print(board[t]);
                    j++;
                    t++;
                }
                System.out.println();
            }
            j=0;
        }

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


        Sokoban s = new Sokoban();

        Board b = new Board(level2,level2,15,10,7,7);
        Board b2 = new Board(level4, level4, 13, 13, 5,10);


        String dead =   "#######" +
                        "#     #" +
                        "#     #" +
                        "#. #  #" +
                        "#. $$ #" +
                        "#.$   #" +
                        "#.#$ @#" +
                        "#######";

    }







}
