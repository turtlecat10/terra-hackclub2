public class CheckLegal {
    public static boolean pieceMove(int prevRow, int prevCol, int row, int col){
        String piece = Main.currentBoard[prevRow][prevCol];
        String nextPiece = Main.currentBoard[row][col];
        int distY = row - prevRow;
        int distX = col - prevCol;
        if(piece == null || piece.isEmpty() || piece.equals("0")){
            return false;
        }
        if(piece.charAt(0) == 'w' && !Main.whiteTurn){
            return false;
        }else if(piece.charAt(0) == 'b' && Main.whiteTurn){
            return false;
        }
        if(!nextPiece.equals("0") && piece.charAt(0) == nextPiece.charAt(0)){
            return false;
        }
        if(piece.charAt(1) == 'r'){
            if(!isRookMove(distX,distY,prevRow,prevCol)){
                return false;
            }
        }else if(piece.charAt(1) == 'n'){
            if(!isKnightMove(distX,distY)){
                return false;
            }
            
        }else if(piece.charAt(1) == 'b'){
            if(!isBishopMove(distX,distY,prevRow,prevCol)){
                return false;
            }
        }else if(piece.charAt(1) == 'q'){
            if(!isQueenMove(distX,distY,prevRow,prevCol)){
                return false;
            }
        }else if(piece.charAt(1) == 'k'){
            if(!isKingMove(distX,distY,prevRow,prevCol)){
                return false;
            }
        }else if(piece.charAt(1) == 'p'){
            if(!isPawnMove(distX,distY,prevRow,prevCol)){
                return false;
            }
        }
        Main.tempBoard[row][col] = Main.tempBoard[prevRow][prevCol];
        Main.tempBoard[prevRow][prevCol] = "0";
        if(Main.enpassant && piece.charAt(1) == 'p'){
            Main.tempBoard[prevRow][col] = "0";
        }
        boolean check = isCheck();
        Run.tempReset();
        if(check){
            Main.enpassant = false;
            return false;
        }
        return true;
    }
    public static boolean isRookMove(int distX, int distY,int row, int col){
        if(distX != 0 && distY != 0){
            return false;
        }
        if(isCollision(distX,distY,row,col)){
            return false;
        }
        return true;

    }
    public static boolean isKnightMove(int distX, int distY){
        int absX = Math.abs(distX);
        int absY = Math.abs(distY);
        if(!((absX == 2 && absY == 1) || (absX == 1 && absY == 2))){
            return false;
        }
        return true;
    }
    public static boolean isBishopMove(int distX, int distY, int row, int col){
        int absX = Math.abs(distX);
        int absY = Math.abs(distY);
        if(absX != absY){
            return false;
        }
        if(isCollision(distX,distY,row,col)){
            return false;
        }
        return true;

    }
    public static boolean isQueenMove(int distX, int distY, int row, int col){
        boolean isStraight = !(distX != 0 && distY != 0);
        boolean isDiagonal = (Math.abs(distX) == Math.abs(distY));
        if(!isStraight && !isDiagonal){
            return false;
        }
        if(isCollision(distX,distY,row,col)){
            return false;
        }
        return true;
    }
    public static boolean isKingMove(int distX, int distY, int row, int col){
        if(Math.abs(distX) == 2 && distY == 0 && (row == 0 || row == 7)){
            if(isCastle(distX,distY,row,col)){
                Main.castling = true;
                return true;
            }else{
                return false;
            }
        }else{
            if(Math.abs(distX) > 1 || Math.abs(distY) > 1){
                return false;
            }
        }
        return true;
    }
    public static boolean isPawnMove(int distX, int distY, int row, int col){
        int newRow = row + distY;
        int newCol = col + distX;

        String pawn = Main.tempBoard[row][col];
        String targetPiece = Main.tempBoard[newRow][newCol];
        char color = pawn.charAt(0);
        if(color == 'w'){
            if(distX == 0 && distY == -1 && targetPiece.equals("0")){
                return true;
            }
            if(distX == 0 && distY == -2 && row == 6 && targetPiece.equals("0")){
                return !isCollision(distX,distY,row,col);
            }
            if(Math.abs(distX) == 1 && distY == -1 && !targetPiece.equals("0")){
                return true;
            }
            if(row == 3 && distY == -1 && Math.abs(distX) == 1 && targetPiece.equals("0")){
                if(newCol == Main.enpassantCol){
                    Main.enpassant = true;
                    return true;
                }
            }
        }else{
            if(distX == 0 && distY == 1 && targetPiece.equals("0")){
                return true;
            }
            if(distX == 0 && distY == 2 && row == 1 && targetPiece.equals("0")){
                return !isCollision(distX,distY,row,col);
            }
            if(Math.abs(distX) == 1 && distY == 1 && !targetPiece.equals("0")){
                return true;
            }
            if(row == 4 && distY == 1 && Math.abs(distX) == 1 && targetPiece.equals("0")){
                if(newCol == Main.enpassantCol){
                    Main.enpassant = true;
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean isCollision(int distX, int distY, int prevRow, int prevCol){
        int directionX;
        int directionY;
        if(distX > 0){
            directionX = 1;
        }else if(distX == 0){
            directionX = 0;
        }else{
            directionX = -1;
        }
        if(distY > 0){
            directionY = 1;
        }else if(distY == 0){
            directionY = 0;
        }else{
            directionY = -1;
        }
        int totalSteps = Math.max(Math.abs(distX),Math.abs(distY));
        int checkRow = prevRow;
        int checkCol = prevCol;
        for(int i = 1; i < totalSteps; i++){
            checkRow += directionY;
            checkCol += directionX;
            if(!Main.tempBoard[checkRow][checkCol].equals("0")){
                return true;
            }
        }
        return false;
    }
    public static boolean isCastle(int distX, int distY, int row, int col){
        if(isCheck()){
            return false;
        }
        boolean[] rights;
        if(Main.whiteTurn){
            rights = Main.whiteCastle;
        }else{
            rights = Main.blackCastle;
        }
        if(rights[1]){
            return false;
        }
        int directionX = (distX > 0) ? 1 : -1;
        String kingPiece = Main.currentBoard[row][col];
        if(distX == 2){
            if(rights[2]){
                return false;
            }
            if(isCollision(3,0,row,col)){
                return false;
            }
        }else if (distX == -2){
            if(rights[0]){
                return false;
            }
            if(isCollision(-4,0,row,col)){
                return false;
            }
        }
        int stepCol = col;
        for(int i = 1; i <= 2; i++){
            int nextCol = stepCol + directionX;
            Main.tempBoard[row][nextCol] = kingPiece;
            Main.tempBoard[row][col] = "0";
            boolean stepCausesCheck = isCheck();
            Run.tempReset();
            if(stepCausesCheck){
                return false;
            }
            stepCol = nextCol;
        }
        return true;
    }
    public static boolean isEnpassant(){

        return true;
    }
    public static boolean isCheck(){
        char myColor = 'w';
        char enemyColor = 'b';
        if(!Main.prevPiece.equals("0")){
            myColor = Main.prevPiece.charAt(0);
            enemyColor = (myColor == 'w') ? 'b' : 'w';
        }else{
            myColor = Main.whiteTurn ? 'w' : 'b';
            enemyColor = Main.whiteTurn ? 'b' : 'w';
        }
        int kingRow = -1;
        int kingCol = -1;
        String kingCode = myColor+"k";

        for(int i = 0; i < 8; i++){
            for(int l = 0; l < 8; l++){
                String piece = Main.tempBoard[i][l];
                if(piece.equals("0") || piece.isEmpty()){
                    continue;
                }
                if(piece.equals(kingCode)){
                    kingRow = i;
                    kingCol = l;
                }
            }
        }
        if(kingRow == -1 || kingCol == -1){
            return false;
        }
        for(int i = 0; i < 8; i++){
            for(int l = 0; l < 8; l++){
                String enemyPiece = Main.tempBoard[i][l];
                if(enemyPiece.equals("0") || enemyPiece.isEmpty()){
                    continue;
                }
                if(enemyPiece.charAt(0) == enemyColor){
                    int distX = kingCol - l;
                    int distY = kingRow - i;
                    char type = enemyPiece.charAt(1);
                    boolean canAttack = false;
                    if(type == 'r'){
                        canAttack = isRookMove(distX, distY, i, l);
                    }else if(type == 'n'){
                        canAttack = isKnightMove(distX ,distY);
                    }else if(type == 'b'){
                        canAttack = isBishopMove(distX, distY, i, l);
                    }else if(type == 'q'){
                        canAttack = isQueenMove(distX, distY, i, l);
                    }else if(type == 'k'){
                        canAttack = (Math.abs(distX) <=1 && Math.abs(distY) <= 1);
                    }else if(type == 'p'){
                        canAttack = isPawnMove(distX, distY, i, l);
                    }
                    if(canAttack){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
