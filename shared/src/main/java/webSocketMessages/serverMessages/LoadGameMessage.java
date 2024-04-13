package webSocketMessages.serverMessages;

import chess.ChessGame;

public class LoadGameMessage extends ServerMessage{
    public String game;
    public LoadGameMessage(String game){this.game = game;}
}
