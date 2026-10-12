import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawingCanvas extends JPanel {
    private static int boarder = 20;
    private static int squareSize = 60;
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
                        Main.selectBoard[row][col] = 1;

                    }else{
                        Main.selectBoard[row][col] = 0;
                    }
                    Run.turn(row,col);
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
    }
}