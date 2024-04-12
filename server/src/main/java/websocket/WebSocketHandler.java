package websocket;

import chess.ChessBoard;
import chess.ChessGame;
import chess.InvalidMoveException;
import com.google.gson.Gson;
//import dataaccess.DataAccess;
//import exception.ResponseException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.GameService;
import service.UserService;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;
//import webSocketMessages.Action;
//import webSocketMessages.Notification;

@WebSocket
public class WebSocketHandler {
    ConnectionManager connectionManager= new ConnectionManager();
    GameService gameService;
    UserService userService;
    private String authToken;

    public WebSocketHandler(GameService gameService, UserService userService){
        this.gameService = gameService;
        this.userService = userService;
    }
    //Connection connection = new Connection();
    //establish a link to connections in connections


    @OnWebSocketMessage
    public void onMessage(Session session, String msg) throws Exception {
        UserGameCommand command = new Gson().fromJson(msg, UserGameCommand.class);
        authToken = command.getAuthString();
        var conn = connectionManager.getConnection(command.getAuthString());
        if (conn != null) {
            switch (command.getCommandType()) {
                case MAKE_MOVE -> makeMove(conn, msg);
                case LEAVE -> leave(conn, msg);
                case RESIGN -> resign(conn, msg);
            }
        } else {
            connectionManager.add(authToken, session);
            conn = connectionManager.getConnection(command.getAuthString());
            switch (command.getCommandType()) {
                case JOIN_PLAYER -> join(conn, command);
                case JOIN_OBSERVER -> observe(conn, command);
            }
            //conn.sendError(session.getRemote(), "unknown user");
        }
    }



    //manage connections

    //add a connection
    // create a new connection
    //send a message to everyone saying this guy joined
    //call broadcast function to do that

    //remove a connection
    //remove it from connections list and send message

    //send messages

    //make a move message
    //send out a message that says username moved here

    //in check message

    //game is over message
    //sends to webfacade and notification handler

    public void makeMove(Connection conn, String msg) throws InvalidMoveException {
        //request needs to have a chess game and a move object
        //game.makeMove(move);
        // update game (game)
        //send message through websocket
    }

    public void resign(Connection conn, String msg){
        //end the game
        //mark other player as winner
    }

    public void leave(Connection conn, String msg){
        //call handler and have them remove the user's connection
        //send the message
    }

    private void join(Connection conn, UserGameCommand msg){
        String authToken = msg.getAuthString();
        int gameID = msg.getGameID();
        ChessGame.TeamColor playerColor = msg.getPlayerColor();
        ServerMessage message = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
        ChessBoard game = gameService.getGame(gameID);
        if(playerColor == ChessGame.TeamColor.WHITE){
            message.setMessage(game.toStringWhite());
        }
        else {
            message.setMessage(game.toStringBlack());
        }
        //String username = userService.getUsername(authToken);
        //send this as a broadcast
        //ServerMessage message = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        //message.setMessage(username + " joined the game!");
        try {
            conn.send(new Gson().toJson(message));
        } catch(Exception E){
            System.out.println(E.getMessage());
        }

    }

    private void observe(Connection conn, UserGameCommand msg){

    }

}
