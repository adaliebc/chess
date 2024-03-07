package dataAccess;

import model.GameInfo;
import service.ResponseException;

import java.util.Collection;

public interface GameDAO {
    public boolean clear();
    public boolean createGame(GameInfo game);
    public GameInfo getGame(int gameID);
    public void addPlayer(int gameID, String playerColor, String username) throws ResponseException;
    public Collection<GameInfo> getGameRecord() throws ResponseException;
}
