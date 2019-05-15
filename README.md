# Battleship
Computer Networks Project 2019
Progga Deb & Kayla Pollock

## Overview
The goal of our system is to recreate the game battleship. This will be a strictly human vs. human game, with a server and a client interacting with each other. This will support multiple games to be executed simultaneously without interference. The server will create a thread for each game and maintain the multiple threads and different boards.

## Requirements
ADD IMAGE OF THE THING WE DREW.. here or in different section... didnt have formal requirements for this one

## Design
This system has two hosts, a server and a client
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
* Synchronization ADD THIS PART.... OWN HEADING????
### Application-Layer Protocols
* What is the format and order of messages that hosts send to each other
* TCP: we need reliable data transfer and we want our program to run similar to our lab assignments using the Socket class 
* We will be sending each client’s 

## User Manual


## Testing


## Concluding Remarks
