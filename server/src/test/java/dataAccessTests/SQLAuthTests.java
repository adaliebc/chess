package dataAccessTests;

import dataAccess.SQLAuthDAO;
import model.AuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;
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
            sql.clear();
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
                sql.clear();
                String token = "token";
                String username = "username";
                AuthData user = new AuthData(token, username);
                sql.addToken(user);

                Assertions.assertTrue(sql.verifyAuth(token));
            } catch (ResponseException r) {
                Assertions.assertNotEquals(HttpURLConnection.HTTP_FORBIDDEN, r.statusCode(),
                        "Server response code was 403 Unable to Add User");
            }
        }

    @Test
    @Order(3)
    @DisplayName("Add Token Negative Test")
    public void addTokenNegativeTest() throws TestException {
        try {
            SQLAuthDAO sql = new SQLAuthDAO();
            sql.clear();
            String token = "token";
            String username = "user'name";
            AuthData user = new AuthData(token, username);
            sql.addToken(user);
        } catch (ResponseException r) {
            Assertions.assertEquals(HttpURLConnection.HTTP_FORBIDDEN, r.statusCode(),
                    "Server response code was 403 Unable to Add User");
        }
    }
    @Test
    @Order(3)
    @DisplayName("Get Token Positive Test")
    public void getTokenPositiveTest() throws TestException {
        //add token, verify auth, should return true
        try {
            SQLAuthDAO sql = new SQLAuthDAO();
            sql.clear();
            String token = "token";
            String username = "username";
            AuthData user = new AuthData(token, username);
            sql.addToken(user);
            AuthData gotUser = sql.getAuth(token);

            Assertions.assertTrue(gotUser.username().equals(username) && gotUser.authToken().equals(token));
        } catch (ResponseException r) {
            Assertions.assertNotEquals(HttpURLConnection.HTTP_FORBIDDEN, r.statusCode(),
                    "Server response code was 403 Unable to Add User");
        }
    }

    @Test
    @Order(4)
    @DisplayName("Get Token Negative Test")
    public void getTokenNegativeTest() throws TestException {
        try {
            SQLAuthDAO sql = new SQLAuthDAO();
            sql.clear();
            String token = "token";
            String username = "username";
            AuthData user = new AuthData(token, username);
            sql.addToken(user);

            token = "tokenzzzz";
            AuthData gotUser = sql.getAuth(token);

            Assertions.assertTrue(gotUser.username() == null && gotUser.authToken() == null);
        } catch (ResponseException r) {
            Assertions.assertNotEquals(HttpURLConnection.HTTP_FORBIDDEN, r.statusCode(),
                    "Server response code was 403 Unable to Add User");
        }
    }

    @Test
    @Order(5)
    @DisplayName("Delete Token Positive Test")
    public void deleteTokenPositiveTest() throws TestException {
        try {
            SQLAuthDAO sql = new SQLAuthDAO();
            sql.clear();
            String token = "token";
            String username = "username";
            AuthData user = new AuthData(token, username);
            sql.addToken(user);
            sql.deleteToken(user);

            Assertions.assertFalse(sql.verifyAuth(token));
        } catch (ResponseException r) {
            Assertions.assertNotEquals(HttpURLConnection.HTTP_FORBIDDEN, r.statusCode(),
                    "Server response code was 403 Unable to Add User");
        }
    }

    @Test
    @Order(6)
    @DisplayName("Delete Token Negative Test")
    public void deleteTokenNegativeTest() throws TestException {
        try {
            SQLAuthDAO sql = new SQLAuthDAO();
            sql.clear();
            String token = "token";
            String username = "username";
            AuthData user = new AuthData(token, username);
            sql.addToken(user);

            token = "tokenzzzz";
            AuthData badUser = new AuthData(token, username);
            sql.deleteToken(badUser);

        } catch (ResponseException r) {
            Assertions.assertEquals(HttpURLConnection.HTTP_FORBIDDEN, r.statusCode(),
                    "Server response code was 403 Unable to Delete User");
        }
    }
    @Test
    @Order(7)
    @DisplayName("Verify Token Positive Test")
    public void verifyTokenPositiveTest() throws TestException {
        try {
            SQLAuthDAO sql = new SQLAuthDAO();
            sql.clear();
            String token = "token";
            String username = "username";
            AuthData user = new AuthData(token, username);
            sql.addToken(user);

            Assertions.assertTrue(sql.verifyAuth(token));
        } catch (ResponseException r) {
            Assertions.assertNotEquals(HttpURLConnection.HTTP_FORBIDDEN, r.statusCode(),
                    "Server response code was 403 Unable to Add User");
        }
    }

    @Test
    @Order(8)
    @DisplayName("Verify Token Negative Test")
    public void verifyTokenNegativeTest() throws TestException {
        try {
            SQLAuthDAO sql = new SQLAuthDAO();
            sql.clear();
            String token = "token";
            String username = "username";
            AuthData user = new AuthData(token, username);
            sql.addToken(user);

            token = "tokenzzzz";

            Assertions.assertFalse(sql.verifyAuth(token));
        } catch (ResponseException r) {
            Assertions.assertNotEquals(HttpURLConnection.HTTP_FORBIDDEN, r.statusCode(),
                    "Server response code was 403 Unable to Add User");
        }
    }
}
