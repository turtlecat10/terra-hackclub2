import javax.swing.*;
import java.util.ArrayList;
public class Main{
    public static int[][] selectBoard = new int[8][8];
    public static int[][] prevSelect = new int[8][8];
    public static String[][] currentBoard = {
    {"br", "bn", "bb", "bq", "bk", "bb", "bn", "br"},
    {"bp", "bp", "bp", "bp", "bp", "bp", "bp", "bp"},
    {"0",  "0",  "0",  "0",  "0",  "0",  "0",  "0" },
    {"0",  "0",  "0",  "0",  "0",  "0",  "0",  "0" },
    {"0",  "0",  "0",  "0",  "0",  "0",  "0",  "0" },
    {"0",  "0",  "0",  "0",  "0",  "0",  "0",  "0" },
    {"wp", "wp", "wp", "wp", "wp", "wp", "wp", "wp"},
    {"wr", "wn", "wb", "wq", "wk", "wb", "wn", "wr"}};
    public static String[][] tempBoard = new String[8][8];
    public static String[][] nextBoard = new String[8][8];
    public static String[][] attackBoard = new String[8][8];
    public static boolean setUp = true;
    public static boolean player1 = true;
    public static String pieceClicked = "0";
    public static int prevRow = -1;
    public static int prevCol = -1;
    public static boolean[] whiteCastle = new boolean[3];
    public static boolean[] blackCastle = new boolean[3];
    public static ArrayList<String> whiteTaken = new ArrayList<>();
    public static ArrayList<String> blackTaken = new ArrayList<>();
    public static int whiteScore = 0;
    public static int blackScore = 0;
    public static boolean pieceChange = false;
    public static boolean enpassant = false;
    public static int enpassantCol = -1;
    public static boolean promotion = false;
    public static int[] promotionSquare = new int[2];
    public static boolean castling = false;
    public static boolean whiteTurn = true; 
    public static String prevPiece = "0";
    public static DrawingCanvas surface;
    public static boolean running = true;
    public static int whiteTime = 10*60;
    public static int blackTime = 10*60;
    public static Timer chessClock;
    public static void main(String[] args){
        Display.loadAssets();

        JFrame frame = new JFrame("Graphics");
        surface = new DrawingCanvas();
        frame.add(surface);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        chessClock = new Timer(1000, e ->{
            if(!Main.setUp && !Main.promotion){
                if(Main.whiteTurn){
                    if(whiteTime > 0){
                        whiteTime--;
                    }
                }else{
                    if(blackTime > 0){
                        blackTime--;
                    }
                }
                if(Main.surface != null){
                    Main.surface.repaint();
                }
            }
        });
        Main.chessClock.start();
        frame.setVisible(true);
    }
}