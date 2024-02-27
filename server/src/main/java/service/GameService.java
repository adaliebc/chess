package service;

import dataAccess.MemoryGameDAO;
import model.*;

import java.util.Collection;
import java.util.Random;
import com.google.gson.Gson;
import java.util.Objects;

public class GameService {

    MemoryGameDAO gdao = new MemoryGameDAO();

    public MResponse clearData(){
        if(gdao.clear()){
            return new MResponse(200, "");
        } else {
            return new MResponse(500, "{ \"message\": \"Error: description\" }");
        }
    }
    public int generateGameID(){
        //create gameID
        Random gameID = new Random();
        return gameID.nextInt(10000);
    }
    public GameID createGame(String gameName){
        int gameID = generateGameID();
        GameInfo game = new GameInfo(gameID, "", "", gameName);
        gdao.createGame(game);
        return new GameID(gameID);
    }

    public MResponse joinGame(int gameID, String playerColor, String username) throws ResponseException{
        GameInfo game = gdao.getGame(gameID);
        if (playerColor == null || playerColor.isEmpty()){
            return new MResponse(200, "");
        }
        else if (playerColor.equalsIgnoreCase("White")){
            if (game.whiteUsername().isEmpty()){
                GameInfo newGame = new GameInfo(gameID, username, game.blackUsername(), game.gameName());
                gdao.addPlayer(game, newGame);
            } else {
                throw new ResponseException(403, "{ \"message\": \"Error: already taken\" }");
            }
        }
         else if (playerColor.equalsIgnoreCase("Black")){
            if (game.blackUsername().isEmpty()){
                GameInfo newGame = new GameInfo(gameID, game.whiteUsername(), username, game.gameName());
                gdao.addPlayer(game, newGame);
            } else {
                throw new ResponseException(403, "{ \"message\": \"Error: already taken\" }");
            }
        }
         return new MResponse(200, "");
    }
    public GameList getGameList() {
        Collection<GameInfo> gameRecord = gdao.getGameRecord();
        return new GameList(gameRecord);
    }
}
