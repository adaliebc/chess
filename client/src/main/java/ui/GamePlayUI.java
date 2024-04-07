package ui;

import chess.ChessGame;

public class GamePlayUI {
    //create a main function
    //local variable team color, and game ID
//need to figure out how to have two players at once
    // a function that accepts user input, maybe put these into a second while loop in prelogin

    //have to be able to join, leave, resign, make_move, help, show valid moves, redraw chess board,
    //while input is not leave or resign
    //if input is make_move
    //user gives in starter coordinates and then ending coordinates
    //give error message if move is illegal




    //i need a websocket client who starts when someone joins the game, then passes from player to player
    //should it be in server facade?

    public void printBoard(ChessGame game){
        //add in a specification, if black, print the board from blacks perspective
        //if white, print white
        //else if it is a obersevr, print both
        game.toString();
    }
}
