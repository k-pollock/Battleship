import java.net.*;
import java.io.*;

public class BattleshipProtocol implements Serializable {
    private static final int WAITING = 0;
    private static final int SETBOARD = 1;
    private static final int PLAYING = 2;
    private static final int GAMEOVER = 3;

    private static final int BOARD_SIZE = 6;

    private static final String SET = "SET";
    private static final String GUESS = "GUESS";

    private static final char MISS = '#';
    private static final char SHIP = 'O';
    private static final char HIT = 'X';
    private static final char OPEN = '-';

    private char boardS[][] = new char[BOARD_SIZE][BOARD_SIZE];
    private char boardC[][] = new char[BOARD_SIZE][BOARD_SIZE];
    private int state = WAITING;

    public void setBoard(char board[][]) {
        char [] alpha = {'A','B','C','D','E'};
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            for (int k = 0; k < BOARD_SIZE; k++)
            {
                if (i == 0 && k == 0)
                {
                    board[i][k] = '*';
                }
                else if (i == 0)
                {
                    board[i][k] = alpha[k-1];
                }
                else if (k == 0)
                { 
                    board[i][k] = (char) (i + '0');
                }
                else
                    board[i][k] = OPEN;
            }
        }
    } // end setBoard

    public String updateBoard(int col, int row, String status, char board[][]) {
        String ship = "";
        if (status == SET){  
            board[row][col] = SHIP; 
        }
        else if (status == GUESS){ 
            if(board[row][col] == SHIP){ 
                board[row][col] = HIT;
                ship = "Hit";
            }
            else if (board[row][col] == OPEN){
                board[row][col] = MISS;
                ship = "Miss";
            } 
        }
        checkBoard(board);
        return ship;
    } // end displayBoard 

    public void checkBoard(char board[][]){
        int hits = 0;
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            for (int k = 0; k < BOARD_SIZE; k++)
            {
                if (board[i][k] == HIT)
                {
                    hits ++;
                }
                if (hits == 3){
                    state = GAMEOVER;
                }
            }
        }
    }
    
    public String displayBoard(char board[][]){
        String actualBoard = "";  
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            for (int k = 0; k < BOARD_SIZE; k++)
            {
                actualBoard += board[i][k] + "  ";
            }
            actualBoard+="\n"; //after each row (8 characters) prints new line
        }
        System.out.println(actualBoard);
        return actualBoard; 
    } 

    public String getBoard(char board[][]){
        String actualBoard = "";  
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            for (int k = 0; k < BOARD_SIZE; k++)
            {
                actualBoard += board[i][k] + "  ";
            }
            actualBoard+="\n"; //after each row (8 characters) prints new line
        }
        return actualBoard;
    }
    
    public String processInput(String theInput, char end) 
    { 
        String board = ""; 
        //String boardC = "";
        //String boardS = "";
        String message = "";
        String message2 = "";
        String what = "";
        if (state == WAITING)   
        {  
            message = "Welcome to Battleship! This is your board. \nYou have 3 ships for your battlefield.";
            System.out.println(message);
            setBoard(boardC); 
            setBoard(boardS);
            board = "\n" + displayBoard(boardC);
            message2 = "\nPlease enter the locations you would like to place your three ships. \nEnter the location in the following format: \n Example: A1, B2, C3";                 
            System.out.println(message2);
            state = SETBOARD;   
        }
        else if (state == SETBOARD)
        {
            theInput = theInput.replaceAll("\\s","");
            theInput = theInput.replaceAll(",","");

            //theInput = "A1A2A3";
            int col = 0;  
            int row = 0;

            for (int j = 0; j < theInput.length(); j++)
            {
                char alpha = theInput.charAt(j);
                col = convertAlpha(alpha);
                char num = theInput.charAt(++j);
                row = Character.getNumericValue(num);
                if (end == 'C')
                {
                    state = SETBOARD;
                    what = updateBoard(col, row, SET, boardC);
                }
                else if(end == 'S')
                {
                    state = PLAYING;
                    what = updateBoard(col, row, SET, boardS);
                }
            } 
            message = "This is your updated board\n";
            message2 = "\nTake your shot";
            if (end == 'C')
            {
                board = getBoard(boardC);
            }
            else if (end == 'S')
            {
                board = getBoard(boardS);
            }
        }
        else if (state == PLAYING)
        {
            //take coordinates and see what happened
            char alpha = theInput.charAt(0);
            int col = convertAlpha(alpha);
            char num = theInput.charAt(1);
            int row = Character.getNumericValue(num);
            if (end == 'C')
                what = updateBoard(col, row, GUESS, boardS);
            else if(end == 'S')
                what = updateBoard(col, row, GUESS, boardC);
            message = "This is your updated board";
            message2 = "\nTake your shot";
            if (end == 'C')
            {
                board = "\n" + getBoard(boardC);
            }
            else if (end == 'S')
            {
                board = "\n" + getBoard(boardS);
            } 
            state = PLAYING;
            checkBoard(boardC);
            checkBoard(boardS);
        }
        else if (state == GAMEOVER)
        {
            message = "GAME OVER";
            board = " ";
            message2 = "Player 1 won! \nSay GG";
        }
        if (what == "Hit" || what == "Miss")
        {
            what = "That was a " + what;
        }
        return message + board + "\n" + what + "\n" + message2;
    } 

    private int convertAlpha(char alpha)
    {
        if (alpha == 'A' || alpha == 'a') 
        {
            return 1;
        }
        else if (alpha == 'B' || alpha == 'b')
        {
            return 2;
        }
        else if (alpha == 'C' || alpha == 'c')
        {
            return 3;
        }
        else if (alpha == 'D' || alpha == 'd')
        {
            return 4;
        }
        else if (alpha == 'E' || alpha == 'e')
        {
            return 5;
        }
        return 0;
    } 
} 
