import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DrawingCanvas extends JPanel implements java.awt.event.KeyListener {
    private static int boarder = 30;
    private static int squareSize = 60;
    public static int hoverRow = -1;
    public static int hoverCol = -1;
    public DrawingCanvas(){
        this.setPreferredSize(new Dimension(squareSize*8+boarder*2,squareSize*8+boarder*2));
        this.setBackground(Color.DARK_GRAY);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.addMouseListener(new MouseAdapter() {
            @Override 
            public void mousePressed(MouseEvent e){
                if(!Main.setUp){
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
            }
        });
    }
    @Override
    public void keyTyped(KeyEvent e){
        char typedChar = e.getKeyChar();
        if(Main.setUp){
            if (typedChar == KeyEvent.VK_BACK_SPACE) {
                if (Main.player1 && Display.name1.length() > 0) {
                    Display.name1 = Display.name1.substring(0, Display.name1.length() - 1);
                } else if (!Main.player1 && Display.name2.length() > 0) {
                    Display.name2 = Display.name2.substring(0, Display.name2.length() - 1);
                }
            } 
            else if (!Character.isISOControl(typedChar)) {
                if (Main.player1) {
                    Display.name1 += String.valueOf(typedChar);
                } else {
                    Display.name2 += String.valueOf(typedChar);
                }
            }
            repaint();
        }
    }
    @Override
    public void keyPressed(KeyEvent e){
        
        if(Main.setUp && e.getKeyCode() == KeyEvent.VK_ENTER){
            if(Main.player1){
                Main.player1 = false;
            }else{
                Main.setUp = false;
            }
            repaint();
        }
    }
    @Override
    public void keyReleased(KeyEvent e){

    } 
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(!Main.setUp){
            Display.drawBoard(g2d);
            Display.pieces(g2d);
            Display.promotion(g2d);
            Display.takenPieces(g2d);
            Display.drawDots(g2d);
            Display.chessClock(g2d);
        }else{
            Display.setUp(g2d);
        }
    }
}