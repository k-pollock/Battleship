import java.net.*;
import java.io.*;

public class MultiServerThread extends Thread {
    private Socket socket = null;

    public MultiServerThread(Socket socket) {
        super("MultiServerThread");
        this.socket = socket;
    }

    public void run() {
        try (
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream()); 
        ) {
            BufferedReader stdIn = 
                new BufferedReader(new InputStreamReader(System.in));
            //different arrays for client and server
            final char server = 'S';
            final char client = 'C';
            String clientInput;
            String cShot;
            String serverInput;
            String board = "";
            String coordinates; 

            // Initiate conversation with client
            BattleshipProtocol bp = new BattleshipProtocol();
            board = bp.processInput(null, server); //greeting
            
            // Sends the initial input from server to client
            out.writeObject(board); 

            // While the object sent from client is not null
            while ((clientInput = (String) in.readObject()) != null)  { 

                // Client breaks connection when game is over
                if (clientInput.equals("gg"))
                    break; 

                // Update client board and send updated board to client
                cShot = bp.processInput(clientInput, client);
                out.writeObject(cShot);

                // Read response from server (Step 10)
                coordinates = stdIn.readLine();

                // If coordinates is not null, processes coordinates taken from server
                if(coordinates != null)  
                { 
                    // Update server board and print updated board on server side
                    serverInput = bp.processInput(coordinates, server);
                }    
            }    
            socket.close(); 
        } catch (IOException e) {
            e.printStackTrace();  
        } 
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }   
    }  
} 
