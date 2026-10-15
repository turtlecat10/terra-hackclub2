public class Run {
    
    public static void turn(int row, int col){
        tempReset();
        if(Main.promotion){
            if(Main.promotionSquare[0] == 0){
                if(row <= 3 && col == Main.promotionSquare[1]){
                    Main.promotion = false;
                    Main.selectBoard = new int[8][8];
                    Main.selectBoard[Main.prevRow][Main.prevCol] = 1;
                    Main.selectBoard[row][col] = 1;
                    setPrev();
                    Main.prevRow = -1;
                    Main.prevCol = -1;
                    Main.blackTaken.add("wp");
                    switch (row){
                        case 0:
                            Main.currentBoard[Main.promotionSquare[0]][Main.promotionSquare[1]] = "wq";
                            Main.whiteScore += 8;
                            break;
                        case 1:
                            Main.currentBoard[Main.promotionSquare[0]][Main.promotionSquare[1]] = "wr";
                            Main.whiteScore += 4;
                            break;
                        case 2:
                            Main.currentBoard[Main.promotionSquare[0]][Main.promotionSquare[1]] = "wb";
                            Main.whiteScore += 2;
                            break;
                        case 3:
                            Main.currentBoard[Main.promotionSquare[0]][Main.promotionSquare[1]] = "wn";
                            Main.whiteScore += 2;
                            break;
                    }
                }
            }else{
                if(row >= 4 && col == Main.promotionSquare[1]){
                    Main.promotion = false;
                    Main.selectBoard = new int[8][8];
                    Main.selectBoard[Main.prevRow][Main.prevCol] = 1;
                    Main.selectBoard[row][col] = 1;
                    setPrev();
                    Main.prevRow = -1;
                    Main.prevCol = -1;
                    Main.whiteTaken.add("bp");
                    switch(row){
                        case 4:
                            Main.currentBoard[Main.promotionSquare[0]][Main.promotionSquare[1]] = "bn";
                            Main.blackScore += 2;
                            break;
                        case 5:
                            Main.currentBoard[Main.promotionSquare[0]][Main.promotionSquare[1]] = "bb";
                            Main.blackScore += 2;
                            break;
                        case 6:
                            Main.currentBoard[Main.promotionSquare[0]][Main.promotionSquare[1]] = "br";
                            Main.blackScore += 4;
                            break;
                        case 7:
                            Main.currentBoard[Main.promotionSquare[0]][Main.promotionSquare[1]] = "bq";
                            Main.blackScore += 8;
                            break;
                    }
                }
            }
        }else if(Main.prevPiece.equals("0")){
            String pieceClicked = Main.currentBoard[row][col];
            if(!pieceClicked.equals("0")){
                Main.prevPiece = pieceClicked;
                Main.prevRow = row;
                Main.prevCol = col;
                // Main.selectBoard = new int[8][8];
                Main.selectBoard[row][col] = 1;
                DrawingCanvas.hoverRow = row;
                DrawingCanvas.hoverCol = col;
            }else{
                Main.selectBoard = new int[8][8];
            }
        }else{
            tempReset();
            if(CheckLegal.pieceMove(Main.prevRow,Main.prevCol,row,col)){
                Main.selectBoard[row][col] = 1;
                run(row,col);
                DrawingCanvas.hoverRow = -1;
                DrawingCanvas.hoverCol = -1;
            }else{
                if(!Main.pieceChange){
                    Main.selectBoard = new int[8][8];
                    Main.prevPiece = "0";
                    Main.prevRow = -1;
                    Main.prevCol = -1;
                    DrawingCanvas.hoverRow = -1;
                    DrawingCanvas.hoverCol = -1;
                }
            }
        }
        Main.surface.repaint();
        if(CheckLegal.checkmate()){
            System.out.println("Checkmate");
        }
        if(CheckLegal.stalemate()){
            System.out.println("Stalemate");
        }
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
            if(Main.whiteTurn){
                Main.whiteScore += 1;
                Main.whiteTaken.add(Main.currentBoard[Main.prevRow][col]);
            }else{
                Main.blackScore += 1;
                Main.blackTaken.add(Main.currentBoard[Main.prevRow][col]);
            }
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
            if(!Main.currentBoard[row][col].equals("0")){
                if(Main.whiteTurn){
                    scoreAddWhite(Main.currentBoard[row][col]);
                    Main.whiteTaken.add(Main.currentBoard[row][col]);
                }else{
                    scoreAddBlack(Main.currentBoard[row][col]);
                    Main.blackTaken.add(Main.currentBoard[row][col]);
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
        if(Main.whiteTurn){
            if(Main.prevPiece.charAt(1) == 'p' && row == 0){
                Main.promotion = true;
                Main.promotionSquare[0] = row;
                Main.promotionSquare[1] = col;
            }
        }else{
            if(Main.prevPiece.charAt(1) == 'p' && row == 7){
                Main.promotionSquare[0] = row;
                Main.promotionSquare[1] = col;
                Main.promotion = true;
            }
        }
        Main.castling = false;
        Main.enpassant = false;
        setPrev();
        Main.prevPiece = "0";
        Main.selectBoard = new int[8][8];
        Main.pieceChange = false;
        if(!Main.promotion){
            Main.prevRow = -1;
            Main.prevCol = -1;
        }
        DrawingCanvas.hoverRow = -1;
        DrawingCanvas.hoverCol = -1;
        Main.whiteTurn = !Main.whiteTurn;
        tempReset();
    }
    public static void tempReset(){
        for(int i = 0; i < 8; i++){
            Main.tempBoard[i] = Main.currentBoard[i].clone();
        }
    }
    public static void setPrev(){
        for(int i = 0; i < 8; i++){
            Main.prevSelect[i] = Main.selectBoard[i].clone();
        }
    }
    public static void scoreAddBlack(String piece){
        switch (piece){
            case "wp":
                Main.blackScore += 1;
                break;
            case "wn":
                Main.blackScore += 3;
                break;
            case "wb":
                Main.blackScore += 3;
                break;
            case "wr":
                Main.blackScore += 5;
                break;
            case "wq":
                Main.blackScore += 9;
                break;
        }
    }
    public static void scoreAddWhite(String piece){
        switch (piece){
            case "bp":
                Main.whiteScore += 1;
                break;
            case "bn":
                Main.whiteScore += 3;
                break;
            case "bb":
                Main.whiteScore += 3;
                break;
            case "br":
                Main.whiteScore += 5;
                break;
            case "bq":
                Main.whiteScore += 9;
                break;
        }
    }
}
