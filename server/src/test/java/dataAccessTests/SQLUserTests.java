package dataAccessTests;

import dataAccess.SQLAuthDAO;
import dataAccess.SQLUserDAO;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;
import service.ResponseException;

import java.net.HttpURLConnection;

public class SQLUserTests {
    @Test
    @Order(1)
    @DisplayName("Clear Positive Test")
    public void clearPositiveTest() throws TestException {
        //add auth, clear data, verify auth, should return false
        try {
            SQLUserDAO sql = new SQLUserDAO();
            String username = "username";
            String password = "password";
            String email = "email@email.com";
            UserData user = new UserData(username, password, email);
            sql.createUser(user);
            sql.clear();

        } catch (ResponseException r) {
            Assertions.assertEquals(HttpURLConnection.HTTP_FORBIDDEN, r.statusCode(),
                    "Server response code was 403 Unable to Add User");
        }
    }
    @Test
    @Order(2)
    @DisplayName("Get User Positive Test")
    public void getUserPositiveTest() throws TestException {
        try {
            SQLUserDAO sql = new SQLUserDAO();
            String username = "username";
            String password = "password";
            String email = "email@email.com";
            UserData user = new UserData(username, password, email);
            sql.createUser(user);
            UserData gotUser = sql.getUser(username);

            Assertions.assertTrue(gotUser.username().equals(username) && gotUser.password().equals(password) && gotUser.email().equals(email));
        } catch (ResponseException r) {
            Assertions.assertNotEquals(HttpURLConnection.HTTP_FORBIDDEN, r.statusCode(),
                    "Server response code was 403 Unable to Add User");
        }
    }

    @Test
    @Order(3)
    @DisplayName("Get User Negative Test")
    public void getUserNegativeTest() throws TestException {
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

    @Test
    @Order(4)
    @DisplayName("Create User Positive Test")
    public void createUserPositiveTest() throws TestException {
        try {
            SQLUserDAO sql = new SQLUserDAO();
            String username = "username";
            String password = "password";
            String email = "email@email.com";
            UserData user = new UserData(username, password, email);
            sql.createUser(user);

            UserData gotUser = sql.getUser(username);

            Assertions.assertFalse(gotUser.username() == null && gotUser.password() == null && gotUser.email() == null);
        } catch (ResponseException r) {
            Assertions.assertNotEquals(HttpURLConnection.HTTP_FORBIDDEN, r.statusCode(),
                    "Server response code was 403 Unable to Add User");
        }
    }

    @Test
    @Order(5)
    @DisplayName("Create User Negative Test")
    public void createUserNegativeTest() throws TestException {
        try {
            SQLUserDAO sql = new SQLUserDAO();
            String username = "username";
            String password = "password";
            String email = "email@email.com";
            UserData user = new UserData(username, password, email);
            sql.createUser(user);

        } catch (ResponseException r) {
            Assertions.assertEquals(HttpURLConnection.HTTP_FORBIDDEN, r.statusCode(),
                    "Server response code was 403 Unable to Add User");
        }
    }
}
