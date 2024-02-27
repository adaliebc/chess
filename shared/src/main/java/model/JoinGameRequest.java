package model;

public record JoinGameRequest(String playerColor, int gameID) {
    //{ "playerColor":"WHITE/BLACK", "gameID": 1234 }
}
