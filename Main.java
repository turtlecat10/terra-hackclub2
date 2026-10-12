import javax.swing.*;
public class Main{
    public static int[][] selectBoard = new int[8][8];
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
    public static String pieceClicked = "0";
    public static int prevRow = -1;
    public static int prevCol = -1;
    public static boolean[] whiteCastle = new boolean[3];
    public static boolean[] blackCastle = new boolean[3];
    public static boolean enpassant = false;
    public static int enpassantCol = -1;
    public static boolean castling = false;
    public static boolean whiteTurn = true; 
    public static String prevPiece = "0";
    public static DrawingCanvas surface;
    public static boolean running = true;
    public static void main(String[] args){
        Display.loadAssets();

        JFrame frame = new JFrame("Graphics");
        surface = new DrawingCanvas();
        frame.add(surface);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        while(running){
            
        }
    }
}