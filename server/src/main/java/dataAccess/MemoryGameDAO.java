package dataAccess;

import java.util.Collection;
import java.util.HashSet;

public class MemoryGameDAO implements GameDAO{
    Collection<Record> gameRecord = new HashSet<Record>();
    public boolean clear(){
        gameRecord.clear();
        return true;
    }
    /* createGame: Create a new game.
    getGame: Retrieve a specified game with the given game ID.
    listGames: Retrieve all games.
    updateGame: Updates a chess game. It should replace the chess game string corresponding to a given gameID.
     This is used when players join a game or when a move is made.*/

}