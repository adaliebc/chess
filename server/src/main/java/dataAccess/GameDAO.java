package dataAccess;

public class GameDAO {
    //clear data
    public boolean clear(){
        return gameRecord.removeAll(gameRecord);
    }
    /* createGame: Create a new game.
    getGame: Retrieve a specified game with the given game ID.
    listGames: Retrieve all games.
    updateGame: Updates a chess game. It should replace the chess game string corresponding to a given gameID.
     This is used when players join a game or when a move is made.*/
}
