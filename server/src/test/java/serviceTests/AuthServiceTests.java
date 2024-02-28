package serviceTests;

import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;
import service.AuthService;
import service.ResponseException;
import service.UserService;

import java.net.HttpURLConnection;

public class AuthServiceTests {
    @Test
    @Order(1)
    @DisplayName("CreateAuthTokenPositiveTest")
    public void createAuthTokenPositiveTest() throws TestException {
        AuthService authService = new AuthService();
        String username = "username";
        AuthData authData = authService.createAuthToken(username);
        Assertions.assertNotNull(authData, "Good Result returned AuthData");
    }

    @Test
    @Order(2)
    @DisplayName("CreateAuthTokenNegativeTest")
    public void createAuthTokenNegativeTest() throws TestException {
        AuthService authService = new AuthService();
        String username = null;
        AuthData authData = authService.createAuthToken(username);
        Assertions.assertNull(authData, "Good Result returned null");
    }
}
