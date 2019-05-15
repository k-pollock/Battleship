# Battleship
Computer Networks Project 2019
Progga Deb & Kayla Pollock

## Overview
The goal of our system is to recreate the game battleship. This will be a strictly human vs. human game, with a server and a client interacting with each other. This will support multiple games to be executed simultaneously without interference. The server will create a thread for each game and maintain the multiple threads and different boards.

## Requirements


## Design
This system has two hosts, a server and a client
#### 1. Server
* Initiates the connection to each client and pairs two clients to a game
* Welcomes user to game and then prompts user to place their pieces. Takes what each client inputs and stores it to send to the other client
* Maintains the different boards, ship placement, hits, misses, and who’s turn it is
* The server will be the central point of communication; the clients will not interact directly with each other, but indirectly through the server
#### 2. Client
* Specifies IP address and TCP port # to connect to a game
* Receives information from other client via server and responds accordingly
* Each client will interact directly with the server and will see two boards

## Implementation


## User Manual


## Testing


## Concluding Remarks
