package ui;

import chess.ChessBoard;
import chess.ChessGame;
import webSocketMessages.serverMessages.ServerMessage;

public class NotificationHandler {
    public NotificationHandler(GamePlayUI gamePlayUI){this.gamePlayUI = gamePlayUI;}
    ServerMessage message;
    GamePlayUI gamePlayUI;
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
        System.out.println(message.getMessage());
        //instead of printing it we want to send it to the right collection
        ChessBoard board = game.getBoard();
        gamePlayUI.webSocketFacade.setBoard(board);
        if(gamePlayUI.playerColor == ChessGame.TeamColor.WHITE){
            System.out.println(board.toStringWhite(null));
        } else if (gamePlayUI.playerColor == ChessGame.TeamColor.BLACK){
            System.out.println(board.toStringBlack(null));
        } else {
            System.out.println(game);
        }
    }
}
