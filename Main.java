import javax.swing.*;
import java.util.ArrayList;
public class Main{
    public static int[][] selectBoard = new int[8][8];
    public static int[][] prevSelect = new int[8][8];
    public static final String[][] INITIAL_BOARD = {
    {"br", "bn", "bb", "bq", "bk", "bb", "bn", "br"},
    {"bp", "bp", "bp", "bp", "bp", "bp", "bp", "bp"},
    {"0",  "0",  "0",  "0",  "0",  "0",  "0",  "0" },
    {"0",  "0",  "0",  "0",  "0",  "0",  "0",  "0" },
    {"0",  "0",  "0",  "0",  "0",  "0",  "0",  "0" },
    {"0",  "0",  "0",  "0",  "0",  "0",  "0",  "0" },
    {"wp", "wp", "wp", "wp", "wp", "wp", "wp", "wp"},
    {"wr", "wn", "wb", "wq", "wk", "wb", "wn", "wr"}};
    public static String[][] currentBoard = new String[8][8];
    public static String[][] tempBoard = new String[8][8];
    public static String[][] nextBoard = new String[8][8];
    public static String[][] attackBoard = new String[8][8];
    public static boolean setUp = true;
    public static boolean choosingGamemode = true;
    public static String[] gameModes = {"15|10","10|5","10|0","3|0","3|2","5|0","5|5","90|30","60|0","0|0","Custom"};
    public static boolean choosingTime = false;
    public static boolean choosingMinutes = true;
    public static String[] gamemodeGames = {"Standard"};
    public static boolean player1 = true;
    public static String pieceClicked = "0";
    public static int prevRow = -1;
    public static int prevCol = -1;
    public static int[] gamemodeTime = new int[2];
    public static int[] gamemodeGame = new int[2];
    public static boolean[] whiteCastle = new boolean[3];
    public static boolean[] blackCastle = new boolean[3];
    public static ArrayList<String> whiteTaken = new ArrayList<>();
    public static ArrayList<String> blackTaken = new ArrayList<>();
    public static int whiteScore = 0;
    public static int blackScore = 0;
    public static double black = 0;
    public static double white = 0;
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
    public static int increment = 0;
    public static Timer chessClock;
    public static boolean timeless = false;
    public static void resetCurrent(){
        for(int i = 0; i < 8; i++){
            for(int l = 0; l < 8; l++){
                Main.currentBoard[i][l] = INITIAL_BOARD[i][l];
            }
        }
    }
    public static void main(String[] args){
        resetCurrent();
        Display.loadAssets();
        gamemodeTime[0] = -1;
        gamemodeTime[1] = -1;
        gamemodeGame[0] = -1;
        gamemodeGame[1] = -1;
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
                    }else{
                        if(!choosingGamemode && !timeless && !setUp){
                            black += 1;
                        }
                        if(!timeless){
                            choosingGamemode = true;
                        }
                    }
                }else{
                    if(blackTime > 0){
                        blackTime--;
                    }else{
                        if(!choosingGamemode && !timeless && !setUp){
                            white += 1;
                        }
                        if(!timeless){
                            choosingGamemode = true;
                        }
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