import java.net.*;
import java.io.*;

public class IMMultiServerThread extends Thread {
    private Socket socket = null;

    public IMMultiServerThread(Socket socket) {
        super("IMMultiServerThread");
        this.socket = socket;
    }

    public void run() {
        try (
        //PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        //BufferedReader in = new BufferedReader(
        //  new InputStreamReader(
        //      socket.getInputStream()))
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream()); 
        ) {
            BufferedReader stdIn = 
                new BufferedReader(new InputStreamReader(System.in));
            final char server = 'S';
            final char client = 'C';
            String clientInput;
            String cShot;
            String serverInput;
            String board = "";
            //String client = "Client";
            String coordinates; 
            int response = 0;

            // Initiate conversation with client
            BattleshipProtocol bp = new BattleshipProtocol();
            board = bp.processInput(null, server); //greeting
            
            // Sends the initial input from server to client
            out.writeObject(board); 

            // While the object sent from client is not null
            while ((clientInput = (String) in.readObject()) != null)  { 
                // Print message from client
                //System.out.println(client + ": " + clientInput);

                // Client breaks connection
                if (clientInput.equals("congrats"))
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
                    System.out.println(serverInput); 
                    // 
                    //out.writeObject(board);     
                }    

                //if (board.equals("Bye"))
                //break; 
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
