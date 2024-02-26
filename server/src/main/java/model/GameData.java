package model;

import chess.*;

public class GameData {
    //need GameID, WhiteU,BlackU, GameName, game
    record userRecord(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
        //create new game
        //find games for username
        //find game data for gameID
    }
}
