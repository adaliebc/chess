package ui;

import chess.ChessGame;

public class GamePlayUI {
    //create a main function
    //local variable team color, and game ID

    // a function that accepts user input

    //have to be able to join, leave, resign, make_move, help, show valid moves, redraw chess board,
    //while input is not leave or resign
    //if input is make_move
    //user gives in starter coordinates and then ending coordinates
    //give error message if move is illegal

    //then we have user game commands which we send to the websocket
    //join_player, join_observer, make_move, leave, resign
    //are those joins triggered by the other joins?

    //then we have the websocket messages
    //load_game, error, notification




    //i need a websocket client who starts when someone joins the game, then passes from player to player
    //So I need a function that creates a connection
    //I need a function that creates a websocket that the connections add to
    // server facade talks to the websocket

    public void printBoard(ChessGame game){
        //add in a specification, if black, print the board from blacks perspective
        //if white, print white
        //else if it is a obersevr, print both
        game.toString();
    }

    public String getHelp () {
        return """
                    'redraw' <GameID> = 
                    'login' <Username> <Password> = login to your account
                    'quit' = exit chess game
                    'help' = show options
                    """;
    }
}
