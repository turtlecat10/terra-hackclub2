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
                    if(Main.choosingGamemode){
                        if(row >=4){
                            Main.gamemodeGame[0] = row;
                            Main.gamemodeGame[1] = col;
                        }else{
                            Main.gamemodeTime[0] = row;
                            Main.gamemodeTime[1] = col;
                        }
                        repaint();
                    }else{
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
        }else if(Main.choosingGamemode && e.getKeyCode() == KeyEvent.VK_ENTER){
            if(Main.gamemodeGame[0] != -1 && Main.gamemodeTime[0] != -1){
                Main.choosingGamemode = false;
                int time = Main.gamemodeTime[1]+Main.gamemodeTime[0]*8;
                int game = Main.gamemodeGame[1]+Main.gamemodeGame[0]*8-32;
                String gameTime = Main.gameModes[time];
                // String gameGame = Main.gamemodeGames[game];
                String[] parts = gameTime.split("\\|");
                int minutes = Integer.valueOf(parts[0].trim());
                int secondsIncrement = Integer.valueOf(parts[1].trim());
                
                if(minutes == 0){
                    Main.whiteTime = -1;
                    Main.blackTime = -1;
                    Main.timeless = true;
                }else{
                    Main.whiteTime = minutes*60;
                    Main.blackTime = minutes*60;
                    Main.timeless = false;
                }
                Main.increment = secondsIncrement;
            }
            Main.resetCurrent();
            Run.tempReset();
            Main.whiteTurn = true;
            Main.whiteTaken.clear();
            Main.blackTaken.clear();
            Main.blackScore = 0;
            Main.whiteScore = 0;
            Main.prevSelect = new int[8][8];
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
            if(!Main.choosingGamemode){
                Display.drawBoard(g2d);
                Display.pieces(g2d);
                Display.promotion(g2d);
                Display.takenPieces(g2d);
                Display.drawDots(g2d);
                Display.chessClock(g2d);
            }else{
                Display.gameMode(g2d);
            }
        }else{
            Display.setUp(g2d);
        }
    }
}