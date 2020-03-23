import Interfaces.Board;

public class App {

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




    }







}
