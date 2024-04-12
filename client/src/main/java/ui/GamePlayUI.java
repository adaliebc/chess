package ui;

import chess.ChessBoard;
import webSocketMessages.serverMessages.ServerMessage;

import java.util.Scanner;

public class GamePlayUI {
    String authToken = "";
    int gameID;
    //player color: either a string that can say observer, black or white, or the actual team color
    /*@Override
    public void notify(ServerMessage message) {
        switch (message.getServerMessageType()) {
            case NOTIFICATION -> displayNotification(((NotificationMessage) message).getMessage());
            case ERROR -> displayError(((ErrorMessage) message).getErrorMessage());
            case LOAD_GAME -> loadGame(((LoadGameMessage) message).getGame());
        }
    }*/
    //create a main function
    //makes a play game function
    public void playGame() {
        System.out.println("Enter in a command or 'help' for your options");

        //take in input and form it into a list. Based on the first item in the list, that is the keyword
        Scanner in = new Scanner(System.in);

        String userInput = in.nextLine();
        String[] inputList = userInput.split(" ");
        while (!inputList[0].equalsIgnoreCase("leave") && !inputList[0].equalsIgnoreCase("resign")) {
            if (inputList[0].equalsIgnoreCase("help")) {
                System.out.println(getHelp());
            }
            //have to be able to leave, resign, make_move, help, show valid moves, redraw chess board,
            //while input is not leave or resign
            //if input is make_move
            //user gives in starter coordinates and then ending coordinates
            //give error message if move is illegal

            //then we have user game commands which we send to the websocket
            //join_player, join_observer, make_move, leave, resign
            //are those joins triggered by the other joins?

            //then we have the websocket messages
            //load_game, error, notification

            userInput = in.nextLine();
            inputList = userInput.split(" ");
        }
    }


    //i need a websocket client who starts when someone joins the game, then passes from player to player
    //So I need a function that creates a connection
    //I need a function that creates a websocket that the connections add to
    // server facade talks to the websocket

    public void printBoard(ChessBoard game){
        //add in a specification, if black, print the board from blacks perspective
        //if white, print white
        //else if it is a obersevr, print both
        game.toString();
    }

    public String getHelp () {
        return """
                    'redraw' = redraws board onto screen at your command
                    'leave' = leave the game
                    'make_move' <beginning coordinate> <ending coordinate> = make a move
                    'resign' = quit and exit the game
                    'highlight' <starting coordinates> = print out a screen to highlight legal moves
                    """;
    }
}
