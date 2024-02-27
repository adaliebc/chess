package service;

import dataAccess.MemoryGameDAO;
import model.*;
import java.util.Random;

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
        GameInfo game = new GameInfo(gameID, null, null, gameName);
        gdao.createGame(game);
        return new GameID(gameID);
    }
}
