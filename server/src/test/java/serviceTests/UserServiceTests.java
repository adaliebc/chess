package serviceTests;

import model.AuthData;
import model.LoginRequest;
import model.MResponse;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;
import passoffTests.testClasses.TestModels;
import service.ResponseException;
import service.UserService;

import java.net.HttpURLConnection;
import java.util.Locale;

public class UserServiceTests {
    @Test
    @Order(1)
    @DisplayName("RegisterTest")
    public void registerTest() throws TestException {
        //add user
        UserService userService = new UserService();
        UserData user = new UserData("aleadyExists", "password", "email@email.com");

        try {
            AuthData first = userService.register(user);
            Assertions.assertNotNull(first.authToken(), "Good Result returned a Token");

            first = userService.register(user);
        } catch (ResponseException r) {
            Assertions.assertEquals(HttpURLConnection.HTTP_FORBIDDEN, r.statusCode(),
                    "Server response code was 403 AlreadyTaken");
        }

    }
    @Test
    @Order(2)
    @DisplayName("LoginTest")
    public void loginTest() throws TestException {
        //add user
        UserService userService = new UserService();
        userService.clearData();
        UserData user = new UserData("username", "password", "email@email.com");
        LoginRequest rightPassword = new LoginRequest("username", "password");
        LoginRequest wrongPassword = new LoginRequest("username", "invalid");

        try {
            userService.register(user);

            AuthData rightPasswordToken = userService.login(rightPassword);
            Assertions.assertNotNull(rightPasswordToken.authToken(), "Good Result returned a Token");

            userService.login(wrongPassword);

        } catch (ResponseException r) {
            Assertions.assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, r.statusCode(),
                    "Server response code was not 401 Unauthorized");
        }

    }

    @Test
    @Order(3)
    @DisplayName("LogoutNegativeTest")
    public void logoutNegativeTest() throws TestException {
        UserService userService = new UserService();
        AuthData nullToken = new AuthData("faketoken", "username");

        try {

            userService.logout(nullToken.authToken());


        } catch (ResponseException r) {
            Assertions.assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, r.statusCode(),
                    "Server response code was not 401 Unauthorized");
        }

    }
    @Test
    @Order(4)
    @DisplayName("LogoutPositiveTest")
    public void logoutPositiveTest() throws TestException {
        //add user
        UserService userService = new UserService();
        UserData user = new UserData("username", "password", "email@email.com");

        try {
            AuthData token = userService.register(user);

            Assertions.assertAll(() -> userService.logout(token.authToken()));

        } catch (ResponseException r) {
            Assertions.assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, r.statusCode(),
                    "Server response code was 401 Unauthorized");
        }

    }
    @Test
    @Order(5)
    @DisplayName("VerifyPositiveTokenTest")
    public void verifyPositiveTokenTest() throws TestException {
        //add user
        UserService userService = new UserService();
        userService.clearData();
        UserData user = new UserData("username", "password", "email@email.com");

        try {
            AuthData token = userService.register(user);

            Assertions.assertAll(() -> userService.verifyToken(token.authToken()));

        } catch (ResponseException r) {
            Assertions.assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, r.statusCode(),
                    "Server response code was 401 Unauthorized");
        }

    }
    @Test
    @Order(6)
    @DisplayName("VerifyNegativeTokenTest")
    public void verifyNegativeTokenTest() throws TestException {
        //add user
        UserService userService = new UserService();
        String token = "thisIsAFakeToken";

        try {
            userService.verifyToken(token);

        } catch (ResponseException r) {
            Assertions.assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, r.statusCode(),
                    "Server response code was 401 Unauthorized");
        }

    }
    @Test
    @Order(7)
    @DisplayName("ClearDataTest")
    public void clearData() throws TestException {
        UserService userService = new UserService();
        MResponse response = userService.clearData();
        Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code(),
                "Server response code was not 200");
    }
    @Test
    @Order(8)
    @DisplayName("getPositiveUserTest")
    public void getPositiveUserTest() throws TestException {
        //add user
        UserService userService = new UserService();
        userService.clearData();
        UserData user = new UserData("username", "password", "email@email.com");

        try {
            AuthData token = userService.register(user);
            String username = userService.getUsername(token.authToken());
            Assertions.assertNotNull(username, "Good Result returned a Username");

        } catch (ResponseException r) {
            Assertions.assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, r.statusCode(),
                    "Server response code was not 401 Unauthorized");
        }

    }
    @Test
    @Order(9)
    @DisplayName("getNegativeUserTest")
    public void getNegativeUserTest() throws TestException {
        //add user
        UserService userService = new UserService();
        String token = "fakeToken";

            String username = userService.getUsername(token);
            Assertions.assertNull(username, "Good Result returned a Username");

    }
}
