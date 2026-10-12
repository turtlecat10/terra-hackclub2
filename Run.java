public class Run {
    
    public static void turn(int row, int col){

        if(Main.prevPiece.equals("0")){
            String pieceClicked = Main.currentBoard[row][col];
            if(!pieceClicked.equals("0")){
                Main.prevPiece = pieceClicked;
                Main.prevRow = row;
                Main.prevCol = col;
            }else{
                Main.selectBoard = new int[8][8];
            }
        }else{
            if(CheckLegal.pieceMove(Main.prevRow,Main.prevCol,row,col)){
                run(row,col);
            }else{
                Main.selectBoard = new int[8][8];
                Main.prevPiece = "0";
                Main.prevRow = -1;
                Main.prevCol = -1;
            }
        }
        Main.surface.repaint();

    }
    public static void run(int row, int col){
        Main.currentBoard[row][col] = Main.prevPiece;
        Main.currentBoard[Main.prevRow][Main.prevCol] = "0";
        Main.selectBoard = new int[8][8];
        Main.prevPiece = "0";
        Main.prevRow = -1;
        Main.prevCol = -1;
    }
}
