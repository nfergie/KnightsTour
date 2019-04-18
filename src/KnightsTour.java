import java.util.Arrays;

public class KnightsTour {

    static boolean safeMove(int x, int y, int[][] board){
        int xsize = board.length;
        int ysize = board.length;
        return( x >= 0 && x < xsize &&
                y >= 0 && y < ysize &&
                board[y][x] == -1);
    }

    static void printBoard(int[][] board){
        for(int i = 0; i < board.length; i++){
            for(int j =0; j < board[board.length-1].length; j++){
                System.out.print(board[i][j]+ " ");
            }
            System.out.println();
        }
    }

    static boolean solveNoHeurHelper(int x, int y, int moveNum, int [][] board,
                                     int[] xMove, int[] yMove){

    }

    static boolean solveNoHeur(int[][] board, int startX, int startY){
        int yMove[] = {2, 1, -1, -2, -2, -1, 1, 2};
        int xMove[] = {1, 2, 2, 1, -1, -2, -2, -1};

        if(!solveNoHeurHelper(startX, startY, 1, board, xMove, yMove)){
            System.out.println("No Solution");
            return false;
        }else{
            printBoard(board);
        }
        return true;
    }

    static boolean solveHeur(int[][] board){
        return true;
    }

    public static void main(String[] args){
        boolean heuris = false;
        int boardSize = 0;
        int startX = -1;
        int startY = -1;
        if(args.length < 1){
            System.out.println("Program requires board size");
            return;
        }else if(args.length == 4){
            heuris = true;
            boardSize = Integer.parseInt(args[1]);
            startX = Integer.parseInt(args[2]);
            startY = Integer.parseInt(args[3]);
        } else if(args.length == 3){
            boardSize = Integer.parseInt(args[1]);
            startX = Integer.parseInt(args[2]);
            startY = Integer.parseInt(args[3]);
        }else{
            System.out.println("Correct usage is [-h] boardSize startLocX startLocY");
            return;
        }

        //create board of right size
        int [][] board = new int[boardSize][boardSize];
        for(int[] row : board){
            Arrays.fill(row, -1);
        }
        board[startY][startX] = 0;

        if(heuris){
            solveHeur(board);
        }else{
            solveNoHeur(board, startX, startY);
        }

    }
}
