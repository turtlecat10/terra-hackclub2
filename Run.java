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
            tempReset();
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
        if(Main.castling){
            Main.currentBoard[row][col] = Main.prevPiece;
            Main.currentBoard[Main.prevRow][Main.prevCol] = "0";
            if(col == 6){
                Main.currentBoard[row][5] = Main.currentBoard[row][7];
                Main.currentBoard[row][7] = "0";
            }else if (col == 2) {
                Main.currentBoard[row][3] = Main.currentBoard[row][0];
                Main.currentBoard[row][0] = "0";
            }
            if(Main.whiteTurn){
                Main.whiteCastle[1] = true;
            }
        }else if(Main.enpassant){
            Main.currentBoard[row][col] = Main.prevPiece;
            Main.currentBoard[Main.prevRow][Main.prevCol] = "0";
            Main.currentBoard[Main.prevRow][col] = "0";

        }else{
            String targetPiece = Main.currentBoard[row][col];
            if(!targetPiece.equals("0") && targetPiece.charAt(1) == 'r'){
                if(Main.whiteTurn){
                    if(col == 0 && row == 0){
                        Main.blackCastle[0] = true;
                    }
                    if(col == 7 && row == 0){
                        Main.blackCastle[2] = true;
                    }
                }else{
                    if(col == 0 && row == 7){
                        Main.whiteCastle[0] = true;
                    }
                    if(col == 7 && row == 7){
                        Main.whiteCastle[2] = true;
                    }
                }
            }
            Main.currentBoard[row][col] = Main.prevPiece;
            Main.currentBoard[Main.prevRow][Main.prevCol] = "0";
        }
        if(Main.prevPiece.charAt(1) == 'k'){
            if(Main.whiteTurn){
                Main.whiteCastle[1] = true;
            }else{
                Main.blackCastle[1] = true;
            }
        }else if (Main.prevPiece.charAt(1) == 'r'){
            if(Main.whiteTurn){
                if(Main.prevCol == 0 && Main.prevRow == 7){
                    Main.whiteCastle[0] = true;
                }else if (Main.prevCol == 7 & Main.prevRow == 7){
                    Main.whiteCastle[2] = true;
                }
            }else{
                if(Main.prevCol == 0 && Main.prevRow == 0){
                    Main.blackCastle[0] = true;
                }else if (Main.prevCol == 7 & Main.prevRow == 0){
                    Main.blackCastle[2] = true;
                }
            }
        }
        if(Main.prevPiece.charAt(1) == 'p' && Math.abs(row - Main.prevRow) == 2){
            Main.enpassantCol = col;
        }else{
            Main.enpassantCol = -1;
        }
        Main.castling = false;
        Main.enpassant = false;
        Main.selectBoard = new int[8][8];
        Main.prevPiece = "0";
        Main.prevRow = -1;
        Main.prevCol = -1;
        Main.whiteTurn = !Main.whiteTurn;
        tempReset();
    }
    public static void tempReset(){
        for(int i = 0; i < 8; i++){
            Main.tempBoard[i] = Main.currentBoard[i].clone();
        }
    }
}
