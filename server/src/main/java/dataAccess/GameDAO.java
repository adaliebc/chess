package dataAccess;

import model.GameData;
import model.GameInfo;
import service.ResponseException;

import java.util.Collection;

public interface GameDAO {
    public boolean clear();
    public boolean createGame(GameData game);
    public GameData getGame(int gameID);
    public void addPlayer(int gameID, String playerColor, String username) throws ResponseException;
    public void updateGame ( int gameID, String chessGame) throws ResponseException;
    public Collection<GameData> getGameRecord() throws ResponseException;
}
