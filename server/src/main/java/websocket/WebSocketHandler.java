package websocket;

import chess.InvalidMoveException;
import com.google.gson.Gson;
//import dataaccess.DataAccess;
//import exception.ResponseException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.GameService;
import webSocketMessages.userCommands.UserGameCommand;
//import webSocketMessages.Action;
//import webSocketMessages.Notification;

@WebSocket
public class WebSocketHandler {
    ConnectionManager connectionManager= new ConnectionManager();
    GameService gameService;
    private String authToken;

    public WebSocketHandler(GameService gameService){
        this.gameService = gameService;
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
                case JOIN_PLAYER -> join(conn, msg);
                case JOIN_OBSERVER -> observe(conn, msg);
                case MAKE_MOVE -> makeMove(conn, msg);
                case LEAVE -> leave(conn, msg);
                case RESIGN -> resign(conn, msg);
            }
        } else {
            Connection.sendError(session.getRemote(), "unknown user");
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

    public void makeMove(Connection connection, String request) throws InvalidMoveException {
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

    private void join(Connection conn, String msg){

    }

    private void observe(Connection conn, String msg){

    }

}
