import java.util.Arrays;

public class KnightsTour {

    static int [] yMove = {2,-2,1,-1,2,-2,1,-1};
    static int [] xMove = {1,1,2,2,-1,-1,-2,-2};

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
        int size = board.length;
        int nextX, nextY;
        if(moveNum == size*size){
            return true;
        }
        for(int i =0; i < 8; i++){
            nextX = x + xMove[i];
            nextY = y + yMove[i];
            if(safeMove(nextX, nextY, board)){
                board[nextY][nextX] = moveNum;
                if(solveNoHeurHelper(nextX, nextY, moveNum+1, board,
                        xMove, yMove)){
                    return true;
                }else{
                    board[nextY][nextX] = -1;
                }
            }
        }
        return false;
    }

    static boolean solveNoHeur(int[][] board, int startX, int startY){

        if(!solveNoHeurHelper(startX, startY, 1, board, xMove, yMove)){
            printBoard(board);
            System.out.println("No Solution");
            return false;
        }else{
            printBoard(board);
        }
        return true;
    }

    static int getDegree(int[][] board, int x, int y){
        int count = 0;
        for(int i=0; i< 8; i++){
            int nextX = x + xMove[i];
            int nextY = y + yMove[i];
            if(safeMove(nextX, nextY, board)){
                count++;
            }
        }
        return count;
    }

    static int [] nextMove(int[][] board, int startX, int startY){
        int [] nextLoc = new int [2];
        Arrays.fill(nextLoc, -1);
        int currDeg = 9;
        for(int i =0; i < 8; i++){
            int nextX = startX + xMove[i];
            int nextY = startY + yMove[i];
            int c = getDegree(board, nextX, nextY);
            if(safeMove(nextX, nextY, board) &&
                   c  < currDeg){
                nextLoc[0] = nextX;
                nextLoc[1] = nextY;
                currDeg = c;
            }
        }
        return nextLoc;
    }

    static boolean solveHeur(int[][] board, int startX, int startY){
        int [] next = nextMove(board, startX, startY);
        int count = 1;
        while(next[0] != -1){
            board[next[1]][next[0]] = count;
            next = nextMove(board, next[0], next[1]);
            count++;
        }
        if(count < board.length*board.length){
            printBoard(board);
            System.out.println("No Solution");
            return false;
        }else{
            printBoard(board);
            return true;
        }
    }

    public static void main(String[] args){
        boolean heuris = false;
        int boardSize = 0;
        int startX = -1;
        int startY = -1;
        if(args.length < 1){
            System.out.println("Program requires board size and start position");
            return;
        }else if(args.length == 4){
            heuris = true;
            boardSize = Integer.parseInt(args[1]);
            startX = Integer.parseInt(args[2]);
            startY = Integer.parseInt(args[3]);
        } else if(args.length == 3){
            boardSize = Integer.parseInt(args[0]);
            startX = Integer.parseInt(args[1]);
            startY = Integer.parseInt(args[2]);
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
            long startTime = System.nanoTime();
            solveHeur(board, startX, startY);
            long endTime = System.nanoTime();
            System.out.println("Time to process(ms): " + (endTime-startTime)/1000000);
        }else{
            long startTime = System.nanoTime();
            solveNoHeur(board, startX, startY);
            long endTime = System.nanoTime();
            System.out.println("Time to process(ms): " + (endTime-startTime)/1000000 );
        }

    }
}
