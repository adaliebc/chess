package dataAccess;

import model.GameData;
import model.GameInfo;
import service.ResponseException;

import java.util.Collection;
import java.util.HashSet;

public class MemoryGameDAO implements GameDAO{
    //may change from GameInfo to GameData once I am able to add the implemented game
    Collection<GameData> gameRecord = new HashSet<>();
    public boolean clear(){
        gameRecord.clear();
        return true;
    }
    public boolean createGame(GameData game){
        gameRecord.add(game);
        return true;

    }
    public GameData getGame(int gameID) {
        for (GameData user : gameRecord){
            if (user.gameID() == gameID){
                return user;
            }
        }
        return null;
    }
    public void addPlayer(int gameID, String playerColor, String username){
        //note, this no longer works
        var placeholder = 0;
    }


    public void updateGame(int gameID, String chessGame) throws ResponseException {
        //note, this no longer works
        var placeholder = 0;
    }

    public Collection<GameData> getGameRecord() {
        return gameRecord;
    }
    /* createGame: Create a new game.
    getGame: Retrieve a specified game with the given game ID.
    listGames: Retrieve all games.
    updateGame: Updates a chess game. It should replace the chess game string corresponding to a given gameID.
     This is used when players join a game or when a move is made.*/

}