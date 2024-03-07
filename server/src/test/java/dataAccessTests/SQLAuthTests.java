package dataAccessTests;

import dataAccess.SQLAuthDAO;
import model.AuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;
import service.AuthService;
import service.ResponseException;

import java.net.HttpURLConnection;

public class SQLAuthTests {
    @Test
    @Order(1)
    @DisplayName("Clear Positive Test")
    public void clearPositiveTest() throws TestException {
        //add auth, clear data, verify auth, should return false
        try {
            SQLAuthDAO sql = new SQLAuthDAO();
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

        @Test
        @Order(2)
        @DisplayName("Add Token Positive Test")
        public void addTokenPositiveTest() throws TestException {
            //add token, verify auth, should return true
            try {
                SQLAuthDAO sql = new SQLAuthDAO();
                String token = "token";
                String username = "username";
                AuthData user = new AuthData(token, username);
                sql.addToken(user);

                Assertions.assertTrue(sql.verifyAuth(token));
            } catch (ResponseException r) {
                Assertions.assertEquals(HttpURLConnection.HTTP_FORBIDDEN, r.statusCode(),
                        "Server response code was 403 Unable to Add User");
            }
        }
}
