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

    //sends commands to websocket server
    //we never pass it a command type

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

    public String getAuthString() {
        return authToken;
    }

    public CommandType getCommandType() {
        return this.commandType;
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

    //not sure if this is the right place to do this
    public void makeMove(ChessMove move, ChessGame game) throws InvalidMoveException {
        game.makeMove(move);
        // update game (game)
        //send message through websocket
    }

    public void resign(String username){
        //end the game
        //mark other player as winner
    }

    public void leave(){
        //call handler and have them remove the user's connection
        //send the message
    }
}