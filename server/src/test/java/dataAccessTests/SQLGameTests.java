package dataAccessTests;

import chess.ChessGame;
import dataAccess.SQLGameDAO;
import dataAccess.SQLUserDAO;
import model.*;
import org.junit.jupiter.api.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import passoffTests.obfuscatedTestClasses.TestServerFacade;
import passoffTests.testClasses.TestException;
import passoffTests.testClasses.TestModels;
import server.Server;
import service.ResponseException;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class SQLGameTests {
    @Test
    @Order(1)
    @DisplayName("Clear Positive Test")
    public void clearPositiveTest() throws TestException {
        SQLGameDAO sql = new SQLGameDAO();
        sql.clear();
        int gameID = 1234;
        String gameName = "gamename";
        GameData game = new GameData(gameID, null, null, gameName, new ChessGame());
        sql.createGame(game);
        sql.clear();

        Assertions.assertTrue(sql.getGameRecord().isEmpty());
    }

    @Test
    @Order(2)
    @DisplayName("Create Game Positive Test")
    public void createGamePositiveTest() throws TestException {
        SQLGameDAO sql = new SQLGameDAO();
        sql.clear();
        int gameID = 1234;
        String gameName = "gamename";
        GameData game = new GameData(gameID, null, null, gameName, new ChessGame());
        sql.createGame(game);

        GameData gotGame = sql.getGame(gameID);

        Assertions.assertEquals("gamename", gotGame.gameName());
    }

    @Test
    @Order(3)
    @DisplayName("Create Game Negative Test")
    public void createGameNegativeTest() throws TestException {
        SQLGameDAO sql = new SQLGameDAO();
        sql.clear();
        int gameID = 1234;
        String gameName = "game'name";
        GameData game = new GameData(gameID, null, null, gameName, new ChessGame());
        sql.createGame(game);

        Assertions.assertFalse(sql.createGame(game));

    }

    @Test
    @Order(4)
    @DisplayName("Get Game Positive Test")
    public void getGamePositiveTest() throws TestException {
        SQLGameDAO sql = new SQLGameDAO();
        sql.clear();
        int gameID = 1234;
        String gameName = "gamename";
        GameData game = new GameData(gameID, null, null, gameName, new ChessGame());
        sql.createGame(game);

        GameData gotGame = sql.getGame(gameID);

        Assertions.assertEquals("gamename", gotGame.gameName());
        Assertions.assertEquals(gotGame.gameID(), gameID);
    }

    @Test
    @Order(5)
    @DisplayName("Get Game Negative Test")
    public void getGameNegativeTest() throws TestException {
        SQLGameDAO sql = new SQLGameDAO();
        sql.clear();
        int gameID = 1234;
        String gameName = "gamename";
        GameData game = new GameData(gameID, null, null, gameName, new ChessGame());
        sql.createGame(game);

        GameData gotGame = sql.getGame(9876);

        Assertions.assertNotEquals("gamename", gotGame.gameName());
        Assertions.assertNotEquals(gotGame.gameID(), gameID);
    }

    @Test
    @Order(6)
    @DisplayName("Join Game Positive Test")
    public void joinGamePositiveTest() throws TestException {
        try {
            SQLGameDAO sql = new SQLGameDAO();
            sql.clear();
            int gameID = 1234;
            String gameName = "gamename";
            GameData game = new GameData(gameID, null, null, gameName, new ChessGame());
            sql.createGame(game);

            String playerColor = "whiteUsername";
            String username = "user";

            sql.addPlayer(gameID, playerColor, username);

            playerColor = "blackUsername";

            sql.addPlayer(gameID, playerColor, username);

            GameData gotGame = sql.getGame(1234);

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
            sql.clear();
            int gameID = 1234;
            String gameName = "gamename";
            GameData game = new GameData(gameID, null, null, gameName, new ChessGame());
            sql.createGame(game);

            String playerColor = "whiteUsername";
            String username = "us'er";

            sql.addPlayer(gameID, playerColor, username);

            GameData gotGame = sql.getGame(1234);

        } catch (ResponseException e) {
            Assertions.assertEquals(HttpURLConnection.HTTP_FORBIDDEN, e.statusCode(),
                    "Server response code was 403 Unable to Add User");
        }
    }

    @Test
    @Order(8)
    @DisplayName("Get GameRecord Positive Test")
    public void getGameRecordPositiveTest() throws TestException {
        Collection<GameData> gameRecord = new ArrayList<>();
        SQLGameDAO sql = new SQLGameDAO();
        sql.clear();
        int gameID = 1234;
        String gameName = "gamename";
        GameData game = new GameData(gameID, null, null, gameName, new ChessGame());
        sql.createGame(game);
        gameRecord.add(game);

        Collection<GameData> sqlGameRecord = sql.getGameRecord();

        Assertions.assertEquals(gameRecord, sqlGameRecord);
    }

    @Test
    @Order(9)
    @DisplayName("Get GameRecord Negative Test")
    public void getGameRecordNegativeTest() {
        Collection<GameData> gameRecord = new ArrayList<>();
        SQLGameDAO sql = new SQLGameDAO();
        sql.clear();
        int gameID = 1234;
        String gameName = "gamename";
        GameData game = new GameData(gameID, null, null, gameName, new ChessGame());
        sql.createGame(game);
        gameRecord.add(game);

        sql.clear();

        Collection<GameData> sqlGameRecord = sql.getGameRecord();

        Assertions.assertNotEquals(gameRecord, sqlGameRecord);
    }

}
