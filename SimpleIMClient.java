
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

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
        // used when client wants to send a message to server
        //PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
        // used when client receives a message from server
        //BufferedReader in = new BufferedReader(
        //        new InputStreamReader(kkSocket.getInputStream()));)
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        )
        {
            BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;

            // Client receives message from Server (Step 5)
            while ((fromServer = (String) in.readObject()) != null) {
                // Print message from server (Step 6)
                System.out.println(fromServer);
                //displayBoard(fromServer);
                if (fromServer.equalsIgnoreCase("Bye."))
                    break;
                System.out.print("You: ");
                // Read response from client (Step 7)
                fromUser = stdIn.readLine();
                
                if (fromUser != null) {
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

    public static String displayBoard(char[][] board){ 
        String actualBoard = "";  
        for (int i = 0; i < 6; i++) 
        {
            for (int k = 0; k < 6; k++)
            {
                actualBoard += board[i][k] + "  ";
            }
            actualBoard+="\n"; //after each row (8 characters) prints new line
        }
        System.out.println(actualBoard);
        return actualBoard;
    }
}
