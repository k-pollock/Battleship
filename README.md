# Battleship
Computer Networks Project 2019

Progga Deb & Kayla Pollock

## Overview
The goal of our system is to recreate the game battleship using a centralized server. This will be a strictly human vs. human game, with a server and a client interacting with each other. It will support multiple games to be executed simultaneously without interference. The server will create a thread for each game and maintain the multiple threads and different boards. There are many improvements that can be made to this aplication outside of the scope of this project.


## Requirements
Server              |    |Client
------------------  | ---|----------
Establish Connection & send and display board with prompt for ship placement |1 -->|Sees board and prompt
Server sends their ship locations. Store ships in appropriate array|2 <--|Input ship locations 
Display & send updated board along with prompt for a shot |3 -->|Sees their updated board
Determines if hit or miss and updates array |4 <--|Inputs guess for oponent's ship location
Sends message if hit or not and updated board. Server checks for winner |5 -->|Wait for server's guess
||6 Repeat steps 3 - 6 until someone wins|
Determines there is a winner (3 hits in an array), send game over message|7 -->|Recieve game over message & respond to end connection

## Design
This system has two hosts, a server and a client. To avoid interference between clients on different games we used synchronization and the serializable class to send objects back and forth. To disconnect, a user can type "gg" meaning "good game" and the program will terminate.

Since there are other features we would like to add, we made sure to keep our code as well documented as possible. We used final variables for aspects that will not be altered during program execution, but might be altered later when expanding the application (ex: board size and number of ships)

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
Thank you for playing Battleship! Here you will find step-by-step instructions on how to use this application. Once you get the game running, the prompts assist you with your input so there is not too much to ellaborate on.

### Connecting 
To use our java application, if the server is already running and you know the port and IP of where it is running, you can easily connect with just the client class. However, to use this application, we reccomend running both the server and the client. Multiple clients can also connect to the same server. When running the server you need to pass the port number you want to use, and to run the client, you need to specify the IP along with the port you used for the server. Once the connection is established the game will begin.

![Connection](/Images/Connecting.png)

### Initial Connection
Upon a successful connection on both the server and client side, each player will see the following message. If the connection failed, the game would not start and an error would occur

![Initial Message](/Images/InitialMessage.png)

### Board Symbol Key

Symbol|Meaning
------|------
"-" | Open: no ships or guesses
"O" | Ship: not hit
"#" | Miss: incorrect guess
"X" | Hit: correct guess

Here is an example of a board when the game is in progress:

![Display](/Images/Board.png)


## Testing
We tested our system by simply running the program multiple times, and by also using BlueJ's degbugging feature.

#### Connecting
To test our connection we tried connecting to a port that was not running the server and that of course didn't work. We also tried to connect from a different computer, which was successful. Moving on to our actual application, we had to do a lot of testing throughout the development process to check how things were outputted and if the data was being sent back and forth properly. We had to check that the board was being stored for the right user, and of course make sure the user input was being interpreted and stored as it should. 

#### Changing States
A lot of testing was needed in the beginning to make sure that the messages were being displayed on both sides and to make sure the program was entering the correct state for each user. We had some issues with our program returning to the "waiting" state after the user already set their board and were unsure why this was occurring. Through BlueJ's Debugging feature though, we were able to set a few breakpoints and resolve that issue.

#### Interpreting Input
When selecting where a user would want to place their ships, and also where they would like to shoot their shot, we made sure to handle the input if a user were to use lowercase or upper case letters, spaces or no spaces, and commas or no commas to account for all of the different possibilities. 

Our testing also revealed other minor issues that we would like to fix if given more time. For example, when selecting where the user wants to shoot and where they want to place their ships, if the input is not in the format char then int, an error is thrown so we would want to implement a try/catch block. 

Through our testing we also realized that ideally, we would want to also validate the user's input. If a user previously guessed the location of a shot we would want to inform the user and promt the to guess again to make the game more fair, and we would want to ensure that when placing ships, three different locations are selected.


## Concluding Remarks
Since the networking aspect and synchronization aspect of this project was our focus, there are still a lot of other areas we would like to improve on. If given more time, we would like to enhance our application by:
1) Making different length ships to better recreat battleship and expand board size to accommodate
2) Have two clients interact with eachother instead of server
3) Show each user their opponent's board as well (without un-hit ships of course)
4) Store previous guesses and let the user guess again if they attempted to hit a spot they already selected
5) Improve the display of the program
