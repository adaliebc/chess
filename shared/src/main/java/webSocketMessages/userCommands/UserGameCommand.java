package webSocketMessages.userCommands;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;

import java.util.Objects;

/**
 * Represents a command a user can send the server over a websocket
 *
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class UserGameCommand {

    //we create the commands here in facade and sends it to handler
    //handler gets it and does the thing

    public UserGameCommand(String authToken) {
        this.authToken = authToken;
    }

    public enum CommandType {
        JOIN_PLAYER,
        JOIN_OBSERVER,
        MAKE_MOVE,
        LEAVE,
        RESIGN
    }

    protected CommandType commandType;

    private final String authToken;
    private String command;
    private int gameID;
    private ChessGame.TeamColor playerColor;
    private ChessMove move;

    public String getAuthString() {
        return authToken;
    }

    public CommandType getCommandType() {
        return this.commandType;
    }
    public String getCommand() {
        return this.command;
    }
    public void setCommand(String command) {
        this.command = command;
    }
    public void setCommandType(CommandType commandType){
        this.commandType = commandType;
    }
    public int getGameID(){
        return this.gameID;
    }
    public void setGameID(int gameID){this.gameID = gameID;}
    public ChessGame.TeamColor getPlayerColor(){return this.playerColor;}

    public void setPlayerColor(ChessGame.TeamColor playerColor) {
        this.playerColor = playerColor;
    }
    public ChessMove getMove() {
        return move;
    }

    public void setMove(ChessMove move) {
        this.move = move;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserGameCommand))
            return false;
        UserGameCommand that = (UserGameCommand) o;
        return getCommandType() == that.getCommandType() && Objects.equals(getAuthString(), that.getAuthString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommandType(), getAuthString());
    }
}