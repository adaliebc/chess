package ui;

import chess.*;
import com.google.gson.Gson;
import service.GameService;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.*;
import java.net.URI;
import java.util.Collection;


public class WebSocketFacade extends Endpoint{
    private Session session;
    private NotificationHandler notificationHandler;
    private ChessBoard board;
//sends commands to server
    //i think we call the functions in UserGameCommand to send to handler

    public WebSocketFacade(NotificationHandler notificationHandler) {
        this.notificationHandler = notificationHandler;
        try {
            URI uri = new URI("ws://localhost:8080/connect");
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, uri);

            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                public void onMessage(String message) {
                    //System.out.println(message);
                    notifyMessage(message);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(String msg) throws Exception {this.session.getBasicRemote().sendText(msg);}
    public void onOpen(Session session, EndpointConfig endpointConfig) {}

    public void notifyMessage(String inputMessage) {
        try {
            ServerMessage message = new Gson().fromJson(inputMessage, ServerMessage.class);
            notificationHandler.notify(message);
        } catch(Exception ex) {
            //observer.notify(new ErrorMessage(ex.getMessage()));
        }
    }
    public void setBoard(ChessBoard board){
        this.board = board;
    }
    public void joinGame(String[] inputList, String authToken){
        if(inputList[0].equalsIgnoreCase("join")){
            joinPlayer(inputList, authToken);
        } else if(inputList[1].equalsIgnoreCase("observe")){
            joinObserver(inputList, authToken);
        }
    }
    private void joinPlayer(String[] inputList, String authToken){
        //send the game command through a message
        try {
            UserGameCommand command = new UserGameCommand(authToken);
            command.setCommandType(UserGameCommand.CommandType.JOIN_PLAYER);
            command.setGameID(Integer.parseInt(inputList[1]));
            if (inputList[2].equalsIgnoreCase("white")) {
                command.setPlayerColor(ChessGame.TeamColor.WHITE);
            } else if(inputList[2].equalsIgnoreCase("black")){
                command.setPlayerColor(ChessGame.TeamColor.BLACK);
            }
            String message = new Gson().toJson(command);
            send(message);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void joinObserver(String[] inputList, String authToken){
        //send the game command through a message
        try {
            UserGameCommand command = new UserGameCommand(authToken);
            command.setCommandType(UserGameCommand.CommandType.JOIN_OBSERVER);
            command.setGameID(Integer.parseInt(inputList[1]));
            String message = new Gson().toJson(command);
            send(message);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void redraw(){
        ChessGame chessGame = new ChessGame();
        chessGame.setBoard(board);
        notificationHandler.loadGame(chessGame);
    }
    public void makeMove(String[] inputList, String authtoken, ChessGame.TeamColor playerColor){
        UserGameCommand command = new UserGameCommand(authtoken);
        command.setCommandType(UserGameCommand.CommandType.MAKE_MOVE);
        command.setPlayerColor(playerColor);

        if(playerColor == null){
            System.out.println("Observers cannot make a move");
            return;
        }

        ChessMove move;
        ChessPosition startingPosition = new ChessPosition(Integer.parseInt(inputList[2]), fromAlphaToInt(inputList[1]));
        ChessPosition endingPosition = new ChessPosition(Integer.parseInt(inputList[4]), fromAlphaToInt(inputList[3]));
        ChessPiece.PieceType chessPiece;
        if (inputList[5].equalsIgnoreCase("null")){
            chessPiece = null;
        } else if (inputList[5].equalsIgnoreCase("bishop")){
            chessPiece = ChessPiece.PieceType.BISHOP;
        } else if (inputList[5].equalsIgnoreCase("knight")){
            chessPiece = ChessPiece.PieceType.KNIGHT;
        } else if (inputList[5].equalsIgnoreCase("queen")){
            chessPiece = ChessPiece.PieceType.QUEEN;
        } else if (inputList[5].equalsIgnoreCase("rook")){
            chessPiece = ChessPiece.PieceType.ROOK;
        } else {
            System.out.println("input a valid promotion character");
            return;
        }
        move = new ChessMove(startingPosition, endingPosition, chessPiece);

        command.setMove(move);

        try{
            String message = new Gson().toJson(command);
            send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private int fromAlphaToInt(String letter){
        if(letter.equalsIgnoreCase("a")){
            return 1;
        } else if (letter.equalsIgnoreCase("b")){
            return 2;
        } else if (letter.equalsIgnoreCase("c")){
            return 3;
        } else if (letter.equalsIgnoreCase("d")) {
            return 4;
        } else if (letter.equalsIgnoreCase("e")){
            return 5;
        } else if (letter.equalsIgnoreCase("f")){
            return 6;
        } else if (letter.equalsIgnoreCase("g")){
            return 7;
        } else if (letter.equalsIgnoreCase("h")){
            return 8;
        }
        return 0;
    }

    public void highlight(String[] inputList, ChessGame.TeamColor playerColor){
        ChessGame chessGame =new ChessGame();
        chessGame.setBoard(board);
        ChessPosition startingPosition = new ChessPosition(Integer.parseInt(inputList[2]), fromAlphaToInt(inputList[1]));
        Collection<ChessMove> validMoves = chessGame.validMoves(startingPosition);
        if (playerColor == ChessGame.TeamColor.WHITE){
            System.out.println(board.toStringWhite(validMoves));
        } else if (playerColor == ChessGame.TeamColor.BLACK){
            System.out.println(board.toStringBlack(validMoves));
        } else{
            System.out.println("Observers cannot highlight validMoves");
            return;
        }
    }
}
