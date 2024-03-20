package serviceTests;

import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;
import service.*;

import java.net.HttpURLConnection;

public class GameServiceTests {
    @Test
    @Order(1)
    @DisplayName("ClearDataTest")
    public void clearData() throws TestException {
        GameService gameService = new GameService();
        MResponse response = gameService.clearData();
        Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code(),
                "Server response code was not 200");
    }

    @Test
    @Order(2)
    @DisplayName("createPositiveGameTest")
    public void createPositiveGameTest() throws TestException {
        GameService gameService = new GameService();
        String gameName = "name";

        GameID gameID = gameService.createGame(gameName);
        Assertions.assertNotNull(gameID, "Good Result returned a gameID");
    }

    @Test
    @Order(3)
    @DisplayName("createNegativeGameTest")
    public void createNegativeGameTest() throws TestException {
        GameService gameService = new GameService();
        String gameName = null;

        GameID gameID = gameService.createGame(gameName);
        Assertions.assertNull(gameID, "Good Result returned null");
    }

    @Test
    @Order(4)
    @DisplayName("joinPositiveGameTest")
    public void joinPositiveGameTest() throws TestException {
        GameService gameService = new GameService();
        String gameName = "name";

        GameID gameID = gameService.createGame(gameName);

        try {
            GameData response = gameService.joinGame(gameID.gameID(), "White", "username");
            Assertions.assertNotNull(response,
                    "Server response code was not 200");

        } catch (ResponseException r) {
            Assertions.assertNotEquals(HttpURLConnection.HTTP_FORBIDDEN, r.statusCode(),
                    "Server response code was 403 Forbidden");
        }
    }

    @Test
    @Order(5)
    @DisplayName("joinNegativeGameTest")
    public void joinNegativeGameTest() throws TestException {
        GameService gameService = new GameService();
        String gameName = "name";

        GameID gameID = gameService.createGame(gameName);

        try {
            GameData response = gameService.joinGame(gameID.gameID(), "White", "username");
            Assertions.assertNotNull(response,
                    "Server response code was not 200");
            GameData secondResponse = gameService.joinGame(gameID.gameID(), "White", "username2");

        } catch (ResponseException r) {
            Assertions.assertEquals(HttpURLConnection.HTTP_FORBIDDEN, r.statusCode(),
                    "Server response code was not 403 Forbidden");
        }
    }
    @Test
    @Order(6)
    @DisplayName("listGameTest")
    public void listGameTest() throws TestException {
        GameService gameService = new GameService();
        GameList gameList = gameService.getGameList();
        Assertions.assertNotNull(gameList,
                "gameList was not empty");
    }
}