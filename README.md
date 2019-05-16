# Battleship
Computer Networks Project 2019
Progga Deb & Kayla Pollock

## Overview
The goal of our system is to recreate the game battleship using a centralized server. This will be a strictly human vs. human game, with a server and a client interacting with each other. It will support multiple games to be executed simultaneously without interference. The server will create a thread for each game and maintain the multiple threads and different boards.


## Requirements
Server              |    |Client
------------------  | -------|----------
Establish Connection & send and display board with prompt for ship placement |1 -->|Sees board and prompt
Server sends their ship locations. Store ships in appropriate array|2 <--|Input ship locations 
Display & send updated board along with prompt for a shot |3 -->|Sees their updated board
Determines if hit or miss and updates array |4 <--|Inputs guess for oponent's ship location
Sends message if hit or not and updated board. Server checks for winner |5 -->|Wait for server's guess
||6 Repeat steps 3 - 6 until someone wins|
Determines there is a winner (3 hits in an array), send game over message|7 -->|Recieve game over message & respond to end connection

## Design
This system has two hosts, a server and a client. To avoid interference between clients on different games we used synchronization and the serializable class to send objects back and forth
### 1. Server
* Initiates the connection to each client and pairs two clients to a game
* Welcomes user to game and then prompts user to place their pieces. Takes what each client inputs and stores it to send to the other client
* Maintains the different boards, ship placement, hits, misses, and who’s turn it is
* The server will be the central point of communication; the clients will not interact directly with each other, but indirectly through the server
### 2. Client
* Specifies IP address and TCP port # to connect to a game
* Receives information from other client via server and responds accordingly
* Each client will interact directly with the server and will see two boards


## Implementation
### Communication via Board Object
* Each client will be able to see two boards, one will be where their own ships are located and if the client hit any, and the other will be their opponent's board so they can keep track of what spaces were a hit or a miss
* The boards will be stored on the server as 2D arrays and updated when needed
### Application-Layer Protocols
* What is the format and order of messages that hosts send to each other
* TCP: we need reliable data transfer and we want our program to run similar to our lab assignments using the Socket class 


## User Manual

![Connection](/Images/Connection.png)
![Display](/Images/Display.png)

## Testing


## Concluding Remarks
If given more time, we would like to enhance our application by:
1) Making different length ships to better recreat battleship and expand board size to accommodate
2) Have two clients interact with eachother
3) Show each user their opponent's board )without unhit ships of course) so they can keep track of previous guesses
4) Store previous guesses and let the user guess again if they attempted to hit a spot they already selected
