import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.*;

public class Display {
    private static final int squares = 8;
    private static final int squareSize = 60;
    private static final int boarder = 30;
    private static final int circle = 20;
    public static String name1 = "";
    public static String name2 = "";

    private static final Color lightSquare = new Color(235, 236, 208);
    private static final Color highLightSquare = new Color(245, 246, 130);
    private static final Color darkSquare = new Color(115, 149, 82);
    private static final Color highDarkSquare = new Color(185, 202, 67);
    private static final Color boarderColor = new Color(200,200,200);
    private static final Color text = new Color(1,1,1);
    private static final Color promotion = new Color(255,255,255);
    private static HashMap<String, BufferedImage> pieceImages = new HashMap<>(); 

    public static void loadAssets(){
        String[] codes = {"wp","wn","wb","wr","wq","wk","bp","bn","bb","br","bq","bk"};
        for(String code : codes){
            try {
                File imgFile = new File("Assets/" + code + ".png");
                BufferedImage img = ImageIO.read(imgFile);
                pieceImages.put(code, img);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    public static void drawBoard(Graphics2D g2d){
        g2d.setColor(boarderColor);
        g2d.fillRect(0,0,squareSize*8+boarder*2,squareSize*8+boarder*2);
        g2d.setColor(text);
        g2d.drawString(name1,boarder,boarder/2);
        g2d.drawString(name2, boarder, boarder+squareSize*8+boarder/2);
        for(int col = 0; col < squares; col++){
            for(int row = 0; row < squares; row++){
                int x = boarder + (col*squareSize);
                int y = boarder + (row*squareSize);
                if((col+row) % 2 == 0){
                    if((Main.selectBoard[row][col] == 1 || Main.prevSelect[row][col] == 1) && !Main.promotion){
                        g2d.setColor(highLightSquare);
                    }else{
                        g2d.setColor(lightSquare);
                    }
                }else{
                    if((Main.selectBoard[row][col] == 1 || Main.prevSelect[row][col] == 1) && !Main.promotion){
                        g2d.setColor(highDarkSquare);
                    }else{
                        g2d.setColor(darkSquare);
                    }
                }
                g2d.fillRect(x,y, squareSize, squareSize);
            }
        }
    }
    public static void pieces(Graphics2D g2d){
        for(int i = 0; i < squares; i++){
            for(int l = 0; l < squares; l++){
                if(!Main.currentBoard[i][l].equals("0")){

                    String code = Main.currentBoard[i][l];
                    BufferedImage img = pieceImages.get(code);
                    if(img != null){
                        int x = boarder + (l*squareSize);
                        int y = boarder + (i*squareSize);
                        g2d.drawImage(img,x,y,squareSize,squareSize, null);
                    }
                }
            }
        }
    }
    public static void promotion(Graphics2D g2d){
        if(Main.promotion){
            if(Main.promotionSquare[0] == 0){
                g2d.setColor(promotion);
                g2d.fillRect(boarder+squareSize*(Main.promotionSquare[1]),boarder,squareSize,squareSize*4);
                BufferedImage img0 = pieceImages.get("wq");
                BufferedImage img1 = pieceImages.get("wr");
                BufferedImage img2 = pieceImages.get("wb");
                BufferedImage img3 = pieceImages.get("wn");
                g2d.drawImage(img0,boarder+squareSize*(Main.promotionSquare[1]),boarder,squareSize,squareSize,null);
                g2d.drawImage(img1,boarder+squareSize*(Main.promotionSquare[1]),boarder+squareSize,squareSize,squareSize,null);
                g2d.drawImage(img2,boarder+squareSize*(Main.promotionSquare[1]),boarder+squareSize*2,squareSize,squareSize,null);
                g2d.drawImage(img3,boarder+squareSize*(Main.promotionSquare[1]),boarder+squareSize*3,squareSize,squareSize,null);
            }else{
                g2d.setColor(promotion);
                g2d.fillRect(boarder+squareSize*(Main.promotionSquare[1]),boarder+squareSize*4,squareSize,squareSize*4);
                BufferedImage img7 = pieceImages.get("bq");
                BufferedImage img6 = pieceImages.get("br");
                BufferedImage img5 = pieceImages.get("bb");
                BufferedImage img4 = pieceImages.get("bn");
                g2d.drawImage(img4,boarder+squareSize*(Main.promotionSquare[1]),boarder+squareSize*4,squareSize,squareSize,null);
                g2d.drawImage(img5,boarder+squareSize*(Main.promotionSquare[1]),boarder+squareSize*5,squareSize,squareSize,null);
                g2d.drawImage(img6,boarder+squareSize*(Main.promotionSquare[1]),boarder+squareSize*6,squareSize,squareSize,null);
                g2d.drawImage(img7,boarder+squareSize*(Main.promotionSquare[1]),boarder+squareSize*7,squareSize,squareSize,null);
            }
        }
    }
    public static void takenPieces(Graphics2D g2d){
        FontMetrics metrics = g2d.getFontMetrics();
        int width1 = metrics.stringWidth(name1);
        int width2 = metrics.stringWidth(name2);
        Map<String, Integer> blackOrder = Map.of("bp", 0, "bn", 1, "bb", 2, "br", 3, "bq", 4);
        Map<String, Integer> whiteOrder = Map.of("wp", 0, "wn", 1, "wb", 2, "wr", 3, "wq", 4);
        Main.blackTaken.sort(Comparator.comparingInt(piece -> whiteOrder.getOrDefault(piece,Integer.MAX_VALUE)));
        Main.whiteTaken.sort(Comparator.comparingInt(piece -> blackOrder.getOrDefault(piece,Integer.MAX_VALUE)));
        int x = boarder+width1+10;
        int y = 2;
        for(int i = 0; i < Main.blackTaken.size(); i++){
            String code = Main.blackTaken.get(i);
            String prevCode = "0";
            if(i != 0){
                prevCode = Main.blackTaken.get(i-1);
            }else{
                prevCode = "0";
            }

            BufferedImage img = pieceImages.get(code);
            if(img != null){
                if(i != 0){
                    if(prevCode.equals(code)){
                        x += 7;
                    }else{
                        x += 15;
                    }
                }
                g2d.drawImage(img,x,y,24,24,null);
            }
        }
        x += 30;
        y += boarder/2;
        g2d.setColor(text);
        if(Main.whiteScore != Main.blackScore){
            g2d.drawString(Integer.toString(Main.blackScore-Main.whiteScore),x,y);
        }
        x = boarder+width2;
        y = boarder+squareSize*8+3;
        for(int i = 0; i < Main.whiteTaken.size(); i++){
            String prevCode = "0";
            String code = Main.whiteTaken.get(i);
            if(i != 0){
                prevCode = Main.whiteTaken.get(i-1);
            }
            BufferedImage img = pieceImages.get(code);
            if(img != null){
                if(i != 0){
                    if(prevCode.equals(code)){
                        x += 7;
                    }else{
                        x += 15;
                    }
                }
                // int y = boarder+width2+10+i*30;
                g2d.drawImage(img,x,y,24,24,null);
            }
        }
        x += 30;
        y += boarder/2;
        if(Main.whiteScore != Main.blackScore){
        g2d.drawString(Integer.toString(Main.whiteScore-Main.blackScore),x,y);
        }
    }
    public static void drawDots(Graphics2D g2d){
        int currentRow = DrawingCanvas.hoverRow;
        int currentCol = DrawingCanvas.hoverCol;
        if(currentRow != -1 && currentCol != -1){
            String currentPiece = Main.currentBoard[currentRow][currentCol];
            
            if(!currentPiece.equals("0")){
                for(int i = 0; i < 8; i++){
                    for(int l = 0; l < 8; l++){
                        int yes = CheckLegal.attackAble(currentCol,currentRow,i,l,currentPiece);
                        if(yes == 0){
                            g2d.setColor(new Color(0,0,0,65));
                            g2d.fillOval(boarder+i*squareSize+squareSize/2-circle/2,boarder+l*squareSize+squareSize/2-circle/2,circle,circle);
                        }else if(yes == 1){
                            g2d.setStroke(new BasicStroke(5));
                            g2d.setColor(new Color(0,0,0,65));
                            g2d.drawOval(boarder+i*squareSize,boarder+l*squareSize,60,60);
                        }
                    }
                }
            }
        }
    }
    public static void setUp(Graphics2D g2d){
        if(Main.player1){
            Font myFont = new Font("Ariel",Font.PLAIN,30);
            Color color = new Color(0,0,0);
            g2d.setFont(myFont);
            g2d.setColor(color);
            g2d.drawString("Player 1 please enter your name",30,30);
            g2d.drawString(name1,30,60);
        }else{
            Font myFont = new Font("Ariel",Font.PLAIN,30);
            Color color = new Color(0,0,0);
            g2d.setFont(myFont);
            g2d.setColor(color);
            g2d.drawString("Player 2 please enter your name",30,30);
            g2d.drawString(name2,30,60);
        }
    }
    public static void chessClock(Graphics2D g2d){
        int whiteMin = Main.whiteTime / 60;
        int whiteSec = Main.whiteTime % 60;
        String whiteClockText = String.format("%02d:%02d",whiteMin,whiteSec);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Monospaced",Font.BOLD, 24));
        g2d.drawString(whiteClockText,boarder+squareSize*6,boarder+squareSize*8+24);
        int blackMin = Main.blackTime / 60;
        int blackSec = Main.blackTime % 60;
        String blackClockText = String.format("%02d:%02d",blackMin,blackSec);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Monospaced",Font.BOLD, 24));
        g2d.drawString(blackClockText,boarder+squareSize*6,24);
    }
}
