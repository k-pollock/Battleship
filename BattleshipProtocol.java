import java.net.*;
import java.io.*;

public class BattleshipProtocol implements Serializable {
    //declare all variables that are immutable
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

    private char boardS[][] = new char[BOARD_SIZE][BOARD_SIZE];
    private char boardC[][] = new char[BOARD_SIZE][BOARD_SIZE];
    private int state = WAITING;

    /*
    * This method will be called once the connection is established to set the boards
    * Precondition: char board [][] is entirely empty, formatting and open spaces needs to be set
    * Postcondition: board will be initialized to empty, with labeling to use later in display
    */
    public void setBoard(char board[][]) {
        //values used to label board
        char [] alpha = {'A','B','C','D','E'};
        //iterate through and set the format of outer parts, set actual board to open
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

    /*
    * Precondition: col and row are user's input and they were peviously converted to ints. Status is what the coordinates mean: 
    * if they are setting board initially status is SET or if they are guessing the location of an opponent's ship, status would be GUESS
    * Board is the array that will be altered
    * Postcondition: after this method executes the array will be updated. A string is returned to inform the user if their guess was a hit or a miss
    */
    public String updateBoard(int col, int row, String status, char board[][]) {
        String ship = "";
        //check if set or guess and adjust value
        if (status == SET){  
            board[row][col] = SHIP; 
        }
        //If guess, return the result of their guess
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
        //since there was a change to the board, check if someone won
        checkBoard(board);
        return ship;
    } // end displayBoard 

    /*
    * This method is used to check the board for a winner. If the number of hits is equal to the number of ships, there is a winner and the 
    * game is over
    * Precondition: board had been altered in some way
    * Postcondition: state is changed if there is a winner
    */
    public void checkBoard(char board[][]){
        int hits = 0;
        //iterate through to counbt number of hits
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            for (int k = 0; k < BOARD_SIZE; k++)
            {
                if (board[i][k] == HIT)
                {
                    hits ++;
                }
                //if a winner, change state to end
                if (hits == SHIPNUM){
                    state = GAMEOVER;
                }
            }
        }
    }
    
    /*
    * This method will be called to display the board to the user
    * Precondition: char board [][] belongs to the user
    * Postcondition: board will be returned as a String so it can be passed through the thread and displayed
    */
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

    /*
    * Retrieves and returns the board of the user that it needs
    * Precondition: the specific board needed was determined already
    * Postcondition: client or server's board will be returned
    */
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
    
    /*
    * Method that is called after a user responds. Called in the thread
    * Precondition: there is some sort of user input that is passed, along with who the input belongs to
    * Postcondition: Proper response is returned depending on state
    */
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
            //adjust input for consistency
            theInput = theInput.replaceAll("\\s","");
            theInput = theInput.replaceAll(",","");
            
            int col = 0;  
            int row = 0;

            //Since input now one long string, assume format was char - int - repeated if needed
            for (int j = 0; j < theInput.length(); j++)
            {
                //get the numeric values of each column and row
                char alpha = theInput.charAt(j);
                col = convertAlpha(alpha);
                char num = theInput.charAt(++j);
                row = Character.getNumericValue(num);
                //check to see if it was client or server
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
            //take coordinates of guess and see what happenes, similar to setting board in waiting state
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
        //only return "what" aka status of guess, if a guess was made
        if (what == "Hit" || what == "Miss")
        {
            what = "That was a " + what;
        }
        return message + board + "\n" + what + "\n" + message2;
    } 

    /*
    * Used to convert char input to an int to use in checking arrays
    * Precondition: the char is passed
    * Postcondition: an int value is returned for each char
    */
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
