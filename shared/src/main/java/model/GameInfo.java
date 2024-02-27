package model;

public record GameInfo(int gameID, String whiteUsername, String blackUsername, String gameName) {
    //May change to GameData when we want to implement the game instance
}
