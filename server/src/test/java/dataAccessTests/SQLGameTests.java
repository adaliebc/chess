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
        //add auth, clear data, verify auth, should return false
        try {
            SQLGameDAO sql = new SQLGameDAO();
            String token = "token";
            String username = "username";
            AuthData user = new AuthData(token, username);
            sql.addToken(user);
            sql.clear();

            Assertions.assertFalse(sql.verifyAuth(token));
        } catch (ResponseException r) {
            Assertions.assertEquals(HttpURLConnection.HTTP_FORBIDDEN, r.statusCode(),
                    "Server response code was 403 Unable to Add User");
        }
    }
}
