import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawingCanvas extends JPanel {
    private static int boarder = 30;
    private static int squareSize = 60;
    public static int hoverRow = -1;
    public static int hoverCol = -1;
    public DrawingCanvas(){
        this.setPreferredSize(new Dimension(squareSize*8+boarder*2,squareSize*8+boarder*2));
        this.setBackground(Color.DARK_GRAY);
        this.addMouseListener(new MouseAdapter() {
            @Override 
            public void mousePressed(MouseEvent e){
                int mouseX = e.getX();
                int mouseY = e.getY();
                int col = (mouseX - boarder) / squareSize;
                int row = (mouseY - boarder) / squareSize; 
                if(col >= 0 && col < 8 && row >= 0 && row < 8){
                    if(Main.selectBoard[row][col] == 0){
                        hoverRow = row;
                        hoverCol = col;
                    }else{
                        hoverRow = -1;
                        hoverCol = -1;
                    }
                    Run.turn(row,col);
                    if(Main.pieceChange){
                        hoverRow = Main.prevRow;
                        hoverCol = Main.prevCol;
                    }
                }
            }
        });
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Display.drawBoard(g2d);
        Display.pieces(g2d);
        Display.promotion(g2d);
        Display.takenPieces(g2d);
        Display.drawDots(g2d);
    }
}