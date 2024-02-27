package dataAccess;

import model.GameInfo;

import java.util.Collection;

public interface GameDAO {
    public boolean clear();
    public boolean createGame(GameInfo game);
    public GameInfo getGame(int gameID);
    public void addPlayer(GameInfo oldGame, GameInfo newGame);
    public Collection<GameInfo> getGameRecord();
}
