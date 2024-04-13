package ui;

import chess.ChessBoard;
import chess.ChessGame;
import webSocketMessages.serverMessages.ServerMessage;

public class NotificationHandler {
    ServerMessage message;
    //notifies user through interface
//display notification
    //@Override
    public void notify(ServerMessage message) {
        this.message = message;
        switch (message.getServerMessageType()) {
            case NOTIFICATION -> displayNotification(message.getMessage());
            case ERROR -> displayError(message.getMessage());
            case LOAD_GAME -> loadGame(message.getGame());
        }
    }
    public void displayNotification(String message){
        System.out.println(message);
    }
    //display error
    public void displayError(String error){
        System.out.println(error);
    }
    //load game
    public void loadGame(ChessGame game){
        //instead of printing it we want to send it to the right collection
        ChessBoard board = game.getBoard();
        if(message.getMessage().equalsIgnoreCase("white")){
            System.out.println(board.toStringWhite());
        } else if (message.getMessage().equalsIgnoreCase("black")){
            System.out.println(board.toStringBlack());
        } else {
            System.out.println(game.toString());
        }
    }
}
