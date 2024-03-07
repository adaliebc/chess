package dataAccessTests;

import dataAccess.SQLGameDAO;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
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
        GameInfo game = new GameInfo(1234, null, null, gameName);
        sql.createGame(game);
        sql.clear();

        Assertions.assertTrue(sql.getGameRecord().isEmpty());
    }

}
