import java.net.*;
import java.io.*;

public class Battleship implements Serializable {
    private static final int WAITING = 0;
    private static final int SENTMESSAGE = 1;

    private static final int BOARD_SIZE = 6;

    private static final char MISS = '#';
    private static final char SHIP = 'O';
    private static final char HIT = 'X';
    private static final char OPEN = '-';

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
    } // end displayBoard

    public String displayBoard(){
        String actualBoard = ""; 
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            for (int k = 0; k < BOARD_SIZE; k++)
            {
                actualBoard += board[i][k] + "  ";
            }
            actualBoard+="\n"; //after each row (8 characters) prints new line
        }
        System.out.println(actualBoard);
        return actualBoard;
    }

    public String processInput(String theInput)
    {
        String board = "";  
        if (state == WAITING) 
        {  
            setBoard();
            board = displayBoard();
            state = SENTMESSAGE;  
        }
        return board;
    }
}
