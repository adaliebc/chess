package model;

import java.util.Objects;

public record GameInfo(int gameID, String whiteUsername, String blackUsername, String gameName) {
    //May change to GameData when we want to implement the game instance

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameInfo gameInfo = (GameInfo) o;
        return gameID == gameInfo.gameID && Objects.equals(whiteUsername, gameInfo.whiteUsername) && Objects.equals(blackUsername, gameInfo.blackUsername) && Objects.equals(gameName, gameInfo.gameName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameID, whiteUsername, blackUsername, gameName);
    }
}
