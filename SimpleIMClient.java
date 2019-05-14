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
        // inititate a connection request to server's IP address, port 
        Socket socket = new Socket(hostName, portNumber);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        )
        {
            BufferedReader stdIn = 
                new BufferedReader(new InputStreamReader(System.in));

            String fromServer;
            String fromUser;
     
            // Client receives message from Server 
            while ((fromServer = (String) in.readObject()) != null) {
                // Print message from server 
                System.out.println(fromServer);
 
                // Server breaks connection 
                //will check for "good game" at end to disconnect
                if (fromServer.equalsIgnoreCase("gg"))
                    break; 

                // Read response (coordinates) from client 
                 fromUser = stdIn.readLine(); 

                // If response is not null, then there is a message to send
                if (fromUser != null) { 
                    out.writeObject(fromUser);  // Sends response to server
                }
            }
            //catch if unable to connect of connection breaks
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
