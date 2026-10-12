import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.HashMap;

public class Display {
    private static final int squares = 8;
    private static final int squareSize = 60;
    private static final int boarder = 20;

    private static final Color lightSquare = new Color(235, 236, 208);
    private static final Color highLightSquare = new Color(245, 246, 130);
    private static final Color darkSquare = new Color(115, 149, 82);
    private static final Color highDarkSquare = new Color(185, 202, 67);
    private static final Color boarderColor = new Color(128,128,128);
    private static HashMap<String, BufferedImage> pieceImages = new HashMap<>(); 

    public static void loadAssets(){
        String[] codes = {"wp","wn","wb","wr","wq","wk","bp","bn","bb","br","bq","bk"};
        for(String code : codes){
            try {
                File imgFile = new File("Assets/" + code + ".png");
                BufferedImage img = ImageIO.read(imgFile);
                pieceImages.put(code,img);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void drawBoard(Graphics2D g2d){
        g2d.setColor(boarderColor);
        g2d.fillRect(0,0,520,520);

        for(int col = 0; col < squares; col++){
            for(int row = 0; row < squares; row++){
                int x = boarder + (col*squareSize);
                int y = boarder + (row*squareSize);
                if((col+row) % 2 == 0){
                    if(Main.selectBoard[row][col] == 1){
                        g2d.setColor(highLightSquare);
                    }else{
                        g2d.setColor(lightSquare);
                    }
                }else{
                    if(Main.selectBoard[row][col] == 1){
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
        
    }
}
