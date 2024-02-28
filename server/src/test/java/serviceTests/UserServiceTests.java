package serviceTests;

import model.AuthData;
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
}
