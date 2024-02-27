package dataAccess;

import model.GameInfo;

public interface GameDAO {
    public boolean clear();
    public boolean createGame(GameInfo game);
}
