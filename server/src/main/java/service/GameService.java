package service;

import chess.ChessGame;
import dataAccess.MemoryGameDAO;
import dataAccess.SQLGameDAO;
import model.*;

import java.util.Collection;
import java.util.Random;
import com.google.gson.Gson;
import java.util.Objects;

public class GameService {

    SQLGameDAO gdao = new SQLGameDAO();

    public MResponse clearData(){
        if(gdao.clear()){
            return new MResponse(200, "");
        } else {
            return new MResponse(500, "{ \"message\": \"Error: description\" }");
        }
    }
    private int generateGameID(){
        //create gameID
        Random gameID = new Random();
        return gameID.nextInt(10000);
    }
    public GameID createGame(String gameName){
        if(gameName == null){
            return null;
        }
        int gameID = generateGameID();
        GameData game = new GameData(gameID, null, null, gameName, new ChessGame());
        gdao.createGame(game);
        return new GameID(gameID);
    }

    public GameData joinGame(int gameID, String playerColor, String username) throws ResponseException{
        GameData game = gdao.getGame(gameID);
        if (playerColor == null || playerColor.isEmpty()){
            return game;
        }
        else if (playerColor.equalsIgnoreCase("White")){
            if (game.whiteUsername() == null){
                gdao.addPlayer(gameID, "whiteUsername", username);
                game = gdao.getGame(gameID);
            } else {
                throw new ResponseException(403, "{ \"message\": \"Error: already taken\" }");
            }
        }
         else if (playerColor.equalsIgnoreCase("Black")){
            if (game.blackUsername() == null){
                gdao.addPlayer(gameID, "blackUsername", username);
                game = gdao.getGame(gameID);
            } else {
                throw new ResponseException(403, "{ \"message\": \"Error: already taken\" }");
            }
        }
         return game;
    }
    public GameList getGameList() {
        Collection<GameData> gameRecord = gdao.getGameRecord();
        return new GameList(gameRecord);
    }
}
