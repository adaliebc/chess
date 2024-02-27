package dataAccess;

import model.AuthData;
import model.GameInfo;
import model.UserData;

import java.util.Collection;
import java.util.HashSet;

public class MemoryGameDAO implements GameDAO{
    //may change from GameInfo to GameData once I am able to add the implemented game
    Collection<GameInfo> gameRecord = new HashSet<GameInfo>();
    public boolean clear(){
        gameRecord.clear();
        return true;
    }
    public boolean createGame(GameInfo game){
        gameRecord.add(game);
        return true;

    }
    public GameInfo getGame(int gameID) {
        for (GameInfo user : gameRecord){
            if (user.gameID() == gameID){
                return user;
            }
        }
        return null;
    }
    public void addPlayer(GameInfo oldGame, GameInfo newGame){
        gameRecord.remove(oldGame);
        gameRecord.add(newGame);

    }

    public Collection<GameInfo> getGameRecord() {
        return gameRecord;
    }
    /* createGame: Create a new game.
    getGame: Retrieve a specified game with the given game ID.
    listGames: Retrieve all games.
    updateGame: Updates a chess game. It should replace the chess game string corresponding to a given gameID.
     This is used when players join a game or when a move is made.*/

}