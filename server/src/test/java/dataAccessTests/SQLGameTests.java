package dataAccessTests;

import dataAccess.SQLGameDAO;
import dataAccess.SQLUserDAO;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import passoffTests.testClasses.TestException;
import service.ResponseException;

import java.net.HttpURLConnection;

public class SQLGameTests {
    @Test
    @Order(1)
    @DisplayName("Clear Positive Test")
    public void clearPositiveTest() throws TestException {
        SQLGameDAO sql = new SQLGameDAO();
        int gameID = 1234;
        String gameName = "gamename";
        GameInfo game = new GameInfo(gameID, null, null, gameName);
        sql.createGame(game);
        sql.clear();

        Assertions.assertTrue(sql.getGameRecord().isEmpty());
    }

    @Test
    @Order(2)
    @DisplayName("Create Game Positive Test")
    public void createGamePositiveTest() throws TestException {
        SQLGameDAO sql = new SQLGameDAO();
        int gameID = 1234;
        String gameName = "gamename";
        GameInfo game = new GameInfo(gameID, null, null, gameName);
        sql.createGame(game);

        GameInfo gotGame = sql.getGame(gameID);

        Assertions.assertEquals("gamename", gotGame.gameName());
    }

    @Test
    @Order(3)
    @DisplayName("Create Game Negative Test")
    public void createGameNegativeTest() throws TestException {
        SQLGameDAO sql = new SQLGameDAO();
        int gameID = 1234;
        String gameName = "game'name";
        GameInfo game = new GameInfo(gameID, null, null, gameName);
        sql.createGame(game);

        Assertions.assertFalse(sql.createGame(game));

    }

    @Test
    @Order(4)
    @DisplayName("Get Game Positive Test")
    public void getGamePositiveTest() throws TestException {
        SQLGameDAO sql = new SQLGameDAO();
        int gameID = 1234;
        String gameName = "gamename";
        GameInfo game = new GameInfo(gameID, null, null, gameName);
        sql.createGame(game);

        GameInfo gotGame = sql.getGame(gameID);

        Assertions.assertEquals("gamename", gotGame.gameName());
        Assertions.assertEquals(gotGame.gameID(), gameID);
    }

    @Test
    @Order(5)
    @DisplayName("Get Game Negative Test")
    public void getGameNegativeTest() throws TestException {
        SQLGameDAO sql = new SQLGameDAO();
        int gameID = 1234;
        String gameName = "gamename";
        GameInfo game = new GameInfo(gameID, null, null, gameName);
        sql.createGame(game);

        GameInfo gotGame = sql.getGame(9876);

        Assertions.assertNotEquals("gamename", gotGame.gameName());
        Assertions.assertNotEquals(gotGame.gameID(), gameID);
    }

    @Test
    @Order(6)
    @DisplayName("Join Game Positive Test")
    public void joinGamePositiveTest() throws TestException {
        try {
            SQLGameDAO sql = new SQLGameDAO();
            int gameID = 1234;
            String gameName = "gamename";
            GameInfo game = new GameInfo(gameID, null, null, gameName);
            sql.createGame(game);

            String playerColor = "whiteUsername";
            String username = "user";

            sql.addPlayer(gameID, playerColor, username);

            playerColor = "blackUsername";

            sql.addPlayer(gameID, playerColor, username);

            GameInfo gotGame = sql.getGame(1234);

            Assertions.assertEquals(gotGame.whiteUsername(), username);
            Assertions.assertEquals(gotGame.blackUsername(), username);
        } catch (ResponseException e) {
            Assertions.assertNotEquals(HttpURLConnection.HTTP_FORBIDDEN, e.statusCode(),
                    "Server response code was 403 Unable to Add User");
        }
    }

    @Test
    @Order(7)
    @DisplayName("Join Game Negative Test")
    public void joinGameNegativeTest() throws TestException {
        try {
            SQLGameDAO sql = new SQLGameDAO();
            int gameID = 1234;
            String gameName = "gamename";
            GameInfo game = new GameInfo(gameID, null, null, gameName);
            sql.createGame(game);

            String playerColor = "whiteUsername";
            String username = "us'er";

            sql.addPlayer(gameID, playerColor, username);

            GameInfo gotGame = sql.getGame(1234);

        } catch (ResponseException e) {
            Assertions.assertEquals(HttpURLConnection.HTTP_FORBIDDEN, e.statusCode(),
                    "Server response code was 403 Unable to Add User");
        }
    }

}
