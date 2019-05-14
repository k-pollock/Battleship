import java.io.*;
import java.net.*;

public class SimpleIMClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        if (args.length != 2) {
            System.err.println(
                "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
        // inititate a connection request to server's IP address, port (Step 2)
        Socket socket = new Socket(hostName, portNumber);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        )
        {
            BufferedReader stdIn = 
                new BufferedReader(new InputStreamReader(System.in));
            //final char client = 'C';
            String fromServer;
            String fromUser;
            //String coordinates;
     
            // Client receives message from Server (Step 5)
            while ((fromServer = (String) in.readObject()) != null) {
                // Print message from server 99(Step 6)
                System.out.println(fromServer);
 
                // Server breaks connection 
                if (fromServer.equalsIgnoreCase("congrats"))
                    break; 

                //System.out.print("You: "); 
                // Read response (coordinates) from client (Step 7)
                 fromUser = stdIn.readLine(); 

                // If response is not null, send coordinates to Server
                if (fromUser != null) { 
                    //fromUser = bp.processInput(coordinates, client);
                    //System.out.println("Client: " + fromUser);
                    out.writeObject(fromUser);  // Sends response to server (Step 8)
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
    }
}
