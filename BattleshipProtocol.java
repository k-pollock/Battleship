import java.net.*;
import java.io.*;

public class BattleshipProtocol implements Serializable {
    private static final int WAITING = 0;
    private static final int SETBOARD = 1;
    private static final int PLAYING = 2;
    private static final int GAMEOVER = 3;

    private static final int BOARD_SIZE = 6;
    private static final int SHIPNUM = 3;

    private static final String SET = "SET";
    private static final String GUESS = "GUESS";

    private static final char MISS = '#';
    private static final char SHIP = 'O';
    private static final char HIT = 'X';
    private static final char OPEN = '-';

    private int count = 0;

    private char board[][] = new char[BOARD_SIZE][BOARD_SIZE];
    private int state = WAITING;

    public void setBoard() {
        // -------------------------------------------------
        // Displays the board.
        // Precondition: None.
        // Postcondition: Board is written to standard 
        // output; zero is an EMPTY square, one is a square 
        // containing a queen (QUEEN).
        // -------------------------------------------------
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
                //System.out.print(board[i][k] + "  ");
            }
            //System.out.println(" "); //after each row (8 characters) prints new line
        }
    } // end setBoard

    public void updateBoard(int col, int row, String status) {
        // -------------------------------------------------
        // Updates the board.
        // Precondition: None.
        // Postcondition: Board is written to standard 
        // output; zero is an EMPTY square, one is a square 
        // containing a queen (QUEEN).
        // -------------------------------------------------
        if (status == SET){
            board[row][col] = SHIP;
        }
        else if (status == GUESS){
            if(board[row][col] == SHIP){
                board[row][col] = HIT;
            }
            else if (board[row][col] == OPEN){
                board[row][col] = MISS;
            }
        }

    } // end displayBoard

    public void checkBoard(){
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

    public String displayBoard(){
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

    public String displayOtherBoard(char [][] board){
        String otherBoard = ""; 
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            for (int k = 0; k < BOARD_SIZE; k++)
            {
                if (board[i][k] != SHIP){
                    otherBoard += board[i][k] + "  ";
                }
                if (board[i][k] == SHIP){
                    otherBoard += OPEN + "  ";
                }
            }
            otherBoard+="\n"; //after each row (8 characters) prints new line
        }
        System.out.println(otherBoard);
        return otherBoard;
    }

    public String processInput(String theInput)
    {
        String board = ""; 
        String message = "";
        String message2 = "";
        if (state == WAITING)  
        {  
            message = "Welcome to Battleship! This is your board. \nYou have 3 ships for your battlefield.";
            System.out.println(message);
            setBoard();
            board = "\n" + displayBoard();
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
                updateBoard(col, row, SET);
            }
            message = "This is your updated board\n";
            message2 = "Take your shot";
            System.out.println(message2);
            board = displayBoard();
            state = PLAYING;
        }
        else if (state == PLAYING)
        {
            //take coordinates and see what happened
            char alpha = theInput.charAt(0);
            int col = convertAlpha(alpha);
            char num = theInput.charAt(1);
            int row = Character.getNumericValue(num);

            updateBoard(col, row, GUESS);
            message = "This is your updated board\n";
            message2 = "Take your shot";
            System.out.println(message2);
            board = displayBoard();
            checkBoard();
            if (state == GAMEOVER){
                System.out.println("YOU WIN");
                message = "GAME OVER";
                board = "";
                message2 = "";
                System.out.println("We have a winner! Say congrats");
            }
        }
        return message + board + message2;

    }

    private int convertAlpha(char alpha)
    {
        if (alpha == 'A')
        {
            return 1;
        }
        else if (alpha == 'B')
        {
            return 2;
        }
        else if (alpha == 'C')
        {
            return 3;
        }
        else if (alpha == 'D')
        {
            return 4;
        }
        else if (alpha == 'E')
        {
            return 5;
        }
        return 0;
    }
}
