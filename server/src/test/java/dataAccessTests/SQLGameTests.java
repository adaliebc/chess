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
        try {
            SQLUserDAO sql = new SQLUserDAO();
            String username = "username";
            String password = "password";
            String email = "email@email.com";
            UserData user = new UserData(username, password, email);
            sql.createUser(user);

            username = "uzzzername";
            UserData gotUser = sql.getUser(username);

            Assertions.assertTrue(gotUser.username() == null && gotUser.password() == null && gotUser.email() == null);
        } catch (ResponseException r) {
            Assertions.assertNotEquals(HttpURLConnection.HTTP_FORBIDDEN, r.statusCode(),
                    "Server response code was 403 Unable to Add User");
        }
    }

}
