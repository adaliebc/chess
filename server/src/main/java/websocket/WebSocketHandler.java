package websocket;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import com.google.gson.Gson;
//import dataaccess.DataAccess;
//import exception.ResponseException;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.GameService;
import service.ResponseException;
import service.UserService;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import javax.management.Notification;
//import webSocketMessages.Action;
//import webSocketMessages.Notification;

@WebSocket
public class WebSocketHandler {
    ConnectionManager connectionManager= new ConnectionManager();
    GameService gameService;
    UserService userService;
    private String authToken;
    private ChessGame chessGame;
    private boolean gameEnded = false;

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
                case MAKE_MOVE -> makeMove(conn, command);
                case LEAVE -> leave(conn, command);
                case RESIGN -> resign(conn, command);
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
    public void makeMove(Connection conn, UserGameCommand command) {
        if(gameEnded){
            ServerMessage message = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            message.setErrorMessage("Cannot make move, game is over");
            try {
                conn.send(new Gson().toJson(message));
            } catch(Exception E){
                System.out.println(E.getMessage());
            }
            return;
        }
        //request needs to have a chess game and a move object
        ServerMessage message = null;
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        ServerMessage broadcastLoadGame = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
        int gameID = command.getGameID();
        String authToken = command.getAuthString();
        String username = userService.getUsername(authToken);
        ChessGame.TeamColor playerColor = getPlayerColor(gameID, username);

        if(playerColor == null) {
            message = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            message.setErrorMessage("Observers cannot make moves");
            //notification.setMessage("Observers cannot make moves");
            //sendMessageAndNotifications(conn, authToken, notification, message);
            try {
                conn.send(new Gson().toJson(message));
            } catch(Exception E){
                System.out.println(E.getMessage());
            }
            return;
        } else if(!checkAvailability(gameID, playerColor, username)) {
            message = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            message.setErrorMessage("You cannot make a move for this color");
            try {
                conn.send(new Gson().toJson(message));
            } catch(Exception E){
                System.out.println(E.getMessage());
            }
            return;
        } else if(chessGame.getBoard().getPiece(command.getMove().getStartPosition()).getTeamColor() != playerColor) {
            message = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            message.setErrorMessage("You cannot make a move for this color");
            try {
                conn.send(new Gson().toJson(message));
            } catch(Exception E){
                System.out.println(E.getMessage());
            }
            return;
        }
        ChessMove move = command.getMove();
        GameData gameData = gameService.getGame(gameID);
        chessGame.setBoard(gameData.game());
        try {
            chessGame.makeMove(move);
        }
        catch(InvalidMoveException e){
            message = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            message.setErrorMessage("Error: Not a valid move");
            //notification.setMessage(username + " made an invalid move");
            //sendMessageAndNotifications(conn, authToken, notification, message);
            try {
                conn.send(new Gson().toJson(message));
            } catch(Exception E){
                System.out.println(E.getMessage());
            }
            return;
        }
        // update game (game)
        try {
            gameService.updateGame(gameID, chessGame.getBoard());
        }
        catch(ResponseException E) {
            message = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            message.setErrorMessage("Error: Game could not be updated");
            //notification.setMessage("Game could not be updated");
            //sendMessageAndNotifications(conn, authToken, notification, message);
            try {
                conn.send(new Gson().toJson(message));
            } catch(Exception e){
                System.out.println(e.getMessage());
            }
            return;
        }
        if(chessGame.isInCheckmate(playerColor)){
            message = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
            message.setMessage("You are in checkmate");
            message.setGame(chessGame);
            notification.setMessage(username + " is in checkmate");
            //sendMessageAndNotifications(conn, authToken, notification, message);
            //play checkmate script
        } else if (chessGame.isInStalemate(playerColor)){
            message = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
            message.setMessage("You are in stalemate");
            message.setGame(chessGame);
            notification.setMessage(username + " is in checkmate");
            //sendMessageAndNotifications(conn, authToken, notification, message);
            //play stalemate script
        }
        else if(chessGame.isInCheck(playerColor)){
            message = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
            message.setMessage("You are in check");
            message.setGame(chessGame);
            notification.setMessage(username + " is in check");
            //sendMessageAndNotifications(conn, authToken, notification, message);
        } else {
            message = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
            message.setMessage("You successfully made a move");
            message.setGame(chessGame);
            notification.setMessage(username + " made a move");
            //sendMessageAndNotifications(conn, authToken, notification, message);
        }
        broadcastLoadGame.setGame(chessGame);
        sendMessageAndNotifications(conn, authToken, broadcastLoadGame, message);

        try {
            connectionManager.broadcast(authToken, notification);
        } catch(Exception E){
            System.out.println(E.getMessage());
        }
    }

    public void resign(Connection conn, UserGameCommand msg){
        ServerMessage message;
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        String authToken = msg.getAuthString();
        String username = userService.getUsername(authToken);
        if(!checkAvailability(msg.getGameID(), msg.getPlayerColor(), username)){
            message = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            message.setErrorMessage("Observers cannot resign, please leave the game");
            try {
                conn.send(new Gson().toJson(message));
            } catch(Exception e){
                System.out.println(e.getMessage());
            }
            return;
        }
        else {
            message = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
            message.setMessage("You have successfully resigned");
            notification.setMessage(username + "has successfully resigned");
            //end the game
            gameEnded = true;
            //mark other player as winner
            sendMessageAndNotifications(conn, authToken, notification, message);
            connectionManager.remove(authToken);
        }
    }

    public void leave(Connection conn, UserGameCommand msg){
        ServerMessage message;
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        String authToken = msg.getAuthString();
        String username = userService.getUsername(authToken);
            message = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
            message.setMessage("You have successfully left the game");
            notification.setMessage(username + "has successfully left");
            sendMessageAndNotifications(conn, authToken, notification, message);
            connectionManager.remove(authToken);
    }

    private void join(Connection conn, UserGameCommand msg){
        ServerMessage message;
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        String authToken = msg.getAuthString();
        String username = userService.getUsername(authToken);
        int gameID = msg.getGameID();
        ChessBoard game = gameService.getGame(gameID).game();
        ChessGame.TeamColor playerColor = msg.getPlayerColor();
            if(checkValidTeam(gameID, playerColor)) {
                if (checkAvailability(gameID, playerColor, username)) {
                    message = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
                    this.chessGame = new ChessGame();
                    chessGame.setBoard(game);
                    chessGame.setTeamTurn(ChessGame.TeamColor.WHITE);
                    message.setGame(chessGame);
                    notification.setMessage(username + " joined the game!");
                    if (playerColor == ChessGame.TeamColor.WHITE) {
                        message.setMessage("white");
                    } else {
                        message.setMessage("black");
                    }
                } else {
                    message = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
                    message.setErrorMessage("Error: Color is already taken");
                    notification.setMessage(username + " could not join the game!");
                }
            } else {
                message = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
                message.setErrorMessage("Error: Game could not be joined");
                notification.setMessage(username + " could not join the game!");
            }
        sendMessageAndNotifications(conn, authToken, notification, message);
    }

    private void observe(Connection conn, UserGameCommand msg){
        ServerMessage message;
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        String authToken = msg.getAuthString();
        String username = userService.getUsername(authToken);
        int gameID = msg.getGameID();
        if(checkValidGame(gameID)) {
            if (username != null) {
                message = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
                ChessBoard game = gameService.getGame(gameID).game();
                ChessGame chessGame = new ChessGame();
                chessGame.setBoard(game);
                message.setGame(chessGame);
                notification.setMessage(username + " joined the game as an observer!");
            } else {
                message = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
                message.setErrorMessage("Error: Not Authorized");
                notification.setMessage("Unknown could not join the game!");
            }
        } else {
            message = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            message.setErrorMessage("Error: Game could not be joined");
            notification.setMessage("Observer could not join the game!");
        }
        sendMessageAndNotifications(conn, authToken, notification, message);
    }

    private Boolean checkAvailability(int gameID, ChessGame.TeamColor playerColor, String username){
        GameData gameData = gameService.getGame(gameID);
        if(playerColor == ChessGame.TeamColor.WHITE){
            return gameData.whiteUsername().equals(username);
        } else if(playerColor == ChessGame.TeamColor.BLACK){
            return gameData.blackUsername().equals(username);
        }
        return false;
    }

    private Boolean checkValidTeam(int gameID, ChessGame.TeamColor playerColor){
        GameData gameData = gameService.getGame(gameID);
        if(playerColor == ChessGame.TeamColor.WHITE){
            return gameData.whiteUsername() != null;
        } else if(playerColor == ChessGame.TeamColor.BLACK){
            return gameData.blackUsername() != null;
        }
        return false;
    }

    private Boolean checkValidGame(int gameID){
        GameData gameData = gameService.getGame(gameID);
        return gameData.gameID() != 0;
    }

    private void sendMessageAndNotifications(Connection conn, String authToken, ServerMessage notification, ServerMessage message){
        try {
            connectionManager.broadcast(authToken, notification);
            conn.send(new Gson().toJson(message));
        } catch(Exception E){
            System.out.println(E.getMessage());
        }
    }
    private ChessGame.TeamColor getPlayerColor(int gameID, String username){
        GameData gameData = gameService.getGame(gameID);
        if(gameData.whiteUsername().equals(username)){
            return ChessGame.TeamColor.WHITE;
        } else if (gameData.blackUsername().equals(username)){
            return ChessGame.TeamColor.BLACK;
        } else{
            return null;
        }
    }
}
